package mekanism.common.tile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

import io.netty.buffer.ByteBuf;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import mekanism.common.integration.EnergyDelegateFactory;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityElectricBlock
    extends TileEntityContainerBlock implements IEnergyWrapper {
    /** How much energy is stored in this block. */
    public double electricityStored;

    /** Maximum amount of energy this machine can hold. */
    public double BASE_MAX_ENERGY;

    /** Actual maximum energy storage, including upgrades */
    public double maxEnergy;

    public boolean isLoaded = false;

    public Map<Class<? extends ITileDelegate>, ITileDelegate> delegates = EnergyDelegateFactory.createEnergyDelegates(this);

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

    @Override
    public void onUpdate() {
        if (!isLoaded) {
            delegates.values().forEach(ITileDelegate::load);
        }
        delegates.values().forEach(ITileDelegate::tick);
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
    public void onChunkUnload() {
        delegates.values().forEach(ITileDelegate::unload);
        isLoaded = false;
        super.onChunkUnload();
    }

    @Override
    public void onChunkLoad() {
        if (!isLoaded) {
            isLoaded = true;
            delegates.values().forEach(ITileDelegate::load);
        }
        super.onChunkLoad();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        delegates.values().forEach(ITileDelegate::unload);
        isLoaded = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        electricityStored = nbtTags.getDouble("electricityStored");

        delegates.values().forEach(d -> d.readFromNBT(nbtTags));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setDouble("electricityStored", getEnergy());

        delegates.values().forEach(d -> d.writeToNBT(nbtTags));
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
    public boolean canOutputTo(ForgeDirection side) {
        return getOutputtingSides().contains(side);
    }

    @Override
    public boolean canReceiveEnergy(ForgeDirection side) {
        return getConsumingSides().contains(side);
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

    @SuppressWarnings("unchecked")
    @Override
    public <E> Optional<E> getDelegate(Class<E> type) {
        return Optional.ofNullable(ITileDelegate.IMPLEMENTATIONS.get(type)).map(delegates::get).filter(type::isInstance).map(o -> (E) o);
    }
    
}
