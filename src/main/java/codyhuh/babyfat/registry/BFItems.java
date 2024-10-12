package codyhuh.babyfat.registry;

import codyhuh.babyfat.BabyFat;
import codyhuh.babyfat.common.items.RanchuBucketItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BFItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BabyFat.MOD_ID);

	public static final RegistryObject<Item> RANCHU_BUCKET = ITEMS.register("ranchu_bucket", () -> new RanchuBucketItem(BFEntities.RANCHU, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> RANCHU_SPAWN_EGG = ITEMS.register("ranchu_spawn_egg", () -> new ForgeSpawnEggItem(BFEntities.RANCHU, 0x736036, 0xd1a965, new Item.Properties()));

	public static final RegistryObject<Item> WATER_LETTUCE = ITEMS.register("water_lettuce", () -> new PlaceOnWaterBlockItem(BFBlocks.WATER_LETTUCE.get(), new Item.Properties()));

	public static final RegistryObject<Item> COPPER_TROPHY = ITEMS.register("copper_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> IRON_TROPHY = ITEMS.register("iron_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> GOLD_TROPHY = ITEMS.register("gold_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> DIAMOND_TROPHY = ITEMS.register("diamond_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ONYX_TROPHY = ITEMS.register("onyx_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> OPAL_TROPHY = ITEMS.register("opal_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> AZURE_TROPHY = ITEMS.register("azure_trophy", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> WILDCARD = ITEMS.register("wildcard", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> RANCHU = ITEMS.register("ranchu", () -> new Item(new Item.Properties().stacksTo(1)));
}
