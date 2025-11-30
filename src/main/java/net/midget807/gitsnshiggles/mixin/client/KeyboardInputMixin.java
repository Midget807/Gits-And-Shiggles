package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
    @Shadow
    @Final
    private GameOptions settings;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 0))
    private boolean gitsnshiggles$invertForward(KeyBinding instance, Operation<Boolean> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if (((MindStoneInvert) player).isInverted()) {
                return this.settings.backKey.isPressed();
            }
        }
        return original.call(instance);
    }
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 1))
    private boolean gitsnshiggles$invertBack(KeyBinding instance, Operation<Boolean> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if (((MindStoneInvert) player).isInverted()) {
                return this.settings.forwardKey.isPressed();
            }
        }
        return original.call(instance);
    }
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 2))
    private boolean gitsnshiggles$invertLeft(KeyBinding instance, Operation<Boolean> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if (((MindStoneInvert) player).isInverted()) {
                return this.settings.rightKey.isPressed();
            }
        }
        return original.call(instance);
    }
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 3))
    private boolean gitsnshiggles$invertRight(KeyBinding instance, Operation<Boolean> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if (((MindStoneInvert) player).isInverted()) {
                return this.settings.leftKey.isPressed();
            }
        }
        return original.call(instance);
    }
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 4))
    private boolean gitsnshiggles$invertJump(KeyBinding instance, Operation<Boolean> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if (((MindStoneInvert) player).isInverted()) {
                return this.settings.sneakKey.isPressed();
            }
        }
        return original.call(instance);
    }
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 5))
    private boolean gitsnshiggles$invertSneak(KeyBinding instance, Operation<Boolean> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            if (((MindStoneInvert) player).isInverted()) {
                return this.settings.jumpKey.isPressed();
            }
        }
        return original.call(instance);
    }
}
