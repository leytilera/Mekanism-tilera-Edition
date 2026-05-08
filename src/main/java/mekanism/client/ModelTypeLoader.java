package mekanism.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import mekanism.api.ModelType;
import mekanism.api.MekanismConfig.client;
import net.anvilcraft.alec.jalec.factories.AlecUnexpectedRuntimeErrorExceptionFactory;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

public class ModelTypeLoader implements IResourceManagerReloadListener {

    Gson gson = new GsonBuilder().create();

    @SuppressWarnings({"unchecked", "ALEC"})
    @Override
    public void onResourceManagerReload(IResourceManager rm) {
        try {
            List<IResource> resources = rm.getAllResources(new ResourceLocation("mekanism", "modelTypes.json"));
            for (IResource res : resources) {
                InputStreamReader reader = new InputStreamReader(res.getInputStream());
                JsonObject obj = gson.fromJson(reader, JsonObject.class);
                reader.close();
                processJson(obj);
            }
        } catch (Exception e) {
            throw AlecUnexpectedRuntimeErrorExceptionFactory.PLAIN.createAlecExceptionWithCause(e, new Object[0]);
        }
    }

    private void processJson(JsonObject obj) {
        if (obj.has("smallFluidPipes")) {
            client.smallPipeFluid = obj.get("smallFluidPipes").getAsBoolean();
        }
        if (obj.has("modelType")) {
            String type = obj.get("modelType").getAsString();
            ModelType t = ModelType.fromString(type);
            if (t != null) client.modelType = t;
        }
        if (obj.has("oldTransmitterRender")) {
            client.oldTransmitterRender = obj.get("oldTransmitterRender").getAsBoolean();
        }
    }

}
