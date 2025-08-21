package net.midget807.gitsnshiggles;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.midget807.gitsnshiggles.datagen.ModBlockTagProvider;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.datagen.ModModelProvider;
import net.midget807.gitsnshiggles.datagen.ModRecipeProvider;
import net.midget807.gitsnshiggles.entity.ElfVariant;
import net.midget807.gitsnshiggles.registry.ModRegistryKeys;
import net.minecraft.registry.RegistryBuilder;

public class GitsAndShigglesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(ModRegistryKeys.ELF_VARIANT, ElfVariant.Variants::bootstrap);
	}
}
