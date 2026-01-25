package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.midget807.gitsnshiggles.registry.ModBlocks;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.NETHER_SPONGE);
        addDrop(ModBlocks.WET_NETHER_SPONGE);
        addDrop(ModBlocks.EPHEDRA_CROP, this.cropDrops(ModBlocks.EPHEDRA_CROP, ModItems.EPHEDRA, ModItems.EPHEDRA_SEEDS, BlockStatePropertyLootCondition.builder(ModBlocks.EPHEDRA_CROP)));
    }
}
