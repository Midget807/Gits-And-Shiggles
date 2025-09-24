package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.TronDiscEntity;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.midget807.gitsnshiggles.util.ColoredItemUtil;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class TronDiscEntityRenderer extends EntityRenderer<TronDiscEntity> {
    public static final Identifier TEXTURE = GitsAndShigglesMain.id("textures/entity/tron_disc_white.png");
    private final TronDiscEntityModel model;

    public TronDiscEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new TronDiscEntityModel(ctx.getPart(ModEntityModelLayers.TRON_DISC_MODEL_LAYER));
    }

    @Override
    public void render(TronDiscEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0f));
        matrices.translate(0.0f, -1.25f, 0.0f);
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
                vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false
        );
        VertexConsumer vertexConsumer2 = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity)));
        vertexConsumer2.color(0x0000FF);
        this.model.render(matrices, vertexConsumer2, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(TronDiscEntity entity) {
        ColoredItemUtil.Colors colors = entity.getEnumColor();
        colors = colors == null ? ColoredItemUtil.Colors.WHITE : colors;
        return GitsAndShigglesMain.id("textures/entity/tron_disc_" + colors.getColor() + ".png");
    }
}
