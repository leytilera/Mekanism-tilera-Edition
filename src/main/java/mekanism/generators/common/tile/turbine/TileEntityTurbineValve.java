package mekanism.generators.common.tile.turbine;

import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

import mekanism.api.Coord4D;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import mekanism.common.integration.EnergyDelegateFactory;
import mekanism.common.tile.TileEntityGasTank.GasMode;
import mekanism.common.util.CableUtils;
import mekanism.common.util.LangUtils;
import mekanism.common.util.PipeUtils;
import mekanism.generators.common.content.turbine.TurbineFluidTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityTurbineValve
    extends TileEntityTurbineCasing implements IFluidHandler, IEnergyWrapper {
    public boolean isLoaded = false;

    public TurbineFluidTank fluidTank;

    public Map<Class<? extends ITileDelegate>, ITileDelegate> delegates = EnergyDelegateFactory.createEnergyDelegates(this);

    public TileEntityTurbineValve() {
        super("TurbineValve");
        fluidTank = new TurbineFluidTank(this);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!isLoaded) {
            delegates.values().forEach(ITileDelegate::load);
        }
        delegates.values().forEach(ITileDelegate::tick);
        isLoaded = true;

        if (!worldObj.isRemote) {
            if (structure != null) {
                double prev = getEnergy();
                CableUtils.emit(this);
            }
        }
    }

    @Override
    public EnumSet<ForgeDirection> getOutputtingSides() {
        if (structure != null) {
            EnumSet set = EnumSet.allOf(ForgeDirection.class);
            set.remove(ForgeDirection.UNKNOWN);

            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (structure.locations.contains(Coord4D.get(this).getFromSide(side))) {
                    set.remove(side);
                }
            }

            return set;
        }

        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getConsumingSides() {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public double getMaxOutput() {
        return structure != null ? structure.getEnergyCapacity() : 0;
    }

    @Override
    public void onChunkUnload() {
        delegates.values().forEach(ITileDelegate::unload);
        isLoaded = false;
        super.onChunkUnload();
    }

    @Override
    public void onChunkLoad() {
        if (!isLoaded) {
            isLoaded = true;
            delegates.values().forEach(ITileDelegate::load);
        }
        super.onChunkLoad();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        isLoaded = false;
        delegates.values().forEach(ITileDelegate::unload);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        delegates.values().forEach(d -> d.readFromNBT(nbtTags));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        delegates.values().forEach(d -> d.writeToNBT(nbtTags));
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canOutputTo(ForgeDirection side) {
        return getOutputtingSides().contains(side);
    }

    @Override
    public boolean canReceiveEnergy(ForgeDirection side) {
        return false;
    }

    @Override
    public double transferEnergyToAcceptor(ForgeDirection side, double amount) {
        return 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return ((!worldObj.isRemote && structure != null)
                || (worldObj.isRemote && clientHasStructure))
            ? new FluidTankInfo[] { fluidTank.getInfo() }
            : PipeUtils.EMPTY;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (structure == null) {
            return 0;
        }
        if (resource.getFluid() == FluidRegistry.WATER) {
            return 0;
        }
        int filled = fluidTank.fill(resource, doFill);
        if (doFill) {
            structure.newSteamInput += filled;
        }

        if (filled < structure.getFluidCapacity() && structure.dumpMode != GasMode.IDLE) {
            filled = structure.getFluidCapacity();
        }

        return filled;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (fluid == FluidRegistry.getFluid("steam")) {
            return (
                (!worldObj.isRemote && structure != null)
                || (!worldObj.isRemote && clientHasStructure)
            );
        }

        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public String getInventoryName() {
        return LangUtils.localize("gui.industrialTurbine");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Optional<E> getDelegate(Class<E> type) {
        return Optional.ofNullable(ITileDelegate.IMPLEMENTATIONS.get(type)).map(delegates::get).filter(type::isInstance).map(o -> (E) o);
    }

}
