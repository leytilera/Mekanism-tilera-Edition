package mekanism.generators.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.MekanismConfig;
import mekanism.client.ModelMekanismBase;
import mekanism.client.model.IModelOnOff;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.client.model.LegacyModelAdvancedSolarGenerator;
import mekanism.generators.client.model.LegacyModelBioGenerator;
import mekanism.generators.client.model.LegacyModelGasGenerator;
import mekanism.generators.client.model.LegacyModelHeatGenerator;
import mekanism.generators.client.model.LegacyModelSolarGenerator;
import mekanism.generators.client.model.LegacyModelWindGenerator;
import mekanism.generators.client.model.ModelAdvancedSolarGenerator;
import mekanism.generators.client.model.ModelBioGenerator;
import mekanism.generators.client.model.ModelGasGenerator;
import mekanism.generators.client.model.ModelHeatGenerator;
import mekanism.generators.client.model.ModelSolarGenerator;
import mekanism.generators.client.model.ModelWindGenerator;
import mekanism.generators.common.GeneratorsBlocks;
import mekanism.generators.common.block.BlockGenerator.GeneratorType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BlockRenderingHandler implements ISimpleBlockRenderingHandler {
    private Minecraft mc = Minecraft.getMinecraft();

    public ModelMekanismBase advancedSolarGenerator
        = MekanismConfig.client.modelType.createModel(
            ModelAdvancedSolarGenerator::new, LegacyModelAdvancedSolarGenerator::new
        );
    public ModelMekanismBase solarGenerator = MekanismConfig.client.modelType.createModel(
        ModelSolarGenerator::new, LegacyModelSolarGenerator::new
    );
    public ModelMekanismBase bioGenerator = MekanismConfig.client.modelType.createModel(
        ModelBioGenerator::new, LegacyModelBioGenerator::new
    );
    public IModelOnOff heatGenerator = MekanismConfig.client.modelType.createModel(
        ModelHeatGenerator::new, LegacyModelHeatGenerator::new
    );
    public ModelMekanismBase gasGenerator = MekanismConfig.client.modelType.createModel(
        ModelGasGenerator::new, LegacyModelGasGenerator::new
    );
    public ModelMekanismBase windGenerator = MekanismConfig.client.modelType.createModel(
        ModelWindGenerator::new, LegacyModelWindGenerator::new
    );

    @Override
    public void
    renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);

        if (block == GeneratorsBlocks.Generator) {
            if (metadata == GeneratorType.BIO_GENERATOR.meta) {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslated(0.0F, -1.0F, 0.0F);
                mc.renderEngine.bindTexture(MekanismUtils.getResource(
                    ResourceType.RENDER, bioGenerator.getTextureName()
                ));
                bioGenerator.render(0.0625F);
            } else if (metadata == GeneratorType.ADVANCED_SOLAR_GENERATOR.meta) {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.2F, 0.0F);
                mc.renderEngine.bindTexture(MekanismUtils.getResource(
                    ResourceType.RENDER, advancedSolarGenerator.getTextureName()
                ));
                advancedSolarGenerator.render(0.022F);
            } else if (metadata == GeneratorType.SOLAR_GENERATOR.meta) {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
                GL11.glTranslated(0.0F, -1.0F, 0.0F);
                mc.renderEngine.bindTexture(MekanismUtils.getResource(
                    ResourceType.RENDER, solarGenerator.getTextureName()
                ));
                solarGenerator.render(0.0625F);
            } else if (metadata == GeneratorType.HEAT_GENERATOR.meta) {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslated(0.0F, -1.0F, 0.0F);
                mc.renderEngine.bindTexture(MekanismUtils.getResource(
                    ResourceType.RENDER, heatGenerator.getTextureName()
                ));
                heatGenerator.render(0.0625F, false, mc.renderEngine);
            } else if (metadata == GeneratorType.GAS_GENERATOR.meta) {
                GL11.glRotatef(180F, 0.0F, 1.0F, 1.0F);
                GL11.glRotatef(90F, -1.0F, 0.0F, 0.0F);
                GL11.glTranslated(0.0F, -1.0F, 0.0F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                mc.renderEngine.bindTexture(MekanismUtils.getResource(
                    ResourceType.RENDER, gasGenerator.getTextureName()
                ));
                gasGenerator.render(0.0625F);
            } else if (metadata == GeneratorType.WIND_GENERATOR.meta) {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.4F, 0.0F);
                mc.renderEngine.bindTexture(MekanismUtils.getResource(
                    ResourceType.RENDER, windGenerator.getTextureName()
                ));
                windGenerator.render(0.016F);
            } else if (metadata != 2) {
                MekanismRenderer.renderItem(renderer, metadata, block);
            }
        }

        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(
        IBlockAccess world,
        int x,
        int y,
        int z,
        Block block,
        int modelId,
        RenderBlocks renderer
    ) {
        //Handled by CTMRenderingHandler
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int meta) {
        return true;
    }

    @Override
    public int getRenderId() {
        return GeneratorsClientProxy.GENERATOR_RENDER_ID;
    }
}
