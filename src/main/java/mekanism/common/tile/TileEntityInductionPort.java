package mekanism.common.tile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.EnumColor;
import mekanism.api.IConfigurable;
import mekanism.api.Range4D;
import mekanism.common.Mekanism;
import mekanism.common.base.IActiveState;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.base.ITileDelegate;
import mekanism.common.integration.EnergyDelegateFactory;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.util.CableUtils;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityInductionPort extends TileEntityInductionCasing
    implements IEnergyWrapper, IConfigurable, IActiveState {
    public boolean isLoaded = false;

    /** false = input, true = output */
    public boolean mode;

    public Map<Class<? extends ITileDelegate>, ITileDelegate> delegates = EnergyDelegateFactory.createEnergyDelegates(this);

    public TileEntityInductionPort() {
        super("InductionPort");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!isLoaded) {
            delegates.values().forEach(ITileDelegate::load);
        }
        delegates.values().forEach(ITileDelegate::tick);
        isLoaded = true;

        if (!worldObj.isRemote) {
            if (structure != null && mode == true) {
                double prev = getEnergy();
                CableUtils.emit(this);
                structure.remainingOutput -= (prev - getEnergy());
            }
        }
    }

    @Override
    public EnumSet<ForgeDirection> getOutputtingSides() {
        if (structure != null && mode == true) {
            EnumSet set = EnumSet.allOf(ForgeDirection.class);
            set.remove(ForgeDirection.UNKNOWN);

            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (structure.locations.contains(Coord4D.get(this).getFromSide(side))) {
                    set.remove(side);
                }
            }

            return set;
        }

        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getConsumingSides() {
        if (structure != null && mode == false) {
            EnumSet set = EnumSet.allOf(ForgeDirection.class);
            set.remove(ForgeDirection.UNKNOWN);
            return set;
        }

        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public double getMaxOutput() {
        return structure != null ? structure.remainingOutput : 0;
    }

    private double getMaxInput() {
        return structure != null ? structure.remainingInput : 0;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            mode = dataStream.readBoolean();

            MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        data.add(mode);

        return data;
    }

    @Override
    public void onChunkUnload() {
        delegates.values().forEach(ITileDelegate::unload);
        isLoaded = false;
        super.onChunkUnload();
    }

    @Override
    public void onChunkLoad() {
        if (!isLoaded) {
            isLoaded = true;
            delegates.values().forEach(ITileDelegate::load);
        }
        super.onChunkLoad();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        isLoaded = false;
        delegates.values().forEach(ITileDelegate::unload);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        mode = nbtTags.getBoolean("mode");

        delegates.values().forEach(d -> d.readFromNBT(nbtTags));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setBoolean("mode", mode);

        delegates.values().forEach(d -> d.writeToNBT(nbtTags));
    }

    @Override
    public boolean canOutputTo(ForgeDirection side) {
        return getOutputtingSides().contains(side);
    }

    @Override
    public boolean canReceiveEnergy(ForgeDirection side) {
        return getConsumingSides().contains(side);
    }

    @Override
    public double transferEnergyToAcceptor(ForgeDirection side, double amount) {
        if (!getConsumingSides().contains(side)) {
            return 0;
        }

        double toUse
            = Math.min(Math.min(getMaxInput(), getMaxEnergy() - getEnergy()), amount);
        setEnergy(getEnergy() + toUse);
        structure.remainingInput -= toUse;

        return toUse;
    }

    @Override
    public boolean onSneakRightClick(EntityPlayer player, int side) {
        if (!worldObj.isRemote) {
            mode = !mode;
            String modeText = " " + (mode ? EnumColor.DARK_RED : EnumColor.DARK_GREEN)
                + LangUtils.transOutputInput(mode) + ".";
            player.addChatMessage(new ChatComponentText(
                EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.GREY
                + LangUtils.localize("tooltip.configurator.inductionPortMode") + modeText
            ));

            Mekanism.packetHandler.sendToReceivers(
                new TileEntityMessage(
                    Coord4D.get(this), getNetworkedData(new ArrayList())
                ),
                new Range4D(Coord4D.get(this))
            );
            markDirty();
        }

        return true;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, int side) {
        return false;
    }

    @Override
    public boolean getActive() {
        return mode;
    }

    @Override
    public void setActive(boolean active) {
        mode = active;
    }

    @Override
    public boolean renderUpdate() {
        return true;
    }

    @Override
    public boolean lightUpdate() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Optional<E> getDelegate(Class<E> type) {
        return Optional.ofNullable(ITileDelegate.IMPLEMENTATIONS.get(type)).map(delegates::get).filter(type::isInstance).map(o -> (E) o);
    }

}