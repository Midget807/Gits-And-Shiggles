package net.midget807.gitsnshiggles.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class TronDiscEntityModel extends EntityModel<Entity> {
	private final ModelPart bb_main;
	public TronDiscEntityModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 14).cuboid(-3.0F, -1.0F, -8.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 14).cuboid(3.0F, -1.0F, -7.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-5.0F, -1.0F, -7.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 18).cuboid(5.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 18).cuboid(-6.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 2).cuboid(6.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 5).cuboid(-7.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(7.0F, -1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 7).cuboid(-8.0F, -1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F))
		.uv(14, 0).cuboid(-3.0F, -1.0F, 7.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 16).cuboid(3.0F, -1.0F, 6.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 16).cuboid(-5.0F, -1.0F, 6.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 18).cuboid(5.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 18).cuboid(-6.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 8).cuboid(6.0F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 11).cuboid(-7.0F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		bb_main.render(matrices, vertexConsumer, light, overlay, color);
	}
}