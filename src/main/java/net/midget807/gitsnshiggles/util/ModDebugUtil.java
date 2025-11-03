package net.midget807.gitsnshiggles.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ModDebugUtil {
    public static void debugMessage(LivingEntity livingEntity, String rawMessage) {
        livingEntity.sendMessage(Text.literal(rawMessage));
    }
    public static void debugMessage(PlayerEntity playerEntity, String rawMessage, boolean overlay) {
        playerEntity.sendMessage(Text.literal(rawMessage), overlay);
    }
    public static void debugMessage(PlayerEntity playerEntity, String rawMessage) {
        playerEntity.sendMessage(Text.literal(rawMessage), false);
    }
}
