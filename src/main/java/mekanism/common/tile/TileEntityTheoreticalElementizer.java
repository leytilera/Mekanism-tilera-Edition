package mekanism.common.tile;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.common.block.BlockMachine;
import mekanism.common.recipe.ElementizerRecipeHandler;
import mekanism.common.recipe.machines.TheoreticalElementizerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTheoreticalElementizer extends TileEntityAdvancedElectricMachine<TheoreticalElementizerRecipe> {

    public TileEntityTheoreticalElementizer() {
        super("elementizer", "TheoreticalElementizer", 24, 1, 1000, BlockMachine.MachineType.THEORETICAL_ELEMENTIZER.baseEnergy);
        MAX_GAS = 1000;
    }
    
    @Override
    public HashMap getRecipes() {
        return new HashMap();
    }

    @Override
    public boolean canOperate(TheoreticalElementizerRecipe recipe) {
        return super.inventory[0] != null && super.inventory[2] == null;
    }

    @Override
    public void operate(TheoreticalElementizerRecipe recipe) {
        if (!this.canOperate(recipe)) {
            return;
        }
        final ItemStack itemstack = ElementizerRecipeHandler.getRandomMagicItem();
        final ItemStack itemStack = super.inventory[0];
        --itemStack.stackSize;
        if (super.inventory[0].stackSize <= 0) {
            super.inventory[0] = null;
        }
        super.inventory[2] = itemstack;
    }

    @Override
    public GasStack getItemGas(ItemStack itemstack) {
        if (ElementizerRecipeHandler.isFuelItem(itemstack)) {
            return new GasStack(GasRegistry.getGas("elementizerFuel"), 1000);
        }
        return null;
    }

    @Override
    public boolean isValidGas(Gas gas) {
        return false;
    }

    @Override
	public TheoreticalElementizerRecipe getRecipe()
	{
        return null;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }
}
