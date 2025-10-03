package mekanism.common;

import java.util.function.Supplier;

import mekanism.api.MekanismConfig.general;
import mekanism.api.util.UnitDisplayUtils.EnergyType;
import net.anvilcraft.anvillib.api.units.IEnergyUnit;
import net.anvilcraft.anvillib.registries.UnitRegistry;

public class Units {

    public static Supplier<IEnergyUnit> JOULES = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("universalelectricity:joules");
    public static Supplier<IEnergyUnit> RF = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("cofhlib:rf");
    public static Supplier<IEnergyUnit> EU = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("ic2:eu");
    public static Supplier<IEnergyUnit> HE = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("hbm:he");
    public static Supplier<IEnergyUnit> AE = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("appliedenergistics2:ae");
    public static Supplier<IEnergyUnit> MJ = () -> (IEnergyUnit) UnitRegistry.INSTANCE.get("buildcraft:mj");

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
        UnitRegistry.INSTANCE.getOrRegister(new IEnergyUnit() {

            @Override
            public String getID() {
                return "buildcraft:mj";
            }

            @Override
            public String getName() {
                return "Minecraft Joules";
            }

            @Override
            public String getAbbreviation() {
                return "MJ";
            }

            @Override
            public double joulesConversionRatio() {
                return RF.get().joulesConversionRatio() * 10;
            }
            
        });
        UnitRegistry.INSTANCE.getOrRegister(new IEnergyUnit() {
            @Override
            public String getID() {
                return "hbm:he";
            }

            @Override
            public String getName() {
                return "HBM Energy";
            }

            @Override
            public String getAbbreviation() {
                return "HE";
            }

            @Override
            public double joulesConversionRatio() {
                return general.FROM_HE;
            }
        });
    }

    public static double convertToJoules(double value, Supplier<IEnergyUnit> unit) {
        return unit.get().convertTo(JOULES.get(), value);
    }

    public static double convertFromJoules(double value, Supplier<IEnergyUnit> unit) {
        return JOULES.get().convertTo(unit.get(), value);
    }

    public static Supplier<IEnergyUnit> fromType(EnergyType type) {
        switch(type) {
            case EU:
                return EU;
            case MJ:
                return MJ;
            case RF:
                return RF;
            default:
                return JOULES;
            
        }
    }
    
}
