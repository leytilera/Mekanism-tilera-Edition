package mekanism.common.tile;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyTile;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.EnumSet;

import mekanism.api.Coord4D;
import mekanism.api.MekanismConfig.general;
import mekanism.api.transmitters.ITransmitterTile;
import mekanism.common.base.IEnergyWrapper;
import mekanism.common.integration.ue.UEDriverProxy;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.electricity.ElectricityPack;
import cpw.mods.fml.common.Optional.Method;

public abstract class TileEntityElectricBlock extends TileEntityContainerBlock implements IEnergyWrapper
{
	/** How much energy is stored in this block. */
	public double electricityStored;

	/** Maximum amount of energy this machine can hold. */
	public double BASE_MAX_ENERGY;

	/** Actual maximum energy storage, including upgrades */
	public double maxEnergy;

	/** Is this registered with IC2 */
	public boolean ic2Registered = false;

	private UEDriverProxy driver;

	/**
	 * The base of all blocks that deal with electricity. It has a facing state, initialized state,
	 * and a current amount of stored energy.
	 * @param name - full name of this block
	 * @param baseMaxEnergy - how much energy this block can store
	 */
	public TileEntityElectricBlock(String name, double baseMaxEnergy)
	{
		super(name);
		BASE_MAX_ENERGY = baseMaxEnergy;
		maxEnergy = BASE_MAX_ENERGY;
		driver = UEDriverProxy.createProxy(this);
	}

	@Method(modid = "IC2")
	public void register()
	{
		if(!worldObj.isRemote)
		{
			TileEntity registered = EnergyNet.instance.getTileEntity(worldObj, xCoord, yCoord, zCoord);
			
			if(registered != this)
			{
				if(registered instanceof IEnergyTile)
				{
					MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile)registered));
				}
				else if(registered == null)
				{
					MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
					ic2Registered = true;
				}
			}
		}
	}

	@Method(modid = "IC2")
	public void deregister()
	{
		if(!worldObj.isRemote)
		{
			TileEntity registered = EnergyNet.instance.getTileEntity(worldObj, xCoord, yCoord, zCoord);
			
			if(registered instanceof IEnergyTile)
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile)registered));
			}
		}
	}

	@Override
	public void onUpdate()
	{
		if(!ic2Registered && MekanismUtils.useIC2())
		{
			register();
		}
		if (!this.worldObj.isRemote) {
            driver.tick();
        }
	}

	@Override
	public EnumSet<ForgeDirection> getOutputtingSides()
	{
		return EnumSet.noneOf(ForgeDirection.class);
	}

	@Override
	public EnumSet<ForgeDirection> getConsumingSides()
	{
		return EnumSet.allOf(ForgeDirection.class);
	}

	@Override
	public double getMaxOutput()
	{
		return 0;
	}

	@Override
	public double getEnergy()
	{
		return electricityStored;
	}

	@Override
	public void setEnergy(double energy)
	{
		electricityStored = Math.max(Math.min(energy, getMaxEnergy()), 0);
		MekanismUtils.saveChunk(this);
	}

	@Override
	public double getMaxEnergy()
	{
		return maxEnergy;
	}

	@Override
	public void handlePacketData(ByteBuf dataStream)
	{
		super.handlePacketData(dataStream);
		
		if(worldObj.isRemote)
		{
			setEnergy(dataStream.readDouble());
		}
	}

	@Override
	public ArrayList getNetworkedData(ArrayList data)
	{
		super.getNetworkedData(data);
		
		data.add(getEnergy());
		
		return data;
	}
	
	@Override
	public void onAdded()
	{
		super.onAdded();
		
		if(MekanismUtils.useIC2())
		{
			register();
		}
	}

	@Override
	public void onChunkUnload()
	{
		if(MekanismUtils.useIC2())
		{
			deregister();
		}
		driver.invalidate();
		super.onChunkUnload();
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		driver.invalidate();
		if(MekanismUtils.useIC2())
		{
			deregister();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTags)
	{
		super.readFromNBT(nbtTags);

		electricityStored = nbtTags.getDouble("electricityStored");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTags)
	{
		super.writeToNBT(nbtTags);

		nbtTags.setDouble("electricityStored", getEnergy());
	}

	/**
	 * Gets the scaled energy level for the GUI.
	 * @param i - multiplier
	 * @return scaled energy
	 */
	public int getScaledEnergyLevel(int i)
	{
		return (int)(getEnergy()*i / getMaxEnergy());
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		if(getConsumingSides().contains(from))
		{
			double toAdd = (int)Math.min(getMaxEnergy()-getEnergy(), maxReceive*general.FROM_TE);

			if(!simulate)
			{
				setEnergy(getEnergy() + toAdd);
			}

			return (int)Math.round(toAdd*general.TO_TE);
		}

		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		if(getOutputtingSides().contains(from))
		{
			double toSend = Math.min(getEnergy(), Math.min(getMaxOutput(), maxExtract*general.FROM_TE));

			if(!simulate)
			{
				setEnergy(getEnergy() - toSend);
			}

			return (int)Math.round(toSend*general.TO_TE);
		}

		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return getConsumingSides().contains(from) || getOutputtingSides().contains(from);
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return (int)Math.round(getEnergy()*general.TO_TE);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return (int)Math.round(getMaxEnergy()*general.TO_TE);
	}

	@Override
	@Method(modid = "IC2")
	public int getSinkTier()
	{
		return 4;
	}

	@Override
	@Method(modid = "IC2")
	public int getSourceTier()
	{
		return 4;
	}

	@Override
	@Method(modid = "IC2")
	public void setStored(int energy)
	{
		setEnergy(energy*general.FROM_IC2);
	}

	@Override
	@Method(modid = "IC2")
	public int addEnergy(int amount)
	{
		setEnergy(getEnergy() + amount*general.FROM_IC2);
		return (int)Math.round(getEnergy()*general.TO_IC2);
	}

	@Override
	@Method(modid = "IC2")
	public boolean isTeleporterCompatible(ForgeDirection side)
	{
		return getOutputtingSides().contains(side);
	}

	@Override
	public boolean canOutputTo(ForgeDirection side)
	{
		return getOutputtingSides().contains(side);
	}

	@Override
	@Method(modid = "IC2")
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
	{
		return getConsumingSides().contains(direction);
	}

	@Override
	@Method(modid = "IC2")
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction)
	{
		return getOutputtingSides().contains(direction) && receiver instanceof IEnergyConductor;
	}

	@Override
	@Method(modid = "IC2")
	public int getStored()
	{
		return (int)Math.round(getEnergy()*general.TO_IC2);
	}

	@Override
	@Method(modid = "IC2")
	public int getCapacity()
	{
		return (int)Math.round(getMaxEnergy()*general.TO_IC2);
	}

	@Override
	@Method(modid = "IC2")
	public int getOutput()
	{
		return (int)Math.round(getMaxOutput()*general.TO_IC2);
	}

	@Override
	@Method(modid = "IC2")
	public double getDemandedEnergy()
	{
		return (getMaxEnergy() - getEnergy())*general.TO_IC2;
	}

	@Override
	@Method(modid = "IC2")
	public double getOfferedEnergy()
	{
		return Math.min(getEnergy(), getMaxOutput())*general.TO_IC2;
	}

	@Override
	public boolean canReceiveEnergy(ForgeDirection side)
	{
		return getConsumingSides().contains(side);
	}

	@Override
	@Method(modid = "IC2")
	public double getOutputEnergyUnitsPerTick()
	{
		return getMaxOutput()*general.TO_IC2;
	}

	@Override
	@Method(modid = "IC2")
	public double injectEnergy(ForgeDirection direction, double amount, double voltage)
	{
		if(Coord4D.get(this).getFromSide(direction).getTileEntity(worldObj) instanceof ITransmitterTile)
		{
			return amount;
		}

		return amount-transferEnergyToAcceptor(direction, amount*general.FROM_IC2)*general.TO_IC2;
	}

	@Override
	@Method(modid = "IC2")
	public void drawEnergy(double amount)
	{
		setEnergy(Math.max(getEnergy() - (amount*general.FROM_IC2), 0));
	}

	@Override
	public double transferEnergyToAcceptor(ForgeDirection side, double amount)
	{
		if(!(getConsumingSides().contains(side) || side == ForgeDirection.UNKNOWN))
		{
			return 0;
		}

		double toUse = Math.min(getMaxEnergy()-getEnergy(), amount);
		setEnergy(getEnergy() + toUse);

		return toUse;
	}

	@Override
	@Method(modid = "basiccomponents")
	public boolean canConnect(ForgeDirection side) {
		return getConsumingSides().contains(side) || getOutputtingSides().contains(side);
	}

	@Override
	@Method(modid = "basiccomponents")
	public double getVoltage() {
		return 120.0;
	}

	@Override
	@Method(modid = "basiccomponents")
	public boolean canInsert() {
		return getConsumingSides().size() > 0;
	}

	@Override
	@Method(modid = "basiccomponents")
    public boolean canExtract() {
		return getOutputtingSides().size() > 0;
	}

	@Override
	@Method(modid = "basiccomponents")
    public boolean canInsertOn(ForgeDirection side) {
		return getConsumingSides().contains(side);
	}

	@Override
	@Method(modid = "basiccomponents")
    public boolean canExtractOn(ForgeDirection side) {
		return getOutputtingSides().contains(side);
	}

	@Override
	@Method(modid = "basiccomponents")
    public void insert(ElectricityPack pack, ForgeDirection side) {
		setEnergy(Math.min(getEnergy() + pack.getWatts(), getMaxEnergy()));
	}

	@Override
	@Method(modid = "basiccomponents")
    public void extract(ElectricityPack pack, ForgeDirection side) {
		setEnergy(Math.max(getEnergy() - pack.getWatts(), 0));
	}

	@Override
	@Method(modid = "basiccomponents")
    public ElectricityPack getDemandedJoules() {
		if (canInsert()) 
			return new ElectricityPack((getMaxEnergy() - getEnergy()) / getVoltage(), getVoltage());
		else 
			return new ElectricityPack();
	}

	@Override
	@Method(modid = "basiccomponents")
    public ElectricityPack getProvidedJoules() {
		if (canExtract())
			return new ElectricityPack(Math.min(getEnergy(), getMaxOutput()) / getVoltage(), getVoltage());
		else
			return new ElectricityPack();

	}

	@Override
	@Method(modid = "basiccomponents")
    public TileEntity getTile() {
		return this;
	}

}
