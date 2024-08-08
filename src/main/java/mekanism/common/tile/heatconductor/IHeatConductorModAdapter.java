package mekanism.common.tile.heatconductor;

import mekanism.common.tile.TileEntityHeatConductor;

/**
 * An interface for an adapter for the HeatConductor TileEntity. This is implemented for
 * every (non-Mekanism) mod that is supported by the HeatConductor. It's responsible for
 * converting temperature units and pushing/pulling heat from adjacent blocks.
 */
public interface IHeatConductorModAdapter<T> {
    public void onTick(TileEntityHeatConductor te);

    public T fromTemperature(double temp);
    public double toTemperature(T temp);
}
