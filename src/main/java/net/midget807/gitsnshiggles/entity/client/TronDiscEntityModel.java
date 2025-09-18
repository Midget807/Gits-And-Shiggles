package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.entity.TronDiscEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TronDiscEntityModel extends EntityModel<TronDiscEntity> {
	private final ModelPart glow;
	private final ModelPart inside;
	private final ModelPart outside;
	private final ModelPart n;
	private final ModelPart nnw;
	private final ModelPart nne;
	private final ModelPart w;
	private final ModelPart wsw;
	private final ModelPart wnw;
	private final ModelPart s;
	private final ModelPart sse;
	private final ModelPart ssw;
	private final ModelPart e;
	private final ModelPart ene;
	private final ModelPart ese;
	public TronDiscEntityModel(ModelPart root) {
		this.glow = root.getChild("glow");
		this.inside = root.getChild("inside");
		this.outside = root.getChild("outside");
		this.n = this.outside.getChild("n");
		this.nnw = this.outside.getChild("nnw");
		this.nne = this.outside.getChild("nne");
		this.w = this.outside.getChild("w");
		this.wsw = this.outside.getChild("wsw");
		this.wnw = this.outside.getChild("wnw");
		this.s = this.outside.getChild("s");
		this.sse = this.outside.getChild("sse");
		this.ssw = this.outside.getChild("ssw");
		this.e = this.outside.getChild("e");
		this.ene = this.outside.getChild("ene");
		this.ese = this.outside.getChild("ese");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData glow = modelPartData.addChild("glow", ModelPartBuilder.create().uv(0, 0).cuboid(4.0F, -2.0F, -6.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 26).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 13).cuboid(-6.0F, -2.0F, -6.0F, 2.0F, 1.0F, 12.0F, new Dilation(0.0F))
		.uv(28, 0).cuboid(-6.0F, -2.0F, 4.0F, 12.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = glow.addChild("cube_r1", ModelPartBuilder.create().uv(0, 38).cuboid(-3.0F, 0.0F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.0F, 5.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r2 = glow.addChild("cube_r2", ModelPartBuilder.create().uv(38, 40).cuboid(-4.0F, 0.0F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -2.0F, 5.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r3 = glow.addChild("cube_r3", ModelPartBuilder.create().uv(32, 35).cuboid(-3.0F, 0.0F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -2.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r4 = glow.addChild("cube_r4", ModelPartBuilder.create().uv(26, 40).cuboid(-4.0F, 0.0F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData inside = modelPartData.addChild("inside", ModelPartBuilder.create().uv(28, 3).cuboid(3.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.5F))
		.uv(12, 38).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.5F))
		.uv(28, 10).cuboid(-4.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.5F))
		.uv(12, 40).cuboid(-3.0F, -2.0F, 3.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData outside = modelPartData.addChild("outside", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData n = outside.addChild("n", ModelPartBuilder.create().uv(28, 20).cuboid(-3.0F, -2.0F, -8.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r5 = n.addChild("cube_r5", ModelPartBuilder.create().uv(28, 23).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -8.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r6 = n.addChild("cube_r6", ModelPartBuilder.create().uv(28, 17).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -8.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData nnw = outside.addChild("nnw", ModelPartBuilder.create().uv(10, 48).cuboid(0.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.0F, -8.0F, 0.0F, -0.3927F, 0.0F));

		ModelPartData cube_r7 = nnw.addChild("cube_r7", ModelPartBuilder.create().uv(6, 54).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.0F, 0.0F, 0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r8 = nnw.addChild("cube_r8", ModelPartBuilder.create().uv(52, 52).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		ModelPartData cube_r9 = nnw.addChild("cube_r9", ModelPartBuilder.create().uv(46, 52).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 0.0F, 0.0F, -0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r10 = nnw.addChild("cube_r10", ModelPartBuilder.create().uv(48, 27).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r11 = nnw.addChild("cube_r11", ModelPartBuilder.create().uv(40, 46).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -1.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData nne = outside.addChild("nne", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, -8.0F, 0.0F, 0.3927F, 0.0F));

		ModelPartData cube_r12 = nne.addChild("cube_r12", ModelPartBuilder.create().uv(54, 21).cuboid(-1.0F, -0.0237F, 0.2164F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, 0.0F, -0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r13 = nne.addChild("cube_r13", ModelPartBuilder.create().uv(54, 18).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		ModelPartData cube_r14 = nne.addChild("cube_r14", ModelPartBuilder.create().uv(54, 15).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, 0.0F, 0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r15 = nne.addChild("cube_r15", ModelPartBuilder.create().uv(20, 49).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r16 = nne.addChild("cube_r16", ModelPartBuilder.create().uv(48, 30).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData w = outside.addChild("w", ModelPartBuilder.create().uv(0, 29).cuboid(-3.0F, -2.0F, -8.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData cube_r17 = w.addChild("cube_r17", ModelPartBuilder.create().uv(16, 29).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -8.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r18 = w.addChild("cube_r18", ModelPartBuilder.create().uv(28, 26).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -8.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData wsw = outside.addChild("wsw", ModelPartBuilder.create().uv(42, 6).cuboid(-0.2898F, -2.0F, -8.5391F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.9635F, 0.0F));

		ModelPartData cube_r19 = wsw.addChild("cube_r19", ModelPartBuilder.create().uv(42, 49).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -2.0F, -8.5391F, 0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r20 = wsw.addChild("cube_r20", ModelPartBuilder.create().uv(36, 49).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -2.0F, -8.5391F, 0.0F, -0.3927F, 0.0F));

		ModelPartData cube_r21 = wsw.addChild("cube_r21", ModelPartBuilder.create().uv(30, 49).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -1.0F, -8.5391F, -0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r22 = wsw.addChild("cube_r22", ModelPartBuilder.create().uv(42, 9).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.7102F, -1.0F, -8.5391F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r23 = wsw.addChild("cube_r23", ModelPartBuilder.create().uv(42, 3).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.7102F, -2.0F, -8.5391F, 0.2182F, 0.0F, 0.0F));

		ModelPartData wnw = outside.addChild("wnw", ModelPartBuilder.create().uv(42, 12).cuboid(-2.7102F, -2.0F, -8.5391F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.1781F, 0.0F));

		ModelPartData cube_r24 = wnw.addChild("cube_r24", ModelPartBuilder.create().uv(50, 46).cuboid(-1.0F, -0.0237F, 0.2164F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, -0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r25 = wnw.addChild("cube_r25", ModelPartBuilder.create().uv(50, 38).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, 0.0F, 0.3927F, 0.0F));

		ModelPartData cube_r26 = wnw.addChild("cube_r26", ModelPartBuilder.create().uv(48, 49).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, 0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r27 = wnw.addChild("cube_r27", ModelPartBuilder.create().uv(0, 43).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.7102F, -1.0F, -8.5391F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r28 = wnw.addChild("cube_r28", ModelPartBuilder.create().uv(12, 42).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.7102F, -2.0F, -8.5391F, 0.2182F, 0.0F, 0.0F));

		ModelPartData s = outside.addChild("s", ModelPartBuilder.create().uv(16, 32).cuboid(-3.0F, -2.0F, -8.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData cube_r29 = s.addChild("cube_r29", ModelPartBuilder.create().uv(32, 29).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -8.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r30 = s.addChild("cube_r30", ModelPartBuilder.create().uv(0, 32).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -8.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData sse = outside.addChild("sse", ModelPartBuilder.create().uv(32, 43).cuboid(-0.2898F, -2.0F, -8.5391F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 2.7489F, 0.0F));

		ModelPartData cube_r31 = sse.addChild("cube_r31", ModelPartBuilder.create().uv(52, 3).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -2.0F, -8.5391F, 0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r32 = sse.addChild("cube_r32", ModelPartBuilder.create().uv(0, 52).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -2.0F, -8.5391F, 0.0F, -0.3927F, 0.0F));

		ModelPartData cube_r33 = sse.addChild("cube_r33", ModelPartBuilder.create().uv(10, 51).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -1.0F, -8.5391F, -0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r34 = sse.addChild("cube_r34", ModelPartBuilder.create().uv(42, 43).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.7102F, -1.0F, -8.5391F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r35 = sse.addChild("cube_r35", ModelPartBuilder.create().uv(22, 43).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.7102F, -2.0F, -8.5391F, 0.2182F, 0.0F, 0.0F));

		ModelPartData ssw = outside.addChild("ssw", ModelPartBuilder.create().uv(44, 18).cuboid(-2.7102F, -2.0F, -8.5391F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.7489F, 0.0F));

		ModelPartData cube_r36 = ssw.addChild("cube_r36", ModelPartBuilder.create().uv(52, 12).cuboid(-1.0F, -0.0237F, 0.2164F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, -0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r37 = ssw.addChild("cube_r37", ModelPartBuilder.create().uv(52, 9).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, 0.0F, 0.3927F, 0.0F));

		ModelPartData cube_r38 = ssw.addChild("cube_r38", ModelPartBuilder.create().uv(52, 6).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, 0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r39 = ssw.addChild("cube_r39", ModelPartBuilder.create().uv(44, 21).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.7102F, -1.0F, -8.5391F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r40 = ssw.addChild("cube_r40", ModelPartBuilder.create().uv(44, 15).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.7102F, -2.0F, -8.5391F, 0.2182F, 0.0F, 0.0F));

		ModelPartData e = outside.addChild("e", ModelPartBuilder.create().uv(0, 35).cuboid(-3.0F, -2.0F, -8.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData cube_r41 = e.addChild("cube_r41", ModelPartBuilder.create().uv(16, 35).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -8.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r42 = e.addChild("cube_r42", ModelPartBuilder.create().uv(32, 32).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -8.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData ene = outside.addChild("ene", ModelPartBuilder.create().uv(44, 35).cuboid(-0.2898F, -2.0F, -8.5391F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.1781F, 0.0F));

		ModelPartData cube_r43 = ene.addChild("cube_r43", ModelPartBuilder.create().uv(28, 52).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -2.0F, -8.5391F, 0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r44 = ene.addChild("cube_r44", ModelPartBuilder.create().uv(22, 52).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -2.0F, -8.5391F, 0.0F, -0.3927F, 0.0F));

		ModelPartData cube_r45 = ene.addChild("cube_r45", ModelPartBuilder.create().uv(16, 52).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.7102F, -1.0F, -8.5391F, -0.2182F, -0.3927F, 0.0F));

		ModelPartData cube_r46 = ene.addChild("cube_r46", ModelPartBuilder.create().uv(10, 45).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.7102F, -1.0F, -8.5391F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r47 = ene.addChild("cube_r47", ModelPartBuilder.create().uv(44, 24).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.7102F, -2.0F, -8.5391F, 0.2182F, 0.0F, 0.0F));

		ModelPartData ese = outside.addChild("ese", ModelPartBuilder.create().uv(20, 46).cuboid(-2.7102F, -2.0F, -8.5391F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.9635F, 0.0F));

		ModelPartData cube_r48 = ese.addChild("cube_r48", ModelPartBuilder.create().uv(52, 41).cuboid(-1.0F, -0.0237F, 0.2164F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, -0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r49 = ese.addChild("cube_r49", ModelPartBuilder.create().uv(40, 52).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, 0.0F, 0.3927F, 0.0F));

		ModelPartData cube_r50 = ese.addChild("cube_r50", ModelPartBuilder.create().uv(34, 52).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7102F, -2.0F, -8.5391F, 0.2182F, 0.3927F, 0.0F));

		ModelPartData cube_r51 = ese.addChild("cube_r51", ModelPartBuilder.create().uv(30, 46).cuboid(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.7102F, -1.0F, -8.5391F, -0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r52 = ese.addChild("cube_r52", ModelPartBuilder.create().uv(0, 46).cuboid(-1.0F, 0.0F, 0.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.7102F, -2.0F, -8.5391F, 0.2182F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(TronDiscEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		glow.render(matrices, vertexConsumer, light, overlay, color);
		inside.render(matrices, vertexConsumer, light, overlay, color);
		outside.render(matrices, vertexConsumer, light, overlay, color);
	}
}