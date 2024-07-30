package mekanism.common.tile.heatconductor;

import ic2.api.energy.tile.IHeatSource;
import mekanism.api.Coord4D;
import mekanism.common.tile.TileEntityHeatConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ModAdapterIC2 implements IHeatConductorModAdapter<Integer> {
    @Override
    public void onTick(TileEntityHeatConductor te) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity adj
                = Coord4D.get(te).getFromSide(dir).getTileEntity(te.getWorldObj());
            if (!(adj instanceof IHeatSource))
                continue;

            IHeatSource hs = (IHeatSource) adj;

            int teTemp = this.fromTemperature(te.temperature);
            int diff = hs.maxrequestHeatTick(dir.getOpposite()) - teTemp;
            if (diff <= 0)
                continue;

            te.transferHeatTo(this.toTemperature(hs.requestHeat(dir.getOpposite(), diff))
            );
        }
    }

    @Override
    public Integer fromTemperature(double temp) {
        return (int) (temp * 4D);
    }

    @Override
    public double toTemperature(Integer temp) {
        return ((double) temp) / 4D;
    }
}
