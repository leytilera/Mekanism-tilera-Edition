package mekanism.client;

import mekanism.client.model.IModelOnOff;
import net.minecraft.client.model.ModelBase;

/**
 * ModelMekanismBase
 *
 * Abstract class with a render method that takes only a size paramter.
 */
public abstract class ModelMekanismBase extends ModelBase implements IModelMekanism, IModelOnOff {}
