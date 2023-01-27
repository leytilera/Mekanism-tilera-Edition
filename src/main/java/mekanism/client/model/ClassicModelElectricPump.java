package mekanism.client.model;

import mekanism.client.ModelMekanismBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ClassicModelElectricPump extends ModelMekanismBase {
    ModelRenderer COREBOX;
    ModelRenderer INPUTSLOT2;
    ModelRenderer INPUTSLOT3;
    ModelRenderer INPUTSLOT4;
    ModelRenderer INPUTSLOT5;
    ModelRenderer INPUTSLOT1;
    ModelRenderer PIPE3;
    ModelRenderer PIPE2;
    ModelRenderer TOPPIPE3;
    ModelRenderer PIPE1;
    ModelRenderer CORESLOT;
    ModelRenderer PIPE4;
    ModelRenderer TOPPIPE2;
    ModelRenderer DISPLAY2;
    ModelRenderer TOPPIPE1;
    ModelRenderer TOPPIPE4;
    ModelRenderer DISPLAY1;

    public ClassicModelElectricPump() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.COREBOX = new ModelRenderer(this, 0, 0);
        this.COREBOX.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12);
        this.COREBOX.setRotationPoint(-6.0F, 10.0F, -6.0F);
        this.COREBOX.setTextureSize(128, 128);
        this.COREBOX.mirror = true;
        this.setRotation(this.COREBOX, 0.0F, 0.0F, 0.0F);
        this.INPUTSLOT2 = new ModelRenderer(this, 0, 24);
        this.INPUTSLOT2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6);
        this.INPUTSLOT2.setRotationPoint(6.0F, 13.0F, -3.0F);
        this.INPUTSLOT2.setTextureSize(128, 128);
        this.INPUTSLOT2.mirror = true;
        this.setRotation(this.INPUTSLOT2, 0.0F, 0.0F, 0.0F);
        this.INPUTSLOT3 = new ModelRenderer(this, 16, 24);
        this.INPUTSLOT3.addBox(0.0F, 0.0F, 0.0F, 6, 6, 2);
        this.INPUTSLOT3.setRotationPoint(-3.0F, 13.0F, 6.0F);
        this.INPUTSLOT3.setTextureSize(128, 128);
        this.INPUTSLOT3.mirror = true;
        this.setRotation(this.INPUTSLOT3, 0.0F, 0.0F, 0.0F);
        this.INPUTSLOT4 = new ModelRenderer(this, 16, 24);
        this.INPUTSLOT4.addBox(0.0F, 0.0F, 0.0F, 6, 6, 2);
        this.INPUTSLOT4.setRotationPoint(-3.0F, 13.0F, -8.0F);
        this.INPUTSLOT4.setTextureSize(128, 128);
        this.INPUTSLOT4.mirror = true;
        this.setRotation(this.INPUTSLOT4, 0.0F, 0.0F, 0.0F);
        this.INPUTSLOT5 = new ModelRenderer(this, 0, 44);
        this.INPUTSLOT5.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6);
        this.INPUTSLOT5.setRotationPoint(-3.0F, 22.0F, -3.0F);
        this.INPUTSLOT5.setTextureSize(128, 128);
        this.INPUTSLOT5.mirror = true;
        this.setRotation(this.INPUTSLOT5, 0.0F, 0.0F, 0.0F);
        this.INPUTSLOT1 = new ModelRenderer(this, 0, 24);
        this.INPUTSLOT1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6);
        this.INPUTSLOT1.setRotationPoint(-8.0F, 13.0F, -3.0F);
        this.INPUTSLOT1.setTextureSize(128, 128);
        this.INPUTSLOT1.mirror = true;
        this.setRotation(this.INPUTSLOT1, 0.0F, 0.0F, 0.0F);
        this.PIPE3 = new ModelRenderer(this, 48, 0);
        this.PIPE3.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
        this.PIPE3.setRotationPoint(-1.0F, 9.0F, 6.0F);
        this.PIPE3.setTextureSize(128, 128);
        this.PIPE3.mirror = true;
        this.setRotation(this.PIPE3, 0.0F, 0.0F, 0.0F);
        this.PIPE2 = new ModelRenderer(this, 54, 0);
        this.PIPE2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
        this.PIPE2.setRotationPoint(6.0F, 9.0F, -1.0F);
        this.PIPE2.setTextureSize(128, 128);
        this.PIPE2.mirror = true;
        this.setRotation(this.PIPE2, 0.0F, 0.0F, 0.0F);
        this.TOPPIPE3 = new ModelRenderer(this, 60, 6);
        this.TOPPIPE3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        this.TOPPIPE3.setRotationPoint(-1.0F, 9.0F, 3.0F);
        this.TOPPIPE3.setTextureSize(128, 128);
        this.TOPPIPE3.mirror = true;
        this.setRotation(this.TOPPIPE3, 0.0F, 0.0F, 0.0F);
        this.PIPE1 = new ModelRenderer(this, 54, 0);
        this.PIPE1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
        this.PIPE1.setRotationPoint(-7.0F, 9.0F, -1.0F);
        this.PIPE1.setTextureSize(128, 128);
        this.PIPE1.mirror = true;
        this.setRotation(this.PIPE1, 0.0F, 0.0F, 0.0F);
        this.CORESLOT = new ModelRenderer(this, 0, 52);
        this.CORESLOT.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6);
        this.CORESLOT.setRotationPoint(-3.0F, 8.0F, -3.0F);
        this.CORESLOT.setTextureSize(128, 128);
        this.CORESLOT.mirror = true;
        this.setRotation(this.CORESLOT, 0.0F, 0.0F, 0.0F);
        this.PIPE4 = new ModelRenderer(this, 48, 0);
        this.PIPE4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
        this.PIPE4.setRotationPoint(-1.0F, 9.0F, -7.0F);
        this.PIPE4.setTextureSize(128, 128);
        this.PIPE4.mirror = true;
        this.setRotation(this.PIPE4, 0.0F, 0.0F, 0.0F);
        this.TOPPIPE2 = new ModelRenderer(this, 60, 0);
        this.TOPPIPE2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
        this.TOPPIPE2.setRotationPoint(3.0F, 9.0F, -1.0F);
        this.TOPPIPE2.setTextureSize(128, 128);
        this.TOPPIPE2.mirror = true;
        this.setRotation(this.TOPPIPE2, 0.0F, 0.0F, 0.0F);
        this.DISPLAY2 = new ModelRenderer(this, 70, 0);
        this.DISPLAY2.addBox(-4.0F, 0.0F, 0.0F, 3, 1, 10);
        this.DISPLAY2.setRotationPoint(6.0F, 9.0F, -5.0F);
        this.DISPLAY2.setTextureSize(128, 128);
        this.DISPLAY2.mirror = true;
        this.setRotation(this.DISPLAY2, 0.0F, 0.0F, 0.0F);
        this.TOPPIPE1 = new ModelRenderer(this, 60, 0);
        this.TOPPIPE1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
        this.TOPPIPE1.setRotationPoint(-6.0F, 9.0F, -1.0F);
        this.TOPPIPE1.setTextureSize(128, 128);
        this.TOPPIPE1.mirror = true;
        this.setRotation(this.TOPPIPE1, 0.0F, 0.0F, 0.0F);
        this.TOPPIPE4 = new ModelRenderer(this, 60, 6);
        this.TOPPIPE4.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        this.TOPPIPE4.setRotationPoint(-1.0F, 9.0F, -6.0F);
        this.TOPPIPE4.setTextureSize(128, 128);
        this.TOPPIPE4.mirror = true;
        this.setRotation(this.TOPPIPE4, 0.0F, 0.0F, 0.0F);
        this.DISPLAY1 = new ModelRenderer(this, 70, 0);
        this.DISPLAY1.addBox(-4.0F, 0.0F, 0.0F, 3, 1, 10);
        this.DISPLAY1.setRotationPoint(-1.0F, 9.0F, -5.0F);
        this.DISPLAY1.setTextureSize(128, 128);
        this.DISPLAY1.mirror = true;
        this.setRotation(this.DISPLAY1, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(float size) {
        this.COREBOX.render(size);
        this.INPUTSLOT2.render(size);
        this.INPUTSLOT3.render(size);
        this.INPUTSLOT4.render(size);
        this.INPUTSLOT5.render(size);
        this.INPUTSLOT1.render(size);
        this.PIPE3.render(size);
        this.PIPE2.render(size);
        this.TOPPIPE3.render(size);
        this.PIPE1.render(size);
        this.CORESLOT.render(size);
        this.PIPE4.render(size);
        this.TOPPIPE2.render(size);
        this.DISPLAY2.render(size);
        this.TOPPIPE1.render(size);
        this.TOPPIPE4.render(size);
        this.DISPLAY1.render(size);
    }

    @Override
    public void render(
        Entity entity, float f, float f1, float f2, float f3, float f4, float f5
    ) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.COREBOX.render(f5);
        this.INPUTSLOT2.render(f5);
        this.INPUTSLOT3.render(f5);
        this.INPUTSLOT4.render(f5);
        this.INPUTSLOT5.render(f5);
        this.INPUTSLOT1.render(f5);
        this.PIPE3.render(f5);
        this.PIPE2.render(f5);
        this.TOPPIPE3.render(f5);
        this.PIPE1.render(f5);
        this.CORESLOT.render(f5);
        this.PIPE4.render(f5);
        this.TOPPIPE2.render(f5);
        this.DISPLAY2.render(f5);
        this.TOPPIPE1.render(f5);
        this.TOPPIPE4.render(f5);
        this.DISPLAY1.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "ClassicElectricPump.png";
    }
}
