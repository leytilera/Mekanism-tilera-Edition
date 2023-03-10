package mekanism.common.inventory.container;

import mekanism.common.inventory.slot.SlotOutput;
import mekanism.common.tile.TileEntityFluidTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ContainerFluidTank extends Container {
    private TileEntityFluidTank tileEntity;

    public ContainerFluidTank(InventoryPlayer inventory, TileEntityFluidTank tentity) {
        tileEntity = tentity;
        addSlotToContainer(new Slot(tentity, 0, 146, 19));
        addSlotToContainer(new SlotOutput(tentity, 1, 146, 51));

        int slotY;

        for (slotY = 0; slotY < 3; slotY++) {
            for (int slotX = 0; slotX < 9; slotX++) {
                addSlotToContainer(new Slot(
                    inventory, slotX + slotY * 9 + 9, 8 + slotX * 18, 84 + slotY * 18
                ));
            }
        }

        for (slotY = 0; slotY < 9; slotY++) {
            addSlotToContainer(new Slot(inventory, slotY, 8 + slotY * 18, 142));
        }

        tileEntity.open(inventory.player);
        tileEntity.openInventory();
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);

        tileEntity.close(entityplayer);
        tileEntity.closeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return tileEntity.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        ItemStack stack = null;
        Slot currentSlot = (Slot) inventorySlots.get(slotID);

        if (currentSlot != null && currentSlot.getHasStack()) {
            int inputSlot = 0;
            int outputSlot = 1;
            int firstGenericInvSlot = 2;
            int firstHotbarSlot = 29;
            int lastHotbarSlot = 38;

            ItemStack slotStack = currentSlot.getStack();
            stack = slotStack.copy();

            if (FluidContainerRegistry.isEmptyContainer(slotStack)
                || FluidContainerRegistry.isFilledContainer(slotStack)) {
                if (slotID != inputSlot && slotID != outputSlot) {
                    if (!mergeItemStack(slotStack, inputSlot, outputSlot, false)) {
                        return null;
                    }
                } else {
                    if (!mergeItemStack(
                            slotStack, firstGenericInvSlot, lastHotbarSlot, true
                        )) {
                        return null;
                    }
                }
            } else {
                if (slotID >= firstGenericInvSlot && slotID < firstHotbarSlot) {
                    if (!mergeItemStack(
                            slotStack, firstHotbarSlot, lastHotbarSlot, false
                        )) {
                        return null;
                    }
                } else if (slotID >= firstHotbarSlot && slotID <= lastHotbarSlot) {
                    if (!mergeItemStack(
                            slotStack, firstGenericInvSlot, firstHotbarSlot, false
                        )) {
                        return null;
                    }
                } else {
                    if (!mergeItemStack(
                            slotStack, firstGenericInvSlot, lastHotbarSlot, true
                        )) {
                        return null;
                    }
                }
            }

            if (slotStack.stackSize == 0) {
                currentSlot.putStack((ItemStack) null);
            } else {
                currentSlot.onSlotChanged();
            }

            if (slotStack.stackSize == stack.stackSize) {
                return null;
            }

            currentSlot.onPickupFromSlot(player, slotStack);
        }

        return stack;
    }
}
