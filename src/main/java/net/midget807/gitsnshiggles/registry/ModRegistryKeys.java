package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.ElfVariant;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.util.Identifier;

public class ModRegistryKeys {
    public static final RegistryKey<Registry<ElfVariant>> ELF_VARIANT = of("elf_variant");

    private static <T> RegistryKey<Registry<T>> of(String name) {
        return RegistryKey.ofRegistry(GitsAndShigglesMain.id(name));
    }

    private static void addToRegistryLoader() {
        RegistryLoader.DYNAMIC_REGISTRIES.add(
                new RegistryLoader.Entry<>(ModRegistryKeys.ELF_VARIANT, ElfVariant.CODEC, true)
        );

        RegistryLoader.SYNCED_REGISTRIES.add(
                new RegistryLoader.Entry<>(ModRegistryKeys.ELF_VARIANT, ElfVariant.CODEC, true)
        );
    }

    public static void registerModRegistryKeys() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Registry Keys");
        //addToRegistryLoader();
    }
}
