package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.effect.EmptyEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> STEPHEN_HAWKING = registerEffect("stephen_hawking", new EmptyEffect(StatusEffectCategory.HARMFUL, 0x000000));
    public static final RegistryEntry<StatusEffect> TIME_STOP = registerEffect("time_stop", new EmptyEffect(StatusEffectCategory.HARMFUL, 0x00FF44));
    public static final RegistryEntry<StatusEffect> RETARDED = registerEffect("retarded", new EmptyEffect(StatusEffectCategory.HARMFUL, 0xFFFFFF));
    public static final RegistryEntry<StatusEffect> INVERTED = registerEffect("inverted", new EmptyEffect(StatusEffectCategory.HARMFUL, 0xFFEF00));

    private static RegistryEntry<StatusEffect> registerEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, GitsAndShigglesMain.id(name), effect);
    }

    public static void registerModEffects() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Effects");
    }
}
