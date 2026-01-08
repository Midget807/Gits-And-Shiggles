package net.midget807.gitsnshiggles.item.client;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SantaHatRenderer implements ArmorRenderer {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final Identifier mainTexture = ModTextureIds.SANTA_HAT_TEXTURE;
    private SantaHatModel<LivingEntity> model;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (model == null) {
            model = new SantaHatModel<>(client.getEntityModelLoader().getModelPart(SantaHatModel.MODEL_LAYER));
        }

        contextModel.copyBipedStateTo(model);
        model.hat.copyTransform(contextModel.head);
        model.setVisible(true);
        model.hat.visible = slot == EquipmentSlot.HEAD;

        model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(mainTexture), true), light, OverlayTexture.DEFAULT_UV);
    }
}
