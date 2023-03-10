package mekanism.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.EnumColor;
import mekanism.common.Mekanism;
import mekanism.common.base.IModule;
import mekanism.common.util.MekanismUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class GuiCredits extends GuiScreen {
    private static String updateProgress = "";

    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72 + 12, "Update"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Cancel"));
    }

    public static void updateInfo(String info) {
        updateProgress = info;
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0) {
            updateProgress = "You already have the latest version.";
        } else if (guibutton.id == 1) {
            mc.displayGuiScreen(null);
        }
    }

    public void writeText(String text, int yAxis) {
        drawString(
            fontRendererObj,
            text,
            width / 2 - 140,
            (height / 4 - 60) + 20 + yAxis,
            0xa0a0a0
        );
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        drawDefaultBackground();
        drawCenteredString(
            fontRendererObj,
            EnumColor.DARK_BLUE + "Mekanism" + EnumColor.GREY + " by aidancbrady",
            width / 2,
            (height / 4 - 60) + 20,
            0xffffff
        );

        writeText(
            EnumColor.INDIGO + "Mekanism " + EnumColor.GREY + Mekanism.versionNumber, 36
        );

        int size = 36;

        for (IModule module : Mekanism.modulesLoaded) {
            size += 9;
            writeText(
                EnumColor.INDIGO + "Mekanism" + module.getName() + EnumColor.GREY + " "
                    + module.getVersion(),
                size
            );
        }

        writeText(
            EnumColor.GREY + "*Code, textures, and ideas by aidancbrady", size + 27
        );
        writeText(
            EnumColor.GREY + "Recent news: " + EnumColor.DARK_BLUE
                + (!Mekanism.recentNews.contains("null") ? Mekanism.recentNews
                                                         : "couldn't access."),
            size + 36
        );
        writeText(EnumColor.GREY + updateProgress, size + 45);

        super.drawScreen(mouseX, mouseY, partialTick);
    }
}
