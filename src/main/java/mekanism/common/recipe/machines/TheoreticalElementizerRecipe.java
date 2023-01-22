package mekanism.common.recipe.machines;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class TheoreticalElementizerRecipe
    extends AdvancedMachineRecipe<TheoreticalElementizerRecipe> {
    public TheoreticalElementizerRecipe(ItemStack output) {
        super(
            new ItemStack(Blocks.fire).setStackDisplayName("Destroys any item"),
            "elementizerFuel",
            output
        );
    }

    @Override
    public TheoreticalElementizerRecipe copy() {
        return new TheoreticalElementizerRecipe(getOutput().output.copy());
    }
}
