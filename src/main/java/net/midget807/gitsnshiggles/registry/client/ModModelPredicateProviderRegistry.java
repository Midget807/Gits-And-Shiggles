package net.midget807.gitsnshiggles.registry.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;

public class ModModelPredicateProviderRegistry {
    public static void registerModelPredicates() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Model Predicates");
        ModelPredicateProviderRegistry.register(
                ModItems.INVERTED_TRIDENT,
                GitsAndShigglesMain.id("inverted_throwing"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f
        );
        ModelPredicateProviderRegistry.register(
                ModItems.GAUNTLET,
                GitsAndShigglesMain.id("left_hand"),
                (stack, world, entity, seed) -> {
                    if (entity != null) {
                        if (entity.getMainArm() == Arm.RIGHT) {
                            return entity.getOffHandStack() == stack ? 1.0f : 0.0f;
                        } else {
                            return entity.getMainHandStack() == stack ? 1.0f : 0.0f;
                        }
                    }
                    return 0.0f;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.INFINITY_GAUNTLET,
                GitsAndShigglesMain.id("left_hand"),
                (stack, world, entity, seed) -> {
                    if (entity != null) {
                        if (entity.getMainArm() == Arm.RIGHT) {
                            return entity.getOffHandStack() == stack ? 1.0f : 0.0f;
                        } else {
                            return entity.getMainHandStack() == stack ? 1.0f : 0.0f;
                        }
                    }
                    return 0.0f;
                }
        );
    }
}
