package codyhuh.babyfat.client;

import codyhuh.babyfat.common.entities.RanchuSexResolver;
import codyhuh.babyfat.registry.BFItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class RanchuBucketItemModelProvider extends ItemModelProvider {
    public RanchuBucketItemModelProvider(PackOutput output, String modId, ExistingFileHelper efh) {
        super(output, modId, efh);
    }

    @Override
    protected void registerModels() {
        for(RanchuSexResolver.RanchuColour col : RanchuSexResolver.RanchuColour.values()) {
            this.bucket(BFItems.RANCHU_BUCKET.get(), col.name().toLowerCase());
        }
    }

    public ItemModelBuilder bucket(Item item, String col)
    {
        return bucket(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), col);
    }

    public ItemModelBuilder bucket(ResourceLocation item, String col)
    {
        return getBuilder(item.toString() + "_" + col)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/bucket/" + col));
    }
}
