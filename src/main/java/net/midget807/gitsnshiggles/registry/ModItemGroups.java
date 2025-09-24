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
                        entries.add(ModItems.RAILGUN);
                        entries.add(ModItems.SANTA_HAT);
                        entries.add(ModItems.DICE);
                        entries.add(ModItems.FLAMETHROWER);
                        entries.add(ModItems.AM_RIFLE);
                        entries.add(ModItems.RANDOM_EGG);
                        entries.add(ModItems.INFINITY_GAUNTLET);
                        entries.add(ModItems.INVERTED_TRIDENT);
                        entries.add(ModItems.TRON_DISC_WHITE);
                        entries.add(ModItems.WIZARD_HAT);
                        entries.add(ModItems.WIZARD_ROBE);
                        entries.add(ModItems.WIZARD_PANTS);
                        entries.add(ModItems.WIZARD_BOOTS);
                        entries.add(ModItems.VOID_STRING);
                        entries.add(ModItems.MAGIC_CLOTH);

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

                    })
                    .build()
    );

    public static void registerModItemGroups() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Item Groups");
    }
}
