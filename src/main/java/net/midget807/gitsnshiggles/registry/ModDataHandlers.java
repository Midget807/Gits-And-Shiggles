package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.ElfVariant;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModDataHandlers {
    public static final TrackedDataHandler<RegistryEntry<ElfVariant>> ELF_VARIANT = TrackedDataHandler.create(ElfVariant.ENTRY_PACKET_CODEC);

    private static void registerDataHandler() {
        TrackedDataHandlerRegistry.register(ELF_VARIANT);
    }

    public static void registerModDataHandlers() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Data Handlers");
        registerDataHandler();
    }
}
