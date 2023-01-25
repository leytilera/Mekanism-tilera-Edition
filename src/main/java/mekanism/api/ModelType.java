package mekanism.api;

import java.util.function.Supplier;

import mekanism.client.IModelMekanism;

public enum ModelType {
    MODERN,
    LEGACY,
    CLASSIC;

    public static ModelType fromString(String s) {
        if (s.equalsIgnoreCase("modern"))
            return MODERN;

        if (s.equalsIgnoreCase("legacy"))
            return LEGACY;

        if (s.equalsIgnoreCase("classic"))
            return CLASSIC;

        return null;
    }

    public boolean isOld() {
        return this != MODERN;
    }

    public <T extends IModelMekanism> T createModel(
        Supplier<? extends T> modern,
        Supplier<? extends T> legacy,
        Supplier<? extends T> classic
    ) {
        switch (this) {
            case MODERN:
                return modern.get();
            case LEGACY:
                return legacy.get();
            case CLASSIC:
                return classic.get();
            default:
                throw new RuntimeException("ALECUS MAXIMUS");
        }
    }

    public <T extends IModelMekanism>
        T createModel(Supplier<? extends T> modern, Supplier<? extends T> legacy) {
        return this.createModel(modern, legacy, legacy);
    }

    public int mapFacing(int facing) {
        if (!this.isOld())
            return facing;

        switch (facing) {
            case 2:
                return 3;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 4;
        }

        return -1;
    }
}
