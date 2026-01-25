package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.cca.KatanaBlockingComponent;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Unique
    private static PlayerEntity player;

    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"))
    private void gitsnshiggles$storeEntity(LivingEntity entity, ItemStack item, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
        if (item.isOf(ModItems.KATANA) && entity instanceof PlayerEntity playerEntity) {
            ItemRendererMixin.player = playerEntity;
        }
    }

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel gitsnshiggles$renderDifferentModel(BakedModel value, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (stack.isOf(ModItems.INVERTED_TRIDENT) && (renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("inverted_trident")));
        }
        if (stack.isOf(ModItems.KATANA) && (renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("katana")));
        }
        if (stack.isOf(ModItems.KATANA) && renderMode != ModelTransformationMode.FIXED && renderMode != ModelTransformationMode.GROUND && renderMode != ModelTransformationMode.GUI) {
            if (player != null) {
                KatanaBlockingComponent katanaBlockingComponent = KatanaBlockingComponent.get(player);
                if (katanaBlockingComponent.getBool()) {
                    return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("katana_blocking")));
                } else {
                    return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("katana_handheld")));
                }
            } else {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("katana_handheld")));
            }
        }
        return value;
    }

    @ModifyVariable(method = "getModel", at = @At("STORE"), ordinal = 1)
    public BakedModel gitsnshiggles$getHeldItemModel(BakedModel value, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isOf(ModItems.INVERTED_TRIDENT)) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("inverted_trident_handheld")));
        }
        if (stack.isOf(ModItems.KATANA)) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("katana_handheld")));
        }
        return value;
    }
}
