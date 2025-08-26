package net.midget807.gitsnshiggles.util;

import net.midget807.gitsnshiggles.registry.ModEffects;
import net.minecraft.client.network.ClientPlayerEntity;

public class ModEffectUtil {
    public static boolean shouldLockPlayerMovement(ClientPlayerEntity player) {
        return player == null  ? false
                : player.hasStatusEffect(ModEffects.STEPHEN_HAWKING);
    }
}
