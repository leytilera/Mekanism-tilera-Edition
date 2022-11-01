package mekanism.common.integration.ue;

import cpw.mods.fml.common.Loader;
import mekanism.common.base.IEnergyWrapper;

public abstract class UEDriverProxy {
    
    public abstract void tick();

    public abstract void invalidate();

    public static UEDriverProxy createProxy(IEnergyWrapper wrapper) {
        if (Loader.isModLoaded("basiccomponents")) {
            return new UELoaded(wrapper);
        } else {
            return new UEUnloaded();
        }
    }

}
