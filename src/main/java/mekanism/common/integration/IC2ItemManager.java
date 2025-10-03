package mekanism.common.integration;

import ic2.api.item.IElectricItemManager;
import mekanism.api.energy.IEnergizedItem;
import mekanism.common.Units;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class IC2ItemManager implements IElectricItemManager {
    public IEnergizedItem energizedItem;

    public static IC2ItemManager getManager(IEnergizedItem item) {
        IC2ItemManager manager = new IC2ItemManager();
        manager.energizedItem = item;

        return manager;
    }

    @Override
    public double charge(
        ItemStack itemStack,
        double amount,
        int tier,
        boolean ignoreTransferLimit,
        boolean simulate
    ) {
        if (energizedItem.canReceive(itemStack)) {
            double energyNeeded = energizedItem.getMaxEnergy(itemStack)
                - energizedItem.getEnergy(itemStack);
            double energyToStore = Math.min(
                Math.min(
                    Units.convertToJoules(amount, Units.EU),
                    energizedItem.getMaxEnergy(itemStack) * 0.01
                ),
                energyNeeded
            );

            if (!simulate) {
                energizedItem.setEnergy(
                    itemStack, energizedItem.getEnergy(itemStack) + energyToStore
                );
            }

            return (int) Math.round(Units.convertFromJoules(energyToStore, Units.EU));
        }

        return 0;
    }

    @Override
    public double discharge(
        ItemStack itemStack,
        double amount,
        int tier,
        boolean ignoreTransferLimit,
        boolean external,
        boolean simulate
    ) {
        if (energizedItem.canSend(itemStack)) {
            double energyWanted = Units.convertToJoules(amount, Units.EU);
            double energyToGive = Math.min(
                Math.min(energyWanted, energizedItem.getMaxEnergy(itemStack) * 0.01),
                energizedItem.getEnergy(itemStack)
            );

            if (!simulate) {
                energizedItem.setEnergy(
                    itemStack, energizedItem.getEnergy(itemStack) - energyToGive
                );
            }

            return (int) Math.round(Units.convertFromJoules(energyToGive, Units.EU));
        }

        return 0;
    }

    @Override
    public boolean canUse(ItemStack itemStack, double amount) {
        return energizedItem.getEnergy(itemStack) >= Units.convertToJoules(amount, Units.EU);
    }

    @Override
    public double getCharge(ItemStack itemStack) {
        return (int) Math.round(Units.convertFromJoules(energizedItem.getEnergy(itemStack), Units.EU));
    }

    @Override
    public boolean use(ItemStack itemStack, double amount, EntityLivingBase entity) {
        return false;
    }

    @Override
    public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {}

    @Override
    public String getToolTip(ItemStack itemStack) {
        return null;
    }
}
