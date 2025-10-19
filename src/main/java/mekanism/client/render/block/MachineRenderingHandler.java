package mekanism.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.MekanismConfig;
import mekanism.client.ClientProxy;
import mekanism.client.ModelMekanismBase;
import mekanism.client.model.ClassicModelElectricPump;
import mekanism.client.model.ClassicModelMetallurgicInfuser;
import mekanism.client.model.IModelOnOff;
import mekanism.client.model.LegacyModelChargepad;
import mekanism.client.model.LegacyModelChemicalCrystallizer;
import mekanism.client.model.LegacyModelChemicalDissolutionChamber;
import mekanism.client.model.LegacyModelChemicalInfuser;
import mekanism.client.model.LegacyModelChemicalOxidizer;
import mekanism.client.model.LegacyModelChemicalWasher;
import mekanism.client.model.LegacyModelDigitalMiner;
import mekanism.client.model.LegacyModelElectricPump;
import mekanism.client.model.LegacyModelElectrolyticSeparator;
import mekanism.client.model.LegacyModelFluidicPlenisher;
import mekanism.client.model.LegacyModelLogisticalSorter;
import mekanism.client.model.LegacyModelMetallurgicInfuser;
import mekanism.client.model.LegacyModelPressurizedReactionChamber;
import mekanism.client.model.LegacyModelRotaryCondensentrator;
import mekanism.client.model.LegacyModelSeismicVibrator;
import mekanism.client.model.ModelChargepad;
import mekanism.client.model.ModelChemicalCrystallizer;
import mekanism.client.model.ModelChemicalDissolutionChamber;
import mekanism.client.model.ModelChemicalInfuser;
import mekanism.client.model.ModelChemicalOxidizer;
import mekanism.client.model.ModelChemicalWasher;
import mekanism.client.model.ModelDigitalMiner;
import mekanism.client.model.ModelElectricPump;
import mekanism.client.model.ModelElectrolyticSeparator;
import mekanism.client.model.ModelFluidicPlenisher;
import mekanism.client.model.ModelLaser;
import mekanism.client.model.ModelLaserAmplifier;
import mekanism.client.model.ModelLogisticalSorter;
import mekanism.client.model.ModelMetallurgicInfuser;
import mekanism.client.model.ModelPressurizedReactionChamber;
import mekanism.client.model.ModelQuantumEntangloporter;
import mekanism.client.model.ModelResistiveHeater;
import mekanism.client.model.ModelRotaryCondensentrator;
import mekanism.client.model.ModelSeismicVibrator;
import mekanism.client.model.ModelSolarNeutronActivator;
import mekanism.client.model.ModelTheoreticalElementizer;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.block.BlockMachine.MachineType;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import java.util.function.Supplier;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRenderingHandler implements ISimpleBlockRenderingHandler {
    private Minecraft mc = Minecraft.getMinecraft();

    public Supplier<ModelMekanismBase> electricPump = MekanismConfig.client.modelType.createModel(
        ModelElectricPump::new,
        LegacyModelElectricPump::new,
        ClassicModelElectricPump::new
    );

    public Supplier<ModelMekanismBase> metallurgicInfuser
        = MekanismConfig.client.modelType.createModel(
            ModelMetallurgicInfuser::new,
            LegacyModelMetallurgicInfuser::new,
            ClassicModelMetallurgicInfuser::new
        );

    public Supplier<ModelMekanismBase> chargepad = MekanismConfig.client.modelType.createModel(
        ModelChargepad::new, LegacyModelChargepad::new
    );

    public Supplier<IModelOnOff> logisticalSorter = MekanismConfig.client.modelType.createModel(
        ModelLogisticalSorter::new, LegacyModelLogisticalSorter::new
    );

    public Supplier<IModelOnOff> digitalMiner = MekanismConfig.client.modelType.createModel(
        ModelDigitalMiner::new, LegacyModelDigitalMiner::new
    );

    public Supplier<ModelMekanismBase> rotaryCondensentrator
        = MekanismConfig.client.modelType.createModel(
            ModelRotaryCondensentrator::new, LegacyModelRotaryCondensentrator::new
        );

    public Supplier<ModelMekanismBase> chemicalOxidizer
        = MekanismConfig.client.modelType.createModel(
            ModelChemicalOxidizer::new, LegacyModelChemicalOxidizer::new
        );

    public Supplier<ModelMekanismBase> chemicalInfuser
        = MekanismConfig.client.modelType.createModel(
            ModelChemicalInfuser::new, LegacyModelChemicalInfuser::new
        );

    public Supplier<ModelMekanismBase> electrolyticSeparator
        = MekanismConfig.client.modelType.createModel(
            ModelElectrolyticSeparator::new, LegacyModelElectrolyticSeparator::new
        );

    public Supplier<ModelMekanismBase> chemicalDissolutionChamber
        = MekanismConfig.client.modelType.createModel(
            ModelChemicalDissolutionChamber::new,
            LegacyModelChemicalDissolutionChamber::new
        );

    public Supplier<ModelMekanismBase> chemicalWasher = MekanismConfig.client.modelType.createModel(
        ModelChemicalWasher::new, LegacyModelChemicalWasher::new
    );

    public Supplier<ModelMekanismBase> chemicalCrystallizer
        = MekanismConfig.client.modelType.createModel(
            ModelChemicalCrystallizer::new, LegacyModelChemicalCrystallizer::new
        );

    public Supplier<ModelMekanismBase> seismicVibrator
        = MekanismConfig.client.modelType.createModel(
            ModelSeismicVibrator::new, LegacyModelSeismicVibrator::new
        );

    public Supplier<ModelMekanismBase> pressurizedReactionChamber
        = MekanismConfig.client.modelType.createModel(
            ModelPressurizedReactionChamber::new,
            LegacyModelPressurizedReactionChamber::new
        );

    public Supplier<ModelMekanismBase> fluidicPlenisher
        = MekanismConfig.client.modelType.createModel(
            ModelFluidicPlenisher::new, LegacyModelFluidicPlenisher::new
        );

    public ModelLaser laser = new ModelLaser();

    public ModelLaserAmplifier laserAmplifier = new ModelLaserAmplifier();

    public ModelSolarNeutronActivator solarNeutronActivator
        = new ModelSolarNeutronActivator();

    public ModelResistiveHeater resistiveHeater = new ModelResistiveHeater();

    public ModelQuantumEntangloporter quantumEntangloporter
        = new ModelQuantumEntangloporter();

    public ModelTheoreticalElementizer theoreticalElementizer
        = new ModelTheoreticalElementizer();

    @Override
    public void
    renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (block == null || renderer == null
            || MachineType.get(block, metadata) == null) {
            return;
        }

        GL11.glPushMatrix();
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);

        MachineType type = MachineType.get(block, metadata);

        if (type == MachineType.ELECTRIC_PUMP) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, -0.85F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, electricPump.get().getTextureName()
            ));
            electricPump.get().render(0.0560F);
        } else if (type == MachineType.METALLURGIC_INFUSER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, metallurgicInfuser.get().getTextureName()
            ));
            metallurgicInfuser.get().render(0.0625F);
        } else if (type == MachineType.CHARGEPAD) {
            GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.1F, 0.0F);
            mc.renderEngine.bindTexture(
                MekanismUtils.getResource(ResourceType.RENDER, chargepad.get().getTextureName())
            );
            chargepad.get().render(0.0625F);
        } else if (type == MachineType.LOGISTICAL_SORTER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, logisticalSorter.get().getTextureName()
            ));
            logisticalSorter.get().render(0.0625F, false, mc.renderEngine);
        } else if (type == MachineType.DIGITAL_MINER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-180F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.35F, 0.1F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, digitalMiner.get().getTextureName()
            ));
            digitalMiner.get().render(0.022F, false, mc.renderEngine);
        } else if (type == MachineType.ROTARY_CONDENSENTRATOR) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.05F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, rotaryCondensentrator.get().getTextureName()
            ));
            rotaryCondensentrator.get().render(0.0625F);
        } else if (type == MachineType.CHEMICAL_OXIDIZER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.00F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, chemicalOxidizer.get().getTextureName()
            ));
            chemicalOxidizer.get().render(0.0625F);
        } else if (type == MachineType.CHEMICAL_INFUSER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, chemicalInfuser.get().getTextureName()
            ));
            chemicalInfuser.get().render(0.0625F);
        } else if (type == MachineType.ELECTROLYTIC_SEPARATOR) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslated(0.0F, -1.0F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, electrolyticSeparator.get().getTextureName()
            ));
            electrolyticSeparator.get().render(0.0625F);
        } else if (type == MachineType.CHEMICAL_DISSOLUTION_CHAMBER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.05F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, chemicalDissolutionChamber.get().getTextureName()
            ));
            chemicalDissolutionChamber.get().render(0.0625F);
        } else if (type == MachineType.CHEMICAL_WASHER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.05F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, chemicalWasher.get().getTextureName()
            ));
            chemicalWasher.get().render(0.0625F);
        } else if (type == MachineType.CHEMICAL_CRYSTALLIZER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.05F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, chemicalCrystallizer.get().getTextureName()
            ));
            chemicalCrystallizer.get().render(0.0625F);
        } else if (type == MachineType.SEISMIC_VIBRATOR) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glScalef(0.6F, 0.6F, 0.6F);
            GL11.glTranslatef(0.0F, -0.55F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, seismicVibrator.get().getTextureName()
            ));
            seismicVibrator.get().render(0.0625F);
        } else if (type == MachineType.PRESSURIZED_REACTION_CHAMBER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.05F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, pressurizedReactionChamber.get().getTextureName()
            ));
            pressurizedReactionChamber.get().render(0.0625F);
        } else if (type == MachineType.FLUIDIC_PLENISHER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.85F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, fluidicPlenisher.get().getTextureName()
            ));
            fluidicPlenisher.get().render(0.0560F);
        } else if (type == MachineType.LASER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.85F, 0.0F);
            mc.renderEngine.bindTexture(
                MekanismUtils.getResource(ResourceType.RENDER, "Laser.png")
            );
            laser.render(0.0560F);
        } else if (type == MachineType.LASER_AMPLIFIER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.85F, 0.0F);
            mc.renderEngine.bindTexture(
                MekanismUtils.getResource(ResourceType.RENDER, "LaserAmplifier.png")
            );
            laserAmplifier.render(0.0560F);
        } else if (type == MachineType.LASER_TRACTOR_BEAM) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.85F, 0.0F);
            mc.renderEngine.bindTexture(
                MekanismUtils.getResource(ResourceType.RENDER, "LaserTractorBeam.png")
            );
            laserAmplifier.render(0.0560F);
        } else if (type == MachineType.RESISTIVE_HEATER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.05F, -0.96F, 0.05F);
            mc.renderEngine.bindTexture(
                MekanismUtils.getResource(ResourceType.RENDER, "ResistiveHeater.png")
            );
            resistiveHeater.render(0.0625F, false, mc.renderEngine);
        } else if (type == MachineType.SOLAR_NEUTRON_ACTIVATOR) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(0.6F, 0.6F, 0.6F);
            GL11.glTranslatef(0.0F, -0.55F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, "SolarNeutronActivator.png"
            ));
            solarNeutronActivator.render(0.0625F);
        } else if (type == MachineType.QUANTUM_ENTANGLOPORTER) {
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, "QuantumEntangloporter.png"
            ));
            quantumEntangloporter.render(0.0625F, mc.renderEngine);
        } else if (type == MachineType.THEORETICAL_ELEMENTIZER) {
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180.0F, 0.0F, -1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0F, 0.0F);
            mc.renderEngine.bindTexture(MekanismUtils.getResource(
                ResourceType.RENDER, "TheoreticalElementizer.png"
            ));
            theoreticalElementizer.render(0.0625F);
        } else {
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            MekanismRenderer.renderItem(renderer, metadata, block);
        }

        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(
        IBlockAccess world,
        int x,
        int y,
        int z,
        Block block,
        int modelId,
        RenderBlocks renderer
    ) {
        //Handled by CTMRenderingHandler
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.MACHINE_RENDER_ID;
    }
}
