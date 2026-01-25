package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public static final TagKey<Block> SPACE_STONE_SAFE = TagKey.of(RegistryKeys.BLOCK, GitsAndShigglesMain.id("space_stone_safe"));

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
                .add(
                        ModBlocks.NETHER_SPONGE,
                        ModBlocks.WET_NETHER_SPONGE
                );
        this.getOrCreateTagBuilder(SPACE_STONE_SAFE)
                .add(
                        Blocks.WATER,
                        Blocks.SNOW,
                        Blocks.TORCH,
                        Blocks.SOUL_TORCH,
                        Blocks.REDSTONE_TORCH,
                        Blocks.WALL_TORCH,
                        Blocks.WEEPING_VINES,
                        Blocks.TWISTING_VINES
                );

    }
}
