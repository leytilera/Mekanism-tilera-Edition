package mekanism.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelQuantumEntangloporter extends ModelBase {
    public static ResourceLocation OVERLAY = MekanismUtils.getResource(
        ResourceType.RENDER, "QuantumEntangloporter_Overlay.png"
    );

    ModelRenderer portTop;
    ModelRenderer portBottom;
    ModelRenderer portLeft;
    ModelRenderer portRight;
    ModelRenderer portBack;
    ModelRenderer portFront;
    ModelRenderer energyCubeCore;
    ModelRenderer frameEdge1;
    ModelRenderer frameEdge2;
    ModelRenderer frameEdge3;
    ModelRenderer frameEdge4;
    ModelRenderer frameEdge5;
    ModelRenderer frameEdge6;
    ModelRenderer frameEdge7;
    ModelRenderer frameEdge8;
    ModelRenderer frameEdge9;
    ModelRenderer frameEdge10;
    ModelRenderer frameEdge11;
    ModelRenderer frameEdge12;
    ModelRenderer frame1;
    ModelRenderer frame2;
    ModelRenderer frame3;
    ModelRenderer frame4;
    ModelRenderer frame5;
    ModelRenderer frame6;
    ModelRenderer frame7;
    ModelRenderer frame8;
    ModelRenderer frame9;
    ModelRenderer frame10;
    ModelRenderer frame11;
    ModelRenderer frame12;
    ModelRenderer corner1;
    ModelRenderer corner2;
    ModelRenderer corner3;
    ModelRenderer corner4;
    ModelRenderer corner5;
    ModelRenderer corner6;
    ModelRenderer corner7;
    ModelRenderer corner8;
    ModelRenderer portRightLarge;
    ModelRenderer portLeftLarge;

    public ModelQuantumEntangloporter() {
        textureWidth = 128;
        textureHeight = 64;

        portTop = new ModelRenderer(this, 36, 0);
        portTop.addBox(0F, 0F, 0F, 8, 1, 8);
        portTop.setRotationPoint(-4F, 8F, -4F);
        portTop.setTextureSize(128, 64);
        portTop.mirror = true;
        setRotation(portTop, 0F, 0F, 0F);
        portBottom = new ModelRenderer(this, 36, 9);
        portBottom.addBox(0F, 0F, 0F, 8, 1, 8);
        portBottom.setRotationPoint(-4F, 23F, -4F);
        portBottom.setTextureSize(128, 64);
        portBottom.mirror = true;
        setRotation(portBottom, 0F, 0F, 0F);
        portLeft = new ModelRenderer(this, 0, 0);
        portLeft.addBox(0F, 0F, 0F, 1, 8, 8);
        portLeft.setRotationPoint(-8F, 12F, -4F);
        portLeft.setTextureSize(128, 64);
        portLeft.mirror = true;
        setRotation(portLeft, 0F, 0F, 0F);
        portRight = new ModelRenderer(this, 0, 0);
        portRight.mirror = true;
        portRight.addBox(0F, 0F, 0F, 1, 8, 8);
        portRight.setRotationPoint(7F, 12F, -4F);
        portRight.setTextureSize(128, 64);
        setRotation(portRight, 0F, 0F, 0F);
        portBack = new ModelRenderer(this, 18, 9);
        portBack.addBox(0F, 0F, 0F, 8, 8, 1);
        portBack.setRotationPoint(-4F, 12F, 7F);
        portBack.setTextureSize(128, 64);
        portBack.mirror = true;
        setRotation(portBack, 0F, 0F, 0F);
        portFront = new ModelRenderer(this, 18, 0);
        portFront.addBox(0F, 0F, 0F, 8, 8, 1);
        portFront.setRotationPoint(-4F, 12F, -8F);
        portFront.setTextureSize(128, 64);
        portFront.mirror = true;
        setRotation(portFront, 0F, 0F, 0F);
        energyCubeCore = new ModelRenderer(this, 0, 41);
        energyCubeCore.addBox(-2F, -2F, -2F, 4, 4, 4);
        energyCubeCore.setRotationPoint(0F, 16F, 0F);
        energyCubeCore.setTextureSize(128, 64);
        energyCubeCore.mirror = true;
        setRotation(energyCubeCore, 0.7132579F, 0.403365F, 0.645384F);
        frameEdge1 = new ModelRenderer(this, 0, 16);
        frameEdge1.addBox(0F, 0F, 0F, 1, 10, 1);
        frameEdge1.setRotationPoint(-7.5F, 11F, -7.5F);
        frameEdge1.setTextureSize(128, 64);
        frameEdge1.mirror = true;
        setRotation(frameEdge1, 0F, 0F, 0F);
        frameEdge2 = new ModelRenderer(this, 0, 16);
        frameEdge2.addBox(0F, 0F, 0F, 1, 10, 1);
        frameEdge2.setRotationPoint(6.5F, 11F, -7.5F);
        frameEdge2.setTextureSize(128, 64);
        frameEdge2.mirror = true;
        setRotation(frameEdge2, 0F, 0F, 0F);
        frameEdge3 = new ModelRenderer(this, 0, 16);
        frameEdge3.addBox(0F, 0F, 0F, 1, 10, 1);
        frameEdge3.setRotationPoint(-7.5F, 11F, 6.5F);
        frameEdge3.setTextureSize(128, 64);
        frameEdge3.mirror = true;
        setRotation(frameEdge3, 0F, 0F, 0F);
        frameEdge4 = new ModelRenderer(this, 0, 16);
        frameEdge4.addBox(0F, 0F, 0F, 1, 10, 1);
        frameEdge4.setRotationPoint(6.5F, 11F, 6.5F);
        frameEdge4.setTextureSize(128, 64);
        frameEdge4.mirror = true;
        setRotation(frameEdge4, 0F, 0F, 0F);
        frameEdge5 = new ModelRenderer(this, 4, 27);
        frameEdge5.addBox(0F, 0F, 0F, 10, 1, 1);
        frameEdge5.setRotationPoint(-5F, 22.5F, -7.5F);
        frameEdge5.setTextureSize(128, 64);
        frameEdge5.mirror = true;
        setRotation(frameEdge5, 0F, 0F, 0F);
        frameEdge6 = new ModelRenderer(this, 4, 16);
        frameEdge6.addBox(0F, 0F, 0F, 1, 1, 10);
        frameEdge6.setRotationPoint(-7.5F, 22.5F, -5F);
        frameEdge6.setTextureSize(128, 64);
        frameEdge6.mirror = true;
        setRotation(frameEdge6, 0F, 0F, 0F);
        frameEdge7 = new ModelRenderer(this, 4, 16);
        frameEdge7.addBox(0F, 0F, 0F, 1, 1, 10);
        frameEdge7.setRotationPoint(6.5F, 22.5F, -5F);
        frameEdge7.setTextureSize(128, 64);
        frameEdge7.mirror = true;
        setRotation(frameEdge7, 0F, 0F, 0F);
        frameEdge8 = new ModelRenderer(this, 4, 27);
        frameEdge8.addBox(0F, 0F, 0F, 10, 1, 1);
        frameEdge8.setRotationPoint(-5F, 22.5F, 6.5F);
        frameEdge8.setTextureSize(128, 64);
        frameEdge8.mirror = true;
        setRotation(frameEdge8, 0F, 0F, 0F);
        frameEdge9 = new ModelRenderer(this, 4, 27);
        frameEdge9.addBox(0F, 0F, 0F, 10, 1, 1);
        frameEdge9.setRotationPoint(-5F, 8.5F, -7.5F);
        frameEdge9.setTextureSize(128, 64);
        frameEdge9.mirror = true;
        setRotation(frameEdge9, 0F, 0F, 0F);
        frameEdge10 = new ModelRenderer(this, 4, 16);
        frameEdge10.addBox(0F, 0F, 0F, 1, 1, 10);
        frameEdge10.setRotationPoint(-7.5F, 8.5F, -5F);
        frameEdge10.setTextureSize(128, 64);
        frameEdge10.mirror = true;
        setRotation(frameEdge10, 0F, 0F, 0F);
        frameEdge11 = new ModelRenderer(this, 4, 16);
        frameEdge11.addBox(0F, 0F, 0F, 1, 1, 10);
        frameEdge11.setRotationPoint(6.5F, 8.5F, -5F);
        frameEdge11.setTextureSize(128, 64);
        frameEdge11.mirror = true;
        setRotation(frameEdge11, 0F, 0F, 0F);
        frameEdge12 = new ModelRenderer(this, 4, 27);
        frameEdge12.addBox(0F, 0F, 0F, 10, 1, 1);
        frameEdge12.setRotationPoint(-5F, 8.5F, 6.5F);
        frameEdge12.setTextureSize(128, 64);
        frameEdge12.mirror = true;
        setRotation(frameEdge12, 0F, 0F, 0F);
        frame1 = new ModelRenderer(this, 0, 29);
        frame1.addBox(0F, 0F, 0F, 2, 10, 2);
        frame1.setRotationPoint(-7F, 11F, -7F);
        frame1.setTextureSize(128, 64);
        frame1.mirror = true;
        setRotation(frame1, 0F, 0F, 0F);
        frame2 = new ModelRenderer(this, 0, 29);
        frame2.mirror = true;
        frame2.addBox(0F, 0F, 0F, 2, 10, 2);
        frame2.setRotationPoint(5F, 11F, -7F);
        frame2.setTextureSize(128, 64);
        setRotation(frame2, 0F, 0F, 0F);
        frame3 = new ModelRenderer(this, 8, 29);
        frame3.addBox(0F, 0F, 0F, 2, 10, 2);
        frame3.setRotationPoint(-7F, 11F, 5F);
        frame3.setTextureSize(128, 64);
        frame3.mirror = true;
        setRotation(frame3, 0F, 0F, 0F);
        frame4 = new ModelRenderer(this, 8, 29);
        frame4.mirror = true;
        frame4.addBox(0F, 0F, 0F, 2, 10, 2);
        frame4.setRotationPoint(5F, 11F, 5F);
        frame4.setTextureSize(128, 64);
        setRotation(frame4, 0F, 0F, 0F);
        frame5 = new ModelRenderer(this, 16, 45);
        frame5.addBox(0F, 0F, 0F, 10, 2, 2);
        frame5.setRotationPoint(-5F, 21F, -7F);
        frame5.setTextureSize(128, 64);
        frame5.mirror = true;
        setRotation(frame5, 0F, 0F, 0F);
        frame6 = new ModelRenderer(this, 40, 29);
        frame6.addBox(0F, 0F, 0F, 2, 2, 10);
        frame6.setRotationPoint(-7F, 21F, -5F);
        frame6.setTextureSize(128, 64);
        frame6.mirror = true;
        setRotation(frame6, 0F, 0F, 0F);
        frame7 = new ModelRenderer(this, 40, 29);
        frame7.mirror = true;
        frame7.addBox(0F, 0F, 0F, 2, 2, 10);
        frame7.setRotationPoint(5F, 21F, -5F);
        frame7.setTextureSize(128, 64);
        setRotation(frame7, 0F, 0F, 0F);
        frame8 = new ModelRenderer(this, 16, 49);
        frame8.addBox(0F, 0F, 0F, 10, 2, 2);
        frame8.setRotationPoint(-5F, 21F, 5F);
        frame8.setTextureSize(128, 64);
        frame8.mirror = true;
        setRotation(frame8, 0F, 0F, 0F);
        frame9 = new ModelRenderer(this, 16, 41);
        frame9.addBox(0F, 0F, 0F, 10, 2, 2);
        frame9.setRotationPoint(-5F, 9F, -7F);
        frame9.setTextureSize(128, 64);
        frame9.mirror = true;
        setRotation(frame9, 0F, 0F, 0F);
        frame10 = new ModelRenderer(this, 16, 29);
        frame10.addBox(0F, 0F, 0F, 2, 2, 10);
        frame10.setRotationPoint(-7F, 9F, -5F);
        frame10.setTextureSize(128, 64);
        frame10.mirror = true;
        setRotation(frame10, 0F, 0F, 0F);
        frame11 = new ModelRenderer(this, 16, 29);
        frame11.mirror = true;
        frame11.addBox(0F, 0F, 0F, 2, 2, 10);
        frame11.setRotationPoint(5F, 9F, -5F);
        frame11.setTextureSize(128, 64);
        setRotation(frame11, 0F, 0F, 0F);
        frame12 = new ModelRenderer(this, 16, 53);
        frame12.addBox(0F, 0F, 0F, 10, 2, 2);
        frame12.setRotationPoint(-5F, 9F, 5F);
        frame12.setTextureSize(128, 64);
        frame12.mirror = true;
        setRotation(frame12, 0F, 0F, 0F);
        corner1 = new ModelRenderer(this, 0, 49);
        corner1.addBox(0F, 0F, 0F, 3, 3, 3);
        corner1.setRotationPoint(-8F, 8F, -8F);
        corner1.setTextureSize(128, 64);
        corner1.mirror = true;
        setRotation(corner1, 0F, 0F, 0F);
        corner2 = new ModelRenderer(this, 0, 49);
        corner2.addBox(0F, 0F, 0F, 3, 3, 3);
        corner2.setRotationPoint(5F, 8F, -8F);
        corner2.setTextureSize(128, 64);
        corner2.mirror = true;
        setRotation(corner2, 0F, 0F, 0F);
        corner3 = new ModelRenderer(this, 0, 49);
        corner3.addBox(0F, 0F, 0F, 3, 3, 3);
        corner3.setRotationPoint(-8F, 8F, 5F);
        corner3.setTextureSize(128, 64);
        corner3.mirror = true;
        setRotation(corner3, 0F, 0F, 0F);
        corner4 = new ModelRenderer(this, 0, 49);
        corner4.addBox(0F, 0F, 0F, 3, 3, 3);
        corner4.setRotationPoint(5F, 8F, 5F);
        corner4.setTextureSize(128, 64);
        corner4.mirror = true;
        setRotation(corner4, 0F, 0F, 0F);
        corner5 = new ModelRenderer(this, 0, 49);
        corner5.addBox(0F, 0F, 0F, 3, 3, 3);
        corner5.setRotationPoint(-8F, 21F, -8F);
        corner5.setTextureSize(128, 64);
        corner5.mirror = true;
        setRotation(corner5, 0F, 0F, 0F);
        corner6 = new ModelRenderer(this, 0, 49);
        corner6.addBox(0F, 0F, 0F, 3, 3, 3);
        corner6.setRotationPoint(5F, 21F, -8F);
        corner6.setTextureSize(128, 64);
        corner6.mirror = true;
        setRotation(corner6, 0F, 0F, 0F);
        corner7 = new ModelRenderer(this, 0, 49);
        corner7.addBox(0F, 0F, 0F, 3, 3, 3);
        corner7.setRotationPoint(-8F, 21F, 5F);
        corner7.setTextureSize(128, 64);
        corner7.mirror = true;
        setRotation(corner7, 0F, 0F, 0F);
        corner8 = new ModelRenderer(this, 0, 49);
        corner8.addBox(0F, 0F, 0F, 3, 3, 3);
        corner8.setRotationPoint(5F, 21F, 5F);
        corner8.setTextureSize(128, 64);
        corner8.mirror = true;
        setRotation(corner8, 0F, 0F, 0F);
        portRightLarge = new ModelRenderer(this, 68, 0);
        portRightLarge.mirror = true;
        portRightLarge.addBox(0F, 0F, 0F, 1, 10, 10);
        portRightLarge.setRotationPoint(7F, 11F, -5F);
        portRightLarge.setTextureSize(128, 64);
        setRotation(portRightLarge, 0F, 0F, 0F);
        portLeftLarge = new ModelRenderer(this, 68, 0);
        portLeftLarge.addBox(0F, 0F, 0F, 1, 10, 10);
        portLeftLarge.setRotationPoint(-8F, 11F, -5F);
        portLeftLarge.setTextureSize(128, 64);
        portLeftLarge.mirror = true;
        setRotation(portLeftLarge, 0F, 0F, 0F);
    }

    public void render(float size, TextureManager manager) {
        GL11.glPushMatrix();
        MekanismRenderer.blendOn();

        doRender(size);

        manager.bindTexture(OVERLAY);
        GL11.glScalef(1.001F, 1.001F, 1.001F);
        GL11.glTranslatef(0, -0.0011F, 0);
        MekanismRenderer.glowOn();

        doRender(size);

        MekanismRenderer.glowOff();
        MekanismRenderer.blendOff();
        GL11.glPopMatrix();
    }

    public void doRender(float size) {
        portTop.render(size);
        portBottom.render(size);
        portLeft.render(size);
        portRight.render(size);
        portBack.render(size);
        portFront.render(size);
        energyCubeCore.render(size);
        frameEdge1.render(size);
        frameEdge2.render(size);
        frameEdge3.render(size);
        frameEdge4.render(size);
        frameEdge5.render(size);
        frameEdge6.render(size);
        frameEdge7.render(size);
        frameEdge8.render(size);
        frameEdge9.render(size);
        frameEdge10.render(size);
        frameEdge11.render(size);
        frameEdge12.render(size);
        frame1.render(size);
        frame2.render(size);
        frame3.render(size);
        frame4.render(size);
        frame5.render(size);
        frame6.render(size);
        frame7.render(size);
        frame8.render(size);
        frame9.render(size);
        frame10.render(size);
        frame11.render(size);
        frame12.render(size);
        corner1.render(size);
        corner2.render(size);
        corner3.render(size);
        corner4.render(size);
        corner5.render(size);
        corner6.render(size);
        corner7.render(size);
        corner8.render(size);
        //portRightLarge.render(size);
        //portLeftLarge.render(size);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
