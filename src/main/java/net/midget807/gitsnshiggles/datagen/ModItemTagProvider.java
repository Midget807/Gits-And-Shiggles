package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> SANTA_HATS = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("santa_hats"));
    public static final TagKey<Item> FLAMETHROWER_INSERTABLE = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("flamethrower_insertable"));
    public static final TagKey<Item> FLAMETHROWER_POWER = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("flamethrower_power"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(SANTA_HATS)
                .add(
                        ModItems.SANTA_HAT,
                        ModItems.LEATHER_SANTA_HAT,
                        ModItems.CHAINMAIL_SANTA_HAT,
                        ModItems.IRON_SANTA_HAT,
                        ModItems.GOLD_SANTA_HAT,
                        ModItems.DIAMOND_SANTA_HAT,
                        ModItems.NETHERITE_SANTA_HAT
                );

        this.getOrCreateTagBuilder(FLAMETHROWER_POWER)
                .add(
                        Items.SOUL_SAND,
                        Items.SOUL_SOIL
                );
        this.getOrCreateTagBuilder(FLAMETHROWER_INSERTABLE)
                .add(
                        Items.MAGMA_BLOCK
                ).addTag(
                        FLAMETHROWER_POWER
                );
        this.getOrCreateTagBuilder(ItemTags.DYEABLE)
                .add(
                        ModItems.WIZARD_HAT,
                        ModItems.WIZARD_ROBE,
                        ModItems.WIZARD_PANTS,
                        ModItems.WIZARD_BOOTS,
                        ModItems.LIGHTSABER,
                        ModItems.TRON_DISC
                );
    }
}
