package codyhuh.babyfat.registry;

import codyhuh.babyfat.BabyFat;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BFTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BabyFat.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BF_TAB = TABS.register("babyfat_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BabyFat.MOD_ID))
                    .icon(BFItems.RANCHU_BUCKET.get()::getDefaultInstance)
                    .displayItems((displayParams, output) -> {
                        output.accept(BFItems.RANCHU_BUCKET.get());
                        output.accept(BFItems.RANCHU_SPAWN_EGG.get());
                        output.accept(BFItems.WATER_LETTUCE.get());
                        output.accept(BFItems.CREATIVE_LETTUCE.get());
                    })
                    .build()
    );
}