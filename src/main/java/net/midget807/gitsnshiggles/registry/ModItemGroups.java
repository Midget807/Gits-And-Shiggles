package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final ItemGroup MAIN = Registry.register(Registries.ITEM_GROUP, GitsAndShigglesMain.id("gas_main"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.gitsnshiggles.main"))
                    .icon(() -> new ItemStack(ModItems.ICON))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.AM_RIFLE);
                        entries.add(ModItems.GUN_NUGGET);
                        entries.add(ModItems.GUN_INGOT);
                        entries.add(ModItems.GUN_BLOCK);

                        entries.add(ModItems.RAILGUN);

                        entries.add(ModItems.SANTA_HAT);
                        entries.add(ModItems.LEATHER_SANTA_HAT);
                        entries.add(ModItems.CHAINMAIL_SANTA_HAT);
                        entries.add(ModItems.GOLD_SANTA_HAT);
                        entries.add(ModItems.IRON_SANTA_HAT);
                        entries.add(ModItems.DIAMOND_SANTA_HAT);
                        entries.add(ModItems.NETHERITE_SANTA_HAT);
                        entries.add(ModItems.SACK);

                        entries.add(ModItems.DICE);

                        entries.add(ModItems.FLAMETHROWER);
                        entries.add(ModItems.FUEL_TANK);

                        entries.add(ModItems.RANDOM_EGG);

                        entries.add(ModItems.INFINITY_GAUNTLET);
                        entries.add(ModItems.GAUNTLET);
                        entries.add(ModItems.EMPTY_STONE);
                        entries.add(ModItems.POWER_STONE);
                        entries.add(ModItems.SPACE_STONE);
                        entries.add(ModItems.REALITY_STONE);
                        entries.add(ModItems.SOUL_STONE);
                        entries.add(ModItems.TIME_STONE);
                        entries.add(ModItems.MIND_STONE);

                        entries.add(ModItems.INVERTED_TRIDENT);
                        entries.add(ModItems.ELDER_GUARDIAN_THORN);

                        entries.add(ModItems.KATANA);
                        entries.add(ModItems.KATANA_HILT);
                        entries.add(ModItems.KATANA_BLADE);
                        entries.add(ModItems.RED_HOT_KATANA_BLADE);

                        entries.add(ModItems.WIZARD_HAT);
                        entries.add(ModItems.WIZARD_ROBE);
                        entries.add(ModItems.WIZARD_PANTS);
                        entries.add(ModItems.WIZARD_BOOTS);
                        entries.add(ModItems.VOID_STRING);
                        entries.add(ModItems.MAGIC_CLOTH);

                        entries.add(ModItems.KYBER_CRYSTAL);
                        entries.add(ModItems.LIGHTSABER_WHITE);
                        entries.add(ModItems.LIGHTSABER_ORANGE);
                        entries.add(ModItems.LIGHTSABER_MAGENTA);
                        entries.add(ModItems.LIGHTSABER_LIGHT_BLUE);
                        entries.add(ModItems.LIGHTSABER_YELLOW);
                        entries.add(ModItems.LIGHTSABER_LIME);
                        entries.add(ModItems.LIGHTSABER_PINK);
                        entries.add(ModItems.LIGHTSABER_GRAY);
                        entries.add(ModItems.LIGHTSABER_LIGHT_GRAY);
                        entries.add(ModItems.LIGHTSABER_CYAN);
                        entries.add(ModItems.LIGHTSABER_PURPLE);
                        entries.add(ModItems.LIGHTSABER_BLUE);
                        entries.add(ModItems.LIGHTSABER_BROWN);
                        entries.add(ModItems.LIGHTSABER_GREEN);
                        entries.add(ModItems.LIGHTSABER_RED);
                        entries.add(ModItems.LIGHTSABER_BLACK);

                        entries.add(ModItems.TRON_DISC);

                        entries.add(ModItems.RED_HOT_IRON_INGOT);
                        entries.add(ModItems.RED_HOT_NETHERITE_INGOT);
                        entries.add(ModItems.RED_HOT_GOLD_INGOT);
                        entries.add(ModItems.RED_HOT_GOLD_ALLOY_INGOT);
                        entries.add(ModItems.RED_HOT_GOLD_ALLOY_PLATE);
                        entries.add(ModItems.GOLD_ALLOY_INGOT);
                        entries.add(ModItems.GOLD_ALLOY_PLATE);

                        entries.add(ModBlocks.NETHER_SPONGE);
                        entries.add(ModBlocks.WET_NETHER_SPONGE);

                    })
                    .build()
    );

    public static void registerModItemGroups() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Item Groups");
    }
}
