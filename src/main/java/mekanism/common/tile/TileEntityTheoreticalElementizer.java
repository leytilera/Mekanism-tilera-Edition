package mekanism.common.tile;

import java.util.HashMap;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.MekanismConfig;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.common.MekanismItems;
import mekanism.common.block.BlockMachine;
import mekanism.common.recipe.machines.TheoreticalElementizerRecipe;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTheoreticalElementizer extends TileEntityAdvancedElectricMachine<TheoreticalElementizerRecipe> {

    private static Item[] items = null;
    private static Item fuelItem = null;

    public TileEntityTheoreticalElementizer() {
        super("elementizer", "TheoreticalElementizer", 24, 1, 1000, BlockMachine.MachineType.THEORETICAL_ELEMENTIZER.baseEnergy);
        MAX_GAS = 1000;
        if (items == null) {
            items = new Item[MekanismConfig.theoreticalelementizer.items.length];
            for (int i = 0; i < items.length; i++) {
                String[] parts = MekanismConfig.theoreticalelementizer.items[i].split(":");
                items[i] = GameRegistry.findItem(parts[0], parts[1]);
            }
        }
        if (fuelItem == null) {
            String[] parts = MekanismConfig.theoreticalelementizer.fuel.split(":");
            fuelItem = GameRegistry.findItem(parts[0], parts[1]);
        }
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
        final ItemStack itemstack = new ItemStack(getRandomMagicItem());
        final ItemStack itemStack = super.inventory[0];
        --itemStack.stackSize;
        if (super.inventory[0].stackSize <= 0) {
            super.inventory[0] = null;
        }
        super.inventory[2] = itemstack;
    }

    @Override
    public GasStack getItemGas(ItemStack itemstack) {
        if (itemstack.getItem() == fuelItem) {
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

    public static Item getRandomMagicItem() {
        int range = items.length + MekanismConfig.theoreticalelementizer.failChanceMultiplier;
        int index = 0;
        if (range > 0) {
            final Random rand = new Random();
            index = rand.nextInt(range);
        }
        if (index < items.length) {
            return items[index];
        }
        return MekanismItems.EnrichedAlloy;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }
}
