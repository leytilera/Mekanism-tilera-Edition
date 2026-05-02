package mekanism.common.base;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileDelegate {

    public static final Map<Class<?>, Class<? extends ITileDelegate>> IMPLEMENTATIONS = new HashMap<>();
    
    void load();

    void unload();

    void tick();

    void writeToNBT(NBTTagCompound nbt);

    void readFromNBT(NBTTagCompound nbt);

}
