package mekanism.generators.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.MekanismConfig;
import mekanism.client.model.IModelOnOff;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.client.model.LegacyModelHeatGenerator;
import mekanism.generators.client.model.ModelHeatGenerator;
import mekanism.generators.common.tile.TileEntityHeatGenerator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHeatGenerator extends TileEntitySpecialRenderer {
    private IModelOnOff model = MekanismConfig.client.modelType.createModel(
        ModelHeatGenerator::new, LegacyModelHeatGenerator::new
    );

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double x, double y, double z, float partialTick
    ) {
        renderAModelAt((TileEntityHeatGenerator) tileEntity, x, y, z, partialTick);
    }

    private void renderAModelAt(
        TileEntityHeatGenerator tileEntity,
        double x,
        double y,
        double z,
        float partialTick
    ) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        bindTexture(MekanismUtils.getResource(
            ResourceType.RENDER, this.model.getTextureNameForState(tileEntity.isActive)
        ));

        switch (MekanismConfig.client.modelType.mapFacing(tileEntity.facing)) {
            case 2:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        model.render(0.0625F, tileEntity.isActive, field_147501_a.field_147553_e);
        GL11.glPopMatrix();
    }
}
