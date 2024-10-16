package codyhuh.babyfat.client.model;

import codyhuh.babyfat.common.entities.Ranchu;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class RanchuModel<T extends Ranchu> extends AgeableListModel<T> {
	private final ModelPart body;
	private final ModelPart tailBase;
	private final ModelPart tailFins;
	private final ModelPart tailbuttonleft;
	private final ModelPart tailbuttonright;
	private final ModelPart tailfanleft;
	private final ModelPart tailfanright;
	private final ModelPart tailphoenixleft;
	private final ModelPart tailphoenixright;
	private final ModelPart tailazureleft;
	private final ModelPart tailazureright;
	private final ModelPart tailonyxleft;
	private final ModelPart tailonyxright;
	private final ModelPart tailopalleft;
	private final ModelPart tailopalright;
	private final ModelPart tailopalsideleft;
	private final ModelPart tailopalsideright;
	private final ModelPart headtop;
	private final ModelPart headbottom;
	private final ModelPart pectoralfinleft;
	private final ModelPart pectoralfinright;

	public RanchuModel(ModelPart root) {
		this.body = root.getChild("body");
		this.tailBase = body.getChild("tailBase");
		this.tailFins = tailBase.getChild("tailFins");
		this.tailbuttonleft = tailFins.getChild("tb1").getChild("tailbuttonleft");
		this.tailbuttonright = tailFins.getChild("tb2").getChild("tailbuttonright");
		this.tailfanleft = tailFins.getChild("tb3").getChild("tailfanleft");
		this.tailfanright = tailFins.getChild("tb4").getChild("tailfanright");
		this.tailphoenixleft = tailFins.getChild("tb5").getChild("tailphoenixleft");
		this.tailphoenixright = tailFins.getChild("tb6").getChild("tailphoenixright");
		this.tailazureleft = tailFins.getChild("tb7").getChild("tailazureleft");
		this.tailazureright = tailFins.getChild("tb8").getChild("tailazureright");
		this.tailonyxleft = tailFins.getChild("tb9").getChild("tailonyxleft");
		this.tailonyxright = tailFins.getChild("tb10").getChild("tailonyxright");
		this.tailopalleft = tailFins.getChild("tb11").getChild("tailopalleft");
		this.tailopalright = tailFins.getChild("tb12").getChild("tailopalright");
		this.tailopalsideleft = tailFins.getChild("tb13").getChild("tailopalsideleft");
		this.tailopalsideright = tailFins.getChild("tb14").getChild("tailopalsideright");
		this.headtop = body.getChild("headtop");
		this.headbottom = headtop.getChild("headbottom");
		this.pectoralfinleft = body.getChild("pectoralfinleft");
		this.pectoralfinright = body.getChild("pectoralfinright");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition tailBase = body.addOrReplaceChild("tailBase", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.5F, 2.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition tailFins = tailBase.addOrReplaceChild("tailFins", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0, 0));

		PartDefinition tailbuttonleft = tailFins.addOrReplaceChild("tb1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailbuttonleft", CubeListBuilder.create().texOffs(13, 21).addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailbuttonright = tailFins.addOrReplaceChild("tb2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailbuttonright", CubeListBuilder.create().texOffs(-9, 21).mirror().addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition tailfanleft = tailFins.addOrReplaceChild("tb3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailfanleft", CubeListBuilder.create().texOffs(13, 31).addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailfanright = tailFins.addOrReplaceChild("tb4", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailfanright", CubeListBuilder.create().texOffs(-9, 31).mirror().addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition tailphoenixleft = tailFins.addOrReplaceChild("tb5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailphoenixleft", CubeListBuilder.create().texOffs(13, 41).addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailphoenixright = tailFins.addOrReplaceChild("tb6", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailphoenixright", CubeListBuilder.create().texOffs(-9, 41).mirror().addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition tailazureleft = tailFins.addOrReplaceChild("tb7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailazureleft", CubeListBuilder.create().texOffs(13, 51).addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailazureright = tailFins.addOrReplaceChild("tb8", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailazureright", CubeListBuilder.create().texOffs(-9, 51).mirror().addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition tailonyxleft = tailFins.addOrReplaceChild("tb9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailonyxleft", CubeListBuilder.create().texOffs(11, 60).addBox(-11.0F, 0.0F, -4.0F, 11.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailonyxright = tailFins.addOrReplaceChild("tb10", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailonyxright", CubeListBuilder.create().texOffs(-9, 60).mirror().addBox(-11.0F, 0.0F, -4.0F, 11.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition tailopalleft = tailFins.addOrReplaceChild("tb11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailopalleft", CubeListBuilder.create().texOffs(13, 71).addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailopalright = tailFins.addOrReplaceChild("tb12", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailopalright", CubeListBuilder.create().texOffs(-9, 71).mirror().addBox(-10.0F, 0.0F, -4.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition tailopalsideleft = tailFins.addOrReplaceChild("tb13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailopalsideleft", CubeListBuilder.create().texOffs(22, 80).addBox(-10.0F, -1.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition tailopalsideright = tailFins.addOrReplaceChild("tb14", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 1.0F, 1.5F, 0.0F, 1.5708F, -1.5708F)).addOrReplaceChild("tailopalsideright", CubeListBuilder.create().texOffs(0, 80).mirror().addBox(-10.0F, -5.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		PartDefinition headtop = body.addOrReplaceChild("headtop", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.5F));

		PartDefinition headbottom = headtop.addOrReplaceChild("headbottom", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

		PartDefinition pectoralfinleft = body.addOrReplaceChild("pectoralfinleft", CubeListBuilder.create(), PartPose.offset(2.5F, 1.0F, -1.0F));

		pectoralfinleft.addOrReplaceChild("Box_r2", CubeListBuilder.create().texOffs(13, 12).mirror().addBox(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition pectoralfinright = body.addOrReplaceChild("pectoralfinright", CubeListBuilder.create(), PartPose.offset(-2.5F, 1.0F, -1.0F));

		PartDefinition Box_r1 = pectoralfinright.addOrReplaceChild("Box_r1", CubeListBuilder.create().texOffs(17, 12).mirror().addBox(-2.0F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		return LayerDefinition.create(meshdefinition, 64, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.tailbuttonleft.visible = false;
		this.tailbuttonright.visible = false;
		this.tailfanleft.visible = false;
		this.tailfanright.visible = false;
		this.tailphoenixleft.visible = false;
		this.tailphoenixright.visible = false;
		this.tailazureleft.visible = false;
		this.tailazureright.visible = false;
		this.tailonyxleft.visible = false;
		this.tailonyxright.visible = false;
		this.tailopalleft.visible = false;
		this.tailopalright.visible = false;
		this.tailopalsideleft.visible = false;
		this.tailopalsideright.visible = false;
		ModelPart[] tgtr = chooseTargetRight(entityIn.getTail());
		ModelPart[] tgtl = chooseTargetLeft(entityIn.getTail());
		for(ModelPart p : tgtr) {
			p.visible = true;
		}
		for(ModelPart p : tgtl) {
			p.visible = true;
		}
		float time = ageInTicks % (20f * Mth.TWO_PI * 4f);
		time /= 20f;
		if (entityIn.isInWater()) {
			this.body.xRot = headPitch * ((float) Math.PI / 180F);
			this.body.yRot = netHeadYaw * ((float) Math.PI / 180F);
			this.tailBase.yRot = Mth.cos(time * Mth.TWO_PI) * Mth.HALF_PI/6f;
			float m = Mth.sin(time * Mth.TWO_PI * 2f + 1);
			m = (m * m)-0.5f;
			this.pectoralfinright.zRot = m * 2 * Mth.HALF_PI/6f;
			this.pectoralfinleft.zRot = -m * 2 * Mth.HALF_PI/6f;
			for(ModelPart p : tgtr) {
				p.yRot = 0;
				p.xRot = 0;
				p.zRot = -Mth.sin(time * Mth.TWO_PI) * Mth.HALF_PI/6f;
			}
			for(ModelPart p : tgtl) {
				p.yRot = 0;
				p.xRot = 0;
				p.zRot = -Mth.sin(time * Mth.TWO_PI) * Mth.HALF_PI/6f;
			}
		}
	}

	private ModelPart[] chooseTargetRight(int var) {
		return switch(var) {
			case 0 ->  new ModelPart[]{tailbuttonright};
			default -> new ModelPart[]{tailfanright};
			case 2 ->  new ModelPart[]{tailphoenixright};
			case 3 ->  new ModelPart[]{tailazureright};
			case 4 ->  new ModelPart[]{tailonyxright};
			case 5 ->  new ModelPart[]{tailopalright, tailopalsideright};
		};
	}

	private ModelPart[] chooseTargetLeft(int var) {
		return switch(var) {
			case 0 ->  new ModelPart[]{tailbuttonleft};
			default -> new ModelPart[]{tailfanleft};
			case 2 ->  new ModelPart[]{tailphoenixleft};
			case 3 ->  new ModelPart[]{tailazureleft};
			case 4 ->  new ModelPart[]{tailonyxleft};
			case 5 ->  new ModelPart[]{tailopalleft, tailopalsideleft};
		};
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return Collections.emptyList();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body);
	}


	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
