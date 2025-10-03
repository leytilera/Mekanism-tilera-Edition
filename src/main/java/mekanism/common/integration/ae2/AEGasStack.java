package mekanism.common.integration.ae2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import appeng.api.AEApi;
import appeng.api.config.FuzzyMode;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAETagCompound;
import io.netty.buffer.ByteBuf;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import mekanism.api.me.IAEGasStack;
import mekanism.api.me.IGasStorageChannel;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class AEGasStack implements IAEGasStack {

    private Gas gas;
    private long stackSize = 0;
    private long requestable = 0;
    private boolean canCraft = false;

    public AEGasStack(IAEGasStack old) {
        this.gas = old.getGas();
        this.stackSize = old.getStackSize();
        this.requestable = old.getCountRequestable();
        this.canCraft = old.isCraftable();
    }

    public AEGasStack(GasStack gasStack) {
        this.gas = gasStack.getGas();
        this.stackSize = gasStack.amount;
        this.canCraft = false;
        this.requestable = 0;
    }

    public AEGasStack(NBTTagCompound nbt) {
        this.gas = Gas.readFromNBT(nbt);
        this.setStackSize(nbt.getLong("amount"));
        this.setCraftable(nbt.getBoolean("isCraftable"));
        this.setCountRequestable(nbt.getLong("countRequestable"));
    }

    public static AEGasStack fromPacket(ByteBuf data) throws IOException {
        int length = data.readInt();
        byte[] bytes = new byte[length];
        data.readBytes(bytes);

        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        NBTTagCompound nbt = CompressedStreamTools.read(inputStream);
        return new AEGasStack(nbt);
    }

    @Override
    public void add(IAEGasStack is) {
        if (is == null) return;
        this.incStackSize(is.getStackSize());
        this.setCountRequestable(this.getCountRequestable() + is.getCountRequestable());
        this.setCraftable(this.isCraftable() || is.isCraftable());
    }

    @Override
    public long getStackSize() {
        return this.stackSize;
    }

    @Override
    public IAEGasStack setStackSize(long stackSize) {
        this.stackSize = stackSize;
        return this;
    }

    @Override
    public long getCountRequestable() {
        return this.requestable;
    }

    @Override
    public IAEGasStack setCountRequestable(long countRequestable) {
        this.requestable = countRequestable;
        return this;
    }

    @Override
    public boolean isCraftable() {
        return this.canCraft;
    }

    @Override
    public IAEGasStack setCraftable(boolean isCraftable) {
        this.canCraft = isCraftable;
        return this;
    }

    @Override
    public IAEGasStack reset() {
        this.canCraft = false;
        this.stackSize = 0;
        this.requestable = 0;
        return this;
    }

    @Override
    public boolean isMeaningful() {
        return this.stackSize != 0 || this.getCountRequestable() > 0 || this.isCraftable();
    }

    @Override
    public void incStackSize(long i) {
        this.stackSize += i;
    }

    @Override
    public void decStackSize(long i) {
        this.stackSize -= i;
    }

    @Override
    public void incCountRequestable(long i) {
        this.requestable += i;
    }

    @Override
    public void decCountRequestable(long i) {
        this.requestable -= i;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        gas.write(nbt);
        nbt.setLong("amount", getStackSize());
        nbt.setBoolean("isCraftable", isCraftable());
        nbt.setLong("countRequestable", getCountRequestable());
    }

    @Override
    public boolean fuzzyComparison(Object st, FuzzyMode mode) {
        if (st instanceof IAEGasStack) {
            return this.gas == ((IAEGasStack)st).getGas();
        } else if(st instanceof GasStack) {
            return this.gas == ((GasStack)st).getGas();
        } else if (st instanceof Gas) {
            return this.gas == st;
        }
        return false;
    }

    @Override
    public void writeToPacket(ByteBuf data) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteOutputStream);
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        CompressedStreamTools.write(nbt, outputStream);

        byte[] bytes = byteOutputStream.toByteArray();
        int length = bytes.length;

        data.writeInt(length);
        data.writeBytes(bytes);
    }

    @Override
    public IAEGasStack copy() {
        return new AEGasStack(this);
    }

    @Override
    public IAEGasStack empty() {
        return this.copy().reset();
    }

    @Override
    public IAETagCompound getTagCompound() {
        return null;
    }

    @Override
    public IStorageChannel<?> getStorageChannel() {
        return AEApi.instance().storage().getStorageChannel(IGasStorageChannel.class);
    }

    @Override
    public GasStack getGasStack() {
        return new GasStack(gas, (int)Math.min(Integer.MAX_VALUE, stackSize));
    }

    @Override
    public Gas getGas() {
        return this.gas;
    }

}