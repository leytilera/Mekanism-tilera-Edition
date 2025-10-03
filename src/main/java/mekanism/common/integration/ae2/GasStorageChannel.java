package mekanism.common.integration.ae2;

import java.io.IOException;

import appeng.api.storage.data.IItemList;
import io.netty.buffer.ByteBuf;
import mekanism.api.gas.GasStack;
import mekanism.api.me.IAEGasStack;
import mekanism.api.me.IGasStorageChannel;
import net.minecraft.nbt.NBTTagCompound;

public class GasStorageChannel implements IGasStorageChannel {

    @Override
    public Class<IAEGasStack> getType() {
        return IAEGasStack.class;
    }

    @Override
    public IItemList<IAEGasStack> createList() {
        return new GasList();
    }

    @Override
    public IAEGasStack createStack(Object input) {
        if (input instanceof GasStack) {
            return new AEGasStack((GasStack)input);
        } else if (input instanceof AEGasStack) {
            return new AEGasStack((AEGasStack)input);
        }
        return null;
    }

    @Override
    public IAEGasStack readFromPacket(ByteBuf input) throws IOException {
        return AEGasStack.fromPacket(input);
    }

    @Override
    public IAEGasStack createFromNBT(NBTTagCompound nbt) {
        return new AEGasStack(nbt);
    }
    
}
