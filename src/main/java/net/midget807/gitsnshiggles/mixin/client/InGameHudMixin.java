package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;
import net.midget807.gitsnshiggles.cca.FlamethrowerComponent;
import net.midget807.gitsnshiggles.cca.KatanaBlockingComponent;
import net.midget807.gitsnshiggles.item.*;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.midget807.gitsnshiggles.util.inject.RailgunAds;
import net.midget807.gitsnshiggles.util.inject.WizardGamba;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
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
    @Unique
    private boolean shouldRenderInfinityStoneCD = false;

    @Inject(method = "renderMainHud", at = @At("TAIL"))
    private void gitsnshiggles$renderMain(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        renderOverheatOverlay(context, this.getCameraPlayer());
    }

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void gitsnshiggles$renderMisc(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        float f = tickCounter.getLastFrameDuration();
        this.railgunScale = MathHelper.lerp(0.5F * f, this.railgunScale, 1.125F);
        if (this.client.options.getPerspective().isFirstPerson()) {
            if (((RailgunAds)this.client.player).gitsAndShiggles$isUsingRailgun()) {
                this.renderRailgunAdsOverlay(context, this.railgunScale);
            } else {
                this.railgunScale = 0.5F;
            }

            if (this.getCameraPlayer() != null && WizardRobesItem.hasFullSuitOfArmor(this.getCameraPlayer())) {
                //this.renderD20(context, this.getCameraPlayer());
            }
            if (this.client.player.isUsingItem() && this.client.player.getActiveItem().getItem() instanceof TronDiscItem) {
                this.renderTronDiscChargeBar(context, this.client.player);
            }
            shouldRenderInfinityStoneCD = this.client.player.isHolding(ModItems.INFINITY_GAUNTLET);
            if (this.shouldRenderInfinityStoneCD) {
                this.client.player.getHandItems().forEach(itemStack -> {
                    if (itemStack.getItem() instanceof InfinityGauntletItem gauntletItem) {
                        renderInfinityStoneCD(context, this.client.player, gauntletItem);
                    }
                });

            }
            if (client.player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.KATANA)) {
                renderKatanaParryBar(context, this.client.player);
            }
        }
    }

    @Unique
    private void renderKatanaParryBar(DrawContext context, ClientPlayerEntity player) {
        int width = context.getScaledWindowWidth() / 2;
        int height = context.getScaledWindowHeight();
        PlayerEntity playerEntity = player;
        KatanaBlockingComponent katanaBlockingComponent = KatanaBlockingComponent.get(playerEntity);

        this.client.getProfiler().push("katana_parry");
        RenderSystem.enableBlend();

        context.drawGuiTexture(ModTextureIds.PARRY_BAR_BG, width - 50, height - 60, 100, 6);
        context.drawTexture(ModTextureIds.PARRY_BAR, width - 50, height - 60, 1, 0, 0, katanaBlockingComponent.getInt() * 100 / KatanaBlockingComponent.MAX_BLOCKING_TIME, 6, 100, 6);

        float damage = player.getStackInHand(Hand.MAIN_HAND).getOrDefault(ModDataComponentTypes.PARRY_DAMAGE, 0.0f);
        context.drawText(this.client.textRenderer, Text.literal(String.format("%.1f", damage)), width + 52 , height - 61, damage == KatanaBlockingComponent.MAX_PARRY_DMG ? Colors.YELLOW : Colors.WHITE, false);

        RenderSystem.disableBlend();
        this.client.getProfiler().pop();
    }

    @ModifyExpressionValue(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
    private boolean gitsnshiggles$derenderCrosshairOnAds(boolean original) {
        return original && !((RailgunAds)this.client.player).gitsAndShiggles$isUsingRailgun();
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
        if (cameraPlayer.getStackInHand(cameraPlayer.getActiveHand()).isOf(ModItems.FLAMETHROWER) && cameraPlayer.getStackInHand(cameraPlayer.getActiveHand()).isOf(ModItems.FLAMETHROWER)) {
            FlamethrowerComponent flamethrowerComponent = FlamethrowerComponent.get(cameraPlayer);
            useTicks = flamethrowerComponent.getValue1();
            extraRangeTicks = flamethrowerComponent.getValue2();
            extraDamageTicks = flamethrowerComponent.getValue3();
        }
        if (useTicks != null) {
            useTickProgress = MathHelper.clamp(Math.round((float) (useTicks * 200) / 120), 0, 200);
        }
        if (extraRangeTicks != null) {
            extraRangeProgress = MathHelper.clamp(Math.round((float) (extraRangeTicks * 10) / 200), 0, 10);
        }
        if (extraDamageTicks != null) {
            extraDamageProgress = MathHelper.clamp(Math.round((float) (extraDamageTicks * 10) / 200), 0, 10);
        }

        RenderSystem.enableBlend();
        if (useTickProgress != null && useTickProgress > 0) {
            context.drawTexture(ModTextureIds.OVERHEAT_BAR, i - 101 + 1, j - 60 + 1, 1, 0, 0, useTickProgress, 7, 200, 7);
        }
        if (extraRangeTicks != null) {
            context.drawTexture(ModTextureIds.RANGE_BAR, i - 94, j - 24 - 11, 1, 0, 0, 1, extraRangeProgress, 1, 10);
        }
        if (extraDamageTicks != null) {
            context.drawTexture(ModTextureIds.DAMAGE_BAR, i - 98, j - 24 - 11, 1, 0, 0, 1, extraDamageProgress, 1, 10);
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
                ((WizardGamba)cameraPlayer).setGambingAnimationTicks(-1);
            } else if (animationTicks % 10 == 0  && animationTicks > -1) {
                randomD20 = random.nextBetween(1, 20);
            }
            context.drawTexture(ModTextureIds.WIZARD_GAMBA, i - 16, j - 80, 0, (randomD20 - 1) * 32, 32, 32, 32, 640);

        }
        RenderSystem.disableBlend();
        this.client.getProfiler().pop();
    }

    @Unique
    private void renderTronDiscChargeBar(DrawContext context, ClientPlayerEntity player) {
        int i = context.getScaledWindowWidth() / 2;
        int j = context.getScaledWindowHeight() / 2;
        context.drawGuiTexture(ModTextureIds.TRON_DISC_CHARGE_BG, i - 36, j + 12, 72, 8);
        context.drawTexture(ModTextureIds.TRON_DISC_BAR, i -36, j + 12, 0, 0, Math.min(TronDiscItem.reboundsForPullProgress(player) * 16, 72), 8, 72, 8);
    }

    @Unique
    private void renderInfinityStoneCD(DrawContext context, ClientPlayerEntity player, InfinityGauntletItem gauntletItem) {
        this.client.getProfiler().push("infinity_stone_cooldown");
        int i = context.getScaledWindowWidth() / 2;
        int j = context.getScaledWindowHeight() / 2;
        int bg_size = 24;
        int icon_size = 16;
        for (int k = 0; k < 6; k++) {
            context.drawTexture(ModTextureIds.INFINITY_STONE_BG, 0, k * bg_size, 0, 0, 0, bg_size, bg_size, bg_size, bg_size);
        }

        context.drawTexture(ModTextureIds.POWER_STONE_OVERLAY, 4, 4, 0, 0, 0, icon_size, icon_size, icon_size, icon_size);
        context.drawTexture(ModTextureIds.SPACE_STONE_OVERLAY, 4, 4 + bg_size, 0, 0, 0, icon_size, icon_size, icon_size, icon_size);
        context.drawTexture(ModTextureIds.REALITY_STONE_OVERLAY, 4, 4 + 2 * bg_size, 0, 0, 0, icon_size, icon_size, icon_size, icon_size);
        context.drawTexture(ModTextureIds.SOUL_STONE_OVERLAY, 4, 4 + 3 * bg_size, 0, 0, 0, icon_size, icon_size, icon_size, icon_size);
        context.drawTexture(ModTextureIds.TIME_STONE_OVERLAY, 4, 4 + 4 * bg_size, 0, 0, 0, icon_size, icon_size, icon_size, icon_size);
        context.drawTexture(ModTextureIds.MIND_STONE_OVERLAY, 4, 4 + 5 * bg_size, 0, 0, 0, icon_size, icon_size, icon_size, icon_size);

        context.drawTexture(
                ModTextureIds.POWER_STONE_BG,
                4,
                5,
                1,
                0,
                1,
                icon_size,
                InfinityStoneUtil.getCDTextureRatio(
                        gauntletItem.powerStoneCD,
                        InfinityStoneUtil.POWER_STONE_CD,
                        13
                ),
                icon_size,
                icon_size
        );
        context.drawTexture(
                ModTextureIds.SPACE_STONE_BG,
                4,
                4 + bg_size,
                1,
                0,
                0,
                icon_size,
                InfinityStoneUtil.getCDTextureRatio(
                        gauntletItem.spaceStoneCD ,
                        InfinityStoneUtil.SPACE_STONE_CD,
                        16
                ),
                icon_size,
                icon_size
        );
        context.drawTexture(
                ModTextureIds.REALITY_STONE_BG,
                4,
                5 + 2 * bg_size,
                1,
                0,
                1,
                icon_size,
                InfinityStoneUtil.getCDTextureRatio(
                        gauntletItem.realityStoneCD,
                        InfinityStoneUtil.REALITY_STONE_CD,
                        15
                ),
                icon_size,
                icon_size
        );
        context.drawTexture(
                ModTextureIds.SOUL_STONE_BG,
                4,
                7 + 3 * bg_size,
                1,
                0,
                3,
                icon_size,
                InfinityStoneUtil.getCDTextureRatio(
                        gauntletItem.soulStoneCD,
                        InfinityStoneUtil.SOUL_STONE_CD,
                        11
                ),
                icon_size,
                icon_size
        );
        context.drawTexture(
                ModTextureIds.TIME_STONE_BG,
                4,
                5 + 4 * bg_size,
                1,
                0,
                1,
                icon_size,
                InfinityStoneUtil.getCDTextureRatio(
                        gauntletItem.timeStoneCD,
                        InfinityStoneUtil.TIME_STONE_CD,
                        15
                ),
                icon_size,
                icon_size
        );
        context.drawTexture(
                ModTextureIds.MIND_STONE_BG,
                4,
                4 + 5 * bg_size,
                1,
                0,
                0,
                icon_size,
                InfinityStoneUtil.getCDTextureRatio(
                        gauntletItem.mindStoneCD,
                        InfinityStoneUtil.MIND_STONE_CD,
                        16
                ),
                icon_size,
                icon_size
        );

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        context.drawText(textRenderer, ModUtil.getDurationText(gauntletItem.powerStoneCD), bg_size + 2, (bg_size / 2) - 4, Colors.WHITE, false);
        context.drawText(textRenderer, ModUtil.getDurationText(gauntletItem.spaceStoneCD), bg_size + 2, (bg_size / 2) - 4 + bg_size, Colors.WHITE, false);
        context.drawText(textRenderer, ModUtil.getDurationText(gauntletItem.realityStoneCD), bg_size + 2, (bg_size / 2) - 4 + (2 * bg_size), Colors.WHITE, false);
        context.drawText(textRenderer, ModUtil.getDurationText(gauntletItem.soulStoneCD), bg_size + 2, (bg_size / 2) - 4 + (3 * bg_size), Colors.WHITE, false);
        context.drawText(textRenderer, ModUtil.getDurationText(gauntletItem.timeStoneCD), bg_size + 2, (bg_size / 2) - 4 + (4 * bg_size), Colors.WHITE, false);
        context.drawText(textRenderer, ModUtil.getDurationText(gauntletItem.mindStoneCD), bg_size + 2, (bg_size / 2) - 4 + (5 * bg_size), Colors.WHITE, false);

        this.client.getProfiler().pop();
    }
}
