package mekanism.common.integration;

import mekanism.api.energy.ICableOutputter;
import mekanism.api.energy.IEnergizedItem;
import mekanism.api.energy.IStrictEnergyAcceptor;
import mekanism.api.energy.IStrictEnergyStorage;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.multipart.PartUniversalCable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.api.CompatibilityModule;

public class UECompatModule extends CompatibilityModule {

    @Override
    public double doReceiveEnergy(Object handler, ForgeDirection direction, double energy, boolean doReceive) {
        if (doCanReceive(handler, direction)) {
            IStrictEnergyAcceptor acceptor = (IStrictEnergyAcceptor) handler;
            return acceptor.transferEnergyToAcceptor(direction, energy);
        }
        return 0;
    }

    @Override
    public double doExtractEnergy(Object handler, ForgeDirection direction, double energy, boolean doExtract) {
        if (doCanExtract(handler, direction) && handler instanceof IStrictEnergyStorage) {
            double provided = doGetProvidedJoules(handler);
            double toExtract = Math.min(provided, energy);
            if (doExtract) {
                IStrictEnergyStorage storage = (IStrictEnergyStorage) handler;
                storage.setEnergy(storage.getEnergy() - toExtract);
            }
            return toExtract;
        }
        return 0;
    }

    @Override
    public double doChargeItem(ItemStack itemStack, double joules, boolean docharge) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double doDischargeItem(ItemStack itemStack, double joules, boolean doDischarge) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean doIsHandler(Object obj) {
        if (obj instanceof PartUniversalCable) return false;
        return obj instanceof IStrictEnergyAcceptor || (obj instanceof ICableOutputter && obj instanceof IStrictEnergyStorage) || obj instanceof IEnergizedItem;
    }

    @Override
    public boolean doIsEnergyContainer(Object obj) {
        return obj instanceof IStrictEnergyStorage;
    }

    @Override
    public double doGetEnergy(Object obj, ForgeDirection direction) {
        if (obj instanceof IStrictEnergyStorage) {
            return ((IStrictEnergyStorage)obj).getEnergy();
        }
        return 0;
    }

    @Override
    public boolean doCanConnect(Object obj, ForgeDirection direction, Object source) {
        if (obj instanceof IStrictEnergyAcceptor && ((IStrictEnergyAcceptor)obj).canReceiveEnergy(direction)) {
            return true;
        } else if (obj instanceof ICableOutputter && ((ICableOutputter)obj).canOutputTo(direction)) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack doGetItemWithCharge(ItemStack itemStack, double energy) {
        IEnergizedItem item = (IEnergizedItem) itemStack.getItem();
        ItemStack copy = itemStack.copy();
        item.setEnergy(copy, energy);
        return copy;
    }

    @Override
    public double doGetMaxEnergy(Object handler, ForgeDirection direction) {
        if (handler instanceof IStrictEnergyStorage) {
            return ((IStrictEnergyStorage)handler).getMaxEnergy();
        }
        return 0;
    }

    @Override
    public double doGetEnergyItem(ItemStack is) {
        IEnergizedItem item = (IEnergizedItem) is.getItem();
        return item.getEnergy(is);
    }

    @Override
    public double doGetMaxEnergyItem(ItemStack is) {
        IEnergizedItem item = (IEnergizedItem) is.getItem();
        return item.getMaxEnergy(is);
    }

    @Override
    public double doGetInputVoltage(Object handler) {
        return 960.0;
    }

    @Override
    public double doGetOutputVoltage(Object handler) {
        return 120.0;
    }

    @Override
    public boolean doCanReceive(Object handler, ForgeDirection side) {
        if (!(handler instanceof IStrictEnergyAcceptor)) return false;
        
        if (side != ForgeDirection.UNKNOWN) {
            return ((IStrictEnergyAcceptor)handler).canReceiveEnergy(side);
        }

        return true;
    }

    @Override
    public boolean doCanExtract(Object handler, ForgeDirection side) {
        if (!(handler instanceof ICableOutputter)) return false;
        
        if (side != ForgeDirection.UNKNOWN) {
            return ((ICableOutputter)handler).canOutputTo(side);
        }

        return true;
    }

    @Override
    public double doGetDemandedJoules(Object handler) {
        if (handler instanceof IStrictEnergyAcceptor) {
            IStrictEnergyAcceptor acceptor = (IStrictEnergyAcceptor) handler;
            return acceptor.getMaxEnergy() - acceptor.getEnergy();
        }
        return 0;
    }

    @Override
    public double doGetProvidedJoules(Object handler) {
        double maxOutput = 10000.0;
        if (handler instanceof IEnergyWrapper) {
            maxOutput = ((IEnergyWrapper)handler).getMaxOutput();
        }
        if (handler instanceof IStrictEnergyStorage && handler instanceof ICableOutputter) {
            IStrictEnergyStorage storage = (IStrictEnergyStorage) handler;
            return Math.min(storage.getEnergy(), maxOutput);
        }
        return 0;
    }
    
}
