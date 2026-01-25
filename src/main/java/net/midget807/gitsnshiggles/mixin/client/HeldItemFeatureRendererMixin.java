package net.midget807.gitsnshiggles.mixin.client;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    protected void gitsnshiggles$twoHandedHolding(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ItemStack main = entity.getMainHandStack();
        boolean mainArm = entity.getMainArm() == arm;
        if (main.isIn(ModItemTagProvider.BIG_ITEM_RENDERING) && !mainArm) {
            matrices.push();
            ((ModelWithArms)this.getContextModel()).setArmAngle(arm, matrices);
            matrices.pop();
            ci.cancel();
        } else {
            boolean leftArm = arm == Arm.LEFT;
            /*if (stack.isOf(ModItems.TRON_DISC) && entity instanceof PlayerEntity player) {
                matrices.push();
                ((ModelWithArms)this.getContextModel()).setArmAngle(arm, matrices);
                matrices.multiply(new Quaternionf().rotationX((float) (-Math.PI / 2)));
                matrices.multiply(new Quaternionf().rotationY((float) (Math.PI / 2)));
                matrices.translate((leftArm ? -1.0f : 1.0f) / 12.0f, 0.135f, -0.625f);
                MinecraftClient.getInstance().getItemRenderer().renderItem(entity, stack, transformationMode, leftArm, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, entity.getId() + transformationMode.ordinal());
                matrices.pop();
                ci.cancel();
            }*/
        }
    }


}
