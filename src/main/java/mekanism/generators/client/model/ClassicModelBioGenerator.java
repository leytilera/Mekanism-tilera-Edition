package mekanism.generators.client.model;

import org.lwjgl.opengl.GL11;

import mekanism.client.ModelMekanismBase;
import net.minecraft.client.model.ModelRenderer;

public class ClassicModelBioGenerator
    extends ModelMekanismBase implements IModelBioGenerator {
    ModelRenderer MAIN_BASE;
    ModelRenderer MATERIAL_INLET;
    ModelRenderer CONVEYOR_BELT_1;
    ModelRenderer CONVEYOR_BELT_SUPPORT;
    ModelRenderer PISTON_SUPPORT_1;
    ModelRenderer PISTON_SUPPORT_2;
    ModelRenderer CONVEYOR_BELT_2;
    ModelRenderer FUEL_CONTAINER_WALL_1;
    ModelRenderer FUEL_CONTAINER_WALL_2;
    ModelRenderer FUEL_CONTAINER_WALL_3;
    ModelRenderer FUEL_CONTAINER_WALL_4;
    ModelRenderer FUEL_CONTAINER_TOP_1;
    ModelRenderer FUEL_CONTAINER_TOP_2;
    ModelRenderer FUEL_OUTLET_1;
    ModelRenderer FUEL_OUTLET_2;
    ModelRenderer PLUG_PANEL;
    ModelRenderer PISTON_HEAD_MOVES;
    ModelRenderer PISTON_ARM_MOVES;
    ModelRenderer PISTON_BODY;

    public ClassicModelBioGenerator() {
        super.textureWidth = 128;
        super.textureHeight = 128;
        this.MAIN_BASE = new ModelRenderer(this, 0, 0);
        this.MAIN_BASE.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
        this.MAIN_BASE.setRotationPoint(-8.0F, 23.0F, -8.0F);
        this.MAIN_BASE.setTextureSize(128, 128);
        this.MAIN_BASE.mirror = true;
        this.setRotation(this.MAIN_BASE, 0.0F, 0.0F, 0.0F);
        this.MATERIAL_INLET = new ModelRenderer(this, 0, 18);
        this.MATERIAL_INLET.addBox(0.0F, 0.0F, 0.0F, 4, 6, 4);
        this.MATERIAL_INLET.setRotationPoint(3.0F, 17.0F, -7.0F);
        this.MATERIAL_INLET.setTextureSize(128, 128);
        this.MATERIAL_INLET.mirror = true;
        this.setRotation(this.MATERIAL_INLET, 0.0F, 0.0F, 0.0F);
        this.CONVEYOR_BELT_1 = new ModelRenderer(this, 0, 29);
        this.CONVEYOR_BELT_1.addBox(0.0F, 0.0F, 0.0F, 6, 1, 4);
        this.CONVEYOR_BELT_1.setRotationPoint(-3.0F, 17.0F, -7.0F);
        this.CONVEYOR_BELT_1.setTextureSize(128, 128);
        this.CONVEYOR_BELT_1.mirror = true;
        this.setRotation(this.CONVEYOR_BELT_1, 0.0F, 0.0F, 0.0F);
        this.CONVEYOR_BELT_SUPPORT = new ModelRenderer(this, 17, 18);
        this.CONVEYOR_BELT_SUPPORT.addBox(0.0F, 0.0F, 0.0F, 4, 6, 4);
        this.CONVEYOR_BELT_SUPPORT.setRotationPoint(-7.0F, 17.0F, -7.0F);
        this.CONVEYOR_BELT_SUPPORT.setTextureSize(128, 128);
        this.CONVEYOR_BELT_SUPPORT.mirror = true;
        this.setRotation(this.CONVEYOR_BELT_SUPPORT, 0.0F, 0.0F, 0.0F);
        this.PISTON_SUPPORT_1 = new ModelRenderer(this, 0, 50);
        this.PISTON_SUPPORT_1.addBox(0.0F, 0.0F, 0.0F, 2, 10, 1);
        this.PISTON_SUPPORT_1.setRotationPoint(-1.0F, 13.0F, -8.0F);
        this.PISTON_SUPPORT_1.setTextureSize(128, 128);
        this.PISTON_SUPPORT_1.mirror = true;
        this.setRotation(this.PISTON_SUPPORT_1, 0.0F, 0.0F, 0.0F);
        this.PISTON_SUPPORT_2 = new ModelRenderer(this, 0, 50);
        this.PISTON_SUPPORT_2.addBox(0.0F, 0.0F, 0.0F, 2, 10, 1);
        this.PISTON_SUPPORT_2.setRotationPoint(-1.0F, 13.0F, -3.0F);
        this.PISTON_SUPPORT_2.setTextureSize(128, 128);
        this.PISTON_SUPPORT_2.mirror = true;
        this.setRotation(this.PISTON_SUPPORT_2, 0.0F, 0.0F, 0.0F);
        this.CONVEYOR_BELT_2 = new ModelRenderer(this, 0, 36);
        this.CONVEYOR_BELT_2.addBox(0.0F, 0.0F, 0.0F, 7, 1, 4);
        this.CONVEYOR_BELT_2.setRotationPoint(-3.0F, 17.0F, -3.0F);
        this.CONVEYOR_BELT_2.setTextureSize(128, 128);
        this.CONVEYOR_BELT_2.mirror = true;
        this.setRotation(this.CONVEYOR_BELT_2, 0.0F, -1.570796F, 0.33161256F);
        this.FUEL_CONTAINER_WALL_1 = new ModelRenderer(this, 65, 15);
        this.FUEL_CONTAINER_WALL_1.addBox(0.0F, 0.0F, 0.0F, 4, 13, 1);
        this.FUEL_CONTAINER_WALL_1.setRotationPoint(-7.0F, 10.0F, 3.0F);
        this.FUEL_CONTAINER_WALL_1.setTextureSize(128, 128);
        this.FUEL_CONTAINER_WALL_1.mirror = true;
        this.setRotation(this.FUEL_CONTAINER_WALL_1, 0.0F, 0.0F, 0.0F);
        this.FUEL_CONTAINER_WALL_2 = new ModelRenderer(this, 77, 0);
        this.FUEL_CONTAINER_WALL_2.addBox(0.0F, 0.0F, 0.0F, 1, 13, 3);
        this.FUEL_CONTAINER_WALL_2.setRotationPoint(-8.0F, 10.0F, 4.0F);
        this.FUEL_CONTAINER_WALL_2.setTextureSize(128, 128);
        this.FUEL_CONTAINER_WALL_2.mirror = true;
        this.setRotation(this.FUEL_CONTAINER_WALL_2, 0.0F, 0.0F, 0.0F);
        this.FUEL_CONTAINER_WALL_3 = new ModelRenderer(this, 65, 0);
        this.FUEL_CONTAINER_WALL_3.addBox(0.0F, 0.0F, 0.0F, 4, 13, 1);
        this.FUEL_CONTAINER_WALL_3.setRotationPoint(-7.0F, 10.0F, 7.0F);
        this.FUEL_CONTAINER_WALL_3.setTextureSize(128, 128);
        this.FUEL_CONTAINER_WALL_3.mirror = true;
        this.setRotation(this.FUEL_CONTAINER_WALL_3, 0.0F, 0.0F, 0.0F);
        this.FUEL_CONTAINER_WALL_4 = new ModelRenderer(this, 77, 0);
        this.FUEL_CONTAINER_WALL_4.addBox(0.0F, 0.0F, 0.0F, 1, 13, 3);
        this.FUEL_CONTAINER_WALL_4.setRotationPoint(-3.0F, 10.0F, 4.0F);
        this.FUEL_CONTAINER_WALL_4.setTextureSize(128, 128);
        this.FUEL_CONTAINER_WALL_4.mirror = true;
        this.setRotation(this.FUEL_CONTAINER_WALL_4, 0.0F, 0.0F, 0.0F);
        this.FUEL_CONTAINER_TOP_1 = new ModelRenderer(this, 86, 0);
        this.FUEL_CONTAINER_TOP_1.addBox(0.0F, 0.0F, 0.0F, 6, 1, 5);
        this.FUEL_CONTAINER_TOP_1.setRotationPoint(-8.0F, 9.0F, 3.0F);
        this.FUEL_CONTAINER_TOP_1.setTextureSize(128, 128);
        this.FUEL_CONTAINER_TOP_1.mirror = true;
        this.setRotation(this.FUEL_CONTAINER_TOP_1, 0.0F, 0.0F, 0.0F);
        this.FUEL_CONTAINER_TOP_2 = new ModelRenderer(this, 86, 7);
        this.FUEL_CONTAINER_TOP_2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
        this.FUEL_CONTAINER_TOP_2.setRotationPoint(-6.0F, 8.0F, 5.0F);
        this.FUEL_CONTAINER_TOP_2.setTextureSize(128, 128);
        this.FUEL_CONTAINER_TOP_2.mirror = true;
        this.setRotation(this.FUEL_CONTAINER_TOP_2, 0.0F, 0.0F, 0.0F);
        this.FUEL_OUTLET_1 = new ModelRenderer(this, 86, 10);
        this.FUEL_OUTLET_1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
        this.FUEL_OUTLET_1.setRotationPoint(-2.0F, 16.0F, 5.0F);
        this.FUEL_OUTLET_1.setTextureSize(128, 128);
        this.FUEL_OUTLET_1.mirror = true;
        this.setRotation(this.FUEL_OUTLET_1, 0.0F, 0.0F, 0.2617994F);
        this.FUEL_OUTLET_2 = new ModelRenderer(this, 86, 14);
        this.FUEL_OUTLET_2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
        this.FUEL_OUTLET_2.setRotationPoint(1.0F, 17.0F, 5.0F);
        this.FUEL_OUTLET_2.setTextureSize(128, 128);
        this.FUEL_OUTLET_2.mirror = true;
        this.setRotation(this.FUEL_OUTLET_2, 0.0F, 0.0F, 0.0F);
        this.PLUG_PANEL = new ModelRenderer(this, 44, 18);
        this.PLUG_PANEL.addBox(0.0F, 0.0F, 0.0F, 4, 9, 6);
        this.PLUG_PANEL.setRotationPoint(4.0F, 14.0F, -3.0F);
        this.PLUG_PANEL.setTextureSize(128, 128);
        this.PLUG_PANEL.mirror = true;
        this.setRotation(this.PLUG_PANEL, 0.0F, 0.0F, 0.0F);
        this.PISTON_ARM_MOVES = new ModelRenderer(this, 8, 57);
        this.PISTON_ARM_MOVES.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
        this.PISTON_ARM_MOVES.setRotationPoint(-1.0F, 11.0F, -6.0F);
        this.PISTON_ARM_MOVES.setTextureSize(128, 128);
        this.PISTON_ARM_MOVES.mirror = true;
        this.setRotation(this.PISTON_ARM_MOVES, 0.0F, 0.0F, 0.0F);
        this.PISTON_BODY = new ModelRenderer(this, 0, 63);
        this.PISTON_BODY.addBox(0.0F, 0.0F, 0.0F, 6, 4, 6);
        this.PISTON_BODY.setRotationPoint(-3.0F, 9.0F, -8.0F);
        this.PISTON_BODY.setTextureSize(128, 128);
        this.PISTON_BODY.mirror = true;
        this.setRotation(this.PISTON_BODY, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(float size, float depth) {
        GL11.glPushMatrix();
        GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
        this.MAIN_BASE.render(size);
        this.MATERIAL_INLET.render(size);
        this.CONVEYOR_BELT_1.render(size);
        this.CONVEYOR_BELT_SUPPORT.render(size);
        this.PISTON_SUPPORT_1.render(size);
        this.PISTON_SUPPORT_2.render(size);
        this.CONVEYOR_BELT_2.render(size);
        this.FUEL_CONTAINER_WALL_1.render(size);
        this.FUEL_CONTAINER_WALL_2.render(size);
        this.FUEL_CONTAINER_WALL_3.render(size);
        this.FUEL_CONTAINER_WALL_4.render(size);
        this.FUEL_CONTAINER_TOP_1.render(size);
        this.FUEL_CONTAINER_TOP_2.render(size);
        this.FUEL_OUTLET_1.render(size);
        this.FUEL_OUTLET_2.render(size);
        this.PLUG_PANEL.render(size);
        this.PISTON_HEAD_MOVES = new ModelRenderer(this, 8, 50);
        this.PISTON_HEAD_MOVES.addBox(0.0F, depth, 0.0F, 4, 2, 4);
        this.PISTON_HEAD_MOVES.setRotationPoint(-2.0F, 13.0F, -7.0F);
        this.PISTON_HEAD_MOVES.setTextureSize(128, 128);
        this.PISTON_HEAD_MOVES.mirror = true;
        this.setRotation(this.PISTON_HEAD_MOVES, 0.0F, 0.0F, 0.0F);
        this.PISTON_HEAD_MOVES.render(size);
        this.PISTON_ARM_MOVES.render(size);
        this.PISTON_BODY.render(size);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "ClassicBioGenerator.png";
    }

    @Override
    public void render(float size) {
        this.render(size, 0.0F);
    }
}
