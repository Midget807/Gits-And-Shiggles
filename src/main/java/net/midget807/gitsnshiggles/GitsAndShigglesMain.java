package net.midget807.gitsnshiggles;

import net.fabricmc.api.ModInitializer;

import net.midget807.gitsnshiggles.registry.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitsAndShigglesMain implements ModInitializer {
	public static final String MOD_ID = "gitsnshiggles";

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger("Gits And Shiggles");

	@Override
	public void onInitialize() {
		LOGGER.info("Ah bing bing bing");

		ModDataComponentTypes.registerModDataComponents();
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModEntities.registerModEntities();
		ModPackets.registerGlobalC2S();
		ModDataHandlers.registerModDataHandlers();
		ModRegistryKeys.registerModRegistryKeys();
	}
}