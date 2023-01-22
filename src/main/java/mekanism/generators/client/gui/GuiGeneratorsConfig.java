package mekanism.generators.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import mekanism.common.Mekanism;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

/**
 * Created by ben on 27/06/14.
 */
public class GuiGeneratorsConfig extends GuiConfig {
    public GuiGeneratorsConfig(GuiScreen parent) {
        super(
            parent,
            new ConfigElement(Mekanism.configuration.getCategory("generation"))
                .getChildElements(),
            "MekanismGenerators",
            false,
            false,
            "MekanismGenerators"
        );
    }
}
