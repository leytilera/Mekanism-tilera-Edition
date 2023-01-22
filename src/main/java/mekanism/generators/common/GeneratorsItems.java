package mekanism.generators.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import mekanism.common.item.ItemMekanism;
import mekanism.generators.common.item.ItemHohlraum;
import mekanism.generators.common.tile.turbine.TileEntityTurbineRotor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

@ObjectHolder("MekanismGenerators")
public class GeneratorsItems {
    public static final Item SolarPanel
        = new ItemMekanism().setUnlocalizedName("SolarPanel");
    public static final ItemHohlraum Hohlraum
        = (ItemHohlraum) new ItemHohlraum().setUnlocalizedName("Hohlraum");
    public static final Item TurbineBlade = new ItemMekanism() {
        @Override
        public boolean doesSneakBypassUse(
            World world, int x, int y, int z, EntityPlayer player
        ) {
            return world.getTileEntity(x, y, z) instanceof TileEntityTurbineRotor;
        }
    }.setUnlocalizedName("TurbineBlade");

    public static void register() {
        GameRegistry.registerItem(SolarPanel, "SolarPanel");
        GameRegistry.registerItem(Hohlraum, "Hohlraum");
        GameRegistry.registerItem(TurbineBlade, "TurbineBlade");
    }
}
