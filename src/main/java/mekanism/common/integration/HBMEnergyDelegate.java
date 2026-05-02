package mekanism.common.integration;

import api.hbm.energymk2.IEnergyHandlerMK2;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import mekanism.common.Units;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("unused")
public class HBMEnergyDelegate<T extends TileEntity & IEnergyWrapper> implements ITileDelegate, IEnergyProviderMK2, IEnergyReceiverMK2, IEnergyHandlerMK2 {

    private boolean isLoaded = false;
    private T host;

    public HBMEnergyDelegate(T host) {
        this.host = host;
    }

    static {
        ITileDelegate.IMPLEMENTATIONS.put(IEnergyProviderMK2.class, HBMEnergyDelegate.class);
        ITileDelegate.IMPLEMENTATIONS.put(IEnergyReceiverMK2.class, HBMEnergyDelegate.class);
        ITileDelegate.IMPLEMENTATIONS.put(IEnergyHandlerMK2.class, HBMEnergyDelegate.class);
    }

    @Override
    public long getPower() {
        return Math.round(Units.convertFromJoules(host.getEnergy(), Units.HE));
    }

    @Override
    public void setPower(long power) {
        host.setEnergy(Units.convertToJoules(power, Units.HE));
    }

    @Override
    public long getMaxPower() {
        return Math.round(Units.convertFromJoules(host.getMaxEnergy(), Units.HE));
    }

    @Override
    public long getProviderSpeed() {
        return Math.round(Units.convertFromJoules(host.getMaxOutput(), Units.HE));
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return host.getConsumingSides().contains(from) || host.getOutputtingSides().contains(from);
    }

    @Override
    public boolean isLoaded() {
        return this.isLoaded;
    }

    @Override
    public void load() {
        this.isLoaded = true;
    }

    @Override
    public void unload() {
        this.isLoaded = false;
    }

    @Override
    public void tick() {
        if (MekanismUtils.useHBM()) {
            receiveHe();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        
    }

    public void receiveHe() {
        if (!host.getWorldObj().isRemote) {
            for (ForgeDirection dir : host.getConsumingSides())
                this.trySubscribe(
                    host.getWorldObj(),
                    host.xCoord + dir.offsetX,
                    host.yCoord + dir.offsetY,
                    host.zCoord + dir.offsetZ,
                    dir
                );
        }
    }
    
}
