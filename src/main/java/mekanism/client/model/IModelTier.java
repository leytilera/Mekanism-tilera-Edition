package mekanism.client.model;

import mekanism.client.IModelMekanism;
import mekanism.common.Tier.BaseTier;
import net.minecraft.client.renderer.texture.TextureManager;

public interface IModelTier extends IModelMekanism {
    String getTextureNameForTier(BaseTier tier);
    default void render(float size, BaseTier tier, TextureManager manager) {
        this.render(size);
    }
}
