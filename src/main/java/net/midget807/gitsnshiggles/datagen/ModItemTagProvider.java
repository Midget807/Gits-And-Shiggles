package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> SANTA_HATS = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("santa_hats"));
    public static final TagKey<Item> FLAMETHROWER_INSERTABLE = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("flamethrower_insertable"));
    public static final TagKey<Item> FLAMETHROWER_POWER = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("flamethrower_power"));
    public static final TagKey<Item> LIGHTSABERS = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("lightsabers"));
    public static final TagKey<Item> DISCS = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("discs"));
    public static final TagKey<Item> DISABLES_SHIELD = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("custom_disables_shield"));
    public static final TagKey<Item> SANTA_DIET = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("santa_diet"));
    public static final TagKey<Item> BIG_ITEM_RENDERING = TagKey.of(RegistryKeys.ITEM, GitsAndShigglesMain.id("big_item_rendering"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(ItemTags.EQUIPPABLE_ENCHANTABLE)
                .add(
                        ModItems.WIZARD_HAT,
                        ModItems.WIZARD_ROBE,
                        ModItems.WIZARD_PANTS,
                        ModItems.WIZARD_BOOTS
                )
                .addTag(SANTA_HATS);
        this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.WIZARD_HAT)
                .addTag(SANTA_HATS);
        this.getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.WIZARD_ROBE);
        this.getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.WIZARD_PANTS);
        this.getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.WIZARD_BOOTS);
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
                        ModItems.LEATHER_SANTA_HAT,
                        ModItems.TRON_DISC
                );
        this.getOrCreateTagBuilder(LIGHTSABERS)
                .add(
                        ModItems.LIGHTSABER_WHITE,
                        ModItems.LIGHTSABER_ORANGE,
                        ModItems.LIGHTSABER_MAGENTA,
                        ModItems.LIGHTSABER_LIGHT_BLUE,
                        ModItems.LIGHTSABER_YELLOW,
                        ModItems.LIGHTSABER_LIME,
                        ModItems.LIGHTSABER_PINK,
                        ModItems.LIGHTSABER_GRAY,
                        ModItems.LIGHTSABER_LIGHT_GRAY,
                        ModItems.LIGHTSABER_CYAN,
                        ModItems.LIGHTSABER_PURPLE,
                        ModItems.LIGHTSABER_BLUE,
                        ModItems.LIGHTSABER_BROWN,
                        ModItems.LIGHTSABER_GREEN,
                        ModItems.LIGHTSABER_RED,
                        ModItems.LIGHTSABER_BLACK
                );
        this.getOrCreateTagBuilder(DISABLES_SHIELD)
                .add(ModItems.AM_RIFLE);
        this.getOrCreateTagBuilder(SANTA_DIET)
                .add(Items.COOKIE);
        this.getOrCreateTagBuilder(BIG_ITEM_RENDERING)
                .add(
                        ModItems.AM_RIFLE,
                        ModItems.KATANA
                );
        this.getOrCreateTagBuilder(DISCS)
                .add(
                        Items.MUSIC_DISC_5,
                        Items.MUSIC_DISC_CREATOR,
                        Items.MUSIC_DISC_CREATOR_MUSIC_BOX,
                        Items.MUSIC_DISC_OTHERSIDE,
                        Items.MUSIC_DISC_RELIC,
                        Items.MUSIC_DISC_PRECIPICE
                )
                .forceAddTag(ItemTags.CREEPER_DROP_MUSIC_DISCS);
    }
}
