package mekanism.generators.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.MekanismConfig;
import mekanism.client.ModelMekanismBase;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.client.model.LegacyModelGasGenerator;
import mekanism.generators.client.model.ModelGasGenerator;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderGasGenerator extends TileEntitySpecialRenderer {
    private ModelMekanismBase model = MekanismConfig.client.modelType.createModel(
        ModelGasGenerator::new, LegacyModelGasGenerator::new
    );

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double x, double y, double z, float partialTick
    ) {
        renderAModelAt((TileEntityGasGenerator) tileEntity, x, y, z, partialTick);
    }

    private void renderAModelAt(
        TileEntityGasGenerator tileEntity, double x, double y, double z, float partialTick
    ) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        bindTexture(
            MekanismUtils.getResource(ResourceType.RENDER, this.model.getTextureName())
        );

        switch (MekanismConfig.client.modelType.mapFacing(tileEntity.facing)) {
            case 2:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 1.0F, 1.0F);
        GL11.glRotatef(90F, -1.0F, 0.0F, 0.0F);
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        model.render(0.0625F);
        GL11.glPopMatrix();
    }
}
