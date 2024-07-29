package mekanism.common.tile;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.common.Optional;
import mekanism.api.Coord4D;
import mekanism.api.IHeatTransfer;
import mekanism.common.Mekanism;
import mekanism.common.Tier.ConductorTier;
import mekanism.common.util.HeatUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "api.hbm.tile.IHeatSource", modid = "hbm")
public class TileEntityHeatConductor
    extends TileEntity implements IHeatSource, IHeatTransfer {
    private double temperature;
    private double heatToAbsorb;

    @Override
    public void updateEntity() {
        if (this.worldObj.isRemote)
            return;

        if (Mekanism.hooks.HBMLoaded)
            this.transferFromHBMHeatSource();

        this.simulateHeat();
        this.applyTemperatureChange();
    }

    @Override
    @Optional.Method(modid = "hbm")
    public int getHeatStored() {
        return temperatureToTU(this.temperature);
    }

    @Override
    @Optional.Method(modid = "hbm")
    public void useUpHeat(int heat) {
        this.temperature -= tuToTemperature(heat);
    }

    @Override
    public double getTemp() {
        return this.temperature;
    }

    @Override
    public double getInverseConductionCoefficient() {
        return ConductorTier.ULTIMATE.inverseConduction;
    }

    @Override
    public double getInsulationCoefficient(ForgeDirection side) {
        return ConductorTier.ULTIMATE.inverseConductionInsulation;
    }

    @Override
    public void transferHeatTo(double heat) {
        this.heatToAbsorb += heat;
    }

    @Override
    public double[] simulateHeat() {
        return HeatUtils.simulate(this);
    }

    @Override
    public double applyTemperatureChange() {
        this.temperature += 0.9D * this.heatToAbsorb;
        this.heatToAbsorb = 0;
        return this.temperature;
    }

    @Override
    public boolean canConnectHeat(ForgeDirection side) {
        return true;
    }

    @Override
    public IHeatTransfer getAdjacent(ForgeDirection side) {
        TileEntity adj = Coord4D.get(this).getFromSide(side).getTileEntity(this.worldObj);

        if (adj instanceof IHeatTransfer)
            return (IHeatTransfer) adj;

        return null;
    }

    public static double tuToTemperature(int tu) {
        return ((double) tu) / 50D;
    }

    public static int temperatureToTU(double temp) {
        return (int) (temp * 50D);
    }

    @Optional.Method(modid = "hbm")
    public void transferFromHBMHeatSource() {
        TileEntity otherTE = Coord4D.get(this)
                                 .getFromSide(ForgeDirection.DOWN)
                                 .getTileEntity(this.worldObj);
        if (!(otherTE instanceof IHeatSource))
            return;

        IHeatSource other = (IHeatSource) otherTE;

        int thisTU = temperatureToTU(this.temperature);
        int diff = other.getHeatStored() - thisTU;
        if (diff <= 0)
            return;

        this.transferHeatTo(tuToTemperature(diff));
        other.useUpHeat(diff);
    }
}
