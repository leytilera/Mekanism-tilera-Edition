package mekanism.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mekanism.common.item.ItemStopwatch;
import mekanism.common.network.PacketChangeTime.ChangeTimeMessage;
import mekanism.common.util.MekanismUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PacketChangeTime implements IMessageHandler<ChangeTimeMessage, IMessage> {
    public static class ChangeTimeMessage implements IMessage {
        public int time;

        public ChangeTimeMessage() {}

        public ChangeTimeMessage(int time) {
            this.time = time;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            time = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(time);
        }
    }

    @Override
    public IMessage onMessage(ChangeTimeMessage message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        ItemStack using = player.getCurrentEquippedItem();

        if (using != null && using.getItem() instanceof ItemStopwatch) {
            using.damageItem(4999, player);
            MekanismUtils.setHourForward(player.worldObj, message.time);
        }

        return null;
    }
}
