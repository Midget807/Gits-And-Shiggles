package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.cca.TimeStopComponent;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ModEffectUtil {
    @Environment(EnvType.CLIENT)
    public static boolean shouldLockPlayerMovement(ClientPlayerEntity player) {
        if (player == null) return false;
        TimeStopComponent timeStopComponent = TimeStopComponent.get(player);
        return player.hasStatusEffect(ModEffects.STEPHEN_HAWKING)
                        || player.hasStatusEffect(ModEffects.TIME_STOP)
                        || timeStopComponent.getBool();
    }

    @Environment(EnvType.CLIENT)
    public static boolean shouldInvertPlayerInputs(ClientPlayerEntity player) {
        return player != null && (
                player.hasStatusEffect(ModEffects.RETARDED)
                        || player.hasStatusEffect(ModEffects.INVERTED)
        );
    }


    public static void resetStephenHawkingEffect(World world, PlayerEntity player) {
        if (!world.isClient) {
            if (!player.hasStatusEffect(ModEffects.STEPHEN_HAWKING) && MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.STEPHEN_HAWKING)) {
                MinecraftClient.getInstance().player.removeStatusEffect(ModEffects.STEPHEN_HAWKING);
            }
        }
    }
}
