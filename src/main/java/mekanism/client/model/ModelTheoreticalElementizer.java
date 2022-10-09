package mekanism.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelTheoreticalElementizer extends ModelBase
{
    ModelRenderer A;
    ModelRenderer B;
    ModelRenderer C;
    ModelRenderer DROT;
    ModelRenderer E;
    ModelRenderer F;
    ModelRenderer G;
    ModelRenderer H;
    ModelRenderer I;
    ModelRenderer J;
    ModelRenderer K;
    ModelRenderer L;
    ModelRenderer M;
    ModelRenderer N;
    ModelRenderer O;
    ModelRenderer P;
    ModelRenderer Q;
    ModelRenderer R;
    
    public ModelTheoreticalElementizer() {
        super.textureWidth = 128;
        super.textureHeight = 128;
        (this.A = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-8.0f, 0.0f, -8.0f, 16, 1, 16);
        this.A.setRotationPoint(0.0f, 23.0f, 0.0f);
        this.A.setTextureSize(64, 32);
        this.A.mirror = true;
        this.setRotation(this.A, 0.0f, 0.0f, 0.0f);
        (this.B = new ModelRenderer((ModelBase)this, 0, 40)).addBox(-5.0f, 0.0f, -4.0f, 10, 10, 8);
        this.B.setRotationPoint(0.0f, 13.0f, 0.0f);
        this.B.setTextureSize(64, 32);
        this.B.mirror = true;
        this.setRotation(this.B, 0.0f, 0.0f, 0.0f);
        (this.C = new ModelRenderer((ModelBase)this, 0, 19)).addBox(-3.0f, 0.0f, -3.0f, 6, 14, 6);
        this.C.setRotationPoint(0.0f, 8.0f, 0.0f);
        this.C.setTextureSize(64, 32);
        this.C.mirror = true;
        this.setRotation(this.C, 0.0f, 0.0f, 0.0f);
        (this.DROT = new ModelRenderer((ModelBase)this, 5, 0)).addBox(-1.0f, -1.0f, -1.0f, 2, 2, 2);
        this.DROT.setRotationPoint(0.0f, 5.0f, 0.0f);
        this.DROT.setTextureSize(64, 32);
        this.DROT.mirror = true;
        this.setRotation(this.DROT, 0.7853982f, 0.7853982f, 0.7853982f);
        (this.E = new ModelRenderer((ModelBase)this, 65, 0)).addBox(-2.0f, 0.0f, 2.0f, 4, 8, 7);
        this.E.setRotationPoint(0.0f, 15.0f, 0.0f);
        this.E.setTextureSize(64, 32);
        this.E.mirror = true;
        this.setRotation(this.E, 0.0f, 0.7853982f, 0.0f);
        (this.F = new ModelRenderer((ModelBase)this, 65, 0)).addBox(-2.0f, 0.0f, 2.0f, 4, 8, 7);
        this.F.setRotationPoint(0.0f, 15.0f, 0.0f);
        this.F.setTextureSize(64, 32);
        this.F.mirror = true;
        this.setRotation(this.F, 0.0f, -0.7853982f, 0.0f);
        (this.G = new ModelRenderer((ModelBase)this, 0, 0)).addBox(6.0f, 0.0f, 0.0f, 1, 4, 1);
        this.G.setRotationPoint(0.0f, 19.0f, -2.0f);
        this.G.setTextureSize(64, 32);
        this.G.mirror = true;
        this.setRotation(this.G, 0.0f, 0.0f, 0.0f);
        (this.H = new ModelRenderer((ModelBase)this, 0, 6)).addBox(5.0f, 0.0f, 0.0f, 1, 1, 1);
        this.H.setRotationPoint(0.0f, 19.0f, -2.0f);
        this.H.setTextureSize(64, 32);
        this.H.mirror = true;
        this.setRotation(this.H, 0.0f, 0.0f, 0.0f);
        (this.I = new ModelRenderer((ModelBase)this, 0, 6)).addBox(5.0f, 0.0f, 0.0f, 1, 1, 1);
        this.I.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.I.setTextureSize(64, 32);
        this.I.mirror = true;
        this.setRotation(this.I, 0.0f, 0.0f, 0.0f);
        (this.J = new ModelRenderer((ModelBase)this, 0, 0)).addBox(6.0f, 0.0f, 0.0f, 1, 4, 1);
        this.J.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.J.setTextureSize(64, 32);
        this.J.mirror = true;
        this.setRotation(this.J, 0.0f, 0.0f, 0.0f);
        (this.K = new ModelRenderer((ModelBase)this, 21, 61)).addBox(0.0f, -1.0f, -4.0f, 2, 9, 5);
        this.K.setRotationPoint(-7.0f, 15.0f, 1.0f);
        this.K.setTextureSize(64, 32);
        this.K.mirror = true;
        this.setRotation(this.K, 0.0f, 0.0f, 0.0f);
        (this.L = new ModelRenderer((ModelBase)this, 21, 77)).addBox(0.0f, -1.0f, -1.0f, 1, 2, 2);
        this.L.setRotationPoint(-8.0f, 16.0f, 0.0f);
        this.L.setTextureSize(64, 32);
        this.L.mirror = true;
        this.setRotation(this.L, 0.0f, 0.0f, 0.0f);
        (this.M = new ModelRenderer((ModelBase)this, 0, 61)).addBox(-4.0f, 0.0f, 0.0f, 8, 10, 0);
        this.M.setRotationPoint(0.0f, 3.0f, 0.0f);
        this.M.setTextureSize(64, 32);
        this.M.mirror = true;
        this.setRotation(this.M, 0.0f, 0.7853982f, 0.0f);
        (this.N = new ModelRenderer((ModelBase)this, 0, 73)).addBox(-4.0f, 0.0f, 0.0f, 8, 10, 0);
        this.N.setRotationPoint(0.0f, 3.0f, 0.0f);
        this.N.setTextureSize(64, 32);
        this.N.mirror = true;
        this.setRotation(this.N, 0.0f, -0.7853982f, 0.0f);
        (this.O = new ModelRenderer((ModelBase)this, 0, 93)).addBox(-5.0f, -5.0f, 0.0f, 6, 6, 0);
        this.O.setRotationPoint(0.0f, 6.0f, 0.0f);
        this.O.setTextureSize(64, 32);
        this.O.mirror = true;
        this.setRotation(this.O, 0.0f, -0.7853982f, 0.7853982f);
        (this.P = new ModelRenderer((ModelBase)this, 0, 85)).addBox(-5.0f, -5.0f, 0.0f, 6, 6, 0);
        this.P.setRotationPoint(0.0f, 6.0f, 0.0f);
        this.P.setTextureSize(64, 32);
        this.P.mirror = true;
        this.setRotation(this.P, 0.0f, 0.7853982f, 0.7853982f);
        (this.Q = new ModelRenderer((ModelBase)this, 65, 17)).addBox(-4.0f, 0.0f, 0.0f, 8, 6, 4);
        this.Q.setRotationPoint(0.0f, 17.0f, -8.0f);
        this.Q.setTextureSize(64, 32);
        this.Q.mirror = true;
        this.setRotation(this.Q, 0.0f, 0.0f, 0.0f);
        (this.R = new ModelRenderer((ModelBase)this, 65, 28)).addBox(-4.0f, 0.0f, 0.0f, 8, 3, 5);
        this.R.setRotationPoint(0.0f, 17.0f, -8.0f);
        this.R.setTextureSize(64, 32);
        this.R.mirror = true;
        this.setRotation(this.R, 0.5934119f, 0.0f, 0.0f);
    }
    
    public void render(final float size) {
        this.A.render(size);
        this.B.render(size);
        this.C.render(size);
        this.DROT.render(size);
        this.E.render(size);
        this.F.render(size);
        this.G.render(size);
        this.H.render(size);
        this.I.render(size);
        this.J.render(size);
        this.K.render(size);
        this.L.render(size);
        this.M.render(size);
        this.N.render(size);
        this.O.render(size);
        this.P.render(size);
        this.Q.render(size);
        this.R.render(size);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.A.render(f5);
        this.B.render(f5);
        this.C.render(f5);
        this.DROT.render(f5);
        this.E.render(f5);
        this.F.render(f5);
        this.G.render(f5);
        this.H.render(f5);
        this.I.render(f5);
        this.J.render(f5);
        this.K.render(f5);
        this.L.render(f5);
        this.M.render(f5);
        this.N.render(f5);
        this.O.render(f5);
        this.P.render(f5);
        this.Q.render(f5);
        this.R.render(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
