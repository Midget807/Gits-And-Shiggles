package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.screen.ChemistryWorkbenchScreenHandler;
import net.midget807.gitsnshiggles.screen.DistillationScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ChemistryWorkbenchScreenHandler> CHEMISTRY_WORKBENCH_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, GitsAndShigglesMain.id("chemistry_workbench"),
                    new ExtendedScreenHandlerType<>(ChemistryWorkbenchScreenHandler::new, BlockPos.PACKET_CODEC)
            );
    public static final ScreenHandlerType<DistillationScreenHandler> DISTILLATION_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, GitsAndShigglesMain.id("distillation"),
                    new ExtendedScreenHandlerType<>(DistillationScreenHandler::new, BlockPos.PACKET_CODEC)
            );

    public static void registerModScreenHandlers() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Screen Handlers");
    }
}
