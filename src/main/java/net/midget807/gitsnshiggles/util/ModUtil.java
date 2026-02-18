package net.midget807.gitsnshiggles.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class ModUtil {
    private static final List<EntityType<? extends LivingEntity>> spawnableMobs = List.of(
            EntityType.ALLAY,
            EntityType.ARMADILLO,
            EntityType.ARMOR_STAND,
            EntityType.AXOLOTL,
            EntityType.BAT,
            EntityType.BEE,
            EntityType.BLAZE,
            EntityType.BOGGED,
            EntityType.BREEZE,
            EntityType.CAMEL,
            EntityType.CAT,
            EntityType.CAVE_SPIDER,
            EntityType.CHICKEN,
            EntityType.COD,
            EntityType.COW,
            EntityType.CREEPER,
            EntityType.DOLPHIN,
            EntityType.DONKEY,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.FOX,
            EntityType.FROG,
            EntityType.GHAST,
            EntityType.GIANT,
            EntityType.GLOW_SQUID,
            EntityType.GOAT,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.HORSE,
            EntityType.HUSK,
            EntityType.ILLUSIONER,
            EntityType.IRON_GOLEM,
            EntityType.LLAMA,
            EntityType.MAGMA_CUBE,
            EntityType.MOOSHROOM,
            EntityType.MULE,
            EntityType.OCELOT,
            EntityType.PANDA,
            EntityType.PARROT,
            EntityType.PHANTOM,
            EntityType.PIG,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.PILLAGER,
            EntityType.POLAR_BEAR,
            EntityType.PUFFERFISH,
            EntityType.RABBIT,
            EntityType.RAVAGER,
            EntityType.SALMON,
            EntityType.SHEEP,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SKELETON_HORSE,
            EntityType.SLIME,
            EntityType.SNIFFER,
            EntityType.SNOW_GOLEM,
            EntityType.SPIDER,
            EntityType.SQUID,
            EntityType.STRAY,
            EntityType.STRIDER,
            EntityType.TADPOLE,
            EntityType.TRADER_LLAMA,
            EntityType.TROPICAL_FISH,
            EntityType.VEX,
            EntityType.VILLAGER,
            EntityType.VINDICATOR,
            EntityType.WANDERING_TRADER,
            EntityType.WARDEN,
            EntityType.WITCH,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.WOLF,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN
    );

    public static List<EntityType<? extends LivingEntity>> getSpawnableMobs() {
        return spawnableMobs;
    }

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
    public static ItemStack exchangeWholeStack(ItemStack inputStack, PlayerEntity player, ItemStack outputStack, boolean creativeOverride) {
        boolean bl = player.isInCreativeMode();
        int count = inputStack.getCount();
        outputStack.setCount(count);
        if (creativeOverride && bl) {
            player.getInventory().insertStack(outputStack);
        } else {
            if (!player.getInventory().insertStack(outputStack)) {
                player.dropItem(outputStack, false);
                inputStack.decrementUnlessCreative(count, player);
                return inputStack;
            }
            return outputStack;
        }
        return inputStack;
    }
    public static ItemStack exchangeWholeStack(ItemStack inputStack, PlayerEntity player, ItemStack outputStack) {
        return exchangeWholeStack(inputStack, player, outputStack, true);
    }

    public static class Speed {
        public static final double BLOCK_PER_SECOND = 0.05;
        public static final double BLOCK_PER_TICK = 1.0;
        public static double getParticleSpeedForRadius(int maxAge, double maxRadius) {
            return (BLOCK_PER_TICK / maxAge) * maxRadius * maxRadius;
        }
    }
}
