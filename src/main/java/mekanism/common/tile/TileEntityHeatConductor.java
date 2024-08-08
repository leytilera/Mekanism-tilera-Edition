package mekanism.common.tile;

import cpw.mods.fml.common.Optional;
import mekanism.api.Coord4D;
import mekanism.api.IHeatTransfer;
import mekanism.common.Mekanism;
import mekanism.common.Tier.ConductorTier;
import mekanism.common.tile.heatconductor.IHeatConductorModAdapter;
import mekanism.common.tile.heatconductor.ModAdapterHBM;
import mekanism.common.tile.heatconductor.ModAdapterIC2;
import mekanism.common.util.HeatUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({
    @Optional.Interface(iface = "api.hbm.tile.IHeatSource", modid = "hbm")
    , @Optional.Interface(iface = "ic2.api.energy.tile.IHeatSource", modid = "IC2")
})
public class TileEntityHeatConductor extends TileEntity
    implements api.hbm.tile.IHeatSource, ic2.api.energy.tile.IHeatSource, IHeatTransfer {
    public static IHeatConductorModAdapter<Integer> ADAPTER_HBM
        = Mekanism.hooks.HBMLoaded ? new ModAdapterHBM() : null;

    public static IHeatConductorModAdapter<Integer> ADAPTER_IC2
        = Mekanism.hooks.IC2Loaded ? new ModAdapterIC2() : null;

    public double temperature;
    public double heatToAbsorb;

    @Override
    public void updateEntity() {
        if (this.worldObj.isRemote)
            return;

        if (ADAPTER_HBM != null)
          ADAPTER_HBM.onTick(this);

        if (ADAPTER_IC2 != null)
          ADAPTER_IC2.onTick(this);

        this.simulateHeat();
        this.applyTemperatureChange();
    }

    @Override
    @Optional.Method(modid = "hbm")
    public int getHeatStored() {
        return ADAPTER_HBM.fromTemperature(this.temperature);
    }

    @Override
    @Optional.Method(modid = "hbm")
    public void useUpHeat(int heat) {
        this.temperature -= ADAPTER_HBM.toTemperature(heat);
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

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setDouble("temperature", this.temperature);
        nbt.setDouble("heatToAbsorb", this.heatToAbsorb);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.temperature = nbt.getDouble("temperature");
        this.heatToAbsorb = nbt.getDouble("heatToAbsorb");
    }

    @Override
    @Optional.Method(modid = "IC2")
    public int maxrequestHeatTick(ForgeDirection directionFrom) {
        return ADAPTER_IC2.fromTemperature(this.temperature);
    }

    @Override
    @Optional.Method(modid = "IC2")
    public int requestHeat(ForgeDirection directionFrom, int requestheat) {
        int toTransfer = Math.min(requestheat, ADAPTER_IC2.fromTemperature(this.temperature));
        this.heatToAbsorb -= ADAPTER_IC2.toTemperature(toTransfer);
        return toTransfer;
    }
}
