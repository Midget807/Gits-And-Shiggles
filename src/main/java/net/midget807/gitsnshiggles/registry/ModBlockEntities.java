package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.block.entity.ChemistryWorkbenchBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {
    public static final BlockEntityType<ChemistryWorkbenchBlockEntity> CHEMISTRY_WORKBENCH = register("chemistry_workbench", ChemistryWorkbenchBlockEntity::new, ModBlocks.CHEMISTRY_WORKBENCH);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, GitsAndShigglesMain.id(name), BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }

    public static void registerModBlockEntities() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Block Entities");
    }
}
