package codyhuh.babyfat.mixin;

import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SimpleTexture.class)
public interface TextureImageAccessor {
    @Invoker("getTextureImage")
    SimpleTexture.TextureImage babyfat$invokeGetTextureImage(ResourceManager pResourceManager);
}
