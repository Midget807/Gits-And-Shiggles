package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.item.RailgunItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item ICON = registerItem("icon", new Item(new Item.Settings()));
    public static final Item RAILGUN = registerItem("railgun", new RailgunItem(new Item.Settings().maxCount(1)));
    public static final Item LIGHTSABER = registerItem("lightsaber", new Item(new Item.Settings().maxCount(1)));
    public static final Item SANTA_HAT = registerItem("santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item DICE = registerItem("dice", new Item(new Item.Settings().maxCount(1)));
    public static final Item FLAMETHROWER = registerItem("flamethrower", new Item(new Item.Settings().maxCount(1)));
    public static final Item AM_RIFLE = registerItem("am_rifle", new Item(new Item.Settings().maxCount(1)));

    public static final Item LEATHER_SANTA_HAT = registerItem("leather_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item CHAINMAIL_SANTA_HAT = registerItem("chainmail_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item IRON_SANTA_HAT = registerItem("iron_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item GOLD_SANTA_HAT = registerItem("gold_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item DIAMOND_SANTA_HAT = registerItem("diamond_santa_hat", new Item(new Item.Settings().maxCount(1)));
    public static final Item NETHERITE_SANTA_HAT = registerItem("netherite_santa_hat", new Item(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, GitsAndShigglesMain.id(name), item);
    }

    public static void registerModItems() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Items");
    }
}
