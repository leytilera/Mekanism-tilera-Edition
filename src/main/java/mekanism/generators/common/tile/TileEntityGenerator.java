package mekanism.generators.common.tile;

import java.util.ArrayList;
import java.util.EnumSet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.MekanismConfig.general;
import mekanism.api.Range4D;
import mekanism.client.sound.ISoundSource;
import mekanism.common.Mekanism;
import mekanism.common.base.IActiveState;
import mekanism.common.base.IHasSound;
import mekanism.common.base.IRedstoneControl;
import mekanism.common.integration.IComputerIntegration;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.security.ISecurityTile;
import mekanism.common.tile.TileEntityNoisyElectricBlock;
import mekanism.common.tile.component.TileComponentSecurity;
import mekanism.common.util.CableUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityGenerator extends TileEntityNoisyElectricBlock
    implements IComputerIntegration, IActiveState, IHasSound, ISoundSource,
               IRedstoneControl, ISecurityTile {
    /** Output per tick this generator can transfer. */
    public double output;

    /** Whether or not this block is in it's active state. */
    public boolean isActive;

    /** The client's current active state. */
    public boolean clientActive;

    /**
     * How many ticks must pass until this block's active state can sync with the client.
     */
    public int updateDelay;

    /** This machine's current RedstoneControl type. */
    public RedstoneControl controlType;

    public TileComponentSecurity securityComponent = new TileComponentSecurity(this);

    /**
     * Generator -- a block that produces energy. It has a certain amount of fuel it can
     * store as well as an output rate.
     * @param name - full name of this generator
     * @param maxEnergy - how much energy this generator can store
     */
    public TileEntityGenerator(
        String soundPath, String name, double maxEnergy, double out
    ) {
        super("gen." + soundPath, name, maxEnergy);

        output = out;
        isActive = false;
        controlType = RedstoneControl.DISABLED;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (worldObj.isRemote && updateDelay > 0) {
            updateDelay--;

            if (updateDelay == 0 && clientActive != isActive) {
                isActive = clientActive;
                MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
            }
        }

        if (!worldObj.isRemote) {
            if (updateDelay > 0) {
                updateDelay--;

                if (updateDelay == 0 && clientActive != isActive) {
                    clientActive = isActive;
                    Mekanism.packetHandler.sendToReceivers(
                        new TileEntityMessage(
                            Coord4D.get(this), getNetworkedData(new ArrayList())
                        ),
                        new Range4D(Coord4D.get(this))
                    );
                }
            }

            if (MekanismUtils.canFunction(this)) {
                CableUtils.emit(this);
            }
        }
    }

    @Override
    public double getMaxOutput() {
        return output;
    }

    @Override
    public EnumSet<ForgeDirection> getConsumingSides() {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getOutputtingSides() {
        return EnumSet.of(ForgeDirection.getOrientation(facing));
    }

    /**
     * Whether or not this generator can operate.
     * @return if the generator can operate
     */
    public abstract boolean canOperate();

    @Override
    public boolean getActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;

        if (clientActive != active && updateDelay == 0) {
            Mekanism.packetHandler.sendToReceivers(
                new TileEntityMessage(
                    Coord4D.get(this), getNetworkedData(new ArrayList())
                ),
                new Range4D(Coord4D.get(this))
            );

            updateDelay = general.UPDATE_DELAY;
            clientActive = active;
        }
    }

    @Override
    public boolean canSetFacing(int side) {
        return side != 0 && side != 1;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            clientActive = dataStream.readBoolean();
            controlType = RedstoneControl.values()[dataStream.readInt()];

            if (updateDelay == 0 && clientActive != isActive) {
                updateDelay = general.UPDATE_DELAY;
                isActive = clientActive;
                MekanismUtils.updateBlock(worldObj, xCoord, yCoord, zCoord);
            }
        }
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        data.add(isActive);
        data.add(controlType.ordinal());

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        isActive = nbtTags.getBoolean("isActive");
        controlType = RedstoneControl.values()[nbtTags.getInteger("controlType")];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setBoolean("isActive", isActive);
        nbtTags.setInteger("controlType", controlType.ordinal());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean renderUpdate() {
        return true;
    }

    @Override
    public boolean lightUpdate() {
        return true;
    }

    @Override
    public RedstoneControl getControlType() {
        return controlType;
    }

    @Override
    public void setControlType(RedstoneControl type) {
        controlType = type;
        MekanismUtils.saveChunk(this);
    }

    @Override
    public boolean canPulse() {
        return false;
    }

    @Override
    public TileComponentSecurity getSecurity() {
        return securityComponent;
    }
}
