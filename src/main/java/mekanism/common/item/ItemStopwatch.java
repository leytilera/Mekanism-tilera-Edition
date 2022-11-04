package mekanism.common.item;


import mekanism.common.Mekanism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStopwatch extends ItemMekanism {

    public ItemStopwatch() {
        super();
        setMaxStackSize(1);
        setMaxDamage(5000);
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemstack) {                                                                                                                                     
        return true;                                                                                                                                                                          
    }

    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {                                                                
        if (itemstack.getItemDamage() > 0 && entity instanceof EntityLivingBase) {                                                                                                                                                  
            itemstack.damageItem(-1, (EntityLivingBase) entity);                                                                                                                                 
        }                                                                                                                                                                                     
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (itemstack.getItemDamage() == 0) {
            entityplayer.openGui(Mekanism.instance, 61, world, 0, 0, 0);
        }
        return itemstack;
    }

}
