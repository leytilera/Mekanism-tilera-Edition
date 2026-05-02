package mekanism.common.integration;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.tile.IEnergyStorage;
import mekanism.api.Coord4D;
import mekanism.api.transmitters.ITransmitterTile;
import mekanism.common.Units;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class IC2EnergyDelegate<T extends TileEntity & IEnergyWrapper> implements ITileDelegate, IEnergySink, IEnergySource, IEnergyStorage {

    private T host;

    public IC2EnergyDelegate(T host) {
        this.host = host;
    }

    static {
        ITileDelegate.IMPLEMENTATIONS.put(IEnergySink.class, IC2EnergyDelegate.class);
        ITileDelegate.IMPLEMENTATIONS.put(IEnergySource.class, IC2EnergyDelegate.class);
        ITileDelegate.IMPLEMENTATIONS.put(cofh.api.energy.IEnergyStorage.class, IC2EnergyDelegate.class);
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity tile, ForgeDirection direction) {
        return host.getConsumingSides().contains(direction);
    }

    @Override
    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return host.getOutputtingSides().contains(direction)
            && receiver instanceof IEnergyConductor;
    }

    @Override
    public int getStored() {
        return (int) Math.round(Units.convertFromJoules(host.getEnergy(), Units.EU));
    }

    @Override
    public void setStored(int energy) {
        host.setEnergy(Units.convertToJoules(energy, Units.EU));
    }

    @Override
    public int addEnergy(int amount) {
        host.setEnergy(host.getEnergy() + Units.convertToJoules(amount, Units.EU));
        return (int) Math.round(Units.convertFromJoules(host.getEnergy(), Units.EU));
    }

    @Override
    public int getCapacity() {
        return (int) Math.round(Units.convertFromJoules(host.getMaxEnergy(), Units.EU));
    }

    @Override
    public int getOutput() {
        return (int) Math.round(Units.convertFromJoules(host.getMaxOutput(), Units.EU));
    }

    @Override
    public double getOutputEnergyUnitsPerTick() {
        return Units.convertFromJoules(host.getMaxOutput(), Units.EU);
    }

    @Override
    public boolean isTeleporterCompatible(ForgeDirection side) {
        return host.getOutputtingSides().contains(side);
    }

    @Override
    public double getOfferedEnergy() {
        return Units.convertFromJoules(Math.min(host.getEnergy(), host.getMaxOutput()), Units.EU);
    }

    @Override
    public void drawEnergy(double amount) {
        host.setEnergy(Math.max(host.getEnergy() - Units.convertToJoules(amount, Units.EU), 0));
    }

    @Override
    public int getSourceTier() {
        return 1;
    }

    @Override
    public double getDemandedEnergy() {
        return Units.convertFromJoules((host.getMaxEnergy() - host.getEnergy()), Units.EU);
    }

    @Override
    public int getSinkTier() {
        return 4;
    }

    @Override
    public double injectEnergy(ForgeDirection direction, double amount, double voltage) {
        if (Coord4D.get(host).getFromSide(direction).getTileEntity(host.getWorldObj())
                instanceof ITransmitterTile) {
            return amount;
        }

        return amount
            - Units.convertFromJoules(host.transferEnergyToAcceptor(direction, Units.convertToJoules(amount, Units.EU)), Units.EU);
    }

    @Override
    public void load() {
        if (MekanismUtils.useIC2()) {
            register();
        }
    }

    @Override
    public void unload() {
        if (MekanismUtils.useIC2()) {
            deregister();
        }
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

    public void register() {
        if (!host.getWorldObj().isRemote) {
            TileEntity registered
                = EnergyNet.instance.getTileEntity(host.getWorldObj(), host.xCoord, host.yCoord, host.zCoord);

            if (registered != host) {
                if (registered instanceof IEnergyTile) {
                    MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile
                    ) registered));
                } else if (registered == null) {
                    MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(host));
                }
            }
        }
    }

    public void deregister() {
        if (!host.getWorldObj().isRemote) {
            TileEntity registered
                = EnergyNet.instance.getTileEntity(host.getWorldObj(), host.xCoord, host.yCoord, host.zCoord);

            if (registered instanceof IEnergyTile) {
                MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile
                ) registered));
            }
        }
    }
    
}
