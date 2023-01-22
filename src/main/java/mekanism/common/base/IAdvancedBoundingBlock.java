package mekanism.common.base;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import ic2.api.energy.tile.IEnergySink;
import mekanism.api.Coord4D;
import mekanism.api.IConfigCardAccess.ISpecialConfigData;
import mekanism.api.energy.IStrictEnergyAcceptor;
import mekanism.api.energy.IStrictEnergyStorage;
import mekanism.common.integration.IComputerIntegration;
import mekanism.common.security.ISecurityTile;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

@InterfaceList({
    @Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
    ,
})
public interface IAdvancedBoundingBlock
    extends IBoundingBlock, ISidedInventory, IEnergySink, IStrictEnergyAcceptor,
            IStrictEnergyStorage, IEnergyHandler, IComputerIntegration,
            ISpecialConfigData, ISecurityTile {
    public int[] getBoundSlots(Coord4D location, int side);

    public boolean canBoundInsert(Coord4D location, int i, ItemStack itemstack);

    public boolean canBoundExtract(Coord4D location, int i, ItemStack itemstack, int j);

    public boolean canBoundReceiveEnergy(Coord4D location, ForgeDirection side);

    public void onPower();

    public void onNoPower();
}
