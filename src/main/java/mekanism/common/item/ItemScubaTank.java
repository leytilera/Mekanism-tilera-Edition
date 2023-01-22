package mekanism.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.EnumColor;
import mekanism.api.MekanismConfig.general;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.IGasItem;
import mekanism.client.render.ModelCustomArmor;
import mekanism.client.render.ModelCustomArmor.ArmorModel;
import mekanism.common.Mekanism;
import mekanism.common.util.LangUtils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

public class ItemScubaTank extends ItemArmor implements IGasItem {
    public int TRANSFER_RATE = 16;

    public ItemScubaTank() {
        super(
            EnumHelper.addArmorMaterial("SCUBATANK", 0, new int[] { 0, 0, 0, 0 }, 0), 0, 1
        );
        setCreativeTab(Mekanism.tabMekanism);
    }

    @Override
    public void addInformation(
        ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag
    ) {
        GasStack gasStack = getGas(itemstack);

        if (gasStack == null) {
            list.add(LangUtils.localize("tooltip.noGas") + ".");
        } else {
            list.add(
                LangUtils.localize("tooltip.stored") + " "
                + gasStack.getGas().getLocalizedName() + ": " + gasStack.amount
            );
        }

        list.add(
            EnumColor.GREY + LangUtils.localize("tooltip.flowing") + ": "
            + (getFlowing(itemstack) ? EnumColor.DARK_GREEN : EnumColor.DARK_RED)
            + getFlowingStr(itemstack)
        );
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1D
            - ((getGas(stack) != null ? (double) getGas(stack).amount : 0D)
               / (double) getMaxGas(stack));
    }

    @Override
    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
        return armorType == 1;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return "mekanism:render/NullArmor.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped
    getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        ModelCustomArmor model = ModelCustomArmor.INSTANCE;
        model.modelType = ArmorModel.SCUBATANK;
        return model;
    }

    public void useGas(ItemStack itemstack) {
        setGas(
            itemstack,
            new GasStack(getGas(itemstack).getGas(), getGas(itemstack).amount - 1)
        );
    }

    public GasStack useGas(ItemStack itemstack, int amount) {
        if (getGas(itemstack) == null) {
            return null;
        }

        Gas type = getGas(itemstack).getGas();

        int gasToUse
            = Math.min(getStored(itemstack), Math.min(getRate(itemstack), amount));
        setGas(itemstack, new GasStack(type, getStored(itemstack) - gasToUse));

        return new GasStack(type, gasToUse);
    }

    @Override
    public int getMaxGas(ItemStack itemstack) {
        return general.maxScubaGas;
    }

    @Override
    public int getRate(ItemStack itemstack) {
        return TRANSFER_RATE;
    }

    @Override
    public int addGas(ItemStack itemstack, GasStack stack) {
        if (getGas(itemstack) != null && getGas(itemstack).getGas() != stack.getGas()) {
            return 0;
        }

        if (stack.getGas() != GasRegistry.getGas("oxygen")) {
            return 0;
        }

        int toUse = Math.min(
            getMaxGas(itemstack) - getStored(itemstack),
            Math.min(getRate(itemstack), stack.amount)
        );
        setGas(itemstack, new GasStack(stack.getGas(), getStored(itemstack) + toUse));

        return toUse;
    }

    @Override
    public GasStack removeGas(ItemStack itemstack, int amount) {
        return null;
    }

    public int getStored(ItemStack itemstack) {
        return getGas(itemstack) != null ? getGas(itemstack).amount : 0;
    }

    public void toggleFlowing(ItemStack stack) {
        setFlowing(stack, !getFlowing(stack));
    }

    public boolean getFlowing(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            return false;
        }

        return stack.stackTagCompound.getBoolean("flowing");
    }

    public String getFlowingStr(ItemStack stack) {
        boolean flowing = getFlowing(stack);

        return LangUtils.localize("tooltip." + (flowing ? "yes" : "no"));
    }

    public void setFlowing(ItemStack stack, boolean flowing) {
        if (stack.stackTagCompound == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.stackTagCompound.setBoolean("flowing", flowing);
    }

    @Override
    public boolean canReceiveGas(ItemStack itemstack, Gas type) {
        return type == GasRegistry.getGas("oxygen");
    }

    @Override
    public boolean canProvideGas(ItemStack itemstack, Gas type) {
        return false;
    }

    @Override
    public GasStack getGas(ItemStack itemstack) {
        if (itemstack.stackTagCompound == null) {
            return null;
        }

        return GasStack.readFromNBT(itemstack.stackTagCompound.getCompoundTag("stored"));
    }

    @Override
    public void setGas(ItemStack itemstack, GasStack stack) {
        if (itemstack.stackTagCompound == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        if (stack == null || stack.amount == 0) {
            itemstack.stackTagCompound.removeTag("stored");
        } else {
            int amount = Math.max(0, Math.min(stack.amount, getMaxGas(itemstack)));
            GasStack gasStack = new GasStack(stack.getGas(), amount);

            itemstack.stackTagCompound.setTag(
                "stored", gasStack.write(new NBTTagCompound())
            );
        }
    }

    public ItemStack getEmptyItem() {
        ItemStack empty = new ItemStack(this);
        setGas(empty, null);

        return empty;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        ItemStack empty = new ItemStack(this);
        setGas(empty, null);
        list.add(empty);

        ItemStack filled = new ItemStack(this);
        setGas(
            filled,
            new GasStack(
                GasRegistry.getGas("oxygen"),
                ((IGasItem) filled.getItem()).getMaxGas(filled)
            )
        );
        list.add(filled);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {}
}
