package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.command.*;

import static net.minecraft.server.command.CommandManager.*;

public class ModCommands {
    public static void registerMainCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("gitsnshiggles")
                    .then(UnluckyPlayerCommand.register(dispatcher, registryAccess))
                    .then(VeryUnluckyPlayerCommand.register(dispatcher, registryAccess))
                    .then(GiveEntityRemover.register(dispatcher, registryAccess))
            );
        });
    }
    public static void registerModCommands() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Commands");
        registerMainCommand();
    }
}
