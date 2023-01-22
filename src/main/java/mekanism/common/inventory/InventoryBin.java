package mekanism.common.inventory;

import mekanism.api.util.StackUtils;
import mekanism.common.Tier.BinTier;
import mekanism.common.base.ITierItem;
import mekanism.common.block.BlockBasic.BasicType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryBin {
    public ItemStack bin;

    public InventoryBin(ItemStack stack) {
        bin = stack;
    }

    public ItemStack getStack() {
        if (getItemCount() > 0 && getItemType() != null) {
            ItemStack ret = getItemType().copy();
            ret.stackSize = Math.min(getItemType().getMaxStackSize(), getItemCount());

            return ret;
        }

        return null;
    }

    public ItemStack removeStack() {
        ItemStack stack = getStack();

        if (stack == null) {
            return null;
        }

        setItemCount(getItemCount() - stack.stackSize);
        return stack.copy();
    }

    public ItemStack add(ItemStack stack) {
        if (isValid(stack) && getItemCount() != getMaxStorage()) {
            if (getItemType() == null) {
                setItemType(stack);
            }

            if (getItemCount() + stack.stackSize <= getMaxStorage()) {
                setItemCount(getItemCount() + stack.stackSize);
                return null;
            } else {
                ItemStack rejects = getItemType().copy();
                rejects.stackSize = (getItemCount() + stack.stackSize) - getMaxStorage();

                setItemCount(getMaxStorage());

                return rejects;
            }
        }

        return stack;
    }

    public boolean isValid(ItemStack stack) {
        if (stack == null || stack.stackSize <= 0) {
            return false;
        }

        if (BasicType.get(stack) == BasicType.BIN) {
            return false;
        }

        if (getItemType() == null) {
            return true;
        }

        if (!stack.isItemEqual(getItemType())
            || !ItemStack.areItemStackTagsEqual(stack, getItemType())) {
            return false;
        }

        return true;
    }

    public int getMaxStorage() {
        return BinTier.values()[((ITierItem) bin.getItem()).getBaseTier(bin).ordinal()]
            .storage;
    }

    public int getItemCount() {
        if (bin.stackTagCompound == null) {
            return 0;
        }

        return bin.stackTagCompound.getInteger("itemCount");
    }

    public void setItemCount(int count) {
        if (bin.stackTagCompound == null) {
            bin.setTagCompound(new NBTTagCompound());
        }

        bin.stackTagCompound.setInteger("itemCount", Math.max(0, count));

        if (getItemCount() == 0) {
            setItemType(null);
        }
    }

    public ItemStack getItemType() {
        if (bin.stackTagCompound == null || getItemCount() == 0) {
            return null;
        }

        return ItemStack.loadItemStackFromNBT(
            bin.stackTagCompound.getCompoundTag("storedItem")
        );
    }

    public void setItemType(ItemStack stack) {
        if (bin.stackTagCompound == null) {
            bin.setTagCompound(new NBTTagCompound());
        }

        if (stack == null) {
            bin.stackTagCompound.removeTag("storedItem");
            return;
        }

        ItemStack ret = StackUtils.size(stack, 1);

        bin.stackTagCompound.setTag(
            "storedItem", StackUtils.size(stack, 1).writeToNBT(new NBTTagCompound())
        );
    }
}
