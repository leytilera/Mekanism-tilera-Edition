package mekanism.common.item;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import mekanism.api.EnumColor;
import mekanism.api.energy.IEnergizedItem;
import mekanism.common.Mekanism;
import mekanism.common.Units;
import mekanism.common.integration.IC2ItemManager;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@InterfaceList({ @Interface(iface = "ic2.api.item.ISpecialElectricItem", modid = "IC2") })
public class ItemEnergized extends ItemMekanism
    implements IEnergizedItem, ISpecialElectricItem, IEnergyContainerItem {
    /** The maximum amount of energy this item can hold. */
    public double MAX_ELECTRICITY;

    public ItemEnergized(double maxElectricity) {
        super();
        MAX_ELECTRICITY = maxElectricity;
        setMaxStackSize(1);
        setCreativeTab(Mekanism.tabMekanism);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1D - (getEnergy(stack) / getMaxEnergy(stack));
    }

    @Override
    public void addInformation(
        ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag
    ) {
        list.add(
            EnumColor.AQUA + LangUtils.localize("tooltip.storedEnergy") + ": "
            + EnumColor.GREY + MekanismUtils.getEnergyDisplay(getEnergy(itemstack))
        );
    }

    public ItemStack getUnchargedItem() {
        return new ItemStack(this);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        ItemStack discharged = new ItemStack(this);
        list.add(discharged);
        ItemStack charged = new ItemStack(this);
        setEnergy(charged, ((IEnergizedItem) charged.getItem()).getMaxEnergy(charged));
        list.add(charged);
    }

    @Override
    @Method(modid = "IC2")
    public boolean canProvideEnergy(ItemStack itemStack) {
        return canSend(itemStack);
    }

    @Override
    @Method(modid = "IC2")
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    @Method(modid = "IC2")
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    @Method(modid = "IC2")
    public double getMaxCharge(ItemStack itemStack) {
        return 0;
    }

    @Override
    @Method(modid = "IC2")
    public int getTier(ItemStack itemStack) {
        return 4;
    }

    @Override
    @Method(modid = "IC2")
    public double getTransferLimit(ItemStack itemStack) {
        return 0;
    }

    @Override
    public double getEnergy(ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            return 0;
        }

        return itemStack.stackTagCompound.getDouble("electricity");
    }

    @Override
    public void setEnergy(ItemStack itemStack, double amount) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        double electricityStored = Math.max(Math.min(amount, getMaxEnergy(itemStack)), 0);
        itemStack.stackTagCompound.setDouble("electricity", electricityStored);
    }

    @Override
    public double getMaxEnergy(ItemStack itemStack) {
        return MAX_ELECTRICITY;
    }

    @Override
    public double getMaxTransfer(ItemStack itemStack) {
        return getMaxEnergy(itemStack) * 0.005;
    }

    @Override
    public boolean canReceive(ItemStack itemStack) {
        return getMaxEnergy(itemStack) - getEnergy(itemStack) > 0;
    }

    @Override
    public boolean canSend(ItemStack itemStack) {
        return getEnergy(itemStack) > 0;
    }

    @Override
    public int receiveEnergy(ItemStack theItem, int energy, boolean simulate) {
        if (canReceive(theItem)) {
            double energyNeeded = getMaxEnergy(theItem) - getEnergy(theItem);
            double toReceive = Math.min(Units.convertToJoules(energy, Units.RF), energyNeeded);

            if (!simulate) {
                setEnergy(theItem, getEnergy(theItem) + toReceive);
            }

            return (int) Math.round(Units.convertFromJoules(toReceive, Units.RF));
        }

        return 0;
    }

    @Override
    public int extractEnergy(ItemStack theItem, int energy, boolean simulate) {
        if (canSend(theItem)) {
            double energyRemaining = getEnergy(theItem);
            double toSend = Math.min(Units.convertToJoules(energy, Units.RF), energyRemaining);

            if (!simulate) {
                setEnergy(theItem, getEnergy(theItem) - toSend);
            }

            return (int) Math.round(Units.convertFromJoules(toSend, Units.RF));
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ItemStack theItem) {
        return (int) Units.convertFromJoules(getEnergy(theItem), Units.RF);
    }

    @Override
    public int getMaxEnergyStored(ItemStack theItem) {
        return (int) Units.convertFromJoules(getMaxEnergy(theItem), Units.RF);
    }

    @Override
    @Method(modid = "IC2")
    public IElectricItemManager getManager(ItemStack itemStack) {
        return IC2ItemManager.getManager(this);
    }
}
