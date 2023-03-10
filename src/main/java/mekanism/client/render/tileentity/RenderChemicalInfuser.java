package mekanism.client.render.tileentity;

import java.util.HashMap;
import java.util.Map;

import mekanism.api.MekanismConfig;
import mekanism.api.gas.Gas;
import mekanism.client.model.IModelGlass;
import mekanism.client.model.LegacyModelChemicalInfuser;
import mekanism.client.model.ModelChemicalInfuser;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.MekanismRenderer.DisplayInteger;
import mekanism.client.render.MekanismRenderer.Model3D;
import mekanism.common.tile.TileEntityChemicalInfuser;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class RenderChemicalInfuser extends TileEntitySpecialRenderer {
    private IModelGlass model = MekanismConfig.client.modelType.createModel(
        ModelChemicalInfuser::new, LegacyModelChemicalInfuser::new
    );

    private static final double offset = 0.001;

    private Map<ForgeDirection, HashMap<Gas, DisplayInteger>> cachedGasses
        = new HashMap<ForgeDirection, HashMap<Gas, DisplayInteger>>();

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double x, double y, double z, float partialTick
    ) {
        renderAModelAt((TileEntityChemicalInfuser) tileEntity, x, y, z, partialTick);
    }

    private void renderAModelAt(
        TileEntityChemicalInfuser tileEntity,
        double x,
        double y,
        double z,
        float partialTick
    ) {
        if (MekanismConfig.client.modelType.isOld()) {
            render(false, x, y, z, tileEntity);

            if (tileEntity.leftTank.getGas() != null) {
                push();

                GL11.glTranslatef((float) x, (float) y, (float) z);
                GL11.glColor4f(
                    1.0F,
                    1.0F,
                    1.0F,
                    (float) tileEntity.leftTank.getStored()
                        / tileEntity.leftTank.getMaxGas()
                );
                bindTexture(MekanismRenderer.getBlocksTexture());
                getListAndRender(
                    ForgeDirection.getOrientation(tileEntity.facing),
                    tileEntity.leftTank.getGas().getGas()
                )
                    .render();
                GL11.glColor4f(1, 1, 1, 1);

                pop();
            }

            if (tileEntity.rightTank.getGas() != null) {
                push();

                switch (ForgeDirection.getOrientation(tileEntity.facing)) {
                    case NORTH:
                        GL11.glTranslatef(-0.4375F, 0, 0);
                        break;
                    case SOUTH:
                        GL11.glTranslatef(0.4375F, 0, 0);
                        break;
                    case EAST:
                        GL11.glTranslatef(0, 0, -0.4375F);
                        break;
                    case WEST:
                        GL11.glTranslatef(0, 0, 0.4375F);
                        break;
                }

                GL11.glTranslatef((float) x, (float) y, (float) z);
                GL11.glColor4f(
                    1.0F,
                    1.0F,
                    1.0F,
                    (float) tileEntity.rightTank.getStored()
                        / tileEntity.rightTank.getMaxGas()
                );
                bindTexture(MekanismRenderer.getBlocksTexture());
                getListAndRender(
                    ForgeDirection.getOrientation(tileEntity.facing),
                    tileEntity.rightTank.getGas().getGas()
                )
                    .render();
                GL11.glColor4f(1, 1, 1, 1);

                pop();
            }

            render(true, x, y, z, tileEntity);
        } else {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

            bindTexture(
                MekanismUtils.getResource(ResourceType.RENDER, model.getTextureName())
            );

            switch (tileEntity.facing) {
                case 2:
                    GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                    break;
                case 3:
                    GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                    break;
                case 4:
                    GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                    break;
                case 5:
                    GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                    break;
            }

            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            model.render(0.0625F);

            GL11.glPopMatrix();
        }
    }

    /*
     * 0: casing, 1: glass
     */
    private void render(
        boolean glass, double x, double y, double z, TileEntityChemicalInfuser tileEntity
    ) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        bindTexture(MekanismUtils.getResource(ResourceType.RENDER, model.getTextureName())
        );

        switch (tileEntity.facing) {
            case 2:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        if (!glass) {
            model.render(0.0625F);
        } else {
            model.renderGlass(0.0625F);
        }

        GL11.glPopMatrix();
    }

    @SuppressWarnings("incomplete-switch")
    private DisplayInteger getListAndRender(ForgeDirection side, Gas gas) {
        if (gas == null || gas.getIcon() == null) {
            return null;
        }

        if (cachedGasses.containsKey(side) && cachedGasses.get(side).containsKey(gas)) {
            return cachedGasses.get(side).get(gas);
        }

        Model3D toReturn = new Model3D();
        toReturn.baseBlock = Blocks.water;
        toReturn.setTexture(gas.getIcon());

        DisplayInteger display = DisplayInteger.createAndStart();

        if (cachedGasses.containsKey(side)) {
            cachedGasses.get(side).put(gas, display);
        } else {
            HashMap<Gas, DisplayInteger> map = new HashMap<Gas, DisplayInteger>();
            map.put(gas, display);
            cachedGasses.put(side, map);
        }

        switch (side) {
            case NORTH: {
                toReturn.minX = 0.625 + offset;
                toReturn.minY = 0.0625 + offset;
                toReturn.minZ = 0.625 + offset;

                toReturn.maxX = 0.8125 - offset;
                toReturn.maxY = 0.9375 - offset;
                toReturn.maxZ = 0.8125 - offset;
                break;
            }
            case SOUTH: {
                toReturn.minX = 0.1875 + offset;
                toReturn.minY = 0.0625 + offset;
                toReturn.minZ = 0.1875 + offset;

                toReturn.maxX = 0.375 - offset;
                toReturn.maxY = 0.9375 - offset;
                toReturn.maxZ = 0.375 - offset;
                break;
            }
            case WEST: {
                toReturn.minX = 0.625 + offset;
                toReturn.minY = 0.0625 + offset;
                toReturn.minZ = 0.1875 + offset;

                toReturn.maxX = 0.8125 - offset;
                toReturn.maxY = 0.9375 - offset;
                toReturn.maxZ = 0.375 - offset;
                break;
            }
            case EAST: {
                toReturn.minX = 0.1875 + offset;
                toReturn.minY = 0.0625 + offset;
                toReturn.minZ = 0.625 + offset;

                toReturn.maxX = 0.375 - offset;
                toReturn.maxY = 0.9375 - offset;
                toReturn.maxZ = 0.8125 - offset;
                break;
            }
        }

        MekanismRenderer.renderObject(toReturn);
        DisplayInteger.endList();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        return display;
    }

    private void pop() {
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private void push() {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
