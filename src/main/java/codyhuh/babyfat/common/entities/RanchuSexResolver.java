package codyhuh.babyfat.common.entities;

import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RanchuSexResolver {
    private static final RanchuSexResolver INSTANCE = new RanchuSexResolver();

    private final WeightedSuperposition DEFAULT = new WeightedSuperposition();

    public static RanchuSexResolver getInstance() {
        return INSTANCE;
    }

    public enum RanchuColour {
        APRICOT,
        AZURE,
        BITUMEN,
        CHARCOAL,
        CHOCOLATE,

        COPPER,
        ELECTRUM,
        GINGER,
        GOLD,
        IVORY,

        ONYX,
        OPAL,
        PALE,
        PEARL,
        PLUM,

        PYRITE,
        REDWOOD,
        ROCKSALT,
        ROSE,
        ROYAL,

        RUBY,
        RUST,
        SILVER,
        SYRUP,
        WILD
    }
    public interface SexLookupSuperposition {
        RanchuColour collapse(RanchuColour a, RanchuColour b, RandomSource random, Consumer<RanchuColour> mutationCallback);
    }

    public class WeightedSuperposition implements SexLookupSuperposition {
        private final Map<RanchuColour, Float> sexChances = new HashMap<>();

        public void addEntry(RanchuColour col, float f) {
            sexChances.put(col, f);
        }

        @Override
        public RanchuColour collapse(RanchuColour a, RanchuColour b, RandomSource random, Consumer<RanchuColour> mutationCallback) {
            float index = random.nextFloat() * sexChances.size();
            float sum = sexChances.values().stream().reduce(0.0f, Float::sum);
            if(index > sum) {
                return random.nextBoolean() ? b : a;
            }

            float accl = 0F;
            for(Map.Entry<RanchuColour, Float> e : sexChances.entrySet()) {
                accl += e.getValue();
                if(index <= accl) {
                    RanchuColour out = e.getKey();
                    mutationCallback.accept(out);
                    return out;
                }
            }

            return RanchuColour.WILD;
        }
    }

    public void wipe() {
        sexResults.clear();
    }

    private Map<Integer, SexLookupSuperposition> sexResults = new HashMap<>();

    public SexLookupSuperposition lookupSuperposition(RanchuColour a, RanchuColour b) {
        int lookup = a.ordinal() + (b.ordinal() << 5);
        return sexResults.getOrDefault(lookup, DEFAULT);
    }

    public SexLookupSuperposition input(RanchuColour a, RanchuColour b, float weight, RanchuColour result) {
        int lookup = a.ordinal() + (b.ordinal() << 5);
        SexLookupSuperposition out;
        if(sexResults.containsKey(lookup)) {
            out = sexResults.get(lookup);
            if(out instanceof WeightedSuperposition s) {
                s.addEntry(result, weight);
            }
        } else {
            out = new WeightedSuperposition();
            ((WeightedSuperposition) out).addEntry(result, weight);
            sexResults.put(lookup, out);
        }
        return out;
    }
}
