package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Items;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.DEBUGGER, Items.STICK, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICON, Models.GENERATED);
        itemModelGenerator.register(ModItems.DICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RANDOM_EGG, Models.GENERATED);

        itemModelGenerator.register(ModItems.SACK, Models.GENERATED);
        itemModelGenerator.register(ModItems.SANTA_HAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHAINMAIL_SANTA_HAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_SANTA_HAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_SANTA_HAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_SANTA_HAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_SANTA_HAT, Models.GENERATED);

        itemModelGenerator.register(ModItems.VOID_STRING, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGIC_CLOTH, Models.GENERATED);

        itemModelGenerator.register(ModItems.KYBER_CRYSTAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.KATANA, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RED_HOT_IRON_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RED_HOT_NETHERITE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.KATANA_HILT, Models.GENERATED);
        itemModelGenerator.register(ModItems.KATANA_BLADE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RED_HOT_KATANA_BLADE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.GUN_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.GUN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.GUN_BLOCK, Models.GENERATED);

        itemModelGenerator.register(ModItems.ELDER_GUARDIAN_THORN, Models.GENERATED);

        itemModelGenerator.register(ModItems.FUEL_TANK, Models.GENERATED);
    }
}
