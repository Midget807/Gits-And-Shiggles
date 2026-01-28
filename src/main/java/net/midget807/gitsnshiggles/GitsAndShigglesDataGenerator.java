package net.midget807.gitsnshiggles;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.midget807.gitsnshiggles.datagen.*;
import net.midget807.gitsnshiggles.entity.ElfVariant;
import net.midget807.gitsnshiggles.registry.ModRegistryKeys;
import net.minecraft.registry.RegistryBuilder;

public class GitsAndShigglesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModFluidTags::new);
		pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(ModRegistryKeys.ELF_VARIANT, ElfVariant.Variants::bootstrap);
	}
}
