package mekanism.client.nei;

import java.util.Collection;
import java.util.List;

import mekanism.api.gas.Gas;
import mekanism.client.gui.GuiTheoreticalElementizer;
import mekanism.client.gui.element.GuiProgress.ProgressBar;
import mekanism.common.recipe.ElementizerRecipeHandler;
import mekanism.common.recipe.machines.AdvancedMachineRecipe;
import mekanism.common.util.LangUtils;
import net.minecraft.item.ItemStack;

public class TheoreticalElementizerRecipeHandler extends AdvancedMachineRecipeHandler {
    @Override
    public String getRecipeName() {
        return LangUtils.localize("tile.MachineBlock3.TheoreticalElementizer.name");
    }

    @Override
    public String getRecipeId() {
        return "mekanism.elementizer";
    }

    @Override
    public Collection<? extends AdvancedMachineRecipe> getRecipes() {
        return ElementizerRecipeHandler.getRecipes();
    }

    @Override
    public List<ItemStack> getFuelStacks(Gas gasType) {
        return ElementizerRecipeHandler.getFuelStacks();
    }

    @Override
    public ProgressBar getProgressType() {
        return ProgressBar.BLUE;
    }

    @Override
    public Class getGuiClass() {
        return GuiTheoreticalElementizer.class;
    }
}
