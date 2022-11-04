package mekanism.common.item;

import mekanism.common.Mekanism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWeatherOrb extends ItemMekanism {
 
    public ItemWeatherOrb() {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(5000);
    }

    @Override
    public boolean hasEffect(final ItemStack itemstack) {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (itemstack.getItemDamage() == 0) {
            entityplayer.openGui(Mekanism.instance, 62, world, 0, 0, 0);
        }
        return itemstack;
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
        if (itemstack.getItemDamage() > 0 && entity instanceof EntityLivingBase) {
            itemstack.damageItem(-1, (EntityLivingBase) entity);
        }
    }

}
