package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.cca.InfinityGauntletComponent;
import net.midget807.gitsnshiggles.util.inject.RealityStoneTransform;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class RealityStoneShieldFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private final A model;


    public RealityStoneShieldFeatureRenderer(FeatureRendererContext<T, M> context, A model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        this.render(matrices, vertexConsumers, entity, light, this.model);
    }

    private void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, int light, A model) {
        this.getContextModel().copyBipedStateTo(model);
        this.setVisible(model, entity);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(RealityStoneShieldEntityModel.TEXTURE));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
        //render(this.getContextModel(), model,);
    }


    public void setVisible(A model, T entity) {
        if (entity instanceof PlayerEntity player) {
            InfinityGauntletComponent infinityGauntletComponent = InfinityGauntletComponent.get(player);
            model.setVisible(infinityGauntletComponent.getDoubleBool1());
        }
    }
}
