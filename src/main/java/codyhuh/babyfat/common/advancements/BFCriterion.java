package codyhuh.babyfat.common.advancements;

import codyhuh.babyfat.BabyFat;
import codyhuh.babyfat.registry.BFCriteriaTriggers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BabyFat.MOD_ID)
public class BFCriterion {

    public static final BFCriteriaTriggers BREED_RANCHUS = CriteriaTriggers.register(new BFCriteriaTriggers("bred_ranchus"));
}