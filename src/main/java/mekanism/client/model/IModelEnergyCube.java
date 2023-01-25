package mekanism.client.model;

import mekanism.common.SideData.IOState;
import mekanism.common.Tier.EnergyCubeTier;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.common.util.ForgeDirection;

public interface IModelEnergyCube extends IModelTier {
    void renderSide(
        float size,
        ForgeDirection side,
        IOState state,
        EnergyCubeTier tier,
        TextureManager renderer
    );
}
