package mekanism.client.gui.element;

import java.util.ArrayList;

import codechicken.lib.vec.Rectangle4i;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.Coord4D;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.sound.SoundHandler;
import mekanism.common.Mekanism;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.tile.TileEntityLaserAmplifier;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiAmplifierTab extends GuiElement {
    public TileEntity tileEntity;

    public GuiAmplifierTab(IGuiWrapper gui, TileEntity tile, ResourceLocation def) {
        super(
            MekanismUtils.getResource(ResourceType.GUI_ELEMENT, "GuiAmplifierTab.png"),
            gui,
            def
        );

        tileEntity = tile;
    }

    @Override
    public Rectangle4i getBounds(int guiWidth, int guiHeight) {
        return new Rectangle4i(guiWidth - 26, guiHeight + 138, 26, 26);
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(RESOURCE);

        guiObj.drawTexturedRect(guiWidth - 26, guiHeight + 138, 0, 0, 26, 26);
        int outputOrdinal = ((TileEntityLaserAmplifier) tileEntity).outputMode.ordinal();

        if (xAxis >= -21 && xAxis <= -3 && yAxis >= 142 && yAxis <= 160) {
            guiObj.drawTexturedRect(
                guiWidth - 21, guiHeight + 142, 26 + 18 * outputOrdinal, 0, 18, 18
            );
        } else {
            guiObj.drawTexturedRect(
                guiWidth - 21, guiHeight + 142, 26 + 18 * outputOrdinal, 18, 18, 18
            );
        }

        mc.renderEngine.bindTexture(defaultLocation);
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        mc.renderEngine.bindTexture(RESOURCE);

        if (xAxis >= -21 && xAxis <= -3 && yAxis >= 142 && yAxis <= 160) {
            String text = LangUtils.localize("gui.redstoneOutput") + ": ";
            displayTooltip(
                text + ((TileEntityLaserAmplifier) tileEntity).outputMode.getName(),
                xAxis,
                yAxis
            );
        }

        mc.renderEngine.bindTexture(defaultLocation);
    }

    @Override
    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        if (button == 0) {
            if (xAxis >= -21 && xAxis <= -3 && yAxis >= 142 && yAxis <= 160) {
                ArrayList data = new ArrayList();
                data.add(3);

                Mekanism.packetHandler.sendToServer(
                    new TileEntityMessage(Coord4D.get(tileEntity), data)
                );
                SoundHandler.playSound("gui.button.press");
            }
        }
    }
}
