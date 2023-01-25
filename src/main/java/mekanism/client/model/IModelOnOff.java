package mekanism.client.model;

import mekanism.client.IModelMekanism;
import net.minecraft.client.renderer.texture.TextureManager;

public interface IModelOnOff extends IModelMekanism {
    default void render(float size, boolean on, TextureManager manager) {
        this.render(size);
    }

    default String getTextureNameForState(boolean on) {
        return this.getTextureName();
    }
}
