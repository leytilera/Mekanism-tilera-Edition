package mekanism.common.base;

import java.util.Arrays;
import java.util.Map;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityProxyBase;

import api.hbm.fluidmk2.IFluidConnectorMK2;
import api.hbm.fluidmk2.IFluidProviderMK2;
import api.hbm.fluidmk2.IFluidReceiverMK2;
import api.hbm.fluidmk2.IFluidUserMK2;
import mekanism.api.Coord4D;
import mekanism.common.Mekanism;
import mekanism.common.integration.HBMIntegration;
import net.anvilcraft.anvillib.garbagecollection.GCManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class FluidHandlerWrapper implements IFluidHandler {
    public Coord4D coord;
    private static Map<TileEntity, FluidHandlerWrapper> wrappers = GCManager.INSTANCE.createGarbageCollectedMap((te, w) -> te.isInvalid() || !te.hasWorldObj(), (m) -> m.clear());

    public static FluidHandlerWrapper get(TileEntity tileEntity) {
        if (tileEntity == null) {
            return null;
        } else if (tileEntity != null && tileEntity.getWorldObj() == null) {
            return null;
        } else if (wrappers.containsKey(tileEntity)) {
            return wrappers.get(tileEntity);
        }

        FluidHandlerWrapper wrapper = null;

        if (tileEntity instanceof IFluidHandler) {
            wrapper = new ForgeHandler((IFluidHandler) tileEntity);
        } else if (Mekanism.hooks.HBMLoaded && tileEntity instanceof IFluidConnectorMK2) {
            wrapper = new HBMHandler((IFluidConnectorMK2) tileEntity);
        }

        if (wrapper != null) {
            wrapper.coord = Coord4D.get(tileEntity);
            wrappers.put(tileEntity, wrapper);
        }

        return wrapper;
    }

    public abstract boolean isValidAcceptor(ForgeDirection side);

    public static class ForgeHandler extends FluidHandlerWrapper {

        private IFluidHandler inner;

        public ForgeHandler(IFluidHandler inner) {
            this.inner = inner;
        }

        @Override
        public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
            return inner.canDrain(arg0, arg1);
        }

        @Override
        public boolean canFill(ForgeDirection arg0, Fluid arg1) {
            return inner.canFill(arg0, arg1);
        }

        @Override
        public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
            return inner.drain(arg0, arg1, arg2);
        }

        @Override
        public FluidStack drain(ForgeDirection arg0, int arg1, boolean arg2) {
            return inner.drain(arg0, arg1, arg2);
        }

        @Override
        public int fill(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
            return inner.fill(arg0, arg1, arg2);
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
            return inner.getTankInfo(arg0);
        }

        @Override
        public boolean isValidAcceptor(ForgeDirection side) {
            FluidTankInfo[] infoArray = inner.getTankInfo(side);
            if (inner.canDrain(side, FluidRegistry.WATER)
                || inner.canFill(
                    side, FluidRegistry.WATER
                )) //I hesitate to pass null to these.
            {
                return true;
            } else if (infoArray != null && infoArray.length > 0) {
                for (FluidTankInfo info : infoArray) {
                    if (info != null) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

    public static class HBMHandler extends FluidHandlerWrapper {
        private IFluidConnectorMK2 inner;
        private IFluidConnectorMK2 connector;

        public HBMHandler(IFluidConnectorMK2 inner) {
            this.inner = inner;
            this.connector = inner;
            if (inner instanceof TileEntityProxyBase) {
                TileEntity real = ((TileEntityProxyBase) inner).getTE();
                if (real instanceof IFluidConnectorMK2)
                    this.inner = (IFluidConnectorMK2) real;
            }
        }

        @Override
        public boolean canDrain(ForgeDirection direction, Fluid fluid) {
            if (!(this.inner instanceof IFluidProviderMK2))
                return false;
            FluidType hbmFluid = HBMIntegration.INSTANCE.convert(fluid);
            if (hbmFluid == null)
                return false;

            if (!this.connector.canConnect(hbmFluid, direction))
                return false;

            return ((IFluidProviderMK2) this.inner).getFluidAvailable(hbmFluid, 0) > 0;
        }

        @Override
        public boolean canFill(ForgeDirection direction, Fluid fluid) {
            if (!(this.inner instanceof IFluidReceiverMK2))
                return false;
            FluidType hbmFluid = HBMIntegration.INSTANCE.convert(fluid);
            if (hbmFluid == null)
                return false;

            if (!this.connector.canConnect(hbmFluid, direction))
                return false;

            return ((IFluidReceiverMK2) this.inner).getDemand(hbmFluid, 0) > 0;
        }

        @Override
        public FluidStack drain(ForgeDirection direction, FluidStack fstack, boolean doDrain) {
            if (!(this.inner instanceof IFluidProviderMK2))
                return null;
            FluidType hbmFluid = HBMIntegration.INSTANCE.convert(fstack.getFluid());
            if (hbmFluid == null)
                return null;

            if (!this.connector.canConnect(hbmFluid, direction))
                return null;

            int avail = (int) ((IFluidProviderMK2) this.inner).getFluidAvailable(hbmFluid, 0);
            int toDrain = Math.min(avail, fstack.amount);
            if (doDrain)
                ((IFluidProviderMK2) this.inner).useUpFluid(hbmFluid, 0, toDrain);
            return toDrain > 0 ? new FluidStack(fstack, toDrain) : null;
        }

        @Override
        public FluidStack drain(ForgeDirection direction, int amount, boolean doDrain) {
            if (!(this.inner instanceof IFluidProviderMK2))
                return null;

            for (FluidTank tank : ((IFluidProviderMK2) this.inner).getAllTanks()) {
                if (!this.connector.canConnect(tank.getTankType(), direction))
                    continue;

                int avail = (int) ((IFluidProviderMK2) this.inner)
                        .getFluidAvailable(tank.getTankType(), 0);
                if (avail == 0)
                    continue;
                int toDrain = Math.min(avail, amount);

                Fluid forgeFluid = HBMIntegration.INSTANCE.convert(tank.getTankType());
                if (forgeFluid == null)
                    continue;

                if (doDrain)
                    ((IFluidProviderMK2) this.inner)
                            .useUpFluid(tank.getTankType(), 0, toDrain);
                return new FluidStack(forgeFluid, toDrain);
            }
            return null;
        }

        @Override
        public int fill(ForgeDirection direction, FluidStack fstack, boolean doFill) {
            if (!(this.inner instanceof IFluidReceiverMK2))
                return 0;
            FluidType hbmFluid = HBMIntegration.INSTANCE.convert(fstack.getFluid());
            if (hbmFluid == null)
                return 0;

            if (doFill) {
                return fstack.amount
                        - (int) ((IFluidReceiverMK2) this.inner).transferFluid(hbmFluid, 0, fstack.amount);
            } else {
                return (int) ((IFluidReceiverMK2) this.inner).getDemand(hbmFluid, 0);
            }
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
            if (!(this.inner instanceof IFluidUserMK2))
                return new FluidTankInfo[0];

            return Arrays.stream(((IFluidUserMK2) this.inner).getAllTanks())
                    .map(
                            t -> new FluidTankInfo(
                                    new FluidStack(
                                            HBMIntegration.INSTANCE.convert(t.getTankType()),
                                            t.getFill()),
                                    t.getMaxFill()))
                    .toArray(FluidTankInfo[]::new);
        }

        @Override
        public boolean isValidAcceptor(ForgeDirection side) {
            return true;
        }
    }
}
