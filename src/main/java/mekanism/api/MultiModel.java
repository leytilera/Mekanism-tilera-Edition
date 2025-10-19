package mekanism.api;

import java.util.function.Supplier;

import mekanism.client.IModelMekanism;

public class MultiModel<T extends IModelMekanism> implements Supplier<T> {

    private T modern;
    private T legacy;
    private T classic;

    public MultiModel(T modern, T legacy, T classic) {
        this.modern = modern;
        this.legacy = legacy;
        this.classic = classic;
    }

    @Override
    public T get() {
        switch (MekanismConfig.client.modelType) {
            case CLASSIC:
                return classic;
            case LEGACY:
                return legacy;
            case MODERN:
                return modern;
            default:
                throw new RuntimeException("ALECUS MAXIMUS");
        }
    }
    
}
