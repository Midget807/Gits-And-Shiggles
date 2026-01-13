package net.midget807.gitsnshiggles.item.client;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class SantaHatRenderer implements ArmorRenderer {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final Identifier mainTexture = ModTextureIds.SANTA_HAT_TEXTURE;
    private final Identifier leatherTexture = ModTextureIds.LEATHER_SANTA_HAT_TEXTURE;
    private final Identifier overlayTexture = ModTextureIds.SANTA_HAT_OVERLAY_TEXTURE;
    private SantaHatModel<LivingEntity> model;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (model == null) {
            model = new SantaHatModel<>(client.getEntityModelLoader().getModelPart(SantaHatModel.MODEL_LAYER));
        }
        boolean isLeather = entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.LEATHER_SANTA_HAT);

        contextModel.copyBipedStateTo(model);
        model.hat.copyTransform(contextModel.head);
        model.setVisible(true);
        model.hat.visible = slot == EquipmentSlot.HEAD;

        if (isLeather) {
            int hexColour = ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536));
            float red = (hexColour >> 16 & 255) / 255F;
            float green = (hexColour >> 8 & 255) / 255F;
            float blue = (hexColour & 255) / 255F;

            int u;
            if(stack.getName().getString().equals("jeb_")) {
                int m = 25;
                int n = entity.age / 25 + entity.getId();
                int o = DyeColor.values().length;
                int p = n % o;
                int q = (n + 1) % o;
                float r = (entity.age % m + 1.0f) / 25.0F;
                int s = SheepEntity.getRgbColor(DyeColor.byId(p));
                int t = SheepEntity.getRgbColor(DyeColor.byId(q));
                u = ColorHelper.Argb.lerp(r, s, t);
            } else {
                u = hexColour;
            }

            model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(leatherTexture), true), light, OverlayTexture.DEFAULT_UV, u);
            model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(overlayTexture), true), light, OverlayTexture.DEFAULT_UV);
        } else {
            model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(mainTexture), true), light, OverlayTexture.DEFAULT_UV);
        }
    }
}
