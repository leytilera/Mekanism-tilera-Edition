package mekanism.common.integration;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraftforge.fluids.Fluid;

public class HBMFluid extends Fluid {
    public FluidType hbm;

    public HBMFluid(FluidType hbm) {
        super(hbm.getName().toLowerCase());

        this.hbm = hbm;
        this.temperature = hbm.temperature + 273;
        this.setGaseous(hbm.hasTrait(Fluids.GASEOUS.getClass()));
    }

    @Override
    public String getUnlocalizedName() {
        return this.hbm.getUnlocalizedName();
    }
}
