package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.midget807.gitsnshiggles.util.ModEffectUtil;
import net.midget807.gitsnshiggles.util.inject.RailgunAds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    public void gitsnshiggles$stopMoving(long window, int button, int action, int mods, CallbackInfo ci) {
        if (ModEffectUtil.shouldLockPlayerMovement(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }
    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    public void domainExpansion$stopMoving(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (ModEffectUtil.shouldLockPlayerMovement(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean gitsnshiggles$railgunReduction(boolean original) {
        return original || (this.client.options.getPerspective().isFirstPerson() && ((RailgunAds)this.client.player).isUsingRailgun());
    }
}
