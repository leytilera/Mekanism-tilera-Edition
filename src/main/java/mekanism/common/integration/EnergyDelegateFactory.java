package mekanism.common.integration;

import java.util.HashMap;
import java.util.Map;

import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import mekanism.common.integration.ae2.MekaEnergyGridBlock;
import mekanism.common.util.MekanismUtils;
import net.minecraft.tileentity.TileEntity;

public class EnergyDelegateFactory {
    
    public static <E extends TileEntity & IEnergyWrapper> Map<Class<? extends ITileDelegate>, ITileDelegate> createEnergyDelegates(E tile) {
        Map<Class<? extends ITileDelegate>, ITileDelegate> delegates = new HashMap<>();
        if (MekanismUtils.useRF()) {
            delegates.put(RFEnergyDelegate.class, new RFEnergyDelegate<>(tile));
        }
        if (MekanismUtils.useIC2()) {
            delegates.put(IC2EnergyDelegate.class, new IC2EnergyDelegate<>(tile));
        }
        if (MekanismUtils.useAE()) {
            delegates.put(MekaEnergyGridBlock.class, new MekaEnergyGridBlock<>(tile));
        }
        if (MekanismUtils.useHBM()) {
            delegates.put(HBMEnergyDelegate.class, new HBMEnergyDelegate<>(tile));
        }
        return delegates;
    }

}
