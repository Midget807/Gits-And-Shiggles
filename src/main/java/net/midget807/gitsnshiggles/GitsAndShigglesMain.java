package net.midget807.gitsnshiggles;

import net.fabricmc.api.ModInitializer;
import net.midget807.gitsnshiggles.event.server.ModLootTableModifiers;
import net.midget807.gitsnshiggles.event.server.ServerWorldEventListener;
import net.midget807.gitsnshiggles.registry.ModArgumentTypes;
import net.midget807.gitsnshiggles.registry.ModBlocks;
import net.midget807.gitsnshiggles.registry.ModCauldronBehaviors;
import net.midget807.gitsnshiggles.registry.ModCommands;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModItemGroups;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.ModPackets;
import net.midget807.gitsnshiggles.registry.ModParticles;
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
        ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModEntities.registerModEntities();
		ModParticles.registerModParticles();
		ModEffects.registerModEffects();
		ModArgumentTypes.registerModArgumentTypes();
		ModCommands.registerModCommands();
		ModPackets.registerGlobalC2S();
        ModPackets.registerS2CWithoutNetworking();
        ModCauldronBehaviors.registerModCauldronBehaviors();

        ModLootTableModifiers.modifyLootTables();
        //ServerWorldEventListener.execute();

	}
}