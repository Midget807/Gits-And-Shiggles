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
                        entries.add(ModItems.LIGHTSABER);
                        entries.add(ModItems.SANTA_HAT);
                        entries.add(ModItems.DICE);
                        entries.add(ModItems.FLAMETHROWER);
                        entries.add(ModItems.AM_RIFLE);
                        entries.add(ModItems.RANDOM_EGG);
                        entries.add(ModItems.INFINITY_GAUNTLET);
                        entries.add(ModItems.INVERTED_TRIDENT);
                    })
                    .build()
    );

    public static void registerModItemGroups() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Item Groups");
    }
}
