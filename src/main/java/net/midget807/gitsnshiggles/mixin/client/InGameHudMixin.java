package net.midget807.gitsnshiggles.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.midget807.gitsnshiggles.item.FlamethrowerItem;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderMainHud", at = @At("TAIL"))
    private void gitsnshiggles$renderMain(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        renderOverheatOverlay(context, this.getCameraPlayer());
    }

    @Unique
    private void renderOverheatOverlay(DrawContext context, PlayerEntity cameraPlayer) {
        this.client.getProfiler().push("flamethrower");
        int i = context.getScaledWindowWidth() / 2;
        int j = context.getScaledWindowHeight();
        Integer useTicks = null;
        Integer useTickProgress = null;
        Integer extraRangeTicks = null;
        Integer extraRangeProgress = null;
        Integer extraDamageTicks = null;
        Integer extraDamageProgress = null;
        if (cameraPlayer.getStackInHand(cameraPlayer.getActiveHand()).isOf(ModItems.FLAMETHROWER) && cameraPlayer.getStackInHand(cameraPlayer.getActiveHand()).getItem() instanceof FlamethrowerItem flamethrowerItem) {
            useTicks = flamethrowerItem.getUseTicks();
            extraDamageTicks = flamethrowerItem.getExtraDamageTicks();
            extraRangeTicks = flamethrowerItem.getExtraRangeTicks();
        }
        if (useTicks != null) {
            useTickProgress = MathHelper.clamp(Math.round((float) (useTicks * 200) / 600), 0, 200);
        }
        if (extraRangeTicks != null) {
            extraRangeProgress = MathHelper.clamp(Math.round((float) (extraRangeTicks * 10) /200), 0, 10);
        }
        if (extraDamageTicks != null) {
            extraDamageProgress = MathHelper.clamp(Math.round((float) (extraDamageTicks * 10) / 200), 0, 10);
        }

        RenderSystem.enableBlend();
        if (useTickProgress != null && useTickProgress > 0) {
            context.drawTexture(ModTextureIds.OVERHEAT_BAR, i - 101 + 1, j - 60 + 1, 1, 0, 0, useTickProgress, 7, 200, 7);
            context.drawTexture(ModTextureIds.RANGE_BAR, i - 95 - 1, j - 24 - 11, 1, 0, 0, 1, extraRangeProgress, 1, 10);
            context.drawTexture(ModTextureIds.DAMAGE_BAR, i - 95 - 5, j - 24 - 11, 1, 0, 0, 1, extraDamageProgress, 1, 10);
        }

        if (cameraPlayer.getStackInHand(cameraPlayer.getActiveHand()).isOf(ModItems.FLAMETHROWER)) {
            context.drawGuiTexture(ModTextureIds.OVERHEAT_BG, i - 101, j - 60, 0, 202, 9);
            context.drawGuiTexture(ModTextureIds.OVERHEAT_OVERLAY, i - 101, j - 60, 2, 202, 9);
            context.drawGuiTexture(ModTextureIds.FLAMETHROWER_BG, i - 95, j - 24 - 12, 0, 3, 12);
            context.drawGuiTexture(ModTextureIds.FLAMETHROWER_BG, i - 95 - 4, j - 24 - 12, 0, 3, 12);
        }
        RenderSystem.disableBlend();
        this.client.getProfiler().pop();

    }
}
