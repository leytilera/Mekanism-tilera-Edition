package mekanism.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.client.ModelMekanismBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelRotaryCondensentrator extends ModelMekanismBase {
    ModelRenderer portRight;
    ModelRenderer tankRight;
    ModelRenderer base;
    ModelRenderer pipe;
    ModelRenderer middle;
    ModelRenderer shaft;
    ModelRenderer portLeft;
    ModelRenderer tankLeft;
    ModelRenderer support4;
    ModelRenderer bridge;
    ModelRenderer tube8;
    ModelRenderer tube7;
    ModelRenderer tube6;
    ModelRenderer tube5;
    ModelRenderer tube4;
    ModelRenderer tube3;
    ModelRenderer tube2;
    ModelRenderer tube1;
    ModelRenderer top;
    ModelRenderer support3;
    ModelRenderer support2;
    ModelRenderer support1;

    public ModelRotaryCondensentrator() {
        textureWidth = 128;
        textureHeight = 64;

        portRight = new ModelRenderer(this, 82, 31);
        portRight.addBox(0F, 0F, 0F, 1, 10, 10);
        portRight.setRotationPoint(7.01F, 11F, -5F);
        portRight.setTextureSize(128, 64);
        portRight.mirror = true;
        setRotation(portRight, 0F, 0F, 0F);
        tankRight = new ModelRenderer(this, 64, 9);
        tankRight.mirror = true;
        tankRight.addBox(0F, 0F, 0F, 6, 8, 14);
        tankRight.setRotationPoint(1F, 11F, -7F);
        tankRight.setTextureSize(128, 64);
        setRotation(tankRight, 0F, 0F, 0F);
        base = new ModelRenderer(this, 0, 34);
        base.addBox(0F, 0F, 0F, 16, 5, 16);
        base.setRotationPoint(-8F, 19F, -8F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        pipe = new ModelRenderer(this, 0, 4);
        pipe.addBox(0F, 0F, 0F, 2, 6, 6);
        pipe.setRotationPoint(-1.01F, 13F, -3F);
        pipe.setTextureSize(128, 64);
        pipe.mirror = true;
        setRotation(pipe, 0F, 0F, 0F);
        middle = new ModelRenderer(this, 0, 17);
        middle.addBox(0F, 0F, 0F, 16, 1, 16);
        middle.setRotationPoint(-8F, 10F, -8F);
        middle.setTextureSize(128, 64);
        middle.mirror = true;
        setRotation(middle, 0F, 0F, 0F);
        shaft = new ModelRenderer(this, 12, 0);
        shaft.addBox(0F, 0F, 0F, 1, 2, 1);
        shaft.setRotationPoint(-0.5F, 11F, -0.5F);
        shaft.setTextureSize(128, 64);
        shaft.mirror = true;
        setRotation(shaft, 0F, 0F, 0F);
        portLeft = new ModelRenderer(this, 64, 31);
        portLeft.addBox(0F, 0F, 0F, 1, 8, 8);
        portLeft.setRotationPoint(-8.01F, 12F, -4F);
        portLeft.setTextureSize(128, 64);
        portLeft.mirror = true;
        setRotation(portLeft, 0F, 0F, 0F);
        tankLeft = new ModelRenderer(this, 64, 9);
        tankLeft.addBox(0F, 0F, 0F, 6, 8, 14);
        tankLeft.setRotationPoint(-7F, 11F, -7F);
        tankLeft.setTextureSize(128, 64);
        tankLeft.mirror = true;
        setRotation(tankLeft, 0F, 0F, 0F);
        support4 = new ModelRenderer(this, 7, 0);
        support4.addBox(0F, 0F, 0F, 1, 1, 1);
        support4.setRotationPoint(6F, 9F, 6F);
        support4.setTextureSize(128, 64);
        support4.mirror = true;
        setRotation(support4, 0F, 0F, 0F);
        bridge = new ModelRenderer(this, 64, 0);
        bridge.addBox(0F, 0F, 0F, 8, 1, 8);
        bridge.setRotationPoint(-4F, 9F, -4F);
        bridge.setTextureSize(128, 64);
        bridge.mirror = true;
        setRotation(bridge, 0F, 0F, 0F);
        tube8 = new ModelRenderer(this, 0, 0);
        tube8.addBox(0F, 0F, 0F, 2, 1, 1);
        tube8.setRotationPoint(-1F, 18F, 5F);
        tube8.setTextureSize(128, 64);
        tube8.mirror = true;
        setRotation(tube8, 0F, 0F, 0F);
        tube7 = new ModelRenderer(this, 0, 0);
        tube7.addBox(0F, 0F, 0F, 2, 1, 1);
        tube7.setRotationPoint(-1F, 12F, 5F);
        tube7.setTextureSize(128, 64);
        tube7.mirror = true;
        setRotation(tube7, 0F, 0F, 0F);
        tube6 = new ModelRenderer(this, 0, 0);
        tube6.addBox(0F, 0F, 0F, 2, 1, 1);
        tube6.setRotationPoint(-1F, 14F, 5F);
        tube6.setTextureSize(128, 64);
        tube6.mirror = true;
        setRotation(tube6, 0F, 0F, 0F);
        tube5 = new ModelRenderer(this, 0, 0);
        tube5.addBox(0F, 0F, 0F, 2, 1, 1);
        tube5.setRotationPoint(-1F, 16F, 5F);
        tube5.setTextureSize(128, 64);
        tube5.mirror = true;
        setRotation(tube5, 0F, 0F, 0F);
        tube4 = new ModelRenderer(this, 0, 0);
        tube4.addBox(0F, 0F, 0F, 2, 1, 1);
        tube4.setRotationPoint(-1F, 18F, -6F);
        tube4.setTextureSize(128, 64);
        tube4.mirror = true;
        setRotation(tube4, 0F, 0F, 0F);
        tube3 = new ModelRenderer(this, 0, 0);
        tube3.addBox(0F, 0F, 0F, 2, 1, 1);
        tube3.setRotationPoint(-1F, 16F, -6F);
        tube3.setTextureSize(128, 64);
        tube3.mirror = true;
        setRotation(tube3, 0F, 0F, 0F);
        tube2 = new ModelRenderer(this, 0, 0);
        tube2.addBox(0F, 0F, 0F, 2, 1, 1);
        tube2.setRotationPoint(-1F, 14F, -6F);
        tube2.setTextureSize(128, 64);
        tube2.mirror = true;
        setRotation(tube2, 0F, 0F, 0F);
        tube1 = new ModelRenderer(this, 0, 0);
        tube1.addBox(0F, 0F, 0F, 2, 1, 1);
        tube1.setRotationPoint(-1F, 12F, -6F);
        tube1.setTextureSize(128, 64);
        tube1.mirror = true;
        setRotation(tube1, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 0);
        top.addBox(0F, 0F, 0F, 16, 1, 16);
        top.setRotationPoint(-8F, 8F, -8F);
        top.setTextureSize(128, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        support3 = new ModelRenderer(this, 7, 0);
        support3.addBox(0F, 0F, 0F, 1, 1, 1);
        support3.setRotationPoint(6F, 9F, -7F);
        support3.setTextureSize(128, 64);
        support3.mirror = true;
        setRotation(support3, 0F, 0F, 0F);
        support2 = new ModelRenderer(this, 7, 0);
        support2.addBox(0F, 0F, 0F, 1, 1, 1);
        support2.setRotationPoint(-7F, 9F, -7F);
        support2.setTextureSize(128, 64);
        support2.mirror = true;
        setRotation(support2, 0F, 0F, 0F);
        support1 = new ModelRenderer(this, 7, 0);
        support1.addBox(0F, 0F, 0F, 1, 1, 1);
        support1.setRotationPoint(-7F, 9F, 6F);
        support1.setTextureSize(128, 64);
        support1.mirror = true;
        setRotation(support1, 0F, 0F, 0F);
    }

    @Override
    public void render(float size) {
        portRight.render(size);
        tankRight.render(size);
        base.render(size);
        pipe.render(size);
        middle.render(size);
        shaft.render(size);
        portLeft.render(size);
        tankLeft.render(size);
        support4.render(size);
        bridge.render(size);
        tube8.render(size);
        tube7.render(size);
        tube6.render(size);
        tube5.render(size);
        tube4.render(size);
        tube3.render(size);
        tube2.render(size);
        tube1.render(size);
        top.render(size);
        support3.render(size);
        support2.render(size);
        support1.render(size);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "RotaryCondensentrator.png";
    }
}
