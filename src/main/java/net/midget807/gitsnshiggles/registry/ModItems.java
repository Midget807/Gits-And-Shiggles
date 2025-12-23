package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.item.*;
import net.midget807.gitsnshiggles.util.ColoredItemUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = GitsAndShigglesMain.id("entity_interaction_range");
    public static final Identifier BLOCK_INTERACTION_RANGE_MODIFIER_ID = GitsAndShigglesMain.id("block_interaction_range");

    public static final Item ICON = registerItem("itemgroup_icon", new Item(new Item.Settings()));
    public static final Item DEBUGGER = registerItem("debugger", new DebuggerItem(new Item.Settings().fireproof()));

    public static final Item RAILGUN = registerItem("railgun", new RailgunItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item SANTA_HAT = registerItem("santa_hat", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item SACK = registerItem("sack", new SackItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item DICE = registerItem("dice", new DiceItem(new Item.Settings().maxCount(1).fireproof().component(ModDataComponentTypes.DICE_ROLL, 0)));
    public static final Item FLAMETHROWER = registerItem("flamethrower", new FlamethrowerItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item AM_RIFLE = registerItem("am_rifle", new AntiMaterialRifleItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(AntiMaterialRifleItem.createAttributeModifiers())));
    public static final Item RANDOM_EGG = registerItem("random_egg", new RandomEggItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item INFINITY_GAUNTLET = registerItem("infinity_gauntlet", new InfinityGauntletItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item INVERTED_TRIDENT = registerItem("inverted_trident", new InvertedTridentItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(InvertedTridentItem.createAttributeModifiers()).component(DataComponentTypes.TOOL, InvertedTridentItem.createToolComponent())));

    public static final Item TRON_DISC_WHITE = registerItem("tron_disc_white", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.WHITE));
    public static final Item TRON_DISC_ORANGE = registerItem("tron_disc_orange", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.ORANGE));
    public static final Item TRON_DISC_MAGENTA = registerItem("tron_disc_magenta", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.MAGENTA));
    public static final Item TRON_DISC_LIGHT_BLUE = registerItem("tron_disc_light_blue", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.LIGHT_BLUE));
    public static final Item TRON_DISC_YELLOW = registerItem("tron_disc_yellow", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.YELLOW));
    public static final Item TRON_DISC_LIME = registerItem("tron_disc_lime", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.LIME));
    public static final Item TRON_DISC_PINK = registerItem("tron_disc_pink", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.PINK));
    public static final Item TRON_DISC_GRAY = registerItem("tron_disc_gray", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.GRAY));
    public static final Item TRON_DISC_LIGHT_GRAY = registerItem("tron_disc_light_gray", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.LIGHT_GRAY));
    public static final Item TRON_DISC_CYAN = registerItem("tron_disc_cyan", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.CYAN));
    public static final Item TRON_DISC_PURPLE = registerItem("tron_disc_purple", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.PURPLE));
    public static final Item TRON_DISC_BLUE = registerItem("tron_disc_blue", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.BLUE));
    public static final Item TRON_DISC_BROWN = registerItem("tron_disc_brown", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.BROWN));
    public static final Item TRON_DISC_GREEN = registerItem("tron_disc_green", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.GREEN));
    public static final Item TRON_DISC_RED = registerItem("tron_disc_red", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.RED));
    public static final Item TRON_DISC_BLACK = registerItem("tron_disc_black", new TronDiscItem(new Item.Settings().maxCount(1).fireproof(), ColoredItemUtil.Colors.BLACK));

    public static final Item KYBER_CRYSTAL = registerItem("kyber_crystal", new Item(new Item.Settings()));
    public static final Item LIGHTSABER_WHITE = registerItem("lightsaber_white", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_ORANGE = registerItem("lightsaber_orange", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_MAGENTA = registerItem("lightsaber_magenta", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_LIGHT_BLUE = registerItem("lightsaber_light_blue", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_YELLOW = registerItem("lightsaber_yellow", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_LIME = registerItem("lightsaber_lime", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_PINK = registerItem("lightsaber_pink", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_GRAY = registerItem("lightsaber_gray", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_LIGHT_GRAY = registerItem("lightsaber_light_gray", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_CYAN = registerItem("lightsaber_cyan", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_PURPLE = registerItem("lightsaber_purple", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_BLUE = registerItem("lightsaber_blue", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_BROWN = registerItem("lightsaber_brown", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_GREEN = registerItem("lightsaber_green", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_RED = registerItem("lightsaber_red", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));
    public static final Item LIGHTSABER_BLACK = registerItem("lightsaber_black", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers())));

    public static final Item LEATHER_SANTA_HAT = registerItem("leather_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item CHAINMAIL_SANTA_HAT = registerItem("chainmail_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item IRON_SANTA_HAT = registerItem("iron_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item GOLD_SANTA_HAT = registerItem("gold_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item DIAMOND_SANTA_HAT = registerItem("diamond_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item NETHERITE_SANTA_HAT = registerItem("netherite_santa_hat", new Item(new Item.Settings().maxCount(1)));

    public static final Item WIZARD_HAT = registerItem("wizard_hat", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).fireproof()));
    public static final Item WIZARD_ROBE = registerItem("wizard_robes", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1).fireproof()));
    public static final Item WIZARD_PANTS = registerItem("wizard_pants", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1).fireproof()));
    public static final Item WIZARD_BOOTS = registerItem("wizard_boots", new WizardRobesItem(ModArmorMaterials.WIZARD_CLOTH, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1).fireproof()));
    public static final Item VOID_STRING = registerItem("void_string", new Item(new Item.Settings()));
    public static final Item MAGIC_CLOTH = registerItem("magic_cloth", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, GitsAndShigglesMain.id(name), item);
    }

    public static void registerModItems() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Items");
    }
}
