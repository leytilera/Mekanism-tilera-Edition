package mekanism.common.tile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
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
import mekanism.common.base.IUpgradeTile;
import mekanism.common.block.BlockMachine.MachineType;
import mekanism.common.integration.IComputerIntegration;
import mekanism.common.security.ISecurityTile;
import mekanism.common.tile.component.TileComponentSecurity;
import mekanism.common.tile.component.TileComponentUpgrade;
import mekanism.common.util.ChargeUtils;
import mekanism.common.util.FluidContainerUtils;
import mekanism.common.util.FluidContainerUtils.FluidChecker;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.PipeUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityFluidicPlenisher extends TileEntityElectricBlock
    implements IComputerIntegration, IConfigurable, IFluidHandler, ISustainedTank,
               IUpgradeTile, IRedstoneControl, ISecurityTile {
    public Set<Coord4D> activeNodes = new HashSet<Coord4D>();
    public Set<Coord4D> usedNodes = new HashSet<Coord4D>();

    public boolean finishedCalc = false;

    public FluidTank fluidTank = new FluidTank(10000);

    /** How much energy this machine consumes per-tick. */
    public double BASE_ENERGY_PER_TICK = usage.fluidicPlenisherUsage;

    public double energyPerTick = BASE_ENERGY_PER_TICK;

    /** How many ticks it takes to run an operation. */
    public int BASE_TICKS_REQUIRED = 20;

    public int ticksRequired = BASE_TICKS_REQUIRED;

    /** How many ticks this machine has been operating for. */
    public int operatingTicks;

    public RedstoneControl controlType = RedstoneControl.DISABLED;

    private static EnumSet<ForgeDirection> dirs
        = EnumSet.complementOf(EnumSet.of(ForgeDirection.UP, ForgeDirection.UNKNOWN));

    public TileComponentUpgrade upgradeComponent = new TileComponentUpgrade(this, 3);
    public TileComponentSecurity securityComponent = new TileComponentSecurity(this);

    public TileEntityFluidicPlenisher() {
        super("FluidicPlenisher", MachineType.FLUIDIC_PLENISHER.baseEnergy);
        inventory = new ItemStack[4];
    }

    @Override
    public void onUpdate() {
        if (!worldObj.isRemote) {
            ChargeUtils.discharge(2, this);

            if (inventory[0] != null) {
                if (inventory[0].getItem() instanceof IFluidContainerItem) {
                    FluidContainerUtils.handleContainerItemEmpty(
                        this,
                        fluidTank,
                        0,
                        1,
                        new FluidChecker() {
                            @Override
                            public boolean isValid(Fluid f) {
                                return f.canBePlacedInWorld();
                            }
                        }
                    );
                } else if (FluidContainerRegistry.isFilledContainer(inventory[0])) {
                    FluidContainerUtils.handleRegistryItemEmpty(
                        this,
                        fluidTank,
                        0,
                        1,
                        new FluidChecker() {
                            @Override
                            public boolean isValid(Fluid f) {
                                return f.canBePlacedInWorld();
                            }
                        }
                    );
                }
            }

            if (MekanismUtils.canFunction(this) && getEnergy() >= energyPerTick
                && fluidTank.getFluid() != null
                && fluidTank.getFluid().getFluid().canBePlacedInWorld()) {
                if (!finishedCalc) {
                    setEnergy(getEnergy() - energyPerTick);
                }

                if ((operatingTicks + 1) < ticksRequired) {
                    operatingTicks++;
                } else {
                    if (!finishedCalc) {
                        doPlenish();
                    } else {
                        Coord4D below
                            = Coord4D.get(this).getFromSide(ForgeDirection.DOWN);

                        if (canReplace(below, false, false)
                            && fluidTank.getFluidAmount()
                                >= FluidContainerRegistry.BUCKET_VOLUME) {
                            if (fluidTank.getFluid().getFluid().canBePlacedInWorld()) {
                                worldObj.setBlock(
                                    below.xCoord,
                                    below.yCoord,
                                    below.zCoord,
                                    MekanismUtils.getFlowingBlock(
                                        fluidTank.getFluid().getFluid()
                                    ),
                                    0,
                                    3
                                );

                                setEnergy(getEnergy() - energyPerTick);
                                fluidTank.drain(
                                    FluidContainerRegistry.BUCKET_VOLUME, true
                                );
                            }
                        }
                    }

                    operatingTicks = 0;
                }
            }
        }
    }

    private void doPlenish() {
        if (usedNodes.size() >= general.maxPlenisherNodes) {
            finishedCalc = true;
            return;
        }

        if (activeNodes.isEmpty()) {
            if (usedNodes.isEmpty()) {
                Coord4D below = Coord4D.get(this).getFromSide(ForgeDirection.DOWN);

                if (!canReplace(below, true, true)) {
                    finishedCalc = true;
                    return;
                }

                activeNodes.add(below);
            } else {
                finishedCalc = true;
                return;
            }
        }

        Set<Coord4D> toRemove = new HashSet<Coord4D>();

        for (Coord4D coord : activeNodes) {
            if (coord.exists(worldObj)) {
                if (canReplace(coord, true, false)) {
                    worldObj.setBlock(
                        coord.xCoord,
                        coord.yCoord,
                        coord.zCoord,
                        MekanismUtils.getFlowingBlock(fluidTank.getFluid().getFluid()),
                        0,
                        3
                    );

                    fluidTank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                }

                for (ForgeDirection dir : dirs) {
                    Coord4D sideCoord = coord.getFromSide(dir);

                    if (sideCoord.exists(worldObj) && canReplace(sideCoord, true, true)) {
                        activeNodes.add(sideCoord);
                    }
                }

                toRemove.add(coord);
                break;
            } else {
                toRemove.add(coord);
            }
        }

        for (Coord4D coord : toRemove) {
            activeNodes.remove(coord);
            usedNodes.add(coord);
        }
    }

    public boolean canReplace(Coord4D coord, boolean checkNodes, boolean isPathfinding) {
        if (checkNodes && usedNodes.contains(coord)) {
            return false;
        }

        if (coord.isAirBlock(worldObj) || MekanismUtils.isDeadFluid(worldObj, coord)) {
            return true;
        }

        if (MekanismUtils.isFluid(worldObj, coord)) {
            return isPathfinding;
        }

        return coord.getBlock(worldObj).isReplaceable(
            worldObj, coord.xCoord, coord.yCoord, coord.zCoord
        );
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            finishedCalc = dataStream.readBoolean();
            controlType = RedstoneControl.values()[dataStream.readInt()];

            if (dataStream.readInt() == 1) {
                fluidTank.setFluid(
                    new FluidStack(dataStream.readInt(), dataStream.readInt())
                );
            } else {
                fluidTank.setFluid(null);
            }

            MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        data.add(finishedCalc);
        data.add(controlType.ordinal());

        if (fluidTank.getFluid() != null) {
            data.add(1);
            data.add(fluidTank.getFluid().getFluidID());
            data.add(fluidTank.getFluid().amount);
        } else {
            data.add(0);
        }

        return data;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setInteger("operatingTicks", operatingTicks);
        nbtTags.setBoolean("finishedCalc", finishedCalc);
        nbtTags.setInteger("controlType", controlType.ordinal());

        if (fluidTank.getFluid() != null) {
            nbtTags.setTag("fluidTank", fluidTank.writeToNBT(new NBTTagCompound()));
        }

        NBTTagList activeList = new NBTTagList();

        for (Coord4D wrapper : activeNodes) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            wrapper.write(tagCompound);
            activeList.appendTag(tagCompound);
        }

        if (activeList.tagCount() != 0) {
            nbtTags.setTag("activeNodes", activeList);
        }

        NBTTagList usedList = new NBTTagList();

        for (Coord4D obj : usedNodes) {
            activeList.appendTag(obj.write(new NBTTagCompound()));
        }

        if (activeList.tagCount() != 0) {
            nbtTags.setTag("usedNodes", usedList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        operatingTicks = nbtTags.getInteger("operatingTicks");
        finishedCalc = nbtTags.getBoolean("finishedCalc");
        controlType = RedstoneControl.values()[nbtTags.getInteger("controlType")];

        if (nbtTags.hasKey("fluidTank")) {
            fluidTank.readFromNBT(nbtTags.getCompoundTag("fluidTank"));
        }

        if (nbtTags.hasKey("activeNodes")) {
            NBTTagList tagList = nbtTags.getTagList("activeNodes", NBT.TAG_COMPOUND);

            for (int i = 0; i < tagList.tagCount(); i++) {
                activeNodes.add(Coord4D.read((NBTTagCompound) tagList.getCompoundTagAt(i))
                );
            }
        }

        if (nbtTags.hasKey("usedNodes")) {
            NBTTagList tagList = nbtTags.getTagList("usedNodes", NBT.TAG_COMPOUND);

            for (int i = 0; i < tagList.tagCount(); i++) {
                usedNodes.add(Coord4D.read((NBTTagCompound) tagList.getCompoundTagAt(i)));
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
        if (slotID == 1) {
            return false;
        } else if (slotID == 0) {
            return FluidContainerRegistry.isFilledContainer(itemstack);
        } else if (slotID == 2) {
            return ChargeUtils.canBeDischarged(itemstack);
        }

        return false;
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
        if (direction == ForgeDirection.UP) {
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
            && from == ForgeDirection.UP) {
            return drain(from, resource.amount, doDrain);
        }

        return null;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (from == ForgeDirection.UP && resource.getFluid().canBePlacedInWorld()) {
            return fluidTank.fill(resource, true);
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return from == ForgeDirection.UP && fluid.canBePlacedInWorld();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean onSneakRightClick(EntityPlayer player, int side) {
        activeNodes.clear();
        usedNodes.clear();
        finishedCalc = false;

        player.addChatMessage(new ChatComponentText(
            EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.GREY
            + LangUtils.localize("tooltip.configurator.plenisherReset")
        ));

        return true;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, int side) {
        return false;
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
                activeNodes.clear();
                usedNodes.clear();
                finishedCalc = false;

                return new Object[] { "Plenisher calculation reset." };
            default:
                throw new NoSuchMethodException();
        }
    }

    @Override
    public TileComponentUpgrade getComponent() {
        return upgradeComponent;
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

    @Override
    public RedstoneControl getControlType() {
        return controlType;
    }

    @Override
    public void setControlType(RedstoneControl type) {
        controlType = type;
    }

    @Override
    public boolean canPulse() {
        return false;
    }

    @Override
    public TileComponentSecurity getSecurity() {
        return securityComponent;
    }
}
