package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        /** Santa */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SANTA_HAT, 1)
                .pattern("W")
                .pattern("R")
                .input('W', Items.WHITE_WOOL)
                .input('R', Items.RED_WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .criterion(hasItem(Items.RED_WOOL), conditionsFromItem(Items.RED_WOOL))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.LEATHER_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.LEATHER)
                .input('H', ModItems.SANTA_HAT)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(ModItems.SANTA_HAT), conditionsFromItem(ModItems.SANTA_HAT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.LEATHER_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHAINMAIL_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.IRON_NUGGET)
                .input('H', ModItems.LEATHER_SANTA_HAT)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(ModItems.LEATHER_SANTA_HAT), conditionsFromItem(ModItems.LEATHER_SANTA_HAT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.CHAINMAIL_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GOLD_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.GOLD_INGOT)
                .input('H', ModItems.CHAINMAIL_SANTA_HAT)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModItems.CHAINMAIL_SANTA_HAT), conditionsFromItem(ModItems.CHAINMAIL_SANTA_HAT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.GOLD_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.IRON_INGOT)
                .input('H', ModItems.GOLD_SANTA_HAT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.GOLD_SANTA_HAT), conditionsFromItem(ModItems.GOLD_SANTA_HAT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.IRON_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIAMOND_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.DIAMOND)
                .input('H', ModItems.IRON_SANTA_HAT)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(ModItems.IRON_SANTA_HAT), conditionsFromItem(ModItems.IRON_SANTA_HAT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.DIAMOND_SANTA_HAT)));
        RecipeProvider.offerNetheriteUpgradeRecipe(recipeExporter, ModItems.DIAMOND_SANTA_HAT, RecipeCategory.COMBAT, ModItems.NETHERITE_SANTA_HAT);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SACK, 1)
                .pattern("SLS")
                .pattern("L L")
                .pattern("LLL")
                .input('S', Items.STRING)
                .input('L', Items.LEATHER)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.SACK)));

        /** Katana */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.KATANA_HILT, 1)
                .pattern(" G ")
                .pattern("LWG")
                .pattern("WL ")
                .input('G', Items.GOLD_INGOT)
                .input('W', ItemTags.PLANKS)
                .input('L', Items.LEATHER)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.KATANA_HILT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RED_HOT_KATANA_BLADE, 1)
                .pattern(" IN")
                .pattern("INI")
                .pattern("NI ")
                .input('I', ModItems.RED_HOT_IRON_INGOT)
                .input('N', ModItems.RED_HOT_NETHERITE_INGOT)
                .criterion(hasItem(ModItems.RED_HOT_IRON_INGOT), conditionsFromItem(ModItems.RED_HOT_IRON_INGOT))
                .criterion(hasItem(ModItems.RED_HOT_NETHERITE_INGOT), conditionsFromItem(ModItems.RED_HOT_NETHERITE_INGOT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.RED_HOT_KATANA_BLADE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.KATANA, 1)
                .pattern(" B")
                .pattern("H ")
                .input('B', ModItems.KATANA_BLADE)
                .input('H', ModItems.KATANA_HILT)
                .criterion(hasItem(ModItems.KATANA_BLADE), conditionsFromItem(ModItems.KATANA_BLADE))
                .criterion(hasItem(ModItems.KATANA_HILT), conditionsFromItem(ModItems.KATANA_HILT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.KATANA)));

        /** AM Rifle */
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GUN_NUGGET, 1)
                .pattern("XXX")
                .pattern("X  ")
                .input('X', Items.IRON_NUGGET)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.GUN_NUGGET)));
        offerCompactingRecipe(recipeExporter, RecipeCategory.MISC, ModItems.GUN_INGOT, ModItems.GUN_NUGGET);
        offerCompactingRecipe(recipeExporter, RecipeCategory.MISC, ModItems.GUN_BLOCK, ModItems.GUN_INGOT);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.AM_RIFLE, 1)
                .pattern("XXX")
                .pattern("XN ")
                .input('X', ModItems.GUN_BLOCK)
                .input('N', ModItems.RED_HOT_NETHERITE_INGOT)
                .criterion(hasItem(ModItems.GUN_BLOCK), conditionsFromItem(ModItems.GUN_BLOCK))
                .criterion(hasItem(ModItems.RED_HOT_NETHERITE_INGOT), conditionsFromItem(ModItems.RED_HOT_NETHERITE_INGOT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.AM_RIFLE)));

        /** Flamethrower */
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FUEL_TANK, 1)
                .pattern(" IN")
                .pattern("I I")
                .pattern("II ")
                .input('I', Items.IRON_INGOT)
                .input('N', Items.IRON_NUGGET)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.FUEL_TANK)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FLAMETHROWER, 1)
                .pattern("KT ")
                .pattern("BFC")
                .pattern("C  ")
                .input('K', Items.DRIED_KELP)
                .input('F', Items.FLINT_AND_STEEL)
                .input('B', Items.COPPER_BLOCK)
                .input('C', Items.COPPER_INGOT)
                .input('T', ModItems.FUEL_TANK)
                .criterion(hasItem(Items.DRIED_KELP), conditionsFromItem(Items.DRIED_KELP))
                .criterion(hasItem(Items.FLINT_AND_STEEL), conditionsFromItem(Items.FLINT_AND_STEEL))
                .criterion(hasItem(Items.COPPER_BLOCK), conditionsFromItem(Items.COPPER_BLOCK))
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(ModItems.FUEL_TANK), conditionsFromItem(ModItems.FUEL_TANK))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.FLAMETHROWER)));

        /** Wizard Robes */
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MAGIC_CLOTH, 1)
                .pattern("ESE")
                .pattern("XXX")
                .pattern("XXX")
                .input('E', Items.ENDER_EYE)
                .input('S', Items.STICK)
                .input('X', ModItems.VOID_STRING)
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.VOID_STRING), conditionsFromItem(ModItems.VOID_STRING))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.MAGIC_CLOTH)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WIZARD_HAT, 1)
                .pattern("  C")
                .pattern(" C ")
                .pattern("CCC")
                .input('C', ModItems.MAGIC_CLOTH)
                .criterion(hasItem(ModItems.MAGIC_CLOTH), conditionsFromItem(ModItems.MAGIC_CLOTH))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.WIZARD_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WIZARD_ROBE, 1)
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .input('C', ModItems.MAGIC_CLOTH)
                .criterion(hasItem(ModItems.MAGIC_CLOTH), conditionsFromItem(ModItems.MAGIC_CLOTH))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.WIZARD_ROBE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WIZARD_PANTS, 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .input('C', ModItems.MAGIC_CLOTH)
                .criterion(hasItem(ModItems.MAGIC_CLOTH), conditionsFromItem(ModItems.MAGIC_CLOTH))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.WIZARD_PANTS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WIZARD_BOOTS, 1)
                .pattern("C C")
                .pattern("C C")
                .input('C', ModItems.MAGIC_CLOTH)
                .criterion(hasItem(ModItems.MAGIC_CLOTH), conditionsFromItem(ModItems.MAGIC_CLOTH))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.WIZARD_BOOTS)));

        /** Trident */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.INVERTED_TRIDENT, 1)
                .pattern("  S")
                .pattern("TH ")
                .pattern("TT ")
                .input('S', Items.PRISMARINE_SHARD)
                .input('H', Items.HEART_OF_THE_SEA)
                .input('T', ModItems.ELDER_GUARDIAN_THORN)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .criterion(hasItem(Items.HEART_OF_THE_SEA), conditionsFromItem(Items.HEART_OF_THE_SEA))
                .criterion(hasItem(ModItems.ELDER_GUARDIAN_THORN), conditionsFromItem(ModItems.ELDER_GUARDIAN_THORN))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.INVERTED_TRIDENT)));

        /** Lightsaber */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.LIGHTSABER_BLACK, 1)
                .pattern("IKI")
                .pattern("INI")
                .input('I', Items.IRON_INGOT)
                .input('N', ModItems.RED_HOT_NETHERITE_INGOT)
                .input('K', ModItems.KYBER_CRYSTAL)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.RED_HOT_NETHERITE_INGOT), conditionsFromItem(ModItems.RED_HOT_NETHERITE_INGOT))
                .criterion(hasItem(ModItems.KYBER_CRYSTAL), conditionsFromItem(ModItems.KYBER_CRYSTAL))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.LIGHTSABER_BLACK)));
        this.createLightsaberRecipe();

        /** Railgun */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RAILGUN, 1)
                .pattern("DNP")
                .pattern("ARA")
                .pattern("PND")
                .input('R', ModItems.AM_RIFLE)
                .input('D', Items.DETECTOR_RAIL)
                .input('N', Items.RAIL)
                .input('P', Items.POWERED_RAIL)
                .input('A', Items.ACTIVATOR_RAIL)
                .criterion(hasItem(ModItems.AM_RIFLE), conditionsFromItem(ModItems.AM_RIFLE))
                .criterion(hasItem(Items.DETECTOR_RAIL), conditionsFromItem(Items.DETECTOR_RAIL))
                .criterion(hasItem(Items.RAIL), conditionsFromItem(Items.RAIL))
                .criterion(hasItem(Items.POWERED_RAIL), conditionsFromItem(Items.POWERED_RAIL))
                .criterion(hasItem(Items.ACTIVATOR_RAIL), conditionsFromItem(Items.ACTIVATOR_RAIL))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.RAILGUN)));

        /** Random Egg */
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RANDOM_EGG, 1)
                .pattern("WGT")
                .pattern("EXH")
                .pattern("FGS")
                .input('X', Items.EGG)
                .input('G', Items.ENCHANTED_GOLDEN_APPLE)
                .input('W', Items.WIND_CHARGE)
                .input('T', Items.TURTLE_HELMET)
                .input('E', Items.ENDER_EYE)
                .input('H', Items.HEART_OF_THE_SEA)
                .input('F', Items.RABBIT_FOOT)
                .input('S', Items.NETHER_STAR)
                .criterion(hasItem(Items.EGG), conditionsFromItem(Items.EGG))
                .criterion(hasItem(Items.ENCHANTED_GOLDEN_APPLE), conditionsFromItem(Items.ENCHANTED_GOLDEN_APPLE))
                .criterion(hasItem(Items.WIND_CHARGE), conditionsFromItem(Items.WIND_CHARGE))
                .criterion(hasItem(Items.TURTLE_HELMET), conditionsFromItem(Items.TURTLE_HELMET))
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .criterion(hasItem(Items.HEART_OF_THE_SEA), conditionsFromItem(Items.HEART_OF_THE_SEA))
                .criterion(hasItem(Items.RABBIT_FOOT), conditionsFromItem(Items.RABBIT_FOOT))
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.RANDOM_EGG)));

        /** Infinity Gauntlet */
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.EMPTY_STONE, 1)
                .pattern("DCD")
                .pattern("CXC")
                .pattern("DCD")
                .input('X', Items.NETHER_STAR)
                .input('C', Items.CRYING_OBSIDIAN)
                .input('D', Items.DIAMOND_BLOCK)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .criterion(hasItem(Items.CRYING_OBSIDIAN), conditionsFromItem(Items.CRYING_OBSIDIAN))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.EMPTY_STONE)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RED_HOT_GOLD_ALLOY_INGOT, 2)
                .input(ModItems.RED_HOT_GOLD_INGOT, 8)
                .input(ModItems.RED_HOT_NETHERITE_INGOT)
                .criterion(hasItem(ModItems.RED_HOT_GOLD_INGOT), conditionsFromItem(ModItems.RED_HOT_GOLD_INGOT))
                .criterion(hasItem(ModItems.RED_HOT_NETHERITE_INGOT), conditionsFromItem(ModItems.RED_HOT_NETHERITE_INGOT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.RED_HOT_GOLD_ALLOY_INGOT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GAUNTLET, 1)
                .pattern("III")
                .pattern(" P ")
                .pattern("P P")
                .input('I', ModItems.GOLD_ALLOY_INGOT)
                .input('P', ModItems.GOLD_ALLOY_PLATE)
                .criterion(hasItem(ModItems.GOLD_ALLOY_INGOT), conditionsFromItem(ModItems.GOLD_ALLOY_INGOT))
                .criterion(hasItem(ModItems.GOLD_ALLOY_PLATE), conditionsFromItem(ModItems.GOLD_ALLOY_PLATE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.GAUNTLET)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.INFINITY_GAUNTLET)
                .input(ModItems.GAUNTLET)
                .input(ModItems.POWER_STONE)
                .input(ModItems.SPACE_STONE)
                .input(ModItems.REALITY_STONE)
                .input(ModItems.SOUL_STONE)
                .input(ModItems.TIME_STONE)
                .input(ModItems.MIND_STONE)
                .criterion(hasItem(ModItems.GAUNTLET), conditionsFromItem(ModItems.GAUNTLET))
                .criterion(hasItem(ModItems.POWER_STONE), conditionsFromItem(ModItems.POWER_STONE))
                .criterion(hasItem(ModItems.SPACE_STONE), conditionsFromItem(ModItems.SPACE_STONE))
                .criterion(hasItem(ModItems.REALITY_STONE), conditionsFromItem(ModItems.REALITY_STONE))
                .criterion(hasItem(ModItems.SOUL_STONE), conditionsFromItem(ModItems.SOUL_STONE))
                .criterion(hasItem(ModItems.TIME_STONE), conditionsFromItem(ModItems.TIME_STONE))
                .criterion(hasItem(ModItems.MIND_STONE), conditionsFromItem(ModItems.MIND_STONE))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.INFINITY_GAUNTLET)));

        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 3000, Items.IRON_INGOT, ModItems.RED_HOT_IRON_INGOT, 0.5f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 6000, Items.NETHERITE_INGOT, ModItems.RED_HOT_NETHERITE_INGOT, 1.5f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 6000, ModItems.KATANA_BLADE, ModItems.RED_HOT_KATANA_BLADE, 1.0f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 3000, Items.GOLD_INGOT, ModItems.RED_HOT_GOLD_INGOT, 0.5f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 6000, ModItems.GOLD_ALLOY_INGOT, ModItems.RED_HOT_GOLD_ALLOY_INGOT, 1.0f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 6000, ModItems.GOLD_ALLOY_PLATE, ModItems.RED_HOT_GOLD_ALLOY_PLATE, 1.0f);
    }

    private static <T extends AbstractCookingRecipe> void offerCampfireHeatingRecipe(RecipeExporter recipeExporter, String cooker, RecipeSerializer<T> serializer, AbstractCookingRecipe.RecipeFactory<T> recipeFactory, int cookingTime, ItemConvertible items, ItemConvertible output, float experience) {
        CookingRecipeJsonBuilder.create(Ingredient.ofItems(items), RecipeCategory.TOOLS, output, experience, cookingTime, serializer, recipeFactory)
                .criterion(hasItem(items), conditionsFromItem(items))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(output) + "_from_" + cooker));
    }

    private void createLightsaberRecipe() {

    }
}
