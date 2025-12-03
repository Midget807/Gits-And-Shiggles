package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.client.network.ClientPlayerEntity;

public class ModEffectUtil {
    @Environment(EnvType.CLIENT)
    public static boolean shouldLockPlayerMovement(ClientPlayerEntity player) {
        return player == null  ? false
                : player.hasStatusEffect(ModEffects.STEPHEN_HAWKING)
                || ((TimeStoneFreeze)player).getTimeTicksFrozen() > 0;
    }

}
