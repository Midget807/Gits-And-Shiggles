package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ElfEntityModel extends EntityModel<ElfEntity> {
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public ElfEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.hat = this.head.getChild("hat");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -4.5F, 8.0F, 8.0F, 8.0F, new Dilation(-3.0F)), ModelTransform.pivot(0.0F, 17.0F, 0.0F));

		ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -2.6F, -2.5F, 4.0F, 2.0F, 4.0F, new Dilation(-0.8F))
		.uv(16, 24).cuboid(-1.0F, -2.5F, -1.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.1F))
		.uv(24, 19).cuboid(-1.0F, -3.4F, -1.5F, 2.0F, 1.0F, 2.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = hat.addChild("cube_r1", ModelPartBuilder.create().uv(8, 22).cuboid(-1.0F, -2.3F, -0.25F, 2.0F, 2.0F, 2.0F, new Dilation(-0.6F))
		.uv(16, 19).cuboid(-1.0F, -2.3F, -0.25F, 2.0F, 3.0F, 2.0F, new Dilation(-0.7F)), ModelTransform.of(0.0F, -4.0F, -1.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r2 = hat.addChild("cube_r2", ModelPartBuilder.create().uv(0, 22).cuboid(-1.0F, -1.6F, -0.5F, 2.0F, 2.0F, 2.0F, new Dilation(-0.5F)), ModelTransform.of(0.0F, -3.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(24, 22).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(26, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.1F))
		.uv(16, 16).cuboid(-1.5F, 2.4F, -1.5F, 3.0F, 1.0F, 2.0F, new Dilation(-0.4F)), ModelTransform.pivot(0.0F, 17.0F, 0.0F));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(12, 26).cuboid(1.0F, -3.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(24, 26).cuboid(1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.09F)), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(8, 26).cuboid(1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 27).cuboid(1.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.09F)), ModelTransform.pivot(-3.0F, 19.0F, 0.0F));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 26).cuboid(-2.0F, -5.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(24, 28).cuboid(-2.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.1F)), ModelTransform.pivot(2.0F, 25.0F, 0.0F));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(4, 26).cuboid(-1.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 27).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(ElfEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		head.render(matrices, vertexConsumer, light, overlay, color);
		body.render(matrices, vertexConsumer, light, overlay, color);
		left_arm.render(matrices, vertexConsumer, light, overlay, color);
		right_arm.render(matrices, vertexConsumer, light, overlay, color);
		left_leg.render(matrices, vertexConsumer, light, overlay, color);
		right_leg.render(matrices, vertexConsumer, light, overlay, color);
	}
}