package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModels models;

    @Shadow public abstract ItemModels getModels();

    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private BuiltinModelItemRenderer builtinModelItemRenderer;

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel gitsnshiggles$renderDifferentModel(BakedModel value, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (stack.isOf(ModItems.INVERTED_TRIDENT) && (renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("inverted_trident")));
        }
        if (stack.isOf(ModItems.KATANA) && (renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(GitsAndShigglesMain.id("katana")));
        }
        if (stack.isOf(ModItems.KATANA) && renderMode != ModelTransformationMode.FIXED && renderMode != ModelTransformationMode.GROUND && renderMode != ModelTransformationMode.GUI) {
            Boolean blockingComponent = stack.getOrDefault(ModDataComponentTypes.BLOCKING, false);
            if (blockingComponent != null) {
                if (blockingComponent) {
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
