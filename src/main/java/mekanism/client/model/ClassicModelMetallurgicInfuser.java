package mekanism.client.model;

import mekanism.client.ModelMekanismBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ClassicModelMetallurgicInfuser extends ModelMekanismBase {
    public ModelRenderer PANEL;
    public ModelRenderer MACHINE;
    public ModelRenderer CENTER_CIRCUIT;
    public ModelRenderer PIPE_1;
    public ModelRenderer PIPE_2;
    public ModelRenderer GUI_STAND;
    public ModelRenderer GUI_SCREEN;
    public ModelRenderer PIPE_2_TOP;
    public ModelRenderer PIPE_1_TOP;
    public ModelRenderer FRONT_PANEL;
    public ModelRenderer WIRE_1;
    public ModelRenderer WIRE_2;
    public ModelRenderer SIDE_WIRE_1;
    public ModelRenderer SIDE_WIRE_2;
    public ModelRenderer VERTICAL_WIRE;
    public ModelRenderer TUBE;

    public ClassicModelMetallurgicInfuser() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.PANEL = new ModelRenderer(this, 0, 0);
        this.PANEL.addBox(-8.0F, -0.5F, -8.0F, 16, 1, 16, 0.0F);
        this.PANEL.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.PANEL.setTextureSize(256, 256);
        this.PANEL.mirror = true;
        this.setRotation(this.PANEL, 0.0F, 0.0F, 0.0F);
        this.MACHINE = new ModelRenderer(this, 80, 0);
        this.MACHINE.addBox(-4.0F, -5.0F, -4.0F, 8, 10, 8, 0.0F);
        this.MACHINE.setRotationPoint(2.0F, -6.0F, 0.0F);
        this.MACHINE.setTextureSize(256, 256);
        this.MACHINE.mirror = true;
        this.setRotation(this.MACHINE, 0.0F, 0.0F, 0.0F);
        this.CENTER_CIRCUIT = new ModelRenderer(this, 64, 0);
        this.CENTER_CIRCUIT.addBox(-2.0F, -0.5F, -2.0F, 4, 1, 4, 0.0F);
        this.CENTER_CIRCUIT.setRotationPoint(2.0F, -11.5F, 0.0F);
        this.CENTER_CIRCUIT.setTextureSize(256, 256);
        this.CENTER_CIRCUIT.mirror = true;
        this.setRotation(this.CENTER_CIRCUIT, 0.0F, 0.0F, 0.0F);
        this.PIPE_1 = new ModelRenderer(this, 0, 32);
        this.PIPE_1.addBox(-1.0F, -5.5F, -0.5F, 2, 11, 1, 0.0F);
        this.PIPE_1.setRotationPoint(2.0F, -6.0F, -4.5F);
        this.PIPE_1.setTextureSize(256, 256);
        this.PIPE_1.mirror = true;
        this.setRotation(this.PIPE_1, 0.0F, 0.0F, 0.0F);
        this.PIPE_2 = new ModelRenderer(this, 0, 32);
        this.PIPE_2.addBox(-1.0F, -5.5F, -0.5F, 2, 11, 1, 0.0F);
        this.PIPE_2.setRotationPoint(2.0F, -6.0F, 4.5F);
        this.PIPE_2.setTextureSize(256, 256);
        this.PIPE_2.mirror = true;
        this.setRotation(this.PIPE_2, 0.0F, 0.0F, 0.0F);
        this.GUI_STAND = new ModelRenderer(this, 32, 32);
        this.GUI_STAND.addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, 0.0F);
        this.GUI_STAND.setRotationPoint(-5.0F, -4.5F, 0.0F);
        this.GUI_STAND.setTextureSize(256, 256);
        this.GUI_STAND.mirror = true;
        this.setRotation(this.GUI_STAND, 0.0F, 0.0F, 0.0F);
        this.GUI_SCREEN = new ModelRenderer(this, 48, 32);
        this.GUI_SCREEN.addBox(-1.5F, -0.5F, -3.0F, 3, 1, 6, 0.0F);
        this.GUI_SCREEN.setRotationPoint(-5.0F, -8.0F, 0.0F);
        this.GUI_SCREEN.setTextureSize(256, 256);
        this.GUI_SCREEN.mirror = true;
        this.setRotation(this.GUI_SCREEN, 0.0F, 0.0F, -0.5F);
        this.PIPE_2_TOP = new ModelRenderer(this, 16, 32);
        this.PIPE_2_TOP.addBox(-1.0F, -0.5F, -1.0F, 2, 1, 2, 0.0F);
        this.PIPE_2_TOP.setRotationPoint(2.0F, -11.0F, 3.0F);
        this.PIPE_2_TOP.setTextureSize(256, 256);
        this.PIPE_2_TOP.mirror = true;
        this.setRotation(this.PIPE_2_TOP, 0.0F, 0.0F, 0.0F);
        this.PIPE_1_TOP = new ModelRenderer(this, 16, 32);
        this.PIPE_1_TOP.addBox(-1.0F, -0.5F, -1.0F, 2, 1, 2, 0.0F);
        this.PIPE_1_TOP.setRotationPoint(2.0F, -11.0F, -3.0F);
        this.PIPE_1_TOP.setTextureSize(256, 256);
        this.PIPE_1_TOP.mirror = true;
        this.setRotation(this.PIPE_1_TOP, 0.0F, 0.0F, 0.0F);
        this.FRONT_PANEL = new ModelRenderer(this, 32, 64);
        this.FRONT_PANEL.addBox(-0.5F, -1.5F, -5.0F, 1, 3, 10, 0.0F);
        this.FRONT_PANEL.setRotationPoint(-7.0F, -2.5F, 0.0F);
        this.FRONT_PANEL.setTextureSize(256, 256);
        this.FRONT_PANEL.mirror = true;
        this.setRotation(this.FRONT_PANEL, 0.0F, 0.0F, 0.0F);
        this.WIRE_1 = new ModelRenderer(this, 48, 48);
        this.WIRE_1.addBox(-2.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
        this.WIRE_1.setRotationPoint(-0.5F, -1.0F, -4.5F);
        this.WIRE_1.setTextureSize(256, 256);
        this.WIRE_1.mirror = true;
        this.setRotation(this.WIRE_1, 0.0F, 0.0F, 0.0F);
        this.WIRE_2 = new ModelRenderer(this, 0, 48);
        this.WIRE_2.addBox(-3.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.WIRE_2.setRotationPoint(-1.5F, -1.0F, 4.5F);
        this.WIRE_2.setTextureSize(256, 256);
        this.WIRE_2.mirror = true;
        this.setRotation(this.WIRE_2, 0.0F, 0.0F, 0.0F);
        this.SIDE_WIRE_1 = new ModelRenderer(this, 32, 48);
        this.SIDE_WIRE_1.addBox(-0.5F, -0.5F, -2.5F, 1, 1, 5, 0.0F);
        this.SIDE_WIRE_1.setRotationPoint(-4.0F, -1.0F, 2.0F);
        this.SIDE_WIRE_1.setTextureSize(256, 256);
        this.SIDE_WIRE_1.mirror = true;
        this.setRotation(this.SIDE_WIRE_1, 0.0F, 0.0F, 0.0F);
        this.SIDE_WIRE_2 = new ModelRenderer(this, 64, 48);
        this.SIDE_WIRE_2.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3, 0.0F);
        this.SIDE_WIRE_2.setRotationPoint(-2.0F, -1.0F, -2.5F);
        this.SIDE_WIRE_2.setTextureSize(256, 256);
        this.SIDE_WIRE_2.mirror = true;
        this.setRotation(this.SIDE_WIRE_2, 0.0F, 0.0F, 0.0F);
        this.VERTICAL_WIRE = new ModelRenderer(this, 0, 64);
        this.VERTICAL_WIRE.addBox(-0.5F, -3.0F, -0.5F, 1, 6, 1, 0.0F);
        this.VERTICAL_WIRE.setRotationPoint(-2.0F, -4.5F, -1.5F);
        this.VERTICAL_WIRE.setTextureSize(256, 256);
        this.VERTICAL_WIRE.mirror = true;
        this.setRotation(this.VERTICAL_WIRE, 0.0F, 0.0F, 0.0F);
        this.TUBE = new ModelRenderer(this, 16, 64);
        this.TUBE.addBox(-2.5F, -0.5F, -1.0F, 5, 1, 2, 0.0F);
        this.TUBE.setRotationPoint(-4.0F, -3.0F, 2.0F);
        this.TUBE.setTextureSize(256, 256);
        this.TUBE.mirror = true;
        this.setRotation(this.TUBE, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(float size) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.5F, 0.0F);
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        this.PANEL.render(size);
        this.MACHINE.render(size);
        this.CENTER_CIRCUIT.render(size);
        this.PIPE_1.render(size);
        this.PIPE_2.render(size);
        this.GUI_STAND.render(size);
        this.GUI_SCREEN.render(size);
        this.PIPE_2_TOP.render(size);
        this.PIPE_1_TOP.render(size);
        this.FRONT_PANEL.render(size);
        this.WIRE_1.render(size);
        this.WIRE_2.render(size);
        this.SIDE_WIRE_1.render(size);
        this.SIDE_WIRE_2.render(size);
        this.VERTICAL_WIRE.render(size);
        this.TUBE.render(size);
        GL11.glPopMatrix();
    }

    @Override
    public void
    render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.PANEL.render(f5);
        this.MACHINE.render(f5);
        this.CENTER_CIRCUIT.render(f5);
        this.PIPE_1.render(f5);
        this.PIPE_2.render(f5);
        this.GUI_STAND.render(f5);
        this.GUI_SCREEN.render(f5);
        this.PIPE_2_TOP.render(f5);
        this.PIPE_1_TOP.render(f5);
        this.FRONT_PANEL.render(f5);
        this.WIRE_1.render(f5);
        this.WIRE_2.render(f5);
        this.SIDE_WIRE_1.render(f5);
        this.SIDE_WIRE_2.render(f5);
        this.VERTICAL_WIRE.render(f5);
        this.TUBE.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getTextureName() {
        return "ClassicMetallurgicInfuser.png";
    }
}
