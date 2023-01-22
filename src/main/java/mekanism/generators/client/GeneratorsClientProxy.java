package mekanism.generators.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.generators.client.gui.GuiBioGenerator;
import mekanism.generators.client.gui.GuiGasGenerator;
import mekanism.generators.client.gui.GuiHeatGenerator;
import mekanism.generators.client.gui.GuiIndustrialTurbine;
import mekanism.generators.client.gui.GuiNeutronCapture;
import mekanism.generators.client.gui.GuiReactorController;
import mekanism.generators.client.gui.GuiReactorFuel;
import mekanism.generators.client.gui.GuiReactorHeat;
import mekanism.generators.client.gui.GuiReactorLogicAdapter;
import mekanism.generators.client.gui.GuiReactorStats;
import mekanism.generators.client.gui.GuiSolarGenerator;
import mekanism.generators.client.gui.GuiTurbineStats;
import mekanism.generators.client.gui.GuiWindGenerator;
import mekanism.generators.client.render.RenderAdvancedSolarGenerator;
import mekanism.generators.client.render.RenderBioGenerator;
import mekanism.generators.client.render.RenderGasGenerator;
import mekanism.generators.client.render.RenderHeatGenerator;
import mekanism.generators.client.render.RenderIndustrialTurbine;
import mekanism.generators.client.render.RenderReactor;
import mekanism.generators.client.render.RenderSolarGenerator;
import mekanism.generators.client.render.RenderTurbineRotor;
import mekanism.generators.client.render.RenderWindGenerator;
import mekanism.generators.client.render.item.GeneratorsItemRenderer;
import mekanism.generators.common.GeneratorsBlocks;
import mekanism.generators.common.GeneratorsCommonProxy;
import mekanism.generators.common.tile.TileEntityAdvancedSolarGenerator;
import mekanism.generators.common.tile.TileEntityBioGenerator;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import mekanism.generators.common.tile.TileEntityHeatGenerator;
import mekanism.generators.common.tile.TileEntitySolarGenerator;
import mekanism.generators.common.tile.TileEntityWindGenerator;
import mekanism.generators.common.tile.reactor.TileEntityReactorController;
import mekanism.generators.common.tile.reactor.TileEntityReactorLogicAdapter;
import mekanism.generators.common.tile.reactor.TileEntityReactorNeutronCapture;
import mekanism.generators.common.tile.turbine.TileEntityTurbineCasing;
import mekanism.generators.common.tile.turbine.TileEntityTurbineRotor;
import mekanism.generators.common.tile.turbine.TileEntityTurbineValve;
import mekanism.generators.common.tile.turbine.TileEntityTurbineVent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class GeneratorsClientProxy extends GeneratorsCommonProxy {
    @Override
    public void registerSpecialTileEntities() {
        ClientRegistry.registerTileEntity(
            TileEntityAdvancedSolarGenerator.class,
            "AdvancedSolarGenerator",
            new RenderAdvancedSolarGenerator()
        );
        ClientRegistry.registerTileEntity(
            TileEntitySolarGenerator.class, "SolarGenerator", new RenderSolarGenerator()
        );
        ClientRegistry.registerTileEntity(
            TileEntityBioGenerator.class, "BioGenerator", new RenderBioGenerator()
        );
        ClientRegistry.registerTileEntity(
            TileEntityHeatGenerator.class, "HeatGenerator", new RenderHeatGenerator()
        );
        ClientRegistry.registerTileEntity(
            TileEntityGasGenerator.class, "GasGenerator", new RenderGasGenerator()
        );
        ClientRegistry.registerTileEntity(
            TileEntityWindGenerator.class, "WindTurbine", new RenderWindGenerator()
        );
        ClientRegistry.registerTileEntity(
            TileEntityReactorController.class, "ReactorController", new RenderReactor()
        );
        ClientRegistry.registerTileEntity(
            TileEntityTurbineRotor.class, "TurbineRod", new RenderTurbineRotor()
        );
        ClientRegistry.registerTileEntity(
            TileEntityTurbineCasing.class, "TurbineCasing", new RenderIndustrialTurbine()
        );
        ClientRegistry.registerTileEntity(
            TileEntityTurbineValve.class, "TurbineValve", new RenderIndustrialTurbine()
        );
        ClientRegistry.registerTileEntity(
            TileEntityTurbineVent.class, "TurbineVent", new RenderIndustrialTurbine()
        );
    }

    @Override
    public void registerRenderInformation() {
        //Register block handler
        RenderingRegistry.registerBlockHandler(new BlockRenderingHandler());

        //Register item handler
        GeneratorsItemRenderer handler = new GeneratorsItemRenderer();

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(GeneratorsBlocks.Generator), handler
        );

        System.out.println("[MekanismGenerators] Render registrations complete.");
    }

    @Override
    public GuiScreen
    getClientGui(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        switch (ID) {
            case 0:
                return new GuiHeatGenerator(
                    player.inventory, (TileEntityHeatGenerator) tileEntity
                );
            case 1:
                return new GuiSolarGenerator(
                    player.inventory, (TileEntitySolarGenerator) tileEntity
                );
            case 3:
                return new GuiGasGenerator(
                    player.inventory, (TileEntityGasGenerator) tileEntity
                );
            case 4:
                return new GuiBioGenerator(
                    player.inventory, (TileEntityBioGenerator) tileEntity
                );
            case 5:
                return new GuiWindGenerator(
                    player.inventory, (TileEntityWindGenerator) tileEntity
                );
            case 6:
                return new GuiIndustrialTurbine(
                    player.inventory, (TileEntityTurbineCasing) tileEntity
                );
            case 7:
                return new GuiTurbineStats(
                    player.inventory, (TileEntityTurbineCasing) tileEntity
                );
            case 10:
                return new GuiReactorController(
                    player.inventory, (TileEntityReactorController) tileEntity
                );
            case 11:
                return new GuiReactorHeat(
                    player.inventory, (TileEntityReactorController) tileEntity
                );
            case 12:
                return new GuiReactorFuel(
                    player.inventory, (TileEntityReactorController) tileEntity
                );
            case 13:
                return new GuiReactorStats(
                    player.inventory, (TileEntityReactorController) tileEntity
                );
            case 14:
                return new GuiNeutronCapture(
                    player.inventory, (TileEntityReactorNeutronCapture) tileEntity
                );
            case 15:
                return new GuiReactorLogicAdapter(
                    player.inventory, (TileEntityReactorLogicAdapter) tileEntity
                );
        }

        return null;
    }

    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event) {
        RenderIndustrialTurbine.resetDisplayInts();
    }
}
