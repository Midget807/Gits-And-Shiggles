package net.midget807.gitsnshiggles.mixin.client;

import net.midget807.gitsnshiggles.util.ModEffectUtil;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void gitsnshiggles$stopMoving(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (ModEffectUtil.shouldLockPlayerMovement(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }
}
