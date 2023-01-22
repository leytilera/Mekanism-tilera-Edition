package mekanism.common.tile;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import mekanism.common.Tier.InductionProviderTier;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityInductionProvider extends TileEntityBasicBlock {
    public InductionProviderTier tier = InductionProviderTier.BASIC;

    @Override
    public void onUpdate() {}

    @Override
    public boolean canUpdate() {
        return false;
    }

    public String getInventoryName() {
        return LangUtils.localize(
            getBlockType().getUnlocalizedName() + ".InductionProvider"
            + tier.getBaseTier().getName() + ".name"
        );
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        if (worldObj.isRemote) {
            tier = InductionProviderTier.values()[dataStream.readInt()];

            super.handlePacketData(dataStream);

            MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        data.add(tier.ordinal());

        super.getNetworkedData(data);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        tier = InductionProviderTier.values()[nbtTags.getInteger("tier")];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setInteger("tier", tier.ordinal());
    }
}
