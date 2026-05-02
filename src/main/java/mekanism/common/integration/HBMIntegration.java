package mekanism.common.integration;

import com.google.common.collect.HashBiMap;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.util.EnumSymbol;
import com.hbm.util.CompatFluidRegistry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.MekanismConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class HBMIntegration {
    public static final HBMIntegration INSTANCE = new HBMIntegration();
    static {
        MinecraftForge.EVENT_BUS.register(HBMIntegration.INSTANCE);
    }

    private final HashBiMap<Fluid, FluidType> fluidMap = HashBiMap.create();
    private final Map<String, Fluid> namedFluids = new HashMap<>();

    public void registerHBMFluids() {
        for (FluidType fluid : Fluids.getAll()) {
            if (fluid == Fluids.NONE
                || MekanismConfig.general.hbmFluidBlacklist.contains(fluid.getName()))
                continue;

            Fluid forgeFluid = FluidRegistry.getFluid(fluid.getName().toLowerCase());
            if (forgeFluid == null) {
                FluidRegistry.registerFluid(forgeFluid = new HBMFluid(fluid));
            }

            fluidMap.put(forgeFluid, fluid);
            namedFluids.put(forgeFluid.getName().toLowerCase(), forgeFluid);
        }
    }

    public void registerUUMatter() {
        CompatFluidRegistry.registerFluid("ic2uumatter", 431885383, 8388736, 0, 0, 0, EnumSymbol.NONE, new ResourceLocation("ic2", "textures/blocks/fluids/uumatter_still.png"));
    }

    public FluidType convert(Fluid fluid) {
        if (fluid == null) {
            return null;
        } else if (fluidMap.containsKey(fluid)) {
            return fluidMap.get(fluid);
        } else if (namedFluids.containsKey(fluid.getName().toLowerCase())) {
            return fluidMap.get(namedFluids.get(fluid.getName().toLowerCase()));
        }
        return null;
    }

    public Fluid convert(FluidType fluid) {
        if (fluid == null) return null;
        return fluidMap.inverse().get(fluid);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre ev) {
        if (ev.map.getTextureType() != 0)
            return;

        for (Fluid fl : this.fluidMap.keySet()) {
            if (!(fl instanceof HBMFluid))
                continue;
            HBMFluid hbmfl = (HBMFluid) fl;
            long nslash = ev.map.basePath.chars().filter(c -> c == '/').count() + 1;

            StringBuilder sb = new StringBuilder();
            sb.append(hbmfl.hbm.getTexture().getResourceDomain());
            sb.append(':');
            for (int i = 0; i < nslash; i++) {
                sb.append("../");
            }
            sb.append(
                StringUtils.removeEnd(hbmfl.hbm.getTexture().getResourcePath(), ".png")
            );

            hbmfl.setIcons(ev.map.registerIcon(sb.toString()));
        }
    }
}
