package mekanism.client;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import mekanism.api.ModelType;
import mekanism.api.MekanismConfig.client;
import net.anvilcraft.alec.jalec.factories.AlecUnexpectedRuntimeErrorExceptionFactory;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

public class ModelTypeLoader implements IResourceManagerReloadListener {

    Gson gson = new GsonBuilder().create();

    @Override
    public void onResourceManagerReload(IResourceManager rm) {
        try {
            InputStream is = rm.getResource(new ResourceLocation("mekanism", "modelTypes.json")).getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            JsonObject obj = gson.fromJson(reader, JsonObject.class);
            reader.close();
            this.processJson(obj);
        } catch (Exception e) {
            throw AlecUnexpectedRuntimeErrorExceptionFactory.PLAIN.createAlecExceptionWithCause(e, new Object[0]);
        }
    }

    private void processJson(JsonObject obj) {
        if (obj.has("smallFluidPipes")) {
            boolean smallPipes = obj.get("smallFluidPipes").getAsBoolean();
            client.smallPipeFluid = smallPipes;
            System.out.println(client.smallPipeFluid);
        }
        if (obj.has("modelType")) {
            String type = obj.get("modelType").getAsString();
            ModelType t = ModelType.fromString(type);
            if (t != null) client.modelType = t;
        }
    }

}
