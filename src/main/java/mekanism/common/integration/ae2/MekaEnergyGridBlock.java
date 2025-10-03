package mekanism.common.integration.ae2;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IAEPowerStorage;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import mekanism.common.Units;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.util.MekanismUtils;
import net.anvilcraft.anvillib.vector.WorldVec;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@InterfaceList({
    @Interface(iface = "appeng.api.networking.IGridHost", modid = "appliedenergistics2"),
    @Interface(iface = "appeng.api.networking.energy.IAEPowerStorage", modid = "appliedenergistics2")
})
public class MekaEnergyGridBlock<T extends TileEntity & IEnergyWrapper & IGridHost> implements IAEPowerStorage {

    private T host;
    private Map<ForgeDirection, IGridNode> nodes = new HashMap<>();

    public MekaEnergyGridBlock(T host) {
        this.host = host;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            String key = "ae2node#" + side.ordinal();
            IGridNode node = this.getGridNode(side);
            if (nbt.hasKey(key) && node != null) {
                node.loadFromNBT(key, nbt);
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if (nodes.containsKey(side)) {
                nodes.get(side).saveToNBT("ae2node#" + side.ordinal(), nbt);
            }
        }
    }

    private boolean shouldCreate(ForgeDirection side) {
        TileEntity te = ((WorldVec)new WorldVec(host).offset(side)).getTileEntity();
        return MekanismUtils.useAE() && te instanceof IGridHost && !(te instanceof IEnergyWrapper);
    }

    public void destroy() {
        this.nodes.values().forEach(n -> n.destroy());
        this.nodes.clear();
    }

    public void update() {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if (this.getGridNode(side) == null) {
                return;
            }
        }
    }

    @Method(modid = "appliedenergistics2")
    public IGridNode getGridNode(ForgeDirection side) {
        if (FMLCommonHandler.instance().getSide().isClient() && (host.getWorldObj() == null || host.getWorldObj().isRemote)) {
			return null;
		} else if (!this.nodes.containsKey(side) && shouldCreate(side)) {
            IGridBlock block = new GridBlock(side);
            IGridNode node = AEApi.instance().createGridNode(block);
            node.updateState();
            this.nodes.put(side, node);
        }
        return this.nodes.get(side);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double getAECurrentPower() {
        return Units.convertFromJoules(host.getEnergy(), Units.AE);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double getAEMaxPower() {
        return Units.convertFromJoules(host.getMaxEnergy(), Units.AE);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public AccessRestriction getPowerFlow() {
        boolean canOutput = !host.getOutputtingSides().isEmpty();
        boolean canInput = !host.getConsumingSides().isEmpty();
        if (canInput && canOutput) {
            return AccessRestriction.READ_WRITE;
        } else if (canInput) {
            return AccessRestriction.WRITE;
        } else if (canOutput) {
            return AccessRestriction.READ;
        }
        return AccessRestriction.NO_ACCESS;
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double injectAEPower(double amt, Actionable mode) {
        if (host.getConsumingSides().isEmpty()) return amt;
        double toAdd = Math.min(host.getMaxEnergy() - host.getEnergy(), Units.convertToJoules(amt, Units.AE));

        if (mode == Actionable.MODULATE) {
            host.setEnergy(host.getEnergy() + toAdd);
        }

        return amt - Units.convertFromJoules(toAdd, Units.AE);
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public boolean isAEPublicPowerStorage() {
        return true;
    }

    @Override
    @Method(modid = "appliedenergistics2")
    public double extractAEPower(double amt, Actionable mode, PowerMultiplier usePowerMultiplier) {
        if (host.getOutputtingSides().isEmpty()) return 0;
        double toSend = Math.min(
            host.getEnergy(), Math.min(host.getMaxOutput(), Units.convertToJoules(amt, Units.AE))
        );

        if (mode == Actionable.MODULATE) {
            host.setEnergy(host.getEnergy() - toSend);
        }

        return Units.convertFromJoules(toSend, Units.AE);
    }
    
    private class GridBlock implements IGridBlock {

        ForgeDirection side;

        public GridBlock(ForgeDirection side) {
            this.side = side;
        }

        @Override
        public double getIdlePowerUsage() {
            return 0;
        }

        @Override
        public EnumSet<GridFlags> getFlags() {
            return EnumSet.of(GridFlags.CANNOT_CARRY, GridFlags.CANNOT_CARRY_COMPRESSED);
        }

        @Override
        public boolean isWorldAccessible() {
            return true;
        }

        @Override
        public DimensionalCoord getLocation() {
            return new DimensionalCoord(host);
        }

        @Override
        public AEColor getGridColor() {
            return AEColor.Transparent;
        }

        @Override
        public void onGridNotification(GridNotification notification) {
            
        }

        @Override
        public void setNetworkStatus(IGrid grid, int channelsInUse) {
            
        }

        @Override
        public EnumSet<ForgeDirection> getConnectableSides() {
            return EnumSet.of(side);
        }

        @Override
        public IGridHost getMachine() {
            return host;
        }

        @Override
        public void gridChanged() {
            
        }

        @Override
        public ItemStack getMachineRepresentation() {
            return null;
        }
        
    }

}
