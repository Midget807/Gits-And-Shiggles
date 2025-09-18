package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.TronDiscEntity;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
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
    public static final Identifier TEXTURE = GitsAndShigglesMain.id("textures/entity/tron_disc.png");
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
        //matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
                vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false
        );
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(TronDiscEntity entity) {
        return TEXTURE;
    }
}
