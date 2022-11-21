package mekanism.common.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mekanism.api.MekanismConfig;
import mekanism.common.MekanismItems;
import mekanism.common.recipe.machines.TheoreticalElementizerRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ElementizerRecipeHandler {
 
    public static List<ItemStack> outputItems = new ArrayList<>();
    public static List<ItemStack> fuelItems = new ArrayList<>();

    public static ItemStack getRandomMagicItem() {
        int range = outputItems.size() + MekanismConfig.general.elementizerFailChanceMultiplier;
        int index = 0;
        if (range > 0) {
            final Random rand = new Random();
            index = rand.nextInt(range);
        }
        if (index < outputItems.size()) {
            return outputItems.get(index);
        }
        return new ItemStack(MekanismItems.EnrichedAlloy);
    }

    public static boolean isFuelItem(ItemStack item) {
        for (ItemStack s : fuelItems) {
            if (s.isItemEqual(item)) {
                return true;
            }
        }
        return false;
    }

    public static List<ItemStack> getFuelStacks() {
        return new ArrayList<>(fuelItems);
    }

    public static List<TheoreticalElementizerRecipe> getRecipes() {
        List<TheoreticalElementizerRecipe> rec = new ArrayList<>();
        for (ItemStack s : outputItems) {
            rec.add(new TheoreticalElementizerRecipe(s));
        }
        return rec;
    }

    public static void loadDefaultItems() {
        fuelItems.add(new ItemStack(Items.diamond));
        outputItems.add(new ItemStack(MekanismItems.Stopwatch));
        outputItems.add(new ItemStack(MekanismItems.WeatherOrb));
    }

}
