package mekanism.common.content.transporter;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import mekanism.common.PacketHandler;
import mekanism.common.content.transporter.Finder.ModIDFinder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TModIDFilter extends TransporterFilter {
    public String modID;

    @Override
    public boolean canFilter(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        return new ModIDFinder(modID).modifies(itemStack);
    }

    @Override
    public Finder getFinder() {
        return new ModIDFinder(modID);
    }

    @Override
    public void write(NBTTagCompound nbtTags) {
        super.write(nbtTags);

        nbtTags.setInteger("type", 3);
        nbtTags.setString("modID", modID);
    }

    @Override
    protected void read(NBTTagCompound nbtTags) {
        super.read(nbtTags);

        modID = nbtTags.getString("modID");
    }

    @Override
    public void write(ArrayList data) {
        data.add(3);

        super.write(data);

        data.add(modID);
    }

    @Override
    protected void read(ByteBuf dataStream) {
        super.read(dataStream);

        modID = PacketHandler.readString(dataStream);
    }

    @Override
    public int hashCode() {
        int code = 1;
        code = 31 * code + super.hashCode();
        code = 31 * code + modID.hashCode();
        return code;
    }

    @Override
    public boolean equals(Object filter) {
        return super.equals(filter) && filter instanceof TModIDFilter
            && ((TModIDFilter) filter).modID.equals(modID);
    }

    @Override
    public TModIDFilter clone() {
        TModIDFilter filter = new TModIDFilter();
        filter.color = color;
        filter.modID = modID;

        return filter;
    }
}
