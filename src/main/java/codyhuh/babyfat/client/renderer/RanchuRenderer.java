package codyhuh.babyfat.client.renderer;

import codyhuh.babyfat.BabyFat;
import codyhuh.babyfat.client.ModModelLayers;
import codyhuh.babyfat.client.model.RanchuModel;
import codyhuh.babyfat.common.entities.Ranchu;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static codyhuh.babyfat.BabyFat.id;

@OnlyIn(Dist.CLIENT)
public class RanchuRenderer<T extends Ranchu> extends MobRenderer<T, RanchuModel<T>> {
	private static Map<Integer, ResourceLocation> TEXTURE_LOOKUP = new HashMap<>();

	private static final ResourceLocation[] TEXTURE_BASES = new ResourceLocation[]{
		id("textures/entity/ranchu/bases/base_grayscale.png"),
		id("textures/entity/ranchu/bases/base_azure.png"),
		id("textures/entity/ranchu/bases/base_onyx.png"),
		id("textures/entity/ranchu/bases/base_opal.png"),
		id("textures/entity/ranchu/bases/base_wild.png")
	};

	private static final ResourceLocation[] TEXTURE_PATTERNS = new ResourceLocation[]{
			pattern("bangle"),
			pattern("bearded"),
			pattern("belly_major"),
			pattern("belly_minor"),
			pattern("bobtail"),
			pattern("bones"),
			pattern("catfood"),
			pattern("checkered"),
			pattern("cheeks"),
			pattern("chevron"),
			pattern("dappled"),
			pattern("dots"),
			pattern("eye"),
			pattern("eyespot"),
			pattern("fins"),
			pattern("grass"),
			pattern("halo"),
			pattern("heart"),
			pattern("horntail"),
			pattern("horseshoe"),
			pattern("humpback_major"),
			pattern("humpback_minor"),
			pattern("jag"),
			pattern("keel"),
			pattern("leadbelly"),
			pattern("lefty"),
			pattern("lips"),
			pattern("mermaid"),
			pattern("moss"),
			pattern("noggin"),
			pattern("ornate"),
			pattern("pants"),
			pattern("patches_major"),
			pattern("patches_minor"),
			pattern("pectoral"),
			pattern("pelvic"),
			pattern("port"),
			pattern("potbelly"),
			pattern("quagga"),
			pattern("racing"),
			pattern("ram"),
			pattern("ribbed"),
			pattern("righty"),
			pattern("rimlight"),
			pattern("sclerotic"),
			pattern("shirt"),
			pattern("sidewind"),
			pattern("sleeves"),
			pattern("socks"),
			pattern("speckled"),
			pattern("spinal_major"),
			pattern("spinal_minor"),
			pattern("splattered"),
			pattern("splotches"),
			pattern("spotted"),
			pattern("starboard"),
			pattern("striped"),
			pattern("swirl"),
			pattern("trispot"),
			pattern("tympanum"),
			pattern("undertow"),
			pattern("welt"),
			pattern("zipper_major"),
			pattern("zipper_minor")
	};

	private static final ResourceLocation[] TEXTURE_COLOURS = new ResourceLocation[]{
			colour("apricot"),
			colour("azure"),
			colour("bitumen"),
			colour("charcoal"),
			colour("chocolate"),

			colour("copper"),
			colour("electrum"),
			colour("ginger"),
			colour("gold"),
			colour("ivory"),

			colour("onyx"),
			colour("opal"),
			colour("pale"),
			colour("pearl"),
			colour("plum"),

			colour("pyrite"),
			colour("redwood"),
			colour("rocksalt"),
			colour("rose"),
			colour("royal"),

			colour("ruby"),
			colour("rust"),
			colour("silver"),
			colour("syrup"),
			colour("wild")
	};
	private static ResourceLocation pattern(String in) {
		return id("textures/entity/ranchu/patterns/pattern_" + in + ".png");
	}
	private static ResourceLocation colour(String in) {
		return id("textures/entity/ranchu/colors/color_" + in + ".png");
	}

	public RanchuRenderer(EntityRendererProvider.Context context) {
		super(context, new RanchuModel<>(context.bakeLayer(ModModelLayers.RANCHU)), 0.35F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		int ego = entity.getVariant();
		int base = (2*2*2-1 & ego);
		int pat1 = (2*2*2*2*2*2-1 & ego >> 3);
		int pat2 = (2*2*2*2*2*2-1 & ego >> 3+6);
		int c1 = (2*2*2*2*2-1 & ego >> 3+6+6+5);
		int c2 = (2*2*2*2*2-1 & ego >> 3+6+6+5+5);
		int baseColour = (2*2*2*2*2-1 & ego >> 3+6+6);
		if(!TEXTURE_LOOKUP.containsKey(ego)) {
			TextureManager t = Minecraft.getInstance().getTextureManager();
			AbstractTexture bt = t.getTexture(TEXTURE_BASES[base]);
			NativeImage baseImage = loadImage(bt);
			if(base == 0) {
				AbstractTexture cbt = t.getTexture(TEXTURE_COLOURS[baseColour]);
				NativeImage cbImage = loadImage(cbt);

				colourImage(cbImage, baseImage);
			}

			AbstractTexture p1t = t.getTexture(TEXTURE_PATTERNS[pat1]);
			NativeImage p1Image = loadImage(p1t);
			AbstractTexture c1t = t.getTexture(TEXTURE_COLOURS[c1]);
			NativeImage c1Image = loadImage(c1t);

			colourImage(c1Image, p1Image);

			overlayImage(p1Image, baseImage);

			AbstractTexture p2t = t.getTexture(TEXTURE_PATTERNS[pat2]);
			NativeImage p2Image = loadImage(p2t);
			AbstractTexture c2t = t.getTexture(TEXTURE_COLOURS[c2]);
			NativeImage c2Image = loadImage(c2t);

			colourImage(c2Image, p2Image);

			overlayImage(p2Image, baseImage);

			ResourceLocation loc = id("ranchu_" + ego);
			t.register(loc, new DynamicTexture(baseImage));
			TEXTURE_LOOKUP.put(ego, loc);
		}
		return TEXTURE_LOOKUP.get(ego);
	}

	private static NativeImage loadImage(AbstractTexture cbt) {
		NativeImage out = null;
		if (cbt instanceof SimpleTexture s) {
			try {
				out = s.getTextureImage(Minecraft.getInstance().getResourceManager()).getImage();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return out;
	}

	private static final int[] colours = new int[5];
	private static void colourImage(NativeImage palette, NativeImage patient) {
		for(int i = 0; i < 5; i++) {
			colours[i] = palette.getPixelRGBA(i, 0);
		}

		for(int i = 0; i < patient.getHeight()*patient.getWidth(); i++) {
			int x = i % patient.getWidth();
			int y = i / patient.getWidth();
			int col = patient.getPixelRGBA(x, y);
			int b = (col >> 16) & 0xFF;
			int r = (col) & 0xFF;
			int g = (col >> 8) & 0xFF;
			int a = (col >> 24) & 0xff;
			if(a > 0) {
				int offs = getXOffset(r);
				int rgba = colours[offs];
				patient.setPixelRGBA(x, y, rgba);
			}
		}
	}

	private static void overlayImage(NativeImage overlay, NativeImage patient) {
		for(int i = 0; i < patient.getHeight()*patient.getWidth(); i++) {
			int x = i % patient.getWidth();
			int y = i / patient.getWidth();
			int col = overlay.getPixelRGBA(x, y);
//			int b = (col >> 16) & 0xFF;
//			int r = (col) & 0xFF;
//			int g = (col >> 8) & 0xFF;
			int a = (col >> 24) & 0xff;
			if(a > 0) {
				patient.setPixelRGBA(x, y, col);
			}
		}
	}

	public static int getXOffset(int r) {
		return switch(r) {
			case 241 -> 0;
			case 221 -> 1;
			case 193 -> 2;
			case 169 -> 3;
			default -> 4;
		};
	};

	@Override
	protected void setupRotations(T entity, PoseStack posestack, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupRotations(entity, posestack, ageInTicks, rotationYaw, partialTicks);
		float f = -Mth.cos(ageInTicks / 20f * Mth.TWO_PI) * Mth.HALF_PI/11f;
        posestack.mulPose(Axis.YP.rotation(f));
		if (entity.isAddedToWorld() && !entity.isInWater()) {
			posestack.translate(0.1F, 0.1F, -0.1F);
			posestack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

}
