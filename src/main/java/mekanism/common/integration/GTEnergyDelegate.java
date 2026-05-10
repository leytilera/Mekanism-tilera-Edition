package mekanism.common.integration;

import mekanism.common.Units;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.IGregtechEnergy;
import mekanism.common.base.ITileDelegate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class GTEnergyDelegate<T extends TileEntity & IEnergyWrapper> implements ITileDelegate, IGregtechEnergy {

    private T host;

    public GTEnergyDelegate(T host) {
        this.host = host;
    }

    static {
        ITileDelegate.IMPLEMENTATIONS.put(IGregtechEnergy.class, GTEnergyDelegate.class);
    }

    @Override
    public long injectEnergyUnits(byte side, long voltage, long amperage) {
        double energyPacket = Units.convertToJoules(voltage, Units.EU);
        long usedAmperes = 0;
        
        while(usedAmperes < amperage && host.transferEnergyToAcceptor(ForgeDirection.getOrientation(side), energyPacket) > 0) {
            usedAmperes ++;
        }

        return usedAmperes;
    }

    @Override
    public boolean inputEnergyFrom(byte side) {
        return host.getConsumingSides().contains(ForgeDirection.getOrientation(side));
    }

    @Override
    public boolean outputsEnergyTo(byte side) {
        return host.getOutputtingSides().contains(ForgeDirection.getOrientation(side));
    }

    @Override
    public TileEntity selfAsTileEntity() {
        return host;
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

    @Override
    public boolean inputEnergyFrom(byte aSide, boolean waitForActive) {
        return inputEnergyFrom(aSide);
    }

    @Override
    public boolean outputsEnergyTo(byte aSide, boolean waitForActive) {
        return outputsEnergyTo(aSide);
    }
    
}
