package mekanism.client.model;

import mekanism.client.ModelMekanismBase;
import mekanism.client.render.MekanismRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;

public class LegacyModelDigitalMiner extends ModelMekanismBase implements IModelOnOff {
    ModelRenderer Base;
    ModelRenderer Back;
    ModelRenderer Panel;
    ModelRenderer PanelStem;
    ModelRenderer KeyboardStem;
    ModelRenderer Keyboard;
    ModelRenderer LaserBase;
    ModelRenderer LegRight;
    ModelRenderer UpperLegRight;
    ModelRenderer ToeTopRight1;
    ModelRenderer ToeTopRight2;
    ModelRenderer ToeRight1;
    ModelRenderer LegHoldRight1;
    ModelRenderer LegHoldRight2;
    ModelRenderer FootBaseRight;
    ModelRenderer ToeRight2;
    ModelRenderer FootBaseLeft;
    ModelRenderer UpperLegLeft;
    ModelRenderer LegHoldLeft1;
    ModelRenderer ToeTopLeft2;
    ModelRenderer ToeTopLeft1;
    ModelRenderer LegHoldLeft2;
    ModelRenderer ToeLeft1;
    ModelRenderer ToeLeft2;
    ModelRenderer LegLeft;
    ModelRenderer RightAxil;
    ModelRenderer RightBackUpperLeg;
    ModelRenderer RightBackLowerLeg;
    ModelRenderer RightBackFoot;
    ModelRenderer LeftAxil;
    ModelRenderer LeftBackUpperLeg;
    ModelRenderer LeftBackLowerLeg;
    ModelRenderer LeftBackFoot;
    ModelRenderer Laser1;
    ModelRenderer Laser2;
    ModelRenderer Laser3;
    ModelRenderer Laser4;

    public LegacyModelDigitalMiner() {
        textureWidth = 256;
        textureHeight = 256;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(0F, 0F, 0F, 18, 22, 20);
        Base.setRotationPoint(-8F, -8F, -10F);
        Base.setTextureSize(256, 256);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 77, 0);
        Back.addBox(0F, 0F, 0F, 17, 20, 18);
        Back.setRotationPoint(7F, -7F, -9F);
        Back.setTextureSize(256, 256);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);
        Panel = new ModelRenderer(this, 0, 43);
        Panel.addBox(-2F, -8F, -7.5F, 2, 12, 15);
        Panel.setRotationPoint(-13F, 2F, 0F);
        Panel.setTextureSize(256, 256);
        Panel.mirror = true;
        setRotation(Panel, 0F, 0F, -0.2478574F);
        PanelStem = new ModelRenderer(this, 0, 71);
        PanelStem.addBox(-7F, 0F, 0F, 7, 4, 4);
        PanelStem.setRotationPoint(-7F, -2F, -2F);
        PanelStem.setTextureSize(256, 256);
        PanelStem.mirror = true;
        setRotation(PanelStem, 0F, 0F, -0.2230717F);
        KeyboardStem = new ModelRenderer(this, 0, 80);
        KeyboardStem.addBox(-7F, 0F, -2F, 7, 1, 4);
        KeyboardStem.setRotationPoint(-7F, 8F, 0F);
        KeyboardStem.setTextureSize(256, 256);
        KeyboardStem.mirror = true;
        setRotation(KeyboardStem, 0F, 0F, -0.3965719F);
        Keyboard = new ModelRenderer(this, 0, 86);
        Keyboard.addBox(-5F, 0F, -5F, 5, 1, 10);
        Keyboard.setRotationPoint(-12F, 10.5F, 0F);
        Keyboard.setTextureSize(256, 256);
        Keyboard.mirror = true;
        setRotation(Keyboard, 0F, 0F, -0.2230717F);
        LaserBase = new ModelRenderer(this, 0, 98);
        LaserBase.addBox(0F, 0F, 0F, 10, 5, 10);
        LaserBase.setRotationPoint(-5F, 12F, -5F);
        LaserBase.setTextureSize(256, 256);
        LaserBase.mirror = true;
        setRotation(LaserBase, 0F, 0F, 0F);
        LegRight = new ModelRenderer(this, 0, 114);
        LegRight.addBox(0F, 0F, 0F, 6, 26, 4);
        LegRight.setRotationPoint(-3F, -4F, -23F);
        LegRight.setTextureSize(256, 256);
        LegRight.mirror = true;
        setRotation(LegRight, 0F, 0F, 0F);
        UpperLegRight = new ModelRenderer(this, 21, 114);
        UpperLegRight.addBox(0F, 0F, 0F, 4, 5, 13);
        UpperLegRight.setRotationPoint(-2F, 0F, -22F);
        UpperLegRight.setTextureSize(256, 256);
        UpperLegRight.mirror = true;
        setRotation(UpperLegRight, 0F, 0F, 0F);
        ToeTopRight1 = new ModelRenderer(this, 21, 134);
        ToeTopRight1.addBox(0F, 0F, 0F, 3, 1, 3);
        ToeTopRight1.setRotationPoint(-4F, 19F, -20.5F);
        ToeTopRight1.setTextureSize(256, 256);
        ToeTopRight1.mirror = true;
        setRotation(ToeTopRight1, 0F, 0F, 0F);
        ToeTopRight2 = new ModelRenderer(this, 21, 134);
        ToeTopRight2.addBox(0F, 0F, 0F, 3, 1, 3);
        ToeTopRight2.setRotationPoint(1.5F, 19F, -20.5F);
        ToeTopRight2.setTextureSize(256, 256);
        ToeTopRight2.mirror = true;
        setRotation(ToeTopRight2, 0F, 0F, 0F);
        ToeRight1 = new ModelRenderer(this, 35, 134);
        ToeRight1.addBox(0F, 0F, 0F, 3, 6, 3);
        ToeRight1.setRotationPoint(1.5F, 22F, -20.5F);
        ToeRight1.setTextureSize(256, 256);
        ToeRight1.mirror = true;
        setRotation(ToeRight1, 1.047198F, 0.3490659F, 0F);
        LegHoldRight1 = new ModelRenderer(this, 0, 146);
        LegHoldRight1.addBox(0F, 0F, 0F, 2, 3, 12);
        LegHoldRight1.setRotationPoint(2F, 2F, -18F);
        LegHoldRight1.setTextureSize(256, 256);
        LegHoldRight1.mirror = true;
        setRotation(LegHoldRight1, 0.6543436F, 0F, 0F);
        LegHoldRight2 = new ModelRenderer(this, 0, 146);
        LegHoldRight2.addBox(0F, 0F, 0F, 2, 3, 12);
        LegHoldRight2.setRotationPoint(-4F, 2F, -18F);
        LegHoldRight2.setTextureSize(256, 256);
        LegHoldRight2.mirror = true;
        setRotation(LegHoldRight2, 0.6543436F, 0F, 0F);
        FootBaseRight = new ModelRenderer(this, 0, 162);
        FootBaseRight.addBox(0F, 0F, 0F, 8, 2, 7);
        FootBaseRight.setRotationPoint(-4F, 22F, -24F);
        FootBaseRight.setTextureSize(256, 256);
        FootBaseRight.mirror = true;
        setRotation(FootBaseRight, 0F, 0F, 0F);
        ToeRight2 = new ModelRenderer(this, 35, 134);
        ToeRight2.addBox(0F, 0F, 0F, 3, 6, 3);
        ToeRight2.setRotationPoint(-4.5F, 22F, -20.5F);
        ToeRight2.setTextureSize(256, 256);
        ToeRight2.mirror = true;
        setRotation(ToeRight2, 1.047198F, -0.3490659F, 0F);
        FootBaseLeft = new ModelRenderer(this, 0, 178);
        FootBaseLeft.addBox(0F, 0F, 0F, 8, 2, 7);
        FootBaseLeft.setRotationPoint(-4F, 22F, 17F);
        FootBaseLeft.setTextureSize(256, 256);
        FootBaseLeft.mirror = true;
        setRotation(FootBaseLeft, 0F, 0F, 0F);
        UpperLegLeft = new ModelRenderer(this, 0, 189);
        UpperLegLeft.addBox(0F, 0F, 0F, 4, 5, 13);
        UpperLegLeft.setRotationPoint(-2F, 0F, 9F);
        UpperLegLeft.setTextureSize(256, 256);
        UpperLegLeft.mirror = true;
        setRotation(UpperLegLeft, 0F, 0F, 0F);
        LegHoldLeft1 = new ModelRenderer(this, 0, 208);
        LegHoldLeft1.addBox(0F, 0F, -12F, 2, 3, 12);
        LegHoldLeft1.setRotationPoint(2F, 2F, 18F);
        LegHoldLeft1.setTextureSize(256, 256);
        LegHoldLeft1.mirror = true;
        setRotation(LegHoldLeft1, -0.6543414F, 0F, 0F);
        ToeTopLeft2 = new ModelRenderer(this, 31, 178);
        ToeTopLeft2.addBox(0F, 0F, 0F, 3, 1, 3);
        ToeTopLeft2.setRotationPoint(1.5F, 19F, 17.5F);
        ToeTopLeft2.setTextureSize(256, 256);
        ToeTopLeft2.mirror = true;
        setRotation(ToeTopLeft2, 0F, 0F, 0F);
        ToeTopLeft1 = new ModelRenderer(this, 31, 178);
        ToeTopLeft1.addBox(0F, 0F, 0F, 3, 1, 3);
        ToeTopLeft1.setRotationPoint(-4F, 19F, 17.5F);
        ToeTopLeft1.setTextureSize(256, 256);
        ToeTopLeft1.mirror = true;
        setRotation(ToeTopLeft1, 0F, 0F, 0F);
        LegHoldLeft2 = new ModelRenderer(this, 0, 208);
        LegHoldLeft2.addBox(0F, 0F, -12F, 2, 3, 12);
        LegHoldLeft2.setRotationPoint(-4F, 2F, 18F);
        LegHoldLeft2.setTextureSize(256, 256);
        LegHoldLeft2.mirror = true;
        setRotation(LegHoldLeft2, -0.6543414F, 0F, 0F);
        ToeLeft1 = new ModelRenderer(this, 44, 178);
        ToeLeft1.addBox(0F, 0F, -3F, 3, 6, 3);
        ToeLeft1.setRotationPoint(1.5F, 22F, 20.5F);
        ToeLeft1.setTextureSize(256, 256);
        ToeLeft1.mirror = true;
        setRotation(ToeLeft1, -1.047198F, -0.3490659F, 0F);
        ToeLeft2 = new ModelRenderer(this, 44, 178);
        ToeLeft2.addBox(0F, 0F, -3F, 3, 6, 3);
        ToeLeft2.setRotationPoint(-4.5F, 22F, 20.5F);
        ToeLeft2.setTextureSize(256, 256);
        ToeLeft2.mirror = true;
        setRotation(ToeLeft2, -1.047198F, 0.3490659F, 0F);
        LegLeft = new ModelRenderer(this, 35, 189);
        LegLeft.addBox(0F, 0F, 0F, 6, 26, 4);
        LegLeft.setRotationPoint(-3F, -4F, 19F);
        LegLeft.setTextureSize(256, 256);
        LegLeft.mirror = true;
        setRotation(LegLeft, 0F, 0F, 0F);
        RightAxil = new ModelRenderer(this, 78, 43);
        RightAxil.addBox(0F, 0F, 0F, 4, 4, 6);
        RightAxil.setRotationPoint(17F, 7F, -15F);
        RightAxil.setTextureSize(256, 256);
        RightAxil.mirror = true;
        setRotation(RightAxil, 0F, 0F, 0F);
        RightBackUpperLeg = new ModelRenderer(this, 78, 54);
        RightBackUpperLeg.addBox(-2.5F, -3F, 0F, 5, 12, 1);
        RightBackUpperLeg.setRotationPoint(19F, 9F, -15.95556F);
        RightBackUpperLeg.setTextureSize(256, 256);
        RightBackUpperLeg.mirror = true;
        setRotation(RightBackUpperLeg, 0F, 0F, 0F);
        RightBackLowerLeg = new ModelRenderer(this, 78, 68);
        RightBackLowerLeg.addBox(-2F, 7F, -0.5F, 4, 6, 2);
        RightBackLowerLeg.setRotationPoint(19F, 9F, -16F);
        RightBackLowerLeg.setTextureSize(256, 256);
        RightBackLowerLeg.mirror = true;
        setRotation(RightBackLowerLeg, 0F, 0F, 0F);
        RightBackFoot = new ModelRenderer(this, 99, 43);
        RightBackFoot.addBox(-3F, 0F, -2.5F, 8, 2, 5);
        RightBackFoot.setRotationPoint(19F, 22F, -15.5F);
        RightBackFoot.setTextureSize(256, 256);
        RightBackFoot.mirror = true;
        setRotation(RightBackFoot, 0F, 0F, 0F);
        LeftAxil = new ModelRenderer(this, 78, 80);
        LeftAxil.addBox(0F, 0F, 0F, 4, 4, 6);
        LeftAxil.setRotationPoint(17F, 7F, 9F);
        LeftAxil.setTextureSize(256, 256);
        LeftAxil.mirror = true;
        setRotation(LeftAxil, 0F, 0F, 0F);
        LeftBackUpperLeg = new ModelRenderer(this, 78, 91);
        LeftBackUpperLeg.addBox(-2.5F, -3F, 0F, 5, 12, 1);
        LeftBackUpperLeg.setRotationPoint(19F, 9F, 15.04444F);
        LeftBackUpperLeg.setTextureSize(256, 256);
        LeftBackUpperLeg.mirror = true;
        setRotation(LeftBackUpperLeg, 0F, 0F, 0F);
        LeftBackLowerLeg = new ModelRenderer(this, 78, 105);
        LeftBackLowerLeg.addBox(-2F, 7F, -0.5F, 4, 6, 2);
        LeftBackLowerLeg.setRotationPoint(19F, 9F, 15F);
        LeftBackLowerLeg.setTextureSize(256, 256);
        LeftBackLowerLeg.mirror = true;
        setRotation(LeftBackLowerLeg, 0F, 0F, 0F);
        LeftBackFoot = new ModelRenderer(this, 99, 80);
        LeftBackFoot.addBox(-3F, 0F, -2.5F, 8, 2, 5);
        LeftBackFoot.setRotationPoint(19F, 22F, 15.5F);
        LeftBackFoot.setTextureSize(256, 256);
        LeftBackFoot.mirror = true;
        setRotation(LeftBackFoot, 0F, 0F, 0F);
        Laser1 = new ModelRenderer(this, 35, 43);
        Laser1.addBox(0F, 0F, 0F, 2, 4, 2);
        Laser1.setRotationPoint(2.5F, 17F, 2.5F);
        Laser1.setTextureSize(256, 256);
        Laser1.mirror = true;
        setRotation(Laser1, 0F, 0F, 0F);
        Laser2 = new ModelRenderer(this, 35, 43);
        Laser2.addBox(0F, 0F, 0F, 2, 4, 2);
        Laser2.setRotationPoint(2.5F, 17F, -4.5F);
        Laser2.setTextureSize(256, 256);
        Laser2.mirror = true;
        setRotation(Laser2, 0F, 0F, 0F);
        Laser3 = new ModelRenderer(this, 35, 43);
        Laser3.addBox(0F, 0F, 0F, 2, 4, 2);
        Laser3.setRotationPoint(-4.5F, 17F, 2.5F);
        Laser3.setTextureSize(256, 256);
        Laser3.mirror = true;
        setRotation(Laser3, 0F, 0F, 0F);
        Laser4 = new ModelRenderer(this, 35, 43);
        Laser4.addBox(0F, 0F, 0F, 2, 4, 2);
        Laser4.setRotationPoint(-4.5F, 17F, -4.5F);
        Laser4.setTextureSize(256, 256);
        Laser4.mirror = true;
        setRotation(Laser4, 0F, 0F, 0F);
    }

    @Override
    public void render(float size, boolean on, TextureManager manager) {
        Base.render(size);
        Back.render(size);

        if (on) {
            MekanismRenderer.glowOn();
        }

        Panel.render(size);

        if (on) {
            MekanismRenderer.glowOff();
        }

        PanelStem.render(size);
        KeyboardStem.render(size);
        Keyboard.render(size);
        LaserBase.render(size);
        LegRight.render(size);
        UpperLegRight.render(size);
        ToeTopRight1.render(size);
        ToeTopRight2.render(size);
        ToeRight1.render(size);
        LegHoldRight1.render(size);
        LegHoldRight2.render(size);
        FootBaseRight.render(size);
        ToeRight2.render(size);
        FootBaseLeft.render(size);
        UpperLegLeft.render(size);
        LegHoldLeft1.render(size);
        ToeTopLeft2.render(size);
        ToeTopLeft1.render(size);
        LegHoldLeft2.render(size);
        ToeLeft1.render(size);
        ToeLeft2.render(size);
        LegLeft.render(size);
        RightAxil.render(size);
        RightBackUpperLeg.render(size);
        RightBackLowerLeg.render(size);
        RightBackFoot.render(size);
        LeftAxil.render(size);
        LeftBackUpperLeg.render(size);
        LeftBackLowerLeg.render(size);
        LeftBackFoot.render(size);
        Laser1.render(size);
        Laser2.render(size);
        Laser3.render(size);
        Laser4.render(size);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "LegacyDigitalMiner.png";
    }

    @Override
    public String getTextureNameForState(boolean on) {
        return on ? "LegacyDigitalMinerOn.png" : this.getTextureName();
    }

    @Override
    public void render(float size) {
        this.render(size, false, Minecraft.getMinecraft().renderEngine);
    }
}
