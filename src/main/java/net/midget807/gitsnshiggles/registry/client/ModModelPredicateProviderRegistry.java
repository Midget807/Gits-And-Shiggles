package net.midget807.gitsnshiggles.registry.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class ModModelPredicateProviderRegistry {
    public static void registerModelPredicates() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Model Predicates");
        ModelPredicateProviderRegistry.register(
                ModItems.INVERTED_TRIDENT,
                GitsAndShigglesMain.id("inverted_throwing"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f
        );
    }
}
