package mekanism.client.nei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.util.ListUtils;
import mekanism.client.gui.GuiPurificationChamber;
import mekanism.client.gui.element.GuiProgress.ProgressBar;
import mekanism.common.Tier.GasTankTier;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.recipe.machines.PurificationRecipe;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PurificationChamberRecipeHandler extends AdvancedMachineRecipeHandler {
    @Override
    public String getRecipeName() {
        return LangUtils.localize("tile.MachineBlock.PurificationChamber.name");
    }

    @Override
    public String getRecipeId() {
        return "mekanism.purificationchamber";
    }

    @Override
    public String getOverlayIdentifier() {
        return "purificationchamber";
    }

    @Override
    public Collection<PurificationRecipe> getRecipes() {
        return Recipe.PURIFICATION_CHAMBER.get().values();
    }

    @Override
    public ProgressBar getProgressType() {
        return ProgressBar.RED;
    }

    @Override
    public List<ItemStack> getFuelStacks(Gas gasType) {
        if (gasType == GasRegistry.getGas("oxygen")) {
            for (GasTankTier tier : GasTankTier.values()) {
                return ListUtils.asList(
                    new ItemStack(Items.flint),
                    MekanismUtils.getFullGasTank(tier, GasRegistry.getGas("oxygen"))
                );
            }
        }

        return new ArrayList<ItemStack>();
    }

    @Override
    public Class getGuiClass() {
        return GuiPurificationChamber.class;
    }
}
