package net.midget807.gitsnshiggles.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @Inject(method = "renderMainHud", at = @At("TAIL"))
    private void gitsnshiggles$renderMain(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        renderOverheatOverlay(context, this.getCameraPlayer());
    }

    @Unique
    private void renderOverheatOverlay(DrawContext context, PlayerEntity cameraPlayer) {

    }
}
