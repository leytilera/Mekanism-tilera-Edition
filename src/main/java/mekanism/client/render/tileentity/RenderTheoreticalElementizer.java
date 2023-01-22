package mekanism.client.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.client.model.ModelTheoreticalElementizer;
import mekanism.common.tile.TileEntityTheoreticalElementizer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTheoreticalElementizer extends TileEntitySpecialRenderer {
    private ModelTheoreticalElementizer model;

    public RenderTheoreticalElementizer() {
        this.model = new ModelTheoreticalElementizer();
    }

    @Override
    public void renderTileEntityAt(
        final TileEntity tileEntity,
        final double x,
        final double y,
        final double z,
        final float partialTick
    ) {
        this.renderAModelAt(
            (TileEntityTheoreticalElementizer) tileEntity, x, y, z, partialTick
        );
    }

    private void renderAModelAt(
        final TileEntityTheoreticalElementizer tileEntity,
        final double x,
        final double y,
        final double z,
        final float partialTick
    ) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
        bindTexture(
            MekanismUtils.getResource(ResourceType.RENDER, "TheoreticalElementizer.png")
        );
        switch (tileEntity.facing) {
            case 2: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 3: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 4: {
                GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 5: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
        }
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 1.0f);
        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
        GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
        this.model.render(0.0625f);
        GL11.glPopMatrix();
    }
}
