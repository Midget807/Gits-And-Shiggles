package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.midget807.gitsnshiggles.util.inject.RailgunAds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean gitsnshiggles$railgunReduction(boolean original) {
        return original || (this.client.options.getPerspective().isFirstPerson() && ((RailgunAds)this.client.player).isUsingRailgun());
    }
}
