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
        itemModelGenerator.register(ModItems.KATANA, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SACK, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOID_STRING, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGIC_CLOTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.KYBER_CRYSTAL, Models.GENERATED);
    }
}
