package mekanism.common.tile;

import java.util.ArrayList;
import java.util.EnumSet;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyTile;
import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.EnumColor;
import mekanism.api.IConfigurable;
import mekanism.api.Range4D;
import mekanism.api.transmitters.ITransmitterTile;
import mekanism.common.Mekanism;
import mekanism.common.Units;
import mekanism.common.base.IActiveState;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.integration.ae2.MekaEnergyGridBlock;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.util.CableUtils;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

@InterfaceList({
    @Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
    , @Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "IC2"),
        @Interface(iface = "ic2.api.tile.IEnergyStorage", modid = "IC2")
})
public class TileEntityInductionPort extends TileEntityInductionCasing
    implements IEnergyWrapper, IConfigurable, IActiveState {
    public boolean ic2Registered = false;
    public boolean isLoaded = false;

    /** false = input, true = output */
    public boolean mode;

    public MekaEnergyGridBlock<TileEntityInductionPort> gridBlock = new MekaEnergyGridBlock<>(this);

    public TileEntityInductionPort() {
        super("InductionPort");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!ic2Registered && MekanismUtils.useIC2()) {
            register();
        }
        if (MekanismUtils.useHBM()) {
            receiveHe();
        }
        if (MekanismUtils.useAE()) {
            this.gridBlock.update();
        }
        isLoaded = true;

        if (!worldObj.isRemote) {
            if (structure != null && mode == true) {
                double prev = getEnergy();
                CableUtils.emit(this);
                structure.remainingOutput -= (prev - getEnergy());
            }
        }
    }

    @Override
    public EnumSet<ForgeDirection> getOutputtingSides() {
        if (structure != null && mode == true) {
            EnumSet set = EnumSet.allOf(ForgeDirection.class);
            set.remove(ForgeDirection.UNKNOWN);

            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (structure.locations.contains(Coord4D.get(this).getFromSide(side))) {
                    set.remove(side);
                }
            }

            return set;
        }

        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getConsumingSides() {
        if (structure != null && mode == false) {
            EnumSet set = EnumSet.allOf(ForgeDirection.class);
            set.remove(ForgeDirection.UNKNOWN);
            return set;
        }

        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Method(modid = "IC2")
    public void register() {
        if (!worldObj.isRemote) {
            TileEntity registered
                = EnergyNet.instance.getTileEntity(worldObj, xCoord, yCoord, zCoord);

            if (registered != this) {
                if (registered instanceof IEnergyTile) {
                    MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile
                    ) registered));
                } else if (registered == null) {
                    MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
                    ic2Registered = true;
                }
            }
        }
    }

    @Method(modid = "IC2")
    public void deregister() {
        if (!worldObj.isRemote) {
            TileEntity registered
                = EnergyNet.instance.getTileEntity(worldObj, xCoord, yCoord, zCoord);

            if (registered instanceof IEnergyTile) {
                MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile
                ) registered));
            }
        }
    }

    @Override
    public double getMaxOutput() {
        return structure != null ? structure.remainingOutput : 0;
    }

    private double getMaxInput() {
        return structure != null ? structure.remainingInput : 0;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            mode = dataStream.readBoolean();

            MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        data.add(mode);

        return data;
    }

    @Override
    public void onAdded() {
        super.onAdded();

        if (MekanismUtils.useIC2()) {
            register();
        }
    }

    @Override
    public void onChunkUnload() {
        if (MekanismUtils.useIC2()) {
            deregister();
        }
        if (MekanismUtils.useAE()) {
            this.gridBlock.destroy();
        }
        isLoaded = false;
        super.onChunkUnload();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        isLoaded = false;
        if (MekanismUtils.useIC2()) {
            deregister();
        }
        if (MekanismUtils.useAE()) {
            this.gridBlock.destroy();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        mode = nbtTags.getBoolean("mode");

        if (MekanismUtils.useAE()) {
            this.gridBlock.readFromNBT(nbtTags);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setBoolean("mode", mode);

        if (MekanismUtils.useAE()) {
            this.gridBlock.writeToNBT(nbtTags);
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (getConsumingSides().contains(from)) {
            double toAdd = (int) Math.min(
                Math.min(getMaxInput(), getMaxEnergy() - getEnergy()),
                Units.convertToJoules(maxReceive, Units.RF)
            );

            if (!simulate) {
                setEnergy(getEnergy() + toAdd);
                structure.remainingInput -= toAdd;
            }

            return (int) Math.round(Units.convertFromJoules(toAdd, Units.RF));
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (getOutputtingSides().contains(from)) {
            double toSend = Math.min(
                getEnergy(), Math.min(getMaxOutput(), Units.convertToJoules(maxExtract, Units.RF))
            );

            if (!simulate) {
                setEnergy(getEnergy() - toSend);
                structure.remainingOutput -= toSend;
            }

            return (int) Math.round(Units.convertFromJoules(toSend, Units.RF));
        }

        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return structure != null;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return (int) Math.round(Units.convertFromJoules(getEnergy(), Units.RF));
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) Math.round(Units.convertFromJoules(getMaxEnergy(), Units.RF));
    }

    @Override
    @Method(modid = "IC2")
    public int getSinkTier() {
        return 4;
    }

    @Override
    @Method(modid = "IC2")
    public int getSourceTier() {
        return 4;
    }

    @Override
    @Method(modid = "IC2")
    public void setStored(int energy) {
        setEnergy(Units.convertToJoules(energy, Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public int addEnergy(int amount) {
        double toUse = Math.min(
            Math.min(getMaxInput(), getMaxEnergy() - getEnergy()),
            Units.convertToJoules(amount, Units.EU)
        );
        setEnergy(getEnergy() + toUse);
        structure.remainingInput -= toUse;
        return (int) Math.round(Units.convertFromJoules(getEnergy(), Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public boolean isTeleporterCompatible(ForgeDirection side) {
        return canOutputTo(side);
    }

    @Override
    public boolean canOutputTo(ForgeDirection side) {
        return getOutputtingSides().contains(side);
    }

    @Override
    @Method(modid = "IC2")
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return false;
        //return getConsumingSides().contains(direction);
    }

    @Override
    @Method(modid = "IC2")
    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return getOutputtingSides().contains(direction)
            && receiver instanceof IEnergyConductor;
    }

    @Override
    @Method(modid = "IC2")
    public int getStored() {
        return (int) Math.round(Units.convertFromJoules(getEnergy(), Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public int getCapacity() {
        return (int) Math.round(Units.convertFromJoules(getMaxEnergy(), Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public int getOutput() {
        return (int) Math.round(Units.convertFromJoules(getMaxOutput(), Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public double getDemandedEnergy() {
        return Units.convertFromJoules((getMaxEnergy() - getEnergy()), Units.EU);
    }

    @Override
    @Method(modid = "IC2")
    public double getOfferedEnergy() {
        return Units.convertFromJoules(Math.min(getEnergy(), getMaxOutput()), Units.EU);
    }

    @Override
    public boolean canReceiveEnergy(ForgeDirection side) {
        return getConsumingSides().contains(side);
    }

    @Override
    @Method(modid = "IC2")
    public double getOutputEnergyUnitsPerTick() {
        return Units.convertFromJoules(getMaxOutput(), Units.EU);
    }

    @Override
    @Method(modid = "IC2")
    public double injectEnergy(ForgeDirection direction, double amount, double voltage) {
        if (Coord4D.get(this).getFromSide(direction).getTileEntity(worldObj)
                instanceof ITransmitterTile) {
            return amount;
        }

        return amount
            - Units.convertFromJoules(transferEnergyToAcceptor(direction, Units.convertToJoules(amount, Units.EU)), Units.EU);
    }

    @Override
    @Method(modid = "IC2")
    public void drawEnergy(double amount) {
        if (structure != null) {
            double toDraw = Math.min(Units.convertToJoules(amount, Units.EU), getMaxOutput());
            setEnergy(Math.max(getEnergy() - toDraw, 0));
            structure.remainingOutput -= toDraw;
        }
    }

    @Override
    public double transferEnergyToAcceptor(ForgeDirection side, double amount) {
        if (!getConsumingSides().contains(side)) {
            return 0;
        }

        double toUse
            = Math.min(Math.min(getMaxInput(), getMaxEnergy() - getEnergy()), amount);
        setEnergy(getEnergy() + toUse);
        structure.remainingInput -= toUse;

        return toUse;
    }

    @Override
    public boolean onSneakRightClick(EntityPlayer player, int side) {
        if (!worldObj.isRemote) {
            mode = !mode;
            String modeText = " " + (mode ? EnumColor.DARK_RED : EnumColor.DARK_GREEN)
                + LangUtils.transOutputInput(mode) + ".";
            player.addChatMessage(new ChatComponentText(
                EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.GREY
                + LangUtils.localize("tooltip.configurator.inductionPortMode") + modeText
            ));

            Mekanism.packetHandler.sendToReceivers(
                new TileEntityMessage(
                    Coord4D.get(this), getNetworkedData(new ArrayList())
                ),
                new Range4D(Coord4D.get(this))
            );
            markDirty();
        }

        return true;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, int side) {
        return false;
    }

    @Override
    public boolean getActive() {
        return mode;
    }

    @Override
    public void setActive(boolean active) {
        mode = active;
    }

    @Override
    public boolean renderUpdate() {
        return true;
    }

    @Override
    public boolean lightUpdate() {
        return false;
    }

    @Override
    @Method(modid = "hbm")
    public long getPower() {
        return Math.round(Units.convertFromJoules(getEnergy(), Units.HE));
    }

    @Override
    @Method(modid = "hbm")
    public void setPower(long power) {
        setEnergy(Units.convertToJoules(power, Units.HE));
    }

    @Override
    @Method(modid = "hbm")
    public long getMaxPower() {
        return Math.round(Units.convertFromJoules(getMaxEnergy(), Units.HE));
    }

    @Override
    @Method(modid = "hbm")
    public long getProviderSpeed() {
        return Math.round(Units.convertFromJoules(getMaxOutput(), Units.HE));
    }

    @Method(modid = "hbm")
    public void receiveHe() {
        if (!worldObj.isRemote) {
            for (ForgeDirection dir : getConsumingSides())
                this.trySubscribe(
                    worldObj,
                    xCoord + dir.offsetX,
                    yCoord + dir.offsetY,
                    zCoord + dir.offsetZ,
                    dir
                );
        }
    }

    @Override
    @Method(modid = "hbm")
    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double getAECurrentPower() {
        return this.gridBlock.getAECurrentPower();
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double getAEMaxPower() {
        return this.gridBlock.getAEMaxPower();
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public AccessRestriction getPowerFlow() {
        return this.gridBlock.getPowerFlow();
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double injectAEPower(double amt, Actionable mode) {
        return this.gridBlock.injectAEPower(amt, mode);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public boolean isAEPublicPowerStorage() {
        return this.gridBlock.isAEPublicPowerStorage();
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double extractAEPower(double amt, Actionable mode, PowerMultiplier usePowerMultiplier) {
        return this.gridBlock.extractAEPower(amt, mode, usePowerMultiplier);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public AECableType getCableConnectionType(ForgeDirection dir) {
        return AECableType.COVERED;
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public IGridNode getGridNode(ForgeDirection dir) {
        return gridBlock.getGridNode(dir);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public void securityBreak() {
        
    }
}