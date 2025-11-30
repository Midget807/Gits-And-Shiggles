package net.midget807.gitsnshiggles.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.MathHelper;

public class ModUtil {
    public static Text getDurationText(int ticks, float multiplier, float tickRate) {
        if (ticks == -1) {
            return Text.translatable("effect.duration.infinite");
        } else {
            int i = MathHelper.floor(ticks * multiplier);
            return Text.literal(StringHelper.formatTicks(i, tickRate));
        }
    }

    public static Text getDurationText(int ticks) {
        if (MinecraftClient.getInstance().world != null) {
            if (ticks == -1) {
                return Text.translatable("effect.duration.infinite");
            } else {
                int i = MathHelper.floor(ticks * 1.0f);
                return Text.literal(StringHelper.formatTicks(i, MinecraftClient.getInstance().world.getTickManager().getTickRate()));
            }
        } else {
            return Text.empty();
        }
    }
}
