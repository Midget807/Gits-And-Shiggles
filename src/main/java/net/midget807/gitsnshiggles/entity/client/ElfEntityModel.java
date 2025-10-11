package net.midget807.gitsnshiggles.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;

public class ElfEntityModel extends EntityModel<ElfEntity> implements ModelWithArms, ModelWithHead {
	public final ModelPart hat;
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart leftArm;
	public final ModelPart rightArm;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;
	public ArmPose leftArmPose = ArmPose.EMPTY;
	public ArmPose rightArmPose = ArmPose.EMPTY;
	public boolean sneaking;
	public float leaningPitch;

	public ElfEntityModel(ModelPart root) {
        this.hat = root.getChild("hat");
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -40.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 28.0F, 0.0F));

		ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 28).cuboid(-4.0F, -39.5F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.5F))
				.uv(0, 16).cuboid(-4.0F, -43.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F))
				.uv(48, 24).cuboid(-3.0F, -45.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 28.0F, 0.0F));

		ModelPartData ball_r1 = hat.addChild("ball_r1", ModelPartBuilder.create().uv(56, 8).cuboid(-4.0F, -61.5F, -31.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 57).cuboid(-3.5F, -57.5F, -30.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 15.0F, -2.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData middle_top_r1 = hat.addChild("middle_top_r1", ModelPartBuilder.create().uv(32, 56).cuboid(-4.0F, -59.5F, -12.5F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 13.0F, -2.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
				.uv(48, 32).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 1.0F, 4.0F, new Dilation(0.1F))
				.uv(48, 16).cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 22.0F, 0.0F));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(32, 36).cuboid(-4.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
				.uv(48, 37).cuboid(-4.0F, -16.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(32, 16).cuboid(0.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
				.uv(48, 45).cuboid(0.0F, -16.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(0, 37).cuboid(-4.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
				.uv(48, 53).cuboid(-4.0F, -16.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.1F)), ModelTransform.pivot(-4.0F, 28.0F, 0.0F));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(16, 37).cuboid(0.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
				.uv(56, 0).cuboid(0.0F, -16.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.1F)), ModelTransform.pivot(4.0F, 28.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void animateModel(ElfEntity livingEntity, float f, float g, float h) {
		this.leaningPitch = livingEntity.getLeaningPitch(h);
		super.animateModel(livingEntity, f, g, h);
	}

	@Override
	public void setAngles(ElfEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean isElytraFlying = entity.getFallFlyingTicks() > 4;
		boolean isSwimmingPose = entity.isInSwimmingPose();

		this.head.yaw = netHeadYaw * (float) (Math.PI / 4);

		if (isElytraFlying) {
			this.head.pitch = (float) (-Math.PI / 4);
		} else if (this.leaningPitch > 0.0f) {
			if (isSwimmingPose) {
				this.head.pitch = this.lerpAngle(this.leaningPitch, this.head.pitch, (float) (-Math.PI / 4));
			} else {
				this.head.pitch = this.lerpAngle(this.leaningPitch, this.head.pitch, headPitch * (float) (Math.PI / 180.0));
			}
		} else {
			this.head.pitch = headPitch * (float) (Math.PI / 180.0);
		}

		this.body.yaw = 0.0f;
		float k = 1.0f;
		if (isElytraFlying) {
			k = (float) entity.getVelocity().lengthSquared();
			k /= 0.2f;
			k *= k * k;
		}
		if (k < 1.0f) {
			k = 1.0f;
		}
		this.leftArm.pivotY = 28.0f;

		this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / k;
		this.leftArm.pitch = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / k;
		this.rightArm.roll = 0.0F;
		this.leftArm.roll = 0.0F;

		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / k;
		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / k;

		this.animateArms(entity, ageInTicks);

		this.hat.copyTransform(this.head);
	}

	private void positionRightArm(ElfEntity entity) {
		switch (this.rightArmPose) {
			case EMPTY:
				this.rightArm.yaw = 0.0F;
				break;
			case ITEM:
				this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) (Math.PI / 10);
				this.rightArm.yaw = 0.0F;
				break;
			case BLOCK:
				this.positionBlockingArm(this.rightArm, true);
				break;
			case BOW_AND_ARROW:
				this.rightArm.yaw = -0.1F + this.head.yaw;
				this.leftArm.yaw = 0.1F + this.head.yaw + 0.4F;
				this.rightArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
				this.leftArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
				break;
			case THROW_SPEAR:
				this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) Math.PI;
				this.rightArm.yaw = 0.0F;
				break;
			case CROSSBOW_CHARGE:
				CrossbowPosing.charge(this.rightArm, this.leftArm, entity, true);
				break;
			case CROSSBOW_HOLD:
				CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
				break;
			case SPYGLASS:
				this.rightArm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622F - (entity.isInSneakingPose() ? (float) (Math.PI / 12) : 0.0F), -2.4F, 3.3F);
				this.rightArm.yaw = this.head.yaw - (float) (Math.PI / 12);
				break;
			case TOOT_HORN:
				this.rightArm.pitch = MathHelper.clamp(this.head.pitch, -1.2F, 1.2F) - 1.4835298F;
				this.rightArm.yaw = this.head.yaw - (float) (Math.PI / 6);
				break;
			case BRUSH:
				this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) (Math.PI / 5);
				this.rightArm.yaw = 0.0F;
		}
	}

	private void positionLeftArm(ElfEntity entity) {
		switch (this.leftArmPose) {
			case EMPTY:
				this.leftArm.yaw = 0.0F;
				break;
			case ITEM:
				this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) (Math.PI / 10);
				this.leftArm.yaw = 0.0F;
				break;
			case BLOCK:
				this.positionBlockingArm(this.leftArm, false);
				break;
			case BOW_AND_ARROW:
				this.rightArm.yaw = -0.1F + this.head.yaw - 0.4F;
				this.leftArm.yaw = 0.1F + this.head.yaw;
				this.rightArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
				this.leftArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
				break;
			case THROW_SPEAR:
				this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) Math.PI;
				this.leftArm.yaw = 0.0F;
				break;
			case CROSSBOW_CHARGE:
				CrossbowPosing.charge(this.rightArm, this.leftArm, entity, false);
				break;
			case CROSSBOW_HOLD:
				CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, false);
				break;
			case SPYGLASS:
				this.leftArm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622F - (entity.isInSneakingPose() ? (float) (Math.PI / 12) : 0.0F), -2.4F, 3.3F);
				this.leftArm.yaw = this.head.yaw + (float) (Math.PI / 12);
				break;
			case TOOT_HORN:
				this.leftArm.pitch = MathHelper.clamp(this.head.pitch, -1.2F, 1.2F) - 1.4835298F;
				this.leftArm.yaw = this.head.yaw + (float) (Math.PI / 6);
				break;
			case BRUSH:
				this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) (Math.PI / 5);
				this.leftArm.yaw = 0.0F;
		}
	}

	private void positionBlockingArm(ModelPart arm, boolean rightArm) {
		arm.pitch = arm.pitch * 0.5F - 0.9424779F + MathHelper.clamp(this.head.pitch, (float) (-Math.PI * 4.0 / 9.0), 0.43633232F);
		arm.yaw = (rightArm ? -30.0F : 30.0F) * (float) (Math.PI / 180.0) + MathHelper.clamp(this.head.yaw, (float) (-Math.PI / 6), (float) (Math.PI / 6));
	}

	protected void animateArms(ElfEntity entity, float animationProgress) {
		if (!(this.handSwingProgress <= 0.0F)) {
			Arm arm = this.getPreferredArm(entity);
			ModelPart modelPart = this.getArm(arm);
			float f = this.handSwingProgress;
			this.body.yaw = MathHelper.sin(MathHelper.sqrt(f) * (float) (Math.PI * 2)) * 0.2F;
			if (arm == Arm.LEFT) {
				this.body.yaw *= -1.0F;
			}

			this.rightArm.pivotZ = MathHelper.sin(this.body.yaw) * 5.0F;
			this.rightArm.pivotX = -MathHelper.cos(this.body.yaw) * 5.0F;
			this.leftArm.pivotZ = -MathHelper.sin(this.body.yaw) * 5.0F;
			this.leftArm.pivotX = MathHelper.cos(this.body.yaw) * 5.0F;
			this.rightArm.yaw = this.rightArm.yaw + this.body.yaw;
			this.leftArm.yaw = this.leftArm.yaw + this.body.yaw;
			this.leftArm.pitch = this.leftArm.pitch + this.body.yaw;
			f = 1.0F - this.handSwingProgress;
			f *= f;
			f *= f;
			f = 1.0F - f;
			float g = MathHelper.sin(f * (float) Math.PI);
			float h = MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -(this.head.pitch - 0.7F) * 0.75F;
			modelPart.pitch -= g * 1.2F + h;
			modelPart.yaw = modelPart.yaw + this.body.yaw * 2.0F;
			modelPart.roll = modelPart.roll + MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -0.4F;
		}
	}

	protected float lerpAngle(float angleOne, float angleTwo, float magnitude) {
		float f = (magnitude - angleTwo) % (float) (Math.PI * 2);
		if (f < (float) -Math.PI) {
			f += (float) (Math.PI * 2);
		}

		if (f >= (float) Math.PI) {
			f -= (float) (Math.PI * 2);
		}

		return angleTwo + angleOne * f;
	}

	private float method_2807(float f) {
		return -65.0F * f + f * f;
	}

	public void copyBipedStateTo(ElfEntityModel model) {
		super.copyStateTo(model);
		model.leftArmPose = this.leftArmPose;
		model.rightArmPose = this.rightArmPose;
		model.sneaking = this.sneaking;
		model.head.copyTransform(this.head);
		model.hat.copyTransform(this.hat);
		model.body.copyTransform(this.body);
		model.rightArm.copyTransform(this.rightArm);
		model.leftArm.copyTransform(this.leftArm);
		model.rightLeg.copyTransform(this.rightLeg);
		model.leftLeg.copyTransform(this.leftLeg);
	}

	public void setVisible(boolean visible) {
		this.head.visible = visible;
		this.hat.visible = visible;
		this.body.visible = visible;
		this.rightArm.visible = visible;
		this.leftArm.visible = visible;
		this.rightLeg.visible = visible;
		this.leftLeg.visible = visible;
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.getArm(arm).rotate(matrices);
	}

	protected ModelPart getArm(Arm arm) {
		return arm == Arm.LEFT ? this.leftArm : this.rightArm;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	private Arm getPreferredArm(ElfEntity entity) {
		Arm arm = entity.getMainArm();
		return entity.preferredHand == Hand.MAIN_HAND ? arm : arm.getOpposite();
	}

	@Environment(EnvType.CLIENT)
	public static enum ArmPose {
		EMPTY(false),
		ITEM(false),
		BLOCK(false),
		BOW_AND_ARROW(true),
		THROW_SPEAR(false),
		CROSSBOW_CHARGE(true),
		CROSSBOW_HOLD(true),
		SPYGLASS(false),
		TOOT_HORN(false),
		BRUSH(false);

		private final boolean twoHanded;

		private ArmPose(final boolean twoHanded) {
			this.twoHanded = twoHanded;
		}

		public boolean isTwoHanded() {
			return this.twoHanded;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		matrices.push();
		matrices.scale(0.4f, 0.4f, 0.4f);
		matrices.translate(0.0f, 2.25f, 0.0f);
		hat.render(matrices, vertexConsumer, light, overlay, color);
		head.render(matrices, vertexConsumer, light, overlay, color);
		matrices.pop();

		matrices.push();
		matrices.multiply(new Quaternionf().rotateZ((float) Math.PI));
		matrices.translate(0.0f, -1.5f, 0.0f);
		matrices.scale(0.4f, 0.4f, 0.4f);
		body.render(matrices, vertexConsumer, light, overlay, color);
		leftArm.render(matrices, vertexConsumer, light, overlay, color);
		rightArm.render(matrices, vertexConsumer, light, overlay, color);
		leftLeg.render(matrices, vertexConsumer, light, overlay, color);
		rightLeg.render(matrices, vertexConsumer, light, overlay, color);
		matrices.pop();
	}
}