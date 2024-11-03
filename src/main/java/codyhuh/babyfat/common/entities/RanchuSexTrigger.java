package codyhuh.babyfat.common.entities;

import codyhuh.babyfat.BabyFat;
import com.google.gson.JsonObject;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class RanchuSexTrigger extends SimpleCriterionTrigger<RanchuSexTrigger.TriggerInstance> {
    static final ResourceLocation ID = BabyFat.id("ranchu_sex");

    public RanchuSexTrigger() {
    }

    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public RanchuSexTrigger.TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        System.out.println("please work");
        JsonObject o = pJson.getAsJsonObject("data");
        int a = GsonHelper.convertToInt(o.getAsJsonPrimitive("same_colour"), "value");
        int b = GsonHelper.convertToInt(o.getAsJsonPrimitive("golden_crown"), "value");

        System.out.println(a);
        System.out.println(b);
        System.out.println(pJson);

        return new TriggerInstance(pPredicate, GsonHelper.convertToInt(o.getAsJsonPrimitive("tail"), "value"),
                GsonHelper.convertToInt(o.getAsJsonPrimitive("base_colour"), "value"),
                a == -1 ? Optional.empty() : Optional.of(a == 1),
                b == -1 ? Optional.empty() : Optional.of(b == 1));
    }

    public void trigger(ServerPlayer pPlayer, Ranchu out) {
        int ego = out.getVariant();
        if(ego == -1) {
            ego = 0;
        }
        int base = (2*2*2-1 & ego);
        int pat1 = (2*2*2*2*2*2-1 & ego >> 3);
        int pat2 = (2*2*2*2*2*2-1 & ego >> 3+6);
        int c1 = (2*2*2*2*2-1 & ego >> 3+6+6+5);
        int c2 = (2*2*2*2*2-1 & ego >> 3+6+6+5+5);
        int baseColour = (2*2*2*2*2-1 & ego >> 3+6+6);
        int tail = out.getTail();

        this.trigger(pPlayer, p_18653_ -> p_18653_.matches(tail, baseColour, c1==c2 && c2==baseColour, out.getSize() >= Ranchu.MAX_SIZE-0.1));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final int tail;
        private final int base;
        private final Optional<Boolean> c;
        private final Optional<Boolean> g;

        public TriggerInstance(ContextAwarePredicate player, int tail, int base, Optional<Boolean> c, Optional<Boolean> g) {
            super(RanchuSexTrigger.ID, player);
            this.tail = tail;
            this.base = base;
            this.c = c;
            this.g = g;
        }

        public boolean matches(int tail, int base, boolean same_col, boolean golden_crown) {
            System.out.println("Input data: " + tail + " " + base + " " + same_col + " " + golden_crown);
            System.out.println("Predicate data: " + this.tail + " " + this.base + " " + this.c + " " + this.g);
            System.out.println("Output data: " + (this.tail == -1 || this.tail == tail) + " " + (this.base == -1 || this.base == base) + " " + (c.isEmpty() || c.get() == same_col) + " " + (g.isEmpty() || g.get() == golden_crown));
            return (this.tail == -1 || this.tail == tail)
                    &&
                    (this.base == -1 || this.base == base)
                    &&
                    (c.isEmpty() || c.get() == same_col)
                    &&
                    (g.isEmpty() || g.get() == golden_crown);

        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject $$1 = super.serializeToJson(pConditions);
            JsonObject o = new JsonObject();
            o.addProperty("tail", tail);
            o.addProperty("base_colour", base);
            o.addProperty("same_colour", c.map(aBoolean -> aBoolean ? 1 : 0).orElse(-1));
            o.addProperty("golden_crown", g.map(aBoolean -> aBoolean ? 1 : 0).orElse(-1));
            $$1.add("data", o);
            return $$1;
        }
    }
}