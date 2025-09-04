package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.effect.EmptyEffect;
import net.midget807.gitsnshiggles.effect.FlyEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> STEPHEN_HAWKING = registerEffect("stephen_hawking", new EmptyEffect(StatusEffectCategory.HARMFUL, 0x000000));
    public static final RegistryEntry<StatusEffect> FLYING = registerEffect("flying", new FlyEffect(StatusEffectCategory.BENEFICIAL, 0xFFFFFF));

    private static RegistryEntry<StatusEffect> registerEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, GitsAndShigglesMain.id(name), effect);
    }

    public static void registerModEffects() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Effects");
    }
}
