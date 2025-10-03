package mekanism.common.base;

import java.util.EnumSet;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import appeng.api.networking.IGridHost;
import appeng.api.networking.energy.IAEPowerStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;
import mekanism.api.energy.ICableOutputter;
import mekanism.api.energy.IStrictEnergyAcceptor;
import mekanism.api.energy.IStrictEnergyStorage;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.util.ForgeDirection;

@InterfaceList({
    @Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2"),
    @Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "IC2"),
    @Interface(iface = "ic2.api.tile.IEnergyStorage", modid = "IC2"),
    @Interface(iface = "api.hbm.energymk2.IEnergyProviderMK2", modid = "hbm"),
    @Interface(iface = "api.hbm.energymk2.IEnergyReceiverMK2", modid = "hbm"),
    @Interface(iface = "appeng.api.networking.IGridHost", modid = "appliedenergistics2"),
    @Interface(iface = "appeng.api.networking.energy.IAEPowerStorage", modid = "appliedenergistics2")
})
public interface IEnergyWrapper
    extends IStrictEnergyStorage, IEnergyHandler, IEnergySink, IEnergySource,
            IEnergyStorage, IStrictEnergyAcceptor, ICableOutputter, IInventory,
            IEnergyReceiverMK2, IEnergyProviderMK2, IAEPowerStorage, IGridHost {
    public EnumSet<ForgeDirection> getOutputtingSides();

    public EnumSet<ForgeDirection> getConsumingSides();

    public double getMaxOutput();
}
