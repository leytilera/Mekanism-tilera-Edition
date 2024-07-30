package mekanism.common.tile.heatconductor;

import api.hbm.tile.IHeatSource;
import mekanism.api.Coord4D;
import mekanism.common.tile.TileEntityHeatConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ModAdapterHBM implements IHeatConductorModAdapter<Integer> {
    @Override
    public void onTick(TileEntityHeatConductor te) {
        TileEntity otherTE = Coord4D.get(te)
                                 .getFromSide(ForgeDirection.DOWN)
                                 .getTileEntity(te.getWorldObj());
        if (!(otherTE instanceof IHeatSource))
            return;

        IHeatSource other = (IHeatSource) otherTE;

        int teTU = this.fromTemperature(te.temperature);
        int diff = other.getHeatStored() - teTU;
        if (diff <= 0)
            return;

        te.transferHeatTo(this.toTemperature(diff));
        other.useUpHeat(diff);
    }

    @Override
    public Integer fromTemperature(double temp) {
        return (int) (temp * 50D);
    }

    @Override
    public double toTemperature(Integer tu) {
        return ((double) tu) / 50D;
    }
}
