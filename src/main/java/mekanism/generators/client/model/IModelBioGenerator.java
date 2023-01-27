package mekanism.generators.client.model;

import mekanism.client.IModelMekanism;

public interface IModelBioGenerator extends IModelMekanism {
    default void render(float size, float depth) {
        this.render(size);
    }
}
