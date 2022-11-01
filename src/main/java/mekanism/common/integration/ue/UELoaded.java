package mekanism.common.integration.ue;

import mekanism.common.base.IEnergyWrapper;
import universalelectricity.prefab.tile.ElectricTileDriver;

public class UELoaded extends UEDriverProxy {

    private ElectricTileDriver driver;

    public UELoaded(IEnergyWrapper wrapper) {
        driver = new ElectricTileDriver(wrapper);
    }

    @Override
    public void tick() {
        driver.tick();
    }

    @Override
    public void invalidate() {
        driver.invalidate();
    }
    
}
