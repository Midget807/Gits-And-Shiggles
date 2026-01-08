package net.midget807.gitsnshiggles.item.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class SantaHatModel<T extends LivingEntity> extends BipedEntityModel<T> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(GitsAndShigglesMain.id("santa_hat"), "main");
    public final ModelPart hat;
	public final ModelPart tilt1;
	public final ModelPart tilt2;
	public final ModelPart tilt3;

	public SantaHatModel(ModelPart root) {
        super(root);
		this.hat = head.getChild("hat");
		this.tilt1 = this.hat.getChild("tilt1");
		this.tilt2 = this.tilt1.getChild("tilt2");
		this.tilt3 = this.tilt2.getChild("tilt3");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData modelPartData = modelData.getRoot().getChild(EntityModelPartNames.HEAD);
		ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -9.0F, -5.0F, 10.0F, 3.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 13).cuboid(-4.0F, -12.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 24.0F, 0.0F));

		ModelPartData tilt1 = hat.addChild("tilt1", ModelPartBuilder.create().uv(0, 25).cuboid(1.0F, -8.0F, -3.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -7.0F, -2.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData tilt2 = tilt1.addChild("tilt2", ModelPartBuilder.create().uv(26, 25).cuboid(2.0F, -8.0F, -3.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData tilt3 = tilt2.addChild("tilt3", ModelPartBuilder.create().uv(26, 35).cuboid(3.0F, -3.0F, -6.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(32, 13).cuboid(2.0F, -7.0F, -6.5F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 3.0F, -0.5236F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	    super.setAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        hat.render(matrices, vertexConsumer, light, overlay, color);
	}
}