package mekanism.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mekanism.api.Coord4D;
import mekanism.api.transmitters.ITransmitterTile;
import mekanism.common.base.FluidHandlerWrapper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public final class PipeUtils {
    public static final FluidTankInfo[] EMPTY = new FluidTankInfo[] {};

    public static boolean isValidAcceptorOnSide(TileEntity tile, ForgeDirection side) {
        if (tile instanceof ITransmitterTile
            || FluidHandlerWrapper.get(tile) == null
            )
            return false;

        FluidHandlerWrapper container = FluidHandlerWrapper.get(tile);
        return container.isValidAcceptor(side.getOpposite());
    }

    /**
     * Gets all the acceptors around a tile entity.
     * @param tileEntity - center tile entity
     * @return array of IFluidHandlers
     */
    public static IFluidHandler[] getConnectedAcceptors(TileEntity tileEntity) {
        IFluidHandler[] acceptors = new IFluidHandler[6];

        for (ForgeDirection orientation : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity acceptor = Coord4D.get(tileEntity)
                                      .getFromSide(orientation)
                                      .getTileEntity(tileEntity.getWorldObj());
            FluidHandlerWrapper wrapper = FluidHandlerWrapper.get(acceptor);
            if (wrapper != null) 
            acceptors[orientation.ordinal()] = wrapper;
        }

        return acceptors;
    }

    /**
     * Emits fluid from a central block by splitting the received stack among the sides
     * given.
     * @param sides - the list of sides to output from
     * @param stack - the stack to output
     * @param from - the TileEntity to output from
     * @return the amount of gas emitted
     */
    public static int
    emit(List<ForgeDirection> sides, FluidStack stack, TileEntity from) {
        if (stack == null) {
            return 0;
        }

        List<IFluidHandler> availableAcceptors = new ArrayList<IFluidHandler>();
        IFluidHandler[] possibleAcceptors = getConnectedAcceptors(from);

        for (int i = 0; i < possibleAcceptors.length; i++) {
            IFluidHandler handler = possibleAcceptors[i];

            if (handler != null
                && handler.canFill(
                    ForgeDirection.getOrientation(i).getOpposite(), stack.getFluid()
                )) {
                availableAcceptors.add(handler);
            }
        }

        Collections.shuffle(availableAcceptors);

        int toSend = stack.amount;
        int prevSending = toSend;

        if (!availableAcceptors.isEmpty()) {
            int divider = availableAcceptors.size();
            int remaining = toSend % divider;
            int sending = (toSend - remaining) / divider;

            for (IFluidHandler acceptor : availableAcceptors) {
                int currentSending = sending;

                if (remaining > 0) {
                    currentSending++;
                    remaining--;
                }

                ForgeDirection dir
                    = ForgeDirection
                          .getOrientation(
                              Arrays.asList(possibleAcceptors).indexOf(acceptor)
                          )
                          .getOpposite();
                toSend -= acceptor.fill(dir, copy(stack, currentSending), true);
            }
        }

        return prevSending - toSend;
    }

    public static FluidStack copy(FluidStack fluid, int amount) {
        FluidStack ret = fluid.copy();
        ret.amount = amount;

        return ret;
    }
}
