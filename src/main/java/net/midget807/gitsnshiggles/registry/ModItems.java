package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.item.*;
import net.midget807.gitsnshiggles.util.ColoredItemUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = GitsAndShigglesMain.id("entity_interaction_range");
    public static final Identifier BLOCK_INTERACTION_RANGE_MODIFIER_ID = GitsAndShigglesMain.id("block_interaction_range");

    public static final Item ICON = registerItem("itemgroup_icon", new Item(new Item.Settings()));
    public static final Item DEBUGGER = registerItem("debugger", new DebuggerItem(new Item.Settings().fireproof()));
    public static final Item ENTITY_REMOVER = registerItem("entity_remover", new EntityRemoverItem(new Item.Settings().maxCount(1)));
    public static final Item MODDING_BOOK = registerItem("modding_book", new AFKCuzImCodingItem(new Item.Settings().maxCount(1)));

    public static final Item RAILGUN = registerItem("railgun", new RailgunItem(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.EPIC)));
    public static final Item SANTA_HAT = registerItem("santa_hat", new SantaHatItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).fireproof().rarity(Rarity.EPIC)));
    public static final Item SACK = registerItem("sack", new SackItem(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item DICE = registerItem("dice", new DiceItem(new Item.Settings().maxCount(1).fireproof().component(ModDataComponentTypes.DICE_ROLL, 0).rarity(Rarity.UNCOMMON)));
    public static final Item RANDOM_EGG = registerItem("random_egg", new RandomEggItem(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON)));

    public static final Item INFINITY_GAUNTLET = registerItem("infinity_gauntlet", new InfinityGauntletItem(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.EPIC)));
    public static final Item GAUNTLET = registerItem("gauntlet", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item EMPTY_STONE = registerItem("empty_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item POWER_STONE = registerItem("power_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item SPACE_STONE = registerItem("space_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item REALITY_STONE = registerItem("reality_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item SOUL_STONE = registerItem("soul_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item TIME_STONE = registerItem("time_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item MIND_STONE = registerItem("mind_stone", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item GOLD_ALLOY_PLATE = registerItem("gold_alloy_plate", new Item(new Item.Settings()));
    public static final Item RED_HOT_GOLD_INGOT = registerItem("red_hot_gold_ingot", new HotItem(new Item.Settings(), Items.GOLD_INGOT));
    public static final Item GOLD_ALLOY_INGOT = registerItem("gold_alloy_ingot", new Item(new Item.Settings()));
    public static final Item RED_HOT_GOLD_ALLOY_INGOT = registerItem("red_hot_gold_alloy_ingot", new HotItem(new Item.Settings(), ModItems.GOLD_ALLOY_INGOT));
    public static final Item RED_HOT_GOLD_ALLOY_PLATE = registerItem("red_hot_gold_alloy_plate", new HotItem(new Item.Settings(), ModItems.GOLD_ALLOY_PLATE));

    public static final Item INVERTED_TRIDENT = registerItem("inverted_trident", new InvertedTridentItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(InvertedTridentItem.createAttributeModifiers()).component(DataComponentTypes.TOOL, InvertedTridentItem.createToolComponent()).rarity(Rarity.UNCOMMON)));
    public static final Item ELDER_GUARDIAN_THORN = registerItem("elder_guardian_thorn", new Item(new Item.Settings()));

    public static final Item FLAMETHROWER = registerItem("flamethrower", new FlamethrowerItem(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.RARE)));
    public static final Item FUEL_TANK = registerItem("fuel_tank", new Item(new Item.Settings().maxCount(1)));

    public static final Item AM_RIFLE = registerItem("am_rifle", new AntiMaterialRifleItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(AntiMaterialRifleItem.createAttributeModifiers()).rarity(Rarity.RARE)));
    public static final Item GUN_NUGGET = registerItem("gun_nugget", new Item(new Item.Settings()));
    public static final Item GUN_INGOT = registerItem("gun_ingot", new Item(new Item.Settings()));
    public static final Item GUN_BLOCK = registerItem("gun_block", new Item(new Item.Settings()));

    public static final Item KATANA = registerItem("katana", new KatanaItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1).fireproof().attributeModifiers(KatanaItem.createAttributeModifiers()).maxCount(1).fireproof().rarity(Rarity.RARE)));
    public static final Item KATANA_HILT = registerItem("katana_hilt", new Item(new Item.Settings().maxCount(1)));
    public static final Item RED_HOT_IRON_INGOT = registerItem("red_hot_iron_ingot", new HotItem(new Item.Settings(), Items.IRON_INGOT));
    public static final Item KATANA_BLADE = registerItem("katana_blade", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item RED_HOT_KATANA_BLADE = registerItem("red_hot_katana_blade", new HotItem(new Item.Settings().maxCount(1).fireproof(), ModItems.KATANA_BLADE));
    public static final Item RED_HOT_NETHERITE_INGOT = registerItem("red_hot_netherite_ingot", new HotItem(new Item.Settings().fireproof(), Items.NETHERITE_INGOT));

    public static final Item TRON_DISC = registerItem("tron_disc", new TronDiscItem(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.RARE)));

    public static final Item KYBER_CRYSTAL = registerItem("kyber_crystal", new Item(new Item.Settings()));
    public static final Item LIGHTSABER_WHITE = registerItem("lightsaber_white", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_ORANGE = registerItem("lightsaber_orange", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_MAGENTA = registerItem("lightsaber_magenta", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_LIGHT_BLUE = registerItem("lightsaber_light_blue", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_YELLOW = registerItem("lightsaber_yellow", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_LIME = registerItem("lightsaber_lime", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_PINK = registerItem("lightsaber_pink", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_GRAY = registerItem("lightsaber_gray", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_LIGHT_GRAY = registerItem("lightsaber_light_gray", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_CYAN = registerItem("lightsaber_cyan", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_PURPLE = registerItem("lightsaber_purple", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_BLUE = registerItem("lightsaber_blue", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_BROWN = registerItem("lightsaber_brown", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_GREEN = registerItem("lightsaber_green", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_RED = registerItem("lightsaber_red", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));
    public static final Item LIGHTSABER_BLACK = registerItem("lightsaber_black", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).rarity(Rarity.EPIC)));

    public static final Item LEATHER_SANTA_HAT = registerItem("leather_santa_hat", new SantaHatItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item CHAINMAIL_SANTA_HAT = registerItem("chainmail_santa_hat", new SantaHatItem(ArmorMaterials.CHAIN, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item IRON_SANTA_HAT = registerItem("iron_santa_hat", new SantaHatItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item GOLD_SANTA_HAT = registerItem("gold_santa_hat", new SantaHatItem(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item DIAMOND_SANTA_HAT = registerItem("diamond_santa_hat", new SantaHatItem(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item NETHERITE_SANTA_HAT = registerItem("netherite_santa_hat", new SantaHatItem(ArmorMaterials.NETHERITE, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).fireproof().rarity(Rarity.EPIC)));

    public static final Item WIZARD_HAT = registerItem("wizard_hat", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item WIZARD_ROBE = registerItem("wizard_robes", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item WIZARD_PANTS = registerItem("wizard_pants", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item WIZARD_BOOTS = registerItem("wizard_boots", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item VOID_STRING = registerItem("void_string", new Item(new Item.Settings()));
    public static final Item MAGIC_CLOTH = registerItem("magic_cloth", new Item(new Item.Settings()));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, GitsAndShigglesMain.id(name), item);
    }

    public static void registerModItems() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Items");
    }
}
