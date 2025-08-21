package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.item.LightsaberItem;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModels models;

    @Shadow public abstract ItemModels getModels();

    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private BuiltinModelItemRenderer builtinModelItemRenderer;

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel gitsnshiggles$renderDifferentModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {
        if (stack.isOf(ModItems.LIGHTSABER) && (renderMode == ModelTransformationMode.NONE || renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND || renderMode == ModelTransformationMode.HEAD || renderMode == ModelTransformationMode.GROUND)) {

        }
        return value;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V", shift = At.Shift.BEFORE))
    private void gitsnshiggles$colorShit(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci, @Local VertexConsumer vertexConsumer) {
        if (stack.getItem() instanceof LightsaberItem ) {
            int hexColor = ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536));
            float red = (hexColor >> 16 & 255) / 255F;
            float green = (hexColor >> 8 & 255) / 255F;
            float blue = (hexColor & 255) / 255F;
            vertexConsumer.color(hexColor);
        }
    }

    @ModifyVariable(method = "getModel", at = @At("STORE"), ordinal = 1)
    public BakedModel gitsnshiggles$getHeldItemModel(BakedModel value, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isOf(ModItems.LIGHTSABER)) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("lightsaber")));
        }
        return value;
    }
}
