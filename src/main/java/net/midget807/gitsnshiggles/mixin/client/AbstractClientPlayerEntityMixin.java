package net.midget807.gitsnshiggles.mixin.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.item.WizardRobesItem;
import net.midget807.gitsnshiggles.util.inject.RailgunAds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity implements RailgunAds {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getFovMultiplier", at = @At("TAIL"), cancellable = true)
    private void gitsnshiggles$railgunAdsFov(CallbackInfoReturnable<Float> cir) {
        if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && this.isUsingRailgun()) {
            cir.setReturnValue(0.1f);
        }
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        if (WizardRobesItem.hasFullSuitOfArmor(this)
                && this.getStackInHand(Hand.MAIN_HAND).isEmpty()
                && this.getStackInHand(Hand.OFF_HAND).isEmpty()
                && this.isSneaking()
        ) {
            return false;
        }
        return super.shouldRender(cameraX, cameraY, cameraZ);
    }
}
