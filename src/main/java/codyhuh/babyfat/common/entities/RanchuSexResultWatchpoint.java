package codyhuh.babyfat.common.entities;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class RanchuSexResultWatchpoint extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    public RanchuSexResultWatchpoint() {
        super(GSON, "babyfat/ranchu_result");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager rm, ProfilerFiller pf) {
        RanchuSexResolver subject = RanchuSexResolver.getInstance();
        subject.wipe();
        for (JsonElement entry : map.values()) {
            JsonObject object = entry.getAsJsonObject();
            RanchuSexResolver.RanchuColour c = RanchuSexResolver.RanchuColour.valueOf(object.getAsJsonPrimitive("colour").getAsString().toUpperCase());
            JsonArray array = object.getAsJsonArray("crosses");
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                RanchuSexResolver.RanchuColour c1 = RanchuSexResolver.RanchuColour.valueOf(obj.getAsJsonPrimitive("colour_1").getAsString().toUpperCase());
                RanchuSexResolver.RanchuColour c2 = RanchuSexResolver.RanchuColour.valueOf(obj.getAsJsonPrimitive("colour_2").getAsString().toUpperCase());
                float w = obj.getAsJsonPrimitive("weight").getAsFloat();
                subject.input(c1, c2, w, c);
            }
        }
    }
}
