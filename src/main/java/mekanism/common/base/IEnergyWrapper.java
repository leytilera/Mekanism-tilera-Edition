package mekanism.common.base;

import java.util.EnumSet;

import api.hbm.energymk2.IEnergyHandlerMK2;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IAEPowerStorage;
import appeng.api.util.AECableType;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;
import mekanism.api.energy.ICableOutputter;
import mekanism.api.energy.IStrictEnergyAcceptor;
import mekanism.api.energy.IStrictEnergyStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
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
    extends IDelegated, IStrictEnergyStorage, IEnergyHandler, IEnergySink, IEnergySource,
            IEnergyStorage, IStrictEnergyAcceptor, ICableOutputter, IInventory,
            IEnergyReceiverMK2, IEnergyProviderMK2, IAEPowerStorage, IGridHost {
    public EnumSet<ForgeDirection> getOutputtingSides();

    public EnumSet<ForgeDirection> getConsumingSides();

    public double getMaxOutput();

    @Override
    default int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return getDelegate(IEnergyHandler.class).map(o -> o.extractEnergy(from, maxExtract, simulate)).orElse(0);
    }

    @Override
    default int getEnergyStored(ForgeDirection from) {
        return getDelegate(IEnergyHandler.class).map(o -> o.getEnergyStored(from)).orElse(0);
    }

    @Override
    default int getMaxEnergyStored(ForgeDirection from) {
        return getDelegate(IEnergyHandler.class).map(o -> o.getMaxEnergyStored(from)).orElse(0);
    }

    @Override
    default int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return getDelegate(IEnergyHandler.class).map(o -> o.receiveEnergy(from, maxReceive, simulate)).orElse(0);
    }

    @Override
    default boolean canConnectEnergy(ForgeDirection from) {
        return getDelegate(IEnergyHandler.class).map(o -> o.canConnectEnergy(from)).orElse(false);
    }

    @Override
    @Method(modid = "IC2")
    default int getSinkTier() {
        return getDelegate(IEnergySink.class).map(o -> o.getSinkTier()).orElse(4);
    }

    @Override
    @Method(modid = "IC2")
    default int getSourceTier() {
        return getDelegate(IEnergySource.class).map(o -> o.getSourceTier()).orElse(1);
    }

    @Override
    @Method(modid = "IC2")
    default void setStored(int energy) {
        getDelegate(IEnergyStorage.class).ifPresent(o -> o.setStored(energy));
    }

    @Override
    @Method(modid = "IC2")
    default int addEnergy(int amount) {
        return getDelegate(IEnergyStorage.class).map(o -> o.addEnergy(amount)).orElse(0);
    }

    @Override
    @Method(modid = "IC2")
    default boolean isTeleporterCompatible(ForgeDirection side) {
        return getDelegate(IEnergyStorage.class).map(o -> o.isTeleporterCompatible(side)).orElse(false);
    }

    @Override
    @Method(modid = "IC2")
    default boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return getDelegate(IEnergySink.class).map(o -> o.acceptsEnergyFrom(emitter, direction)).orElse(false);
    }

    @Override
    @Method(modid = "IC2")
    default boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return getDelegate(IEnergySource.class).map(o -> o.emitsEnergyTo(receiver, direction)).orElse(false);
    }

    @Override
    @Method(modid = "IC2")
    default int getStored() {
        return getDelegate(IEnergyStorage.class).map(o -> o.getStored()).orElse(0);
    }

    @Override
    @Method(modid = "IC2")
    default int getCapacity() {
        return getDelegate(IEnergyStorage.class).map(o -> o.getCapacity()).orElse(0);
    }

    @Override
    @Method(modid = "IC2")
    default int getOutput() {
        return getDelegate(IEnergyStorage.class).map(o -> o.getOutput()).orElse(0);
    }

    @Override
    @Method(modid = "IC2")
    default double getDemandedEnergy() {
        return getDelegate(IEnergySink.class).map(o -> o.getDemandedEnergy()).orElse(0.0);
    }

    @Override
    @Method(modid = "IC2")
    default double getOfferedEnergy() {
        return getDelegate(IEnergySource.class).map(o -> o.getOfferedEnergy()).orElse(0.0);
    }

    @Override
    @Method(modid = "IC2")
    default double getOutputEnergyUnitsPerTick() {
        return getDelegate(IEnergyStorage.class).map(o -> o.getOutputEnergyUnitsPerTick()).orElse(0.0);
    }

    @Override
    @Method(modid = "IC2")
    default double injectEnergy(ForgeDirection direction, double amount, double voltage) {
        return getDelegate(IEnergySink.class).map(o -> o.injectEnergy(direction, amount, voltage)).orElse(amount);
    }

    @Override
    @Method(modid = "IC2")
    default void drawEnergy(double amount) {
        getDelegate(IEnergySource.class).ifPresent(o -> o.drawEnergy(amount));
    }

    @Override
    @Method(modid = "hbm")
    default long getMaxPower() {
        return getDelegate(IEnergyHandlerMK2.class).map(o -> o.getMaxPower()).orElse(0L);
    }

    @Override
    @Method(modid = "hbm")
    default long getPower() {
        return getDelegate(IEnergyHandlerMK2.class).map(o -> o.getPower()).orElse(0L);
    }

    @Override
    @Method(modid = "hbm")
    default void setPower(long power) {
        getDelegate(IEnergyHandlerMK2.class).ifPresent(o -> o.setPower(power));
    }

    @Override
    @Method(modid = "hbm")
    default boolean canConnect(ForgeDirection dir) {
        return getDelegate(IEnergyHandlerMK2.class).map(o -> o.canConnect(dir)).orElse(false);
    }

    @Override
    @Method(modid = "hbm")
    default boolean isLoaded() {
        return getDelegate(IEnergyHandlerMK2.class).map(o -> o.isLoaded()).orElse(false);
    }

    @Override
    @Method(modid = "hbm")
    default long getProviderSpeed() {
        return getDelegate(IEnergyProviderMK2.class).map(o -> o.getProviderSpeed()).orElse(0L);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default double getAECurrentPower() {
        return this.getDelegate(IAEPowerStorage.class).map(o -> o.getAECurrentPower()).orElse(0.0);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default double getAEMaxPower() {
        return this.getDelegate(IAEPowerStorage.class).map(o -> o.getAEMaxPower()).orElse(0.0);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default AccessRestriction getPowerFlow() {
        return this.getDelegate(IAEPowerStorage.class).map(o -> o.getPowerFlow()).orElse(AccessRestriction.NO_ACCESS);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default double injectAEPower(double amt, Actionable mode) {
        return this.getDelegate(IAEPowerStorage.class).map(o -> o.injectAEPower(amt, mode)).orElse(amt);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default boolean isAEPublicPowerStorage() {
        return this.getDelegate(IAEPowerStorage.class).map(o -> o.isAEPublicPowerStorage()).orElse(false);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default double extractAEPower(double amt, Actionable mode, PowerMultiplier usePowerMultiplier) {
        return this.getDelegate(IAEPowerStorage.class).map(o -> o.extractAEPower(amt, mode, usePowerMultiplier)).orElse(0.0);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default AECableType getCableConnectionType(ForgeDirection dir) {
        return getDelegate(IGridHost.class).map(o -> o.getCableConnectionType(dir)).orElse(AECableType.NONE);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default IGridNode getGridNode(ForgeDirection dir) {
        return getDelegate(IGridHost.class).map(o -> o.getGridNode(dir)).orElse(null);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    default void securityBreak() {
        getDelegate(IGridHost.class).ifPresent(o -> o.securityBreak());
    }
    
}
