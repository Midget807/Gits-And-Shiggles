package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;
import net.midget807.gitsnshiggles.item.FlamethrowerItem;
import net.midget807.gitsnshiggles.item.WizardRobesItem;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.midget807.gitsnshiggles.util.inject.RailgunAds;
import net.midget807.gitsnshiggles.util.inject.WizardGamba;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Colors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
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
    @Unique
    private float railgunScale;
    private int randomD20;

    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private Random random;

    @Inject(method = "renderMainHud", at = @At("TAIL"))
    private void gitsnshiggles$renderMain(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        renderOverheatOverlay(context, this.getCameraPlayer());
    }

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void gitsnshiggles$renderMisc(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        float f = tickCounter.getLastFrameDuration();
        this.railgunScale = MathHelper.lerp(0.5F * f, this.railgunScale, 1.125F);
        if (this.client.options.getPerspective().isFirstPerson()) {
            if (((RailgunAds)this.client.player).isUsingRailgun()) {
                this.renderRailgunAdsOverlay(context, this.railgunScale);
            } else {
                this.railgunScale = 0.5F;
            }
            this.renderD20(context, this.getCameraPlayer());
        }
    }

    @ModifyExpressionValue(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
    private boolean gitsnshiggles$derenderCrosshairOnAds(boolean original) {
        return original && !((RailgunAds)this.client.player).isUsingRailgun();
    }

    @Unique
    private void renderRailgunAdsOverlay(DrawContext context, float scale) {
        float f = Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min(context.getScaledWindowWidth() / f, context.getScaledWindowHeight() / f) * scale;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(f * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;
        int m = k + i;
        int n = l + j;
        RenderSystem.enableBlend();
        context.drawTexture(ModTextureIds.RAILGUN_ADS, k, l, -90, 0.0F, 0.0F, i, j, i, j);
        RenderSystem.disableBlend();
        context.fill(RenderLayer.getGuiOverlay(), 0, n, context.getScaledWindowWidth(), context.getScaledWindowHeight(), -90, Colors.BLACK);
        context.fill(RenderLayer.getGuiOverlay(), 0, 0, context.getScaledWindowWidth(), l, -90, Colors.BLACK);
        context.fill(RenderLayer.getGuiOverlay(), 0, l, k, n, -90, Colors.BLACK);
        context.fill(RenderLayer.getGuiOverlay(), m, l, context.getScaledWindowWidth(), n, -90, Colors.BLACK);

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

    @Unique
    private void renderD20(DrawContext context, PlayerEntity cameraPlayer) {
        this.client.getProfiler().push("fireball_d20");
        int i = context.getScaledWindowWidth() / 2;
        int j = context.getScaledWindowHeight();
        Integer animationTicks = null;
        if (cameraPlayer != null) {
            animationTicks = ((WizardGamba)cameraPlayer).getGambingAnimationTicks();
        }
        RenderSystem.enableBlend();
        if (animationTicks != null) {
            if (animationTicks == 60) {
                ((WizardGamba)cameraPlayer).setGambing(false);
            } else if (animationTicks % 10 == 0  && animationTicks > -1) {
                randomD20 = random.nextBetween(1, 20);
            }
            if (WizardRobesItem.hasFullSuitOfArmor(cameraPlayer)) {
                context.drawTexture(ModTextureIds.WIZARD_GAMBA, i - 16, j - 80, 0, (randomD20 - 1) * 32, 32, 32, 32, 640);
            }
        }
        RenderSystem.disableBlend();
        this.client.getProfiler().pop();


    }
}
