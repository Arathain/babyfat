package codyhuh.babyfat.mixin;

import codyhuh.babyfat.registry.BFItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemParser.class)
public class ItemParserMixin {
    @WrapOperation(
            method = {"suggestItem", "suggestTag"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/commands/arguments/item/ItemParser;items:Lnet/minecraft/core/HolderLookup;")
    )
    private HolderLookup<Item> babyfat$removeHidden(ItemParser instance, Operation<HolderLookup<Item>> og) {
        return og.call(instance).filterElements(i -> !i.getDefaultInstance().is(BFItems.HIDDEN));
    }
}
