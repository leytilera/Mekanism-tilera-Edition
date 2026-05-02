package mekanism.common.integration;

import cofh.api.energy.IEnergyHandler;
import mekanism.common.Units;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class RFEnergyDelegate<T extends TileEntity & IEnergyWrapper> implements ITileDelegate, IEnergyHandler {

    private T host;

    public RFEnergyDelegate(T host) {
        this.host = host;
    }

    static {
        ITileDelegate.IMPLEMENTATIONS.put(IEnergyHandler.class, RFEnergyDelegate.class);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return host.getConsumingSides().contains(from) || host.getOutputtingSides().contains(from);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (host.getConsumingSides().contains(from)) {
            double toAdd = (int
            ) Math.min(host.getMaxEnergy() - host.getEnergy(), Units.convertToJoules(maxReceive, Units.RF));

            if (!simulate) {
                host.setEnergy(host.getEnergy() + toAdd);
            }

            return (int) Math.round(Units.convertFromJoules(toAdd, Units.RF));
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (host.getOutputtingSides().contains(from)) {
            double toSend = Math.min(
                host.getEnergy(), Math.min(host.getMaxOutput(), Units.convertToJoules(maxExtract, Units.RF))
            );

            if (!simulate) {
                host.setEnergy(host.getEnergy() - toSend);
            }

            return (int) Math.round(Units.convertFromJoules(toSend, Units.RF));
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return (int) Math.round(Units.convertFromJoules(host.getEnergy(), Units.RF));
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) Math.round(Units.convertFromJoules(host.getMaxEnergy(), Units.RF));
    }

    @Override
    public void load() {
        
    }

    @Override
    public void unload() {
        
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        
    }
    
}
