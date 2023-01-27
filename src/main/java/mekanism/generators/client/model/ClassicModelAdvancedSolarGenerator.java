package mekanism.generators.client.model;

import org.lwjgl.opengl.GL11;

import mekanism.client.ModelMekanismBase;
import net.minecraft.client.model.ModelRenderer;

public class ClassicModelAdvancedSolarGenerator extends ModelMekanismBase {
    ModelRenderer BASE;
    ModelRenderer SUPPORT_1_ROTATES;
    ModelRenderer MECHANISM_ROTATES;
    ModelRenderer PANEL_1_ROTATES;
    ModelRenderer PANEL_2_ROTATES;
    ModelRenderer PANEL_2_SUPPORT_1_ROTATES;
    ModelRenderer PANEL_2_SUPPORT_2_ROTATES;
    ModelRenderer PANEL_2_SUPPORT_3_ROTATES;
    ModelRenderer PANEL_2_SUPPORT_4_ROTATES;
    ModelRenderer PANEL_1_SUPPORT_1_ROTATES;
    ModelRenderer PANEL_1_SUPPORT_2_ROTATES;
    ModelRenderer PANEL_1_SUPPORT_3_ROTATES;
    ModelRenderer PANEL_1_SUPPORT_4_ROTATES;
    ModelRenderer SUPPORT_2_ROTATES;
    ModelRenderer SUPPORT_3_ROTATES;

    public ClassicModelAdvancedSolarGenerator() {
        super.textureWidth = 256;
        super.textureHeight = 256;
        this.BASE = new ModelRenderer(this, 0, 54);
        this.BASE.addBox(0.0F, 0.0F, 0.0F, 8, 6, 8);
        this.BASE.setRotationPoint(-4.0F, 18.0F, -4.0F);
        this.BASE.setTextureSize(256, 256);
        this.BASE.mirror = true;
        this.setRotation(this.BASE, 0.0F, 0.0F, 0.0F);
        this.SUPPORT_1_ROTATES = new ModelRenderer(this, 0, 72);
        this.SUPPORT_1_ROTATES.addBox(-2.0F, 0.0F, -2.0F, 4, 42, 4);
        this.SUPPORT_1_ROTATES.setRotationPoint(0.0F, -22.0F, 0.0F);
        this.SUPPORT_1_ROTATES.setTextureSize(256, 256);
        this.SUPPORT_1_ROTATES.mirror = true;
        this.setRotation(this.SUPPORT_1_ROTATES, 0.0F, 0.0F, 0.0F);
        this.MECHANISM_ROTATES = new ModelRenderer(this, 0, 121);
        this.MECHANISM_ROTATES.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        this.MECHANISM_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.MECHANISM_ROTATES.setTextureSize(256, 256);
        this.MECHANISM_ROTATES.mirror = true;
        this.setRotation(this.MECHANISM_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_1_ROTATES = new ModelRenderer(this, 0, 0);
        this.PANEL_1_ROTATES.addBox(3.0F, -1.0F, -24.0F, 19, 2, 48);
        this.PANEL_1_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_1_ROTATES.setTextureSize(256, 256);
        this.PANEL_1_ROTATES.mirror = true;
        this.setRotation(this.PANEL_1_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_2_ROTATES = new ModelRenderer(this, 0, 0);
        this.PANEL_2_ROTATES.addBox(-22.0F, -1.0F, -24.0F, 19, 2, 48);
        this.PANEL_2_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_2_ROTATES.setTextureSize(256, 256);
        this.PANEL_2_ROTATES.mirror = true;
        this.setRotation(this.PANEL_2_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_2_SUPPORT_1_ROTATES = new ModelRenderer(this, 43, 53);
        this.PANEL_2_SUPPORT_1_ROTATES.addBox(-24.0F, -2.0F, -25.0F, 21, 4, 4);
        this.PANEL_2_SUPPORT_1_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_2_SUPPORT_1_ROTATES.setTextureSize(256, 256);
        this.PANEL_2_SUPPORT_1_ROTATES.mirror = true;
        this.setRotation(this.PANEL_2_SUPPORT_1_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_2_SUPPORT_2_ROTATES = new ModelRenderer(this, 43, 53);
        this.PANEL_2_SUPPORT_2_ROTATES.addBox(-24.0F, -2.0F, 22.0F, 21, 4, 4);
        this.PANEL_2_SUPPORT_2_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_2_SUPPORT_2_ROTATES.setTextureSize(256, 256);
        this.PANEL_2_SUPPORT_2_ROTATES.mirror = true;
        this.setRotation(this.PANEL_2_SUPPORT_2_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_2_SUPPORT_3_ROTATES = new ModelRenderer(this, 43, 53);
        this.PANEL_2_SUPPORT_3_ROTATES.addBox(-24.0F, -2.0F, -2.0F, 21, 4, 4);
        this.PANEL_2_SUPPORT_3_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_2_SUPPORT_3_ROTATES.setTextureSize(256, 256);
        this.PANEL_2_SUPPORT_3_ROTATES.mirror = true;
        this.setRotation(this.PANEL_2_SUPPORT_3_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_2_SUPPORT_4_ROTATES = new ModelRenderer(this, 43, 63);
        this.PANEL_2_SUPPORT_4_ROTATES.addBox(-24.0F, -2.0F, -21.0F, 4, 4, 43);
        this.PANEL_2_SUPPORT_4_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_2_SUPPORT_4_ROTATES.setTextureSize(256, 256);
        this.PANEL_2_SUPPORT_4_ROTATES.mirror = true;
        this.setRotation(this.PANEL_2_SUPPORT_4_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_1_SUPPORT_1_ROTATES = new ModelRenderer(this, 43, 53);
        this.PANEL_1_SUPPORT_1_ROTATES.addBox(3.0F, -2.0F, -25.0F, 21, 4, 4);
        this.PANEL_1_SUPPORT_1_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_1_SUPPORT_1_ROTATES.setTextureSize(256, 256);
        this.PANEL_1_SUPPORT_1_ROTATES.mirror = true;
        this.setRotation(this.PANEL_1_SUPPORT_1_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_1_SUPPORT_2_ROTATES = new ModelRenderer(this, 43, 63);
        this.PANEL_1_SUPPORT_2_ROTATES.addBox(20.0F, -2.0F, -21.0F, 4, 4, 43);
        this.PANEL_1_SUPPORT_2_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_1_SUPPORT_2_ROTATES.setTextureSize(256, 256);
        this.PANEL_1_SUPPORT_2_ROTATES.mirror = true;
        this.setRotation(this.PANEL_1_SUPPORT_2_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_1_SUPPORT_3_ROTATES = new ModelRenderer(this, 43, 53);
        this.PANEL_1_SUPPORT_3_ROTATES.addBox(3.0F, -2.0F, -2.0F, 21, 4, 4);
        this.PANEL_1_SUPPORT_3_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_1_SUPPORT_3_ROTATES.setTextureSize(256, 256);
        this.PANEL_1_SUPPORT_3_ROTATES.mirror = true;
        this.setRotation(this.PANEL_1_SUPPORT_3_ROTATES, 0.0F, 0.0F, 0.0F);
        this.PANEL_1_SUPPORT_4_ROTATES = new ModelRenderer(this, 43, 53);
        this.PANEL_1_SUPPORT_4_ROTATES.addBox(3.0F, -2.0F, 22.0F, 21, 4, 4);
        this.PANEL_1_SUPPORT_4_ROTATES.setRotationPoint(0.0F, -21.0F, 0.0F);
        this.PANEL_1_SUPPORT_4_ROTATES.setTextureSize(256, 256);
        this.PANEL_1_SUPPORT_4_ROTATES.mirror = true;
        this.setRotation(this.PANEL_1_SUPPORT_4_ROTATES, 0.0F, 0.0F, 0.0F);
        this.SUPPORT_2_ROTATES = new ModelRenderer(this, 20, 72);
        this.SUPPORT_2_ROTATES.addBox(2.0F, -19.0F, -1.0F, 1, 37, 2);
        this.SUPPORT_2_ROTATES.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.SUPPORT_2_ROTATES.setTextureSize(256, 256);
        this.SUPPORT_2_ROTATES.mirror = true;
        this.setRotation(this.SUPPORT_2_ROTATES, 0.0F, 0.0F, 0.0F);
        this.SUPPORT_3_ROTATES = new ModelRenderer(this, 30, 72);
        this.SUPPORT_3_ROTATES.addBox(-3.0F, -19.0F, -1.0F, 1, 37, 2);
        this.SUPPORT_3_ROTATES.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.SUPPORT_3_ROTATES.setTextureSize(256, 256);
        this.SUPPORT_3_ROTATES.mirror = true;
        this.setRotation(this.SUPPORT_3_ROTATES, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(float size) {
        GL11.glPushMatrix();
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        this.BASE.render(size);
        this.SUPPORT_1_ROTATES.render(size);
        this.MECHANISM_ROTATES.render(size);
        this.PANEL_1_ROTATES.render(size);
        this.PANEL_2_ROTATES.render(size);
        this.PANEL_2_SUPPORT_1_ROTATES.render(size);
        this.PANEL_2_SUPPORT_2_ROTATES.render(size);
        this.PANEL_2_SUPPORT_3_ROTATES.render(size);
        this.PANEL_2_SUPPORT_4_ROTATES.render(size);
        this.PANEL_1_SUPPORT_1_ROTATES.render(size);
        this.PANEL_1_SUPPORT_2_ROTATES.render(size);
        this.PANEL_1_SUPPORT_3_ROTATES.render(size);
        this.PANEL_1_SUPPORT_4_ROTATES.render(size);
        this.SUPPORT_2_ROTATES.render(size);
        this.SUPPORT_3_ROTATES.render(size);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "ClassicAdvancedSolarGenerator.png";
    }
}
