package mekanism.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.EnumColor;
import mekanism.api.gas.GasStack;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.tile.TileEntityTheoreticalElementizer;
import net.minecraft.entity.player.InventoryPlayer;

@SideOnly(Side.CLIENT)
public class GuiTheoreticalElementizer
    extends GuiAdvancedElectricMachine //TODO implement specific GUI
{
    public GuiTheoreticalElementizer(
        final InventoryPlayer inventory, final TileEntityTheoreticalElementizer tentity
    ) {
        super(inventory, tentity);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String displayText = "";
        if (super.tileEntity.isActive) {
            displayText = "Status: "
                + (int) (super.tileEntity.operatingTicks
                         / (float) super.tileEntity.ticksRequired * 100.0f)
                + "%";
        } else {
            displayText = "Status: " + EnumColor.DARK_RED + "Off";
        }
        this.fontRendererObj.drawString(displayText, 80, 60, 4210752);
    }

    @Override
    public void displayGauge(int xPos, int yPos, int sizeX, int sizeY, GasStack gas) {
        if (gas == null) {
            return;
        }

        int guiWidth = (width - xSize) / 2;
        int guiHeight = (height - ySize) / 2;

        mc.renderEngine.bindTexture(MekanismRenderer.getBlocksTexture());
        drawTexturedModelRectFromIcon(
            guiWidth + xPos, guiHeight + yPos, gas.getGas().getIcon(), sizeX, sizeY
        );
    }
}
