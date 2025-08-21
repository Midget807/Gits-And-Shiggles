package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.item.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item ICON = registerItem("icon", new Item(new Item.Settings()));
    public static final Item RAILGUN = registerItem("railgun", new RailgunItem(new Item.Settings().maxCount(1)));
    public static final Item LIGHTSABER = registerItem("lightsaber", new LightsaberItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(LightsaberItem.createAttributeModifiers()).component(DataComponentTypes.DYED_COLOR, LightsaberItem.createColorComponent())));
    public static final Item SANTA_HAT = registerItem("santa_hat", new Item(new Item.Settings().maxCount(1).fireproof()));
    public static final Item DICE = registerItem("dice", new DiceItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item FLAMETHROWER = registerItem("flamethrower", new FlamethrowerItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item AM_RIFLE = registerItem("am_rifle", new AntiMaterialRifleItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(AntiMaterialRifleItem.createAttributeModifiers())));
    public static final Item RANDOM_EGG = registerItem("random_egg", new RandomEggItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item INFINITY_GAUNTLET = registerItem("infinity_gauntlet", new InfinityGauntletItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item INVERTED_TRIDENT = registerItem("inverted_trident", new InvertedTridentItem(new Item.Settings().maxCount(1).fireproof().attributeModifiers(InvertedTridentItem.createAttributeModifiers()).component(DataComponentTypes.TOOL, InvertedTridentItem.createToolComponent())));
    public static final Item TRON_DISC = registerItem("tron_disc", new TronDiscItem(new Item.Settings().maxCount(1).fireproof()));

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
