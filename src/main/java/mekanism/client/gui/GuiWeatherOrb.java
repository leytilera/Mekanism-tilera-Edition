package mekanism.client.gui;

import mekanism.common.Mekanism;
import mekanism.common.network.PacketChangeWeather.ChangeWeatherMessage;
import mekanism.common.network.PacketChangeWeather.WeatherType;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiWeatherOrb extends GuiScreen {
    private static EntityPlayer player;
    private int xSize;
    private int ySize;

    public GuiWeatherOrb(final EntityPlayer entityplayer) {
        this.xSize = 176;
        this.ySize = 166;
        GuiWeatherOrb.player = entityplayer;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(
            new GuiButton(0, this.width / 2 - 80, this.height / 2 - 65, 50, 20, "Clear")
        );
        this.buttonList.add(
            new GuiButton(1, this.width / 2 - 80, this.height / 2 - 35, 50, 20, "Storm")
        );
        this.buttonList.add(
            new GuiButton(2, this.width / 2 + 5, this.height / 2 - 65, 50, 20, "Haze")
        );
        this.buttonList.add(
            new GuiButton(3, this.width / 2 + 5, this.height / 2 - 35, 50, 20, "Rain")
        );
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(
            MekanismUtils.getResource(ResourceType.GUI, "GuiWeatherOrb.png")
        );
        this.drawTexturedModalRect(
            this.width / 2 - 100, this.height / 2 - 100, 0, 0, xSize, ySize
        );
        this.drawString(
            this.fontRendererObj,
            "Weather Orb",
            this.width / 2 - 45,
            this.height / 2 - 95,
            16777215
        );
        super.drawScreen(i, j, f);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
    }

    @Override
    public void keyTyped(final char c, final int i) {
        if (i == 1) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void actionPerformed(final GuiButton guibutton) {
        if (guibutton.id == 0) {
            MekanismUtils.doFakeEntityExplosion(GuiWeatherOrb.player);
            Mekanism.packetHandler.sendToServer(new ChangeWeatherMessage(WeatherType.CLEAR
            ));
            this.mc.thePlayer.closeScreen();
        }
        if (guibutton.id == 1) {
            MekanismUtils.doFakeEntityExplosion(GuiWeatherOrb.player);
            Mekanism.packetHandler.sendToServer(new ChangeWeatherMessage(WeatherType.STORM
            ));
            this.mc.thePlayer.closeScreen();
        }
        if (guibutton.id == 2) {
            MekanismUtils.doFakeEntityExplosion(GuiWeatherOrb.player);
            Mekanism.packetHandler.sendToServer(new ChangeWeatherMessage(WeatherType.HAZE)
            );
            this.mc.thePlayer.closeScreen();
        }
        if (guibutton.id == 3) {
            MekanismUtils.doFakeEntityExplosion(GuiWeatherOrb.player);
            Mekanism.packetHandler.sendToServer(new ChangeWeatherMessage(WeatherType.RAIN)
            );
            this.mc.thePlayer.closeScreen();
        }
    }
}
