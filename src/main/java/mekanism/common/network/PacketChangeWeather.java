package mekanism.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mekanism.common.item.ItemWeatherOrb;
import mekanism.common.network.PacketChangeWeather.ChangeWeatherMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PacketChangeWeather
    implements IMessageHandler<ChangeWeatherMessage, IMessage> {
    public static enum WeatherType {
        CLEAR,
        HAZE,
        RAIN,
        STORM;

        public static WeatherType valueOf(int ordinal) {
            if (ordinal >= WeatherType.values().length || ordinal < 0)
                return null;
            return WeatherType.values()[ordinal];
        }
    }

    public static class ChangeWeatherMessage implements IMessage {
        public WeatherType type;

        public ChangeWeatherMessage() {}

        public ChangeWeatherMessage(WeatherType type) {
            this.type = type;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            type = WeatherType.valueOf(buf.readInt());
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(type.ordinal());
        }
    }

    @Override
    public IMessage onMessage(ChangeWeatherMessage message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        ItemStack using = player.getCurrentEquippedItem();

        if (using != null && using.getItem() instanceof ItemWeatherOrb) {
            using.damageItem(4999, player);
            switch (message.type) {
                case CLEAR:
                    player.worldObj.getWorldInfo().setRaining(false);
                    player.worldObj.getWorldInfo().setThundering(false);
                    break;
                case HAZE:
                    player.worldObj.getWorldInfo().setThundering(true);
                    break;
                case RAIN:
                    player.worldObj.getWorldInfo().setRaining(true);
                    break;
                case STORM:
                    player.worldObj.getWorldInfo().setRaining(true);
                    player.worldObj.getWorldInfo().setThundering(true);
                    break;
            }
        }

        return null;
    }
}
