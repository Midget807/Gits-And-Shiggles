package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.block.NetherSpongeBlock;
import net.midget807.gitsnshiggles.block.WetNetherSpongeBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
    public static final Block NETHER_SPONGE = registerBlock("nether_sponge", new NetherSpongeBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(0.6f).sounds(BlockSoundGroup.SPONGE)), new Item.Settings().fireproof());
    public static final Block WET_NETHER_SPONGE = registerBlock("wet_nether_sponge", new WetNetherSpongeBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(0.6f).sounds(BlockSoundGroup.WET_SPONGE)), new Item.Settings().fireproof());

    private static Block registerBlock(String name, Block block, Item.Settings settings) {
        registerBlockItem(name, block, settings);
        return Registry.register(Registries.BLOCK, GitsAndShigglesMain.id(name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, GitsAndShigglesMain.id(name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return registerBlockItem(name, block, new Item.Settings());
    }

    private static Item registerBlockItem(String name, Block block, Item.Settings settings) {
        return Registry.register(Registries.ITEM, GitsAndShigglesMain.id(name), new BlockItem(block, settings));
    }

    public static void registerModBlocks() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Blocks");
    }
}
