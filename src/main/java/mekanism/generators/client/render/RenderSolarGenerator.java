package mekanism.generators.client.render;

import mekanism.api.MekanismConfig;
import mekanism.client.ModelMekanismBase;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.client.model.LegacyModelSolarGenerator;
import mekanism.generators.client.model.ModelSolarGenerator;
import mekanism.generators.common.tile.TileEntitySolarGenerator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderSolarGenerator extends TileEntitySpecialRenderer {
    private ModelMekanismBase model = MekanismConfig.client.modelType.createModel(
        ModelSolarGenerator::new, LegacyModelSolarGenerator::new
    );

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double x, double y, double z, float partialTick
    ) {
        renderAModelAt((TileEntitySolarGenerator) tileEntity, x, y, z, partialTick);
    }

    private void renderAModelAt(
        TileEntitySolarGenerator tileEntity,
        double x,
        double y,
        double z,
        float partialTick
    ) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        bindTexture(
            MekanismUtils.getResource(ResourceType.RENDER, this.model.getTextureName())
        );

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        model.render(0.0625F);
        GL11.glPopMatrix();
    }
}
