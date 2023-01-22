package mekanism.common.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.EnumColor;
import mekanism.api.IConfigurable;
import mekanism.api.MekanismConfig.general;
import mekanism.api.MekanismConfig.usage;
import mekanism.common.Upgrade;
import mekanism.common.base.IRedstoneControl;
import mekanism.common.base.ISustainedTank;
import mekanism.common.base.ITankManager;
import mekanism.common.base.IUpgradeTile;
import mekanism.common.integration.IComputerIntegration;
import mekanism.common.security.ISecurityTile;
import mekanism.common.tile.component.TileComponentSecurity;
import mekanism.common.tile.component.TileComponentUpgrade;
import mekanism.common.util.ChargeUtils;
import mekanism.common.util.FluidContainerUtils;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.PipeUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityElectricPump extends TileEntityElectricBlock
    implements IFluidHandler, ISustainedTank, IConfigurable, IRedstoneControl,
               IUpgradeTile, ITankManager, IComputerIntegration, ISecurityTile {
    /** This pump's tank */
    public FluidTank fluidTank = new FluidTank(10000);

    /** The type of fluid this pump is pumping */
    public Fluid activeType;

    public boolean suckedLastOperation;

    /** How much energy this machine consumes per-tick. */
    public double BASE_ENERGY_PER_TICK = usage.electricPumpUsage;

    public double energyPerTick = BASE_ENERGY_PER_TICK;

    /** How many ticks it takes to run an operation. */
    public int BASE_TICKS_REQUIRED = 20;

    public int ticksRequired = BASE_TICKS_REQUIRED;

    /** How many ticks this machine has been operating for. */
    public int operatingTicks;

    /** The nodes that have full sources near them or in them */
    public Set<Coord4D> recurringNodes = new HashSet<Coord4D>();

    /** This machine's current RedstoneControl type. */
    public RedstoneControl controlType = RedstoneControl.DISABLED;

    public TileComponentUpgrade upgradeComponent = new TileComponentUpgrade(this, 3);
    public TileComponentSecurity securityComponent = new TileComponentSecurity(this);

    public TileEntityElectricPump() {
        super("ElectricPump", 10000);
        inventory = new ItemStack[4];

        upgradeComponent.setSupported(Upgrade.FILTER);
    }

    @Override
    public void onUpdate() {
        if (!worldObj.isRemote) {
            ChargeUtils.discharge(2, this);

            if (inventory[0] != null && fluidTank.getFluid() != null) {
                if (inventory[0].getItem() instanceof IFluidContainerItem) {
                    FluidContainerUtils.handleContainerItemFill(this, fluidTank, 0, 1);
                } else if (FluidContainerRegistry.isEmptyContainer(inventory[0])) {
                    FluidContainerUtils.handleRegistryItemFill(this, fluidTank, 0, 1);
                }
            }
        }

        if (!worldObj.isRemote) {
            if (MekanismUtils.canFunction(this) && getEnergy() >= energyPerTick) {
                if (suckedLastOperation) {
                    setEnergy(getEnergy() - energyPerTick);
                }

                if ((operatingTicks + 1) < ticksRequired) {
                    operatingTicks++;
                } else {
                    if (fluidTank.getFluid() == null
                        || fluidTank.getFluid().amount
                                + FluidContainerRegistry.BUCKET_VOLUME
                            <= fluidTank.getCapacity()) {
                        if (!suck(true)) {
                            suckedLastOperation = false;
                            reset();
                        } else {
                            suckedLastOperation = true;
                        }
                    } else {
                        suckedLastOperation = false;
                    }

                    operatingTicks = 0;
                }
            } else {
                suckedLastOperation = false;
            }
        }

        super.onUpdate();

        if (!worldObj.isRemote && fluidTank.getFluid() != null) {
            TileEntity tileEntity = Coord4D.get(this)
                                        .getFromSide(ForgeDirection.UP)
                                        .getTileEntity(worldObj);

            if (tileEntity instanceof IFluidHandler) {
                FluidStack toDrain = new FluidStack(
                    fluidTank.getFluid(),
                    Math.min(
                        256 * (upgradeComponent.getUpgrades(Upgrade.SPEED) + 1),
                        fluidTank.getFluidAmount()
                    )
                );
                fluidTank.drain(
                    ((IFluidHandler) tileEntity).fill(ForgeDirection.DOWN, toDrain, true),
                    true
                );
            }
        }
    }

    public boolean hasFilter() {
        return upgradeComponent.getInstalledTypes().contains(Upgrade.FILTER);
    }

    public boolean suck(boolean take) {
        List<Coord4D> tempPumpList
            = Arrays.asList(recurringNodes.toArray(new Coord4D[recurringNodes.size()]));
        Collections.shuffle(tempPumpList);

        //First see if there are any fluid blocks touching the pump - if so, sucks and
        //adds the location to the recurring list
        for (ForgeDirection orientation : ForgeDirection.VALID_DIRECTIONS) {
            Coord4D wrapper = Coord4D.get(this).getFromSide(orientation);
            FluidStack fluid = MekanismUtils.getFluid(worldObj, wrapper, hasFilter());

            if (fluid != null && (activeType == null || fluid.getFluid() == activeType)
                && (fluidTank.getFluid() == null
                    || fluidTank.getFluid().isFluidEqual(fluid))) {
                if (take) {
                    activeType = fluid.getFluid();
                    recurringNodes.add(wrapper.clone());
                    fluidTank.fill(fluid, true);

                    if (shouldTake(fluid, wrapper)) {
                        worldObj.setBlock(
                            wrapper.xCoord,
                            wrapper.yCoord,
                            wrapper.zCoord,
                            Blocks.air,
                            0,
                            3
                        );
                    }
                }

                return true;
            }
        }

        //Finally, go over the recurring list of nodes and see if there is a fluid block
        //available to suck - if not, will iterate around the recurring block, attempt to
        //suck, and then add the adjacent block to the recurring list
        for (Coord4D wrapper : tempPumpList) {
            FluidStack fluid = MekanismUtils.getFluid(worldObj, wrapper, hasFilter());

            if (fluid != null && (activeType == null || fluid.getFluid() == activeType)
                && (fluidTank.getFluid() == null
                    || fluidTank.getFluid().isFluidEqual(fluid))) {
                if (take) {
                    activeType = fluid.getFluid();
                    fluidTank.fill(fluid, true);

                    if (shouldTake(fluid, wrapper)) {
                        worldObj.setBlock(
                            wrapper.xCoord,
                            wrapper.yCoord,
                            wrapper.zCoord,
                            Blocks.air,
                            0,
                            3
                        );
                    }
                }

                return true;
            }

            //Add all the blocks surrounding this recurring node to the recurring node
            //list
            for (ForgeDirection orientation : ForgeDirection.VALID_DIRECTIONS) {
                Coord4D side = wrapper.getFromSide(orientation);

                if (Coord4D.get(this).distanceTo(side) <= general.maxPumpRange) {
                    fluid = MekanismUtils.getFluid(worldObj, side, hasFilter());

                    if (fluid != null
                        && (activeType == null || fluid.getFluid() == activeType)
                        && (fluidTank.getFluid() == null
                            || fluidTank.getFluid().isFluidEqual(fluid))) {
                        if (take) {
                            activeType = fluid.getFluid();
                            recurringNodes.add(side);
                            fluidTank.fill(fluid, true);

                            if (shouldTake(fluid, side)) {
                                worldObj.setBlock(
                                    side.xCoord,
                                    side.yCoord,
                                    side.zCoord,
                                    Blocks.air,
                                    0,
                                    3
                                );
                            }
                        }

                        return true;
                    }
                }
            }

            recurringNodes.remove(wrapper);
        }

        return false;
    }

    public void reset() {
        activeType = null;
        recurringNodes.clear();
    }

    private boolean shouldTake(FluidStack fluid, Coord4D coord) {
        if (fluid.getFluid() == FluidRegistry.WATER
            || fluid.getFluid() == FluidRegistry.getFluid("heavywater")) {
            return general.pumpWaterSources;
        }

        return true;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            if (dataStream.readInt() == 1) {
                fluidTank.setFluid(new FluidStack(
                    FluidRegistry.getFluid(dataStream.readInt()), dataStream.readInt()
                ));
            } else {
                fluidTank.setFluid(null);
            }

            controlType = RedstoneControl.values()[dataStream.readInt()];

            MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        if (fluidTank.getFluid() != null) {
            data.add(1);
            data.add(fluidTank.getFluid().getFluidID());
            data.add(fluidTank.getFluid().amount);
        } else {
            data.add(0);
        }

        data.add(controlType.ordinal());

        return data;
    }

    public int getScaledFluidLevel(int i) {
        return fluidTank.getFluid() != null ? fluidTank.getFluid().amount * i / 10000 : 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setInteger("operatingTicks", operatingTicks);
        nbtTags.setBoolean("suckedLastOperation", suckedLastOperation);

        if (activeType != null) {
            nbtTags.setString("activeType", FluidRegistry.getFluidName(activeType));
        }

        if (fluidTank.getFluid() != null) {
            nbtTags.setTag("fluidTank", fluidTank.writeToNBT(new NBTTagCompound()));
        }

        nbtTags.setInteger("controlType", controlType.ordinal());

        NBTTagList recurringList = new NBTTagList();

        for (Coord4D wrapper : recurringNodes) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            wrapper.write(tagCompound);
            recurringList.appendTag(tagCompound);
        }

        if (recurringList.tagCount() != 0) {
            nbtTags.setTag("recurringNodes", recurringList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        operatingTicks = nbtTags.getInteger("operatingTicks");
        suckedLastOperation = nbtTags.getBoolean("suckedLastOperation");

        if (nbtTags.hasKey("activeType")) {
            activeType = FluidRegistry.getFluid(nbtTags.getString("activeType"));
        }

        if (nbtTags.hasKey("fluidTank")) {
            fluidTank.readFromNBT(nbtTags.getCompoundTag("fluidTank"));
        }

        if (nbtTags.hasKey("controlType")) {
            controlType = RedstoneControl.values()[nbtTags.getInteger("controlType")];
        }

        if (nbtTags.hasKey("recurringNodes")) {
            NBTTagList tagList = nbtTags.getTagList("recurringNodes", NBT.TAG_COMPOUND);

            for (int i = 0; i < tagList.tagCount(); i++) {
                recurringNodes.add(Coord4D.read((NBTTagCompound
                ) tagList.getCompoundTagAt(i)));
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
        if (slotID == 1) {
            return false;
        } else if (slotID == 0) {
            return FluidContainerRegistry.isEmptyContainer(itemstack);
        } else if (slotID == 2) {
            return ChargeUtils.canBeDischarged(itemstack);
        }

        return true;
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int side) {
        if (slotID == 2) {
            return ChargeUtils.canBeOutputted(itemstack, false);
        } else if (slotID == 1) {
            return true;
        }

        return false;
    }

    @Override
    public EnumSet<ForgeDirection> getConsumingSides() {
        return EnumSet.of(ForgeDirection.getOrientation(facing).getOpposite());
    }

    @Override
    public boolean canSetFacing(int side) {
        return side != 0 && side != 1;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if (side == 1) {
            return new int[] { 0 };
        } else if (side == 0) {
            return new int[] { 1 };
        } else {
            return new int[] { 2 };
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection direction) {
        if (direction == ForgeDirection.getOrientation(1)) {
            return new FluidTankInfo[] { fluidTank.getInfo() };
        }

        return PipeUtils.EMPTY;
    }

    @Override
    public void setFluidStack(FluidStack fluidStack, Object... data) {
        fluidTank.setFluid(fluidStack);
    }

    @Override
    public FluidStack getFluidStack(Object... data) {
        return fluidTank.getFluid();
    }

    @Override
    public boolean hasTank(Object... data) {
        return true;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (fluidTank.getFluid() != null
            && fluidTank.getFluid().getFluid() == resource.getFluid()
            && from == ForgeDirection.getOrientation(1)) {
            return drain(from, resource.amount, doDrain);
        }

        return null;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (from == ForgeDirection.getOrientation(1)) {
            return fluidTank.drain(maxDrain, doDrain);
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return from == ForgeDirection.getOrientation(1);
    }

    @Override
    public boolean onSneakRightClick(EntityPlayer player, int side) {
        reset();

        player.addChatMessage(new ChatComponentText(
            EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.GREY
            + LangUtils.localize("tooltip.configurator.pumpReset")
        ));

        return true;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, int side) {
        return false;
    }

    @Override
    public RedstoneControl getControlType() {
        return controlType;
    }

    @Override
    public void setControlType(RedstoneControl type) {
        controlType = type;
        MekanismUtils.saveChunk(this);
    }

    @Override
    public boolean canPulse() {
        return true;
    }

    @Override
    public TileComponentUpgrade getComponent() {
        return upgradeComponent;
    }

    @Override
    public Object[] getTanks() {
        return new Object[] { fluidTank };
    }

    private static final String[] methods = new String[] { "reset" };

    @Override
    public String[] getMethods() {
        return methods;
    }

    @Override
    public Object[] invoke(int method, Object[] arguments) throws Exception {
        switch (method) {
            case 0:
                reset();
                return new Object[] { "Pump calculation reset." };
            default:
                throw new NoSuchMethodException();
        }
    }

    @Override
    public TileComponentSecurity getSecurity() {
        return securityComponent;
    }

    @Override
    public void recalculateUpgradables(Upgrade upgrade) {
        super.recalculateUpgradables(upgrade);

        switch (upgrade) {
            case SPEED:
                ticksRequired = MekanismUtils.getTicks(this, BASE_TICKS_REQUIRED);
            case ENERGY:
                energyPerTick
                    = MekanismUtils.getEnergyPerTick(this, BASE_ENERGY_PER_TICK);
                maxEnergy = MekanismUtils.getMaxEnergy(this, BASE_MAX_ENERGY);
            default:
                break;
        }
    }
}
