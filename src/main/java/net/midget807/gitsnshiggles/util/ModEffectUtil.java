package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ModEffectUtil {
    @Environment(EnvType.CLIENT)
    public static boolean shouldLockPlayerMovement(ClientPlayerEntity player) {
        return player == null  ? false
                : player.hasStatusEffect(ModEffects.STEPHEN_HAWKING)
                || ((TimeStoneFreeze)player).getTimeTicksFrozen() > 0;
    }


    public static void resetStephenHawkingEffect(World world, PlayerEntity player) {
        if (!world.isClient) {
            if (!player.hasStatusEffect(ModEffects.STEPHEN_HAWKING) && MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.STEPHEN_HAWKING)) {
                MinecraftClient.getInstance().player.removeStatusEffect(ModEffects.STEPHEN_HAWKING);
            }
        }
    }
}
