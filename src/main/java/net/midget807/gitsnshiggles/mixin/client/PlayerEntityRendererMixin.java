package net.midget807.gitsnshiggles.mixin.client;

import net.midget807.gitsnshiggles.entity.client.RealityStoneShieldEntityModel;
import net.midget807.gitsnshiggles.entity.client.RealityStoneShieldFeatureRenderer;
import net.midget807.gitsnshiggles.item.WizardRobesItem;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 0))
    private void gitsnshiggles$addsCustomFeatures(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(
                new RealityStoneShieldFeatureRenderer<>(
                        this,
                        new RealityStoneShieldEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(slim ? ModEntityModelLayers.REALITY_STONE_SHIELD_SLIM : ModEntityModelLayers.REALITY_STONE_SHIELD))
                )
        );
    }

    @Inject(method = "renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$cancelNameRenderer(AbstractClientPlayerEntity abstractClientPlayerEntity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, float f, CallbackInfo ci) {
        if (WizardRobesItem.hasFullSuitOfArmor(abstractClientPlayerEntity)) {
            ci.cancel();
        }
    }
    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void gitsnshiggles$customArmPoses(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(ModItems.RAILGUN) && !player.handSwinging) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
        }
        MinecraftClient client = MinecraftClient.getInstance();
    }
}
