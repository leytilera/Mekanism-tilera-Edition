package mekanism.generators.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.client.ModelMekanismBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelGasGenerator extends ModelMekanismBase {
    ModelRenderer port4;
    ModelRenderer baseStand;
    ModelRenderer pillar4;
    ModelRenderer port3;
    ModelRenderer port2;
    ModelRenderer connectorAngle1;
    ModelRenderer pillar3;
    ModelRenderer pillar2;
    ModelRenderer pillar1;
    ModelRenderer center;
    ModelRenderer connector3;
    ModelRenderer port1;
    ModelRenderer connector4;
    ModelRenderer connectorAngle4;
    ModelRenderer base;
    ModelRenderer connectorAngle3;
    ModelRenderer connector2;
    ModelRenderer connectorAngle2;
    ModelRenderer connector1;

    public ModelGasGenerator() {
        textureWidth = 128;
        textureHeight = 64;

        port4 = new ModelRenderer(this, 40, 34);
        port4.addBox(0F, 0F, 0F, 1, 8, 8);
        port4.setRotationPoint(7F, 12F, -4F);
        port4.setTextureSize(128, 64);
        port4.mirror = true;
        setRotation(port4, 0F, 0F, 0F);
        baseStand = new ModelRenderer(this, 0, 20);
        baseStand.addBox(0F, 0F, 0F, 13, 1, 13);
        baseStand.setRotationPoint(-6.5F, 19F, -6.5F);
        baseStand.setTextureSize(128, 64);
        baseStand.mirror = true;
        setRotation(baseStand, 0F, 0F, 0F);
        pillar4 = new ModelRenderer(this, 0, 0);
        pillar4.addBox(0F, 0F, 0F, 3, 9, 3);
        pillar4.setRotationPoint(4F, 10F, 4F);
        pillar4.setTextureSize(128, 64);
        pillar4.mirror = true;
        setRotation(pillar4, 0F, 0F, 0F);
        port3 = new ModelRenderer(this, 40, 50);
        port3.addBox(0F, 0F, 0F, 8, 8, 1);
        port3.setRotationPoint(-4F, 12F, 7F);
        port3.setTextureSize(128, 64);
        port3.mirror = true;
        setRotation(port3, 0F, 0F, 0F);
        port2 = new ModelRenderer(this, 40, 34);
        port2.addBox(0F, 0F, 0F, 1, 8, 8);
        port2.setRotationPoint(-8F, 12F, -4F);
        port2.setTextureSize(128, 64);
        port2.mirror = true;
        setRotation(port2, 0F, 0F, 0F);
        connectorAngle1 = new ModelRenderer(this, 48, 13);
        connectorAngle1.addBox(0F, 0F, 0.5F, 8, 1, 2);
        connectorAngle1.setRotationPoint(-4F, 13.5F, -6F);
        connectorAngle1.setTextureSize(128, 64);
        connectorAngle1.mirror = true;
        setRotation(connectorAngle1, 0.986111F, 0F, 0F);
        pillar3 = new ModelRenderer(this, 0, 0);
        pillar3.addBox(0F, 0F, 0F, 3, 9, 3);
        pillar3.setRotationPoint(-7F, 10F, 4F);
        pillar3.setTextureSize(128, 64);
        pillar3.mirror = true;
        setRotation(pillar3, 0F, 0F, 0F);
        pillar2 = new ModelRenderer(this, 0, 0);
        pillar2.addBox(0F, 0F, 0F, 3, 9, 3);
        pillar2.setRotationPoint(4F, 10F, -7F);
        pillar2.setTextureSize(128, 64);
        pillar2.mirror = true;
        setRotation(pillar2, 0F, 0F, 0F);
        pillar1 = new ModelRenderer(this, 0, 0);
        pillar1.addBox(0F, 0F, 0F, 3, 9, 3);
        pillar1.setRotationPoint(-7F, 10F, -7F);
        pillar1.setTextureSize(128, 64);
        pillar1.mirror = true;
        setRotation(pillar1, 0F, 0F, 0F);
        center = new ModelRenderer(this, 0, 34);
        center.addBox(0F, 0F, 0F, 10, 12, 10);
        center.setRotationPoint(-5F, 8F, -5F);
        center.setTextureSize(128, 64);
        center.mirror = true;
        setRotation(center, 0F, 0F, 0F);
        connector3 = new ModelRenderer(this, 39, 20);
        connector3.addBox(0F, 0F, 0F, 1, 1, 8);
        connector3.setRotationPoint(5F, 11F, -4F);
        connector3.setTextureSize(128, 64);
        connector3.mirror = true;
        setRotation(connector3, 0F, 0F, 0F);
        port1 = new ModelRenderer(this, 40, 50);
        port1.addBox(0F, 0F, 0F, 8, 8, 1);
        port1.setRotationPoint(-4F, 12F, -8F);
        port1.setTextureSize(128, 64);
        port1.mirror = true;
        setRotation(port1, 0F, 0F, 0F);
        connector4 = new ModelRenderer(this, 39, 29);
        connector4.addBox(0F, 0F, 0F, 8, 1, 1);
        connector4.setRotationPoint(-4F, 11F, 5F);
        connector4.setTextureSize(128, 64);
        connector4.mirror = true;
        setRotation(connector4, 0F, 0F, 0F);
        connectorAngle4 = new ModelRenderer(this, 48, 10);
        connectorAngle4.addBox(0F, 0F, -1F, 8, 2, 1);
        connectorAngle4.setRotationPoint(-4F, 11F, 6F);
        connectorAngle4.setTextureSize(128, 64);
        connectorAngle4.mirror = true;
        setRotation(connectorAngle4, 0.7941248F, 0F, 0F);
        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 16, 4, 16);
        base.setRotationPoint(-8F, 20F, -8F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        connectorAngle3 = new ModelRenderer(this, 48, 0);
        connectorAngle3.addBox(-1F, 0F, 0F, 1, 2, 8);
        connectorAngle3.setRotationPoint(6F, 11F, -4F);
        connectorAngle3.setTextureSize(128, 64);
        connectorAngle3.mirror = true;
        setRotation(connectorAngle3, 0F, 0F, -0.7941248F);
        connector2 = new ModelRenderer(this, 39, 20);
        connector2.addBox(0F, 0F, 0F, 1, 1, 8);
        connector2.setRotationPoint(-6F, 11F, -4F);
        connector2.setTextureSize(128, 64);
        connector2.mirror = true;
        setRotation(connector2, 0F, 0F, 0F);
        connectorAngle2 = new ModelRenderer(this, 48, 0);
        connectorAngle2.addBox(0F, 0F, 0F, 1, 2, 8);
        connectorAngle2.setRotationPoint(-6F, 11F, -4F);
        connectorAngle2.setTextureSize(128, 64);
        connectorAngle2.mirror = true;
        setRotation(connectorAngle2, 0F, 0F, 0.7941248F);
        connector1 = new ModelRenderer(this, 48, 13);
        connector1.addBox(0F, 0F, 0F, 8, 1, 2);
        connector1.setRotationPoint(-4F, 13F, -7.5F);
        connector1.setTextureSize(128, 64);
        connector1.mirror = true;
        setRotation(connector1, 0F, 0F, 0F);
    }

    @Override
    public void render(float size) {
        port4.render(size);
        baseStand.render(size);
        pillar4.render(size);
        port3.render(size);
        port2.render(size);
        connectorAngle1.render(size);
        pillar3.render(size);
        pillar2.render(size);
        pillar1.render(size);
        center.render(size);
        connector3.render(size);
        port1.render(size);
        connector4.render(size);
        connectorAngle4.render(size);
        base.render(size);
        connectorAngle3.render(size);
        connector2.render(size);
        connectorAngle2.render(size);
        connector1.render(size);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "GasGenerator.png";
    }
}
