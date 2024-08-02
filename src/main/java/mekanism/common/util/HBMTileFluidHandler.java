package mekanism.common.util;

import java.util.Arrays;

import api.hbm.fluid.IFluidConnector;
import api.hbm.fluid.IFluidUser;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import mekanism.api.Coord4D;
import mekanism.common.integration.HBMIntegration;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class HBMTileFluidHandler implements IFluidHandler {
    public IFluidConnector inner;
    public Coord4D pos;

    public HBMTileFluidHandler(IFluidConnector inner) {
        this.inner = inner;
        this.pos = Coord4D.get((TileEntity) inner);
    }

    @Override
    public boolean canDrain(ForgeDirection direction, Fluid fluid) {
        if (!(this.inner instanceof IFluidUser))
            return false;
        FluidType hbmFluid = HBMIntegration.INSTANCE.fluidMap.get(fluid);
        if (hbmFluid == null)
            return false;

        if (!this.inner.canConnect(hbmFluid, direction))
            return false;

        return ((IFluidUser) this.inner).getTotalFluidForSend(hbmFluid, 0) > 0;
    }

    @Override
    public boolean canFill(ForgeDirection direction, Fluid fluid) {
        FluidType hbmFluid = HBMIntegration.INSTANCE.fluidMap.get(fluid);
        if (hbmFluid == null)
            return false;

        if (!this.inner.canConnect(hbmFluid, direction))
            return false;

        return this.inner.getDemand(hbmFluid, 0) > 0;
    }

    @Override
    public FluidStack
    drain(ForgeDirection direction, FluidStack fstack, boolean doDrain) {
        if (!(this.inner instanceof IFluidUser))
            return null;
        FluidType hbmFluid = HBMIntegration.INSTANCE.fluidMap.get(fstack.getFluid());
        if (hbmFluid == null)
            return null;

        if (!this.inner.canConnect(hbmFluid, direction))
            return null;

        int avail = (int) ((IFluidUser) this.inner).getTotalFluidForSend(hbmFluid, 0);
        int toDrain = Math.min(avail, fstack.amount);
        if (doDrain)
            ((IFluidUser) this.inner).removeFluidForTransfer(hbmFluid, 0, toDrain);
        return toDrain > 0 ? new FluidStack(fstack, toDrain) : null;
    }

    @Override
    public FluidStack drain(ForgeDirection direction, int amount, boolean doDrain) {
        if (!(this.inner instanceof IFluidUser))
            return null;

        for (FluidTank tank : ((IFluidUser) this.inner).getAllTanks()) {
            if (!this.inner.canConnect(tank.getTankType(), direction))
                continue;

            int avail = (int) ((IFluidUser) this.inner)
                            .getTotalFluidForSend(tank.getTankType(), 0);
            if (avail == 0)
                continue;
            int toDrain = Math.min(avail, amount);

            Fluid forgeFluid
                = HBMIntegration.INSTANCE.fluidMap.inverse().get(tank.getTankType());
            if (forgeFluid == null)
                continue;

            if (doDrain)
                ((IFluidUser) this.inner)
                    .removeFluidForTransfer(tank.getTankType(), 0, toDrain);
            return new FluidStack(forgeFluid, toDrain);
        }
        return null;
    }

    @Override
    public int fill(ForgeDirection direction, FluidStack fstack, boolean doFill) {
        FluidType hbmFluid = HBMIntegration.INSTANCE.fluidMap.get(fstack.getFluid());
        if (hbmFluid == null)
            return 0;

        if (doFill) {
            return fstack.amount
                - (int) this.inner.transferFluid(hbmFluid, 0, fstack.amount);
        } else {
            return (int) this.inner.getDemand(hbmFluid, 0);
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        if (!(this.inner instanceof IFluidUser))
            return new FluidTankInfo[0];

        return Arrays.stream(((IFluidUser) this.inner).getAllTanks())
            .map(
                t
                -> new FluidTankInfo(
                    new FluidStack(
                        HBMIntegration.INSTANCE.fluidMap.inverse().get(t.getTankType()),
                        t.getFill()
                    ),
                    t.getMaxFill()
                )
            )
            .toArray(FluidTankInfo[] ::new);
    }
}
