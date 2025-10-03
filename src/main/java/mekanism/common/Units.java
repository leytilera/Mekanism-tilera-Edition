package mekanism.common;

import java.util.function.Supplier;

import mekanism.api.MekanismConfig.general;
import net.anvilcraft.anvillib.api.units.IEnergyUnit;
import net.anvilcraft.anvillib.registries.UnitRegistry;

public class Units {

    public static Supplier<IEnergyUnit> JOULES = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("universalelectricity:joules");
    public static Supplier<IEnergyUnit> RF = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("cofhlib:rf");
    public static Supplier<IEnergyUnit> EU = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("ic2:eu");
    public static Supplier<IEnergyUnit> HE = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("hbm:he");
    public static Supplier<IEnergyUnit> AE = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("appliedenergistics2:ae");

    static {
        UnitRegistry.INSTANCE.getOrRegister(new IEnergyUnit() {

            @Override
            public String getID() {
                return "ic2:eu";
            }

            @Override
            public String getName() {
                return "Electrical Units";
            }

            @Override
            public String getAbbreviation() {
                return "EU";
            }

            @Override
            public double joulesConversionRatio() {
                return general.FROM_IC2;
            }
            
        });
    }

    public static double convertToJoules(double value, Supplier<IEnergyUnit> unit) {
        return unit.get().convertTo(JOULES.get(), value);
    }

    public static double convertFromJoules(double value, Supplier<IEnergyUnit> unit) {
        return JOULES.get().convertTo(unit.get(), value);
    }
    
}
