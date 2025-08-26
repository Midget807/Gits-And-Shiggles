package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.command.ModItemArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public class ModArgumentTypes {
    public static void register() {
        ArgumentTypeRegistry.registerArgumentType(GitsAndShigglesMain.id("mod_items"), ModItemArgumentType.class, ModItemArgumentType.Serializer.INSTANCE);
    }
    public static void registerModArgumentTypes() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Argument Types");
    }
}
