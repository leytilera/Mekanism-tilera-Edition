package mekanism.common.tile;

import java.util.ArrayList;
import java.util.EnumSet;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import cpw.mods.fml.common.Optional.Method;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyTile;
import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.transmitters.ITransmitterTile;
import mekanism.common.Units;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.integration.ae2.MekaEnergyGridBlock;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityElectricBlock
    extends TileEntityContainerBlock implements IEnergyWrapper {
    /** How much energy is stored in this block. */
    public double electricityStored;

    /** Maximum amount of energy this machine can hold. */
    public double BASE_MAX_ENERGY;

    /** Actual maximum energy storage, including upgrades */
    public double maxEnergy;

    /** Is this registered with IC2 */
    public boolean ic2Registered = false;

    public boolean isLoaded = false;

    public MekaEnergyGridBlock<TileEntityElectricBlock> gridBlock = new MekaEnergyGridBlock<>(this);

    /**
     * The base of all blocks that deal with electricity. It has a facing state,
     * initialized state, and a current amount of stored energy.
     * @param name - full name of this block
     * @param baseMaxEnergy - how much energy this block can store
     */
    public TileEntityElectricBlock(String name, double baseMaxEnergy) {
        super(name);
        BASE_MAX_ENERGY = baseMaxEnergy;
        maxEnergy = BASE_MAX_ENERGY;
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
    public void onUpdate() {
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
    }

    @Override
    public EnumSet<ForgeDirection> getOutputtingSides() {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getConsumingSides() {
        return EnumSet.allOf(ForgeDirection.class);
    }

    @Override
    public double getMaxOutput() {
        return 0;
    }

    @Override
    public double getEnergy() {
        return electricityStored;
    }

    @Override
    public void setEnergy(double energy) {
        electricityStored = Math.max(Math.min(energy, getMaxEnergy()), 0);
        MekanismUtils.saveChunk(this);
    }

    @Override
    public double getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            setEnergy(dataStream.readDouble());
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        data.add(getEnergy());

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

        electricityStored = nbtTags.getDouble("electricityStored");

        if (MekanismUtils.useAE()) {
            this.gridBlock.readFromNBT(nbtTags);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setDouble("electricityStored", getEnergy());

        if (MekanismUtils.useAE()) {
            this.gridBlock.writeToNBT(nbtTags);
        }
    }

    /**
     * Gets the scaled energy level for the GUI.
     * @param i - multiplier
     * @return scaled energy
     */
    public int getScaledEnergyLevel(int i) {
        return (int) (getEnergy() * i / getMaxEnergy());
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (getConsumingSides().contains(from)) {
            double toAdd = (int
            ) Math.min(getMaxEnergy() - getEnergy(), Units.convertToJoules(maxReceive, Units.RF));

            if (!simulate) {
                setEnergy(getEnergy() + toAdd);
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
            }

            return (int) Math.round(Units.convertFromJoules(toSend, Units.RF));
        }

        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return getConsumingSides().contains(from) || getOutputtingSides().contains(from);
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
        return 1;
    }

    @Override
    @Method(modid = "IC2")
    public void setStored(int energy) {
        setEnergy(Units.convertToJoules(energy, Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public int addEnergy(int amount) {
        setEnergy(getEnergy() + Units.convertToJoules(amount, Units.EU));
        return (int) Math.round(Units.convertFromJoules(getEnergy(), Units.EU));
    }

    @Override
    @Method(modid = "IC2")
    public boolean isTeleporterCompatible(ForgeDirection side) {
        return getOutputtingSides().contains(side);
    }

    @Override
    public boolean canOutputTo(ForgeDirection side) {
        return getOutputtingSides().contains(side);
    }

    @Override
    @Method(modid = "IC2")
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return getConsumingSides().contains(direction);
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
        setEnergy(Math.max(getEnergy() - Units.convertToJoules(amount, Units.EU), 0));
    }

    @Override
    public double transferEnergyToAcceptor(ForgeDirection side, double amount) {
        if (!(getConsumingSides().contains(side) || side == ForgeDirection.UNKNOWN)) {
            return 0;
        }

        double toUse = Math.min(getMaxEnergy() - getEnergy(), amount);
        setEnergy(getEnergy() + toUse);

        return toUse;
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
    @Method(modid = "hbm")
    public boolean canConnect(ForgeDirection from) {
        return getConsumingSides().contains(from) || getOutputtingSides().contains(from);
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
