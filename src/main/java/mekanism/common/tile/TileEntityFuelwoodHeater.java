package mekanism.common.tile;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.IHeatTransfer;
import mekanism.api.MekanismConfig.general;
import mekanism.api.Range4D;
import mekanism.common.Mekanism;
import mekanism.common.base.IActiveState;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.security.ISecurityTile;
import mekanism.common.tile.component.TileComponentSecurity;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFuelwoodHeater extends TileEntityContainerBlock
    implements IHeatTransfer, ISecurityTile, IActiveState {
    public double temperature;
    public double heatToAbsorb = 0;

    public int burnTime;
    public int maxBurnTime;

    /** Whether or not this machine is in it's active state. */
    public boolean isActive;

    /** The client's current active state. */
    public boolean clientActive;

    /**
     * How many ticks must pass until this block's active state can sync with the client.
     */
    public int updateDelay;

    public double lastEnvironmentLoss;

    public TileComponentSecurity securityComponent = new TileComponentSecurity(this);

    public TileEntityFuelwoodHeater() {
        super("FuelwoodHeater");
        inventory = new ItemStack[1];
    }

    @Override
    public void onUpdate() {
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
                    Mekanism.packetHandler.sendToReceivers(
                        new TileEntityMessage(
                            Coord4D.get(this), getNetworkedData(new ArrayList())
                        ),
                        new Range4D(Coord4D.get(this))
                    );
                }
            }

            boolean burning = false;

            if (burnTime > 0) {
                burnTime--;
                burning = true;
            } else {
                if (inventory[0] != null) {
                    maxBurnTime = burnTime
                        = TileEntityFurnace.getItemBurnTime(inventory[0]) / 2;

                    if (burnTime > 0) {
                        inventory[0].stackSize--;

                        if (inventory[0].stackSize == 0) {
                            inventory[0]
                                = inventory[0].getItem().getContainerItem(inventory[0]);
                        }

                        burning = true;
                    }
                }
            }

            if (burning) {
                heatToAbsorb += general.heatPerFuelTick;
            }

            double[] loss = simulateHeat();
            applyTemperatureChange();

            lastEnvironmentLoss = loss[1];

            setActive(burning);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        temperature = nbtTags.getDouble("temperature");
        clientActive = isActive = nbtTags.getBoolean("isActive");
        burnTime = nbtTags.getInteger("burnTime");
        maxBurnTime = nbtTags.getInteger("maxBurnTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setDouble("temperature", temperature);
        nbtTags.setBoolean("isActive", isActive);
        nbtTags.setInteger("burnTime", burnTime);
        nbtTags.setInteger("maxBurnTime", maxBurnTime);
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            temperature = dataStream.readDouble();
            clientActive = dataStream.readBoolean();
            burnTime = dataStream.readInt();
            maxBurnTime = dataStream.readInt();

            lastEnvironmentLoss = dataStream.readDouble();

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

        data.add(temperature);
        data.add(isActive);
        data.add(burnTime);
        data.add(maxBurnTime);

        data.add(lastEnvironmentLoss);

        return data;
    }

    @Override
    public boolean canSetFacing(int side) {
        return side != 0 && side != 1;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0 };
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

            updateDelay = 10;
            clientActive = active;
        }
    }

    @Override
    public boolean getActive() {
        return isActive;
    }

    @Override
    public boolean renderUpdate() {
        return false;
    }

    @Override
    public boolean lightUpdate() {
        return true;
    }

    @Override
    public double getTemp() {
        return temperature;
    }

    @Override
    public double getInverseConductionCoefficient() {
        return 5;
    }

    @Override
    public double getInsulationCoefficient(ForgeDirection side) {
        return 1000;
    }

    @Override
    public void transferHeatTo(double heat) {
        heatToAbsorb += heat;
    }

    @Override
    public double[] simulateHeat() {
        return HeatUtils.simulate(this);
    }

    @Override
    public double applyTemperatureChange() {
        temperature += heatToAbsorb;
        heatToAbsorb = 0;

        return temperature;
    }

    @Override
    public boolean canConnectHeat(ForgeDirection side) {
        return true;
    }

    @Override
    public IHeatTransfer getAdjacent(ForgeDirection side) {
        TileEntity adj = Coord4D.get(this).getFromSide(side).getTileEntity(worldObj);

        if (adj instanceof IHeatTransfer) {
            return (IHeatTransfer) adj;
        }

        return null;
    }

    @Override
    public TileComponentSecurity getSecurity() {
        return securityComponent;
    }
}
