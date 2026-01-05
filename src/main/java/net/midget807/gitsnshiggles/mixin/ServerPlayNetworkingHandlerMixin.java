package net.midget807.gitsnshiggles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkingHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @ModifyExpressionValue(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"))
    private boolean gitsnshiggles$ignoreRailgunMovementSpeed(boolean original) {
        return original || !((RailgunRecoil)this.player).hasRailgunRecoil();
    }

    @ModifyExpressionValue(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isInTeleportationState()Z", ordinal = 1))
    private boolean gitsnshiggles$ignoreRailgunMovementMotion(boolean original) {
        return original || !((RailgunRecoil)this.player).hasRailgunRecoil();
    }

    @ModifyExpressionValue(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isInTeleportationState()Z", ordinal = 0))
    private boolean gitsnshiggles$ignoreRailgunMovementSpeed2(boolean original) {
        return original || !this.player.getInventory().contains(itemStack -> itemStack.isOf(ModItems.RAILGUN));
    }
}
