package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> SANTA_HATS = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("santa_hats"));

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
    }
}
