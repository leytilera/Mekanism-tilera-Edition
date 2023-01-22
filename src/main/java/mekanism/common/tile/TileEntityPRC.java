package mekanism.common.tile;

import java.util.ArrayList;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import mekanism.api.EnumColor;
import mekanism.api.MekanismConfig.usage;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.GasTank;
import mekanism.api.gas.IGasHandler;
import mekanism.api.gas.ITubeConnection;
import mekanism.api.transmitters.TransmissionType;
import mekanism.common.SideData;
import mekanism.common.Upgrade;
import mekanism.common.base.ISustainedData;
import mekanism.common.base.ITankManager;
import mekanism.common.block.BlockMachine.MachineType;
import mekanism.common.item.ItemUpgrade;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.inputs.PressurizedInput;
import mekanism.common.recipe.machines.PressurizedRecipe;
import mekanism.common.recipe.outputs.PressurizedOutput;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.TileComponentUpgrade;
import mekanism.common.util.ChargeUtils;
import mekanism.common.util.InventoryUtils;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityPRC
    extends TileEntityBasicMachine<PressurizedInput, PressurizedOutput, PressurizedRecipe>
    implements IFluidHandler, IGasHandler, ITubeConnection, ISustainedData, ITankManager {
    public FluidTank inputFluidTank = new FluidTank(10000);
    public GasTank inputGasTank = new GasTank(10000);

    public GasTank outputGasTank = new GasTank(10000);

    public TileEntityPRC() {
        super(
            "prc",
            MachineType.PRESSURIZED_REACTION_CHAMBER.name,
            new ResourceLocation("mekanism", "gui/GuiPRC.png"),
            usage.pressurizedReactionBaseUsage,
            100,
            MachineType.PRESSURIZED_REACTION_CHAMBER.baseEnergy
        );

        configComponent = new TileComponentConfig(
            this,
            TransmissionType.ITEM,
            TransmissionType.ENERGY,
            TransmissionType.FLUID,
            TransmissionType.GAS
        );

        configComponent.addOutput(
            TransmissionType.ITEM,
            new SideData("None", EnumColor.GREY, InventoryUtils.EMPTY)
        );
        configComponent.addOutput(
            TransmissionType.ITEM,
            new SideData("Input", EnumColor.DARK_RED, new int[] { 0 })
        );
        configComponent.addOutput(
            TransmissionType.ITEM,
            new SideData("Energy", EnumColor.DARK_GREEN, new int[] { 1 })
        );
        configComponent.addOutput(
            TransmissionType.ITEM,
            new SideData("Output", EnumColor.DARK_BLUE, new int[] { 2 })
        );
        configComponent.setConfig(TransmissionType.ITEM, new byte[] { 2, 1, 0, 0, 0, 3 });

        configComponent.addOutput(
            TransmissionType.FLUID,
            new SideData("None", EnumColor.GREY, InventoryUtils.EMPTY)
        );
        configComponent.addOutput(
            TransmissionType.FLUID,
            new SideData("Fluid", EnumColor.YELLOW, new int[] { 0 })
        );
        configComponent.setConfig(
            TransmissionType.FLUID, new byte[] { 0, 0, 0, 1, 0, 0 }
        );
        configComponent.setCanEject(TransmissionType.FLUID, false);

        configComponent.addOutput(
            TransmissionType.GAS,
            new SideData("None", EnumColor.GREY, InventoryUtils.EMPTY)
        );
        configComponent.addOutput(
            TransmissionType.GAS, new SideData("Gas", EnumColor.DARK_RED, new int[] { 1 })
        );
        configComponent.addOutput(
            TransmissionType.GAS,
            new SideData("Output", EnumColor.DARK_BLUE, new int[] { 2 })
        );
        configComponent.setConfig(TransmissionType.GAS, new byte[] { 0, 0, 0, 0, 1, 2 });

        configComponent.setInputConfig(TransmissionType.ENERGY);

        inventory = new ItemStack[4];

        upgradeComponent = new TileComponentUpgrade(this, 3);
        upgradeComponent.setSupported(Upgrade.MUFFLING);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(
            TransmissionType.ITEM,
            configComponent.getOutputs(TransmissionType.ITEM).get(3)
        );
        ejectorComponent.setOutputData(
            TransmissionType.GAS, configComponent.getOutputs(TransmissionType.GAS).get(2)
        );
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!worldObj.isRemote) {
            PressurizedRecipe recipe = getRecipe();

            ChargeUtils.discharge(1, this);

            if (canOperate(recipe) && MekanismUtils.canFunction(this)
                && getEnergy() >= MekanismUtils.getEnergyPerTick(
                       this, BASE_ENERGY_PER_TICK + recipe.extraEnergy
                   )) {
                boolean update = BASE_TICKS_REQUIRED != recipe.ticks;

                BASE_TICKS_REQUIRED = recipe.ticks;

                if (update) {
                    recalculateUpgradables(Upgrade.SPEED);
                }

                setActive(true);

                if ((operatingTicks + 1) < ticksRequired) {
                    operatingTicks++;
                    electricityStored -= MekanismUtils.getEnergyPerTick(
                        this, BASE_ENERGY_PER_TICK + recipe.extraEnergy
                    );
                }
				else if((operatingTicks+1) >= ticksRequired && getEnergy() >= MekanismUtils.getEnergyPerTick(this, BASE_ENERGY_PER_TICK + recipe.extraEnergy))
				{
                    operate(recipe);

                    operatingTicks = 0;
                    electricityStored -= MekanismUtils.getEnergyPerTick(
                        this, BASE_ENERGY_PER_TICK + recipe.extraEnergy
                    );
                }
            } else {
                BASE_TICKS_REQUIRED = 100;

                if (prevEnergy >= getEnergy()) {
                    setActive(false);
                }
            }

            if (!canOperate(recipe)) {
                operatingTicks = 0;
            }

            prevEnergy = getEnergy();
        }
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
        if (slotID == 0) {
            return RecipeHandler.isInPressurizedRecipe(itemstack);
        } else if (slotID == 1) {
            return ChargeUtils.canBeDischarged(itemstack);
        } else if (slotID == 3) {
            return itemstack.getItem() instanceof ItemUpgrade;
        }

        return false;
    }

    @Override
    public PressurizedRecipe getRecipe() {
        PressurizedInput input = getInput();

        if (cachedRecipe == null || !input.testEquality(cachedRecipe.getInput())) {
            cachedRecipe = RecipeHandler.getPRCRecipe(input);
        }

        return cachedRecipe;
    }

    @Override
    public PressurizedInput getInput() {
        return new PressurizedInput(
            inventory[0], inputFluidTank.getFluid(), inputGasTank.getGas()
        );
    }

    @Override
    public void operate(PressurizedRecipe recipe) {
        recipe.operate(inventory, inputFluidTank, inputGasTank, outputGasTank);

        markDirty();
        ejectorComponent.outputItems();
    }

    @Override
    public boolean canOperate(PressurizedRecipe recipe) {
        return recipe != null
            && recipe.canOperate(inventory, inputFluidTank, inputGasTank, outputGasTank);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int side) {
        if (slotID == 1) {
            return ChargeUtils.canBeOutputted(itemstack, false);
        } else if (slotID == 2 || slotID == 4) {
            return true;
        }

        return false;
    }

    @Override
    public ArrayList getNetworkedData(ArrayList data) {
        super.getNetworkedData(data);

        if (inputFluidTank.getFluid() != null) {
            data.add(true);
            data.add(inputFluidTank.getFluid().getFluid().getID());
            data.add(inputFluidTank.getFluidAmount());
        } else {
            data.add(false);
        }

        if (inputGasTank.getGas() != null) {
            data.add(true);
            data.add(inputGasTank.getGas().getGas().getID());
            data.add(inputGasTank.getStored());
        } else {
            data.add(false);
        }

        if (outputGasTank.getGas() != null) {
            data.add(true);
            data.add(outputGasTank.getGas().getGas().getID());
            data.add(outputGasTank.getStored());
        } else {
            data.add(false);
        }

        return data;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (worldObj.isRemote) {
            if (dataStream.readBoolean()) {
                inputFluidTank.setFluid(new FluidStack(
                    FluidRegistry.getFluid(dataStream.readInt()), dataStream.readInt()
                ));
            } else {
                inputFluidTank.setFluid(null);
            }

            if (dataStream.readBoolean()) {
                inputGasTank.setGas(new GasStack(
                    GasRegistry.getGas(dataStream.readInt()), dataStream.readInt()
                ));
            } else {
                inputGasTank.setGas(null);
            }

            if (dataStream.readBoolean()) {
                outputGasTank.setGas(new GasStack(
                    GasRegistry.getGas(dataStream.readInt()), dataStream.readInt()
                ));
            } else {
                outputGasTank.setGas(null);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        inputFluidTank.readFromNBT(nbtTags.getCompoundTag("inputFluidTank"));
        inputGasTank.read(nbtTags.getCompoundTag("inputGasTank"));
        outputGasTank.read(nbtTags.getCompoundTag("outputGasTank"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setTag("inputFluidTank", inputFluidTank.writeToNBT(new NBTTagCompound()));
        nbtTags.setTag("inputGasTank", inputGasTank.write(new NBTTagCompound()));
        nbtTags.setTag("outputGasTank", outputGasTank.write(new NBTTagCompound()));
    }

    @Override
    public String getInventoryName() {
        return LangUtils.localize(
            getBlockType().getUnlocalizedName() + "." + fullName + ".short.name"
        );
    }

    @Override
    public Map getRecipes() {
        return null;
    }

    private static final String[] methods
        = new String[] { "getEnergy",       "getProgress",    "isActive",
                         "facing",          "canOperate",     "getMaxEnergy",
                         "getEnergyNeeded", "getFluidStored", "getGasStored" };

    @Override
    public String[] getMethods() {
        return methods;
    }

    @Override
    public Object[] invoke(int method, Object[] arguments) throws Exception {
        switch (method) {
            case 0:
                return new Object[] { getEnergy() };
            case 1:
                return new Object[] { operatingTicks };
            case 2:
                return new Object[] { isActive };
            case 3:
                return new Object[] { facing };
            case 4:
                return new Object[] { canOperate(getRecipe()) };
            case 5:
                return new Object[] { getMaxEnergy() };
            case 6:
                return new Object[] { getMaxEnergy() - getEnergy() };
            case 7:
                return new Object[] { inputFluidTank.getFluidAmount() };
            case 8:
                return new Object[] { inputGasTank.getStored() };
            default:
                throw new NoSuchMethodException();
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (canFill(from, resource.getFluid())) {
            return inputFluidTank.fill(resource, doFill);
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        SideData data
            = configComponent.getOutput(TransmissionType.FLUID, from.ordinal(), facing);

        if (data.hasSlot(0)) {
            return inputFluidTank.getFluid() == null
                || inputFluidTank.getFluid().getFluid() == fluid;
        }

        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        SideData data
            = configComponent.getOutput(TransmissionType.FLUID, from.ordinal(), facing);

        return data.getFluidTankInfo(this);
    }

    @Override
    public int receiveGas(ForgeDirection side, GasStack stack, boolean doTransfer) {
        if (canReceiveGas(side, stack.getGas())) {
            return inputGasTank.receive(stack, doTransfer);
        }

        return 0;
    }

    @Override
    public int receiveGas(ForgeDirection side, GasStack stack) {
        return receiveGas(side, stack, true);
    }

    @Override
    public GasStack drawGas(ForgeDirection side, int amount, boolean doTransfer) {
        if (canDrawGas(side, null)) {
            return outputGasTank.draw(amount, doTransfer);
        }

        return null;
    }

    @Override
    public GasStack drawGas(ForgeDirection side, int amount) {
        return drawGas(side, amount, true);
    }

    @Override
    public boolean canReceiveGas(ForgeDirection side, Gas type) {
        return configComponent.getOutput(TransmissionType.GAS, side.ordinal(), facing)
                   .hasSlot(1)
            && inputGasTank.canReceive(type);
    }

    @Override
    public boolean canDrawGas(ForgeDirection side, Gas type) {
        return configComponent.getOutput(TransmissionType.GAS, side.ordinal(), facing)
                   .hasSlot(2)
            && outputGasTank.canDraw(type);
    }

    @Override
    public boolean canTubeConnect(ForgeDirection side) {
        return configComponent.getOutput(TransmissionType.GAS, side.ordinal(), facing)
            .hasSlot(1, 2);
    }

    @Override
    public void writeSustainedData(ItemStack itemStack) {
        if (inputFluidTank.getFluid() != null) {
            itemStack.stackTagCompound.setTag(
                "inputFluidTank",
                inputFluidTank.getFluid().writeToNBT(new NBTTagCompound())
            );
        }

        if (inputGasTank.getGas() != null) {
            itemStack.stackTagCompound.setTag(
                "inputGasTank", inputGasTank.getGas().write(new NBTTagCompound())
            );
        }

        if (outputGasTank.getGas() != null) {
            itemStack.stackTagCompound.setTag(
                "outputGasTank", outputGasTank.getGas().write(new NBTTagCompound())
            );
        }
    }

    @Override
    public void readSustainedData(ItemStack itemStack) {
        inputFluidTank.setFluid(FluidStack.loadFluidStackFromNBT(
            itemStack.stackTagCompound.getCompoundTag("inputFluidTank")
        ));
        inputGasTank.setGas(GasStack.readFromNBT(
            itemStack.stackTagCompound.getCompoundTag("inputGasTank")
        ));
        outputGasTank.setGas(GasStack.readFromNBT(
            itemStack.stackTagCompound.getCompoundTag("outputGasTank")
        ));
    }

    @Override
    public Object[] getTanks() {
        return new Object[] { inputFluidTank, inputGasTank, outputGasTank };
    }
}
