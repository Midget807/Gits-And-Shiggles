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
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.LEATHER_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.LEATHER)
                .input('H', ModItemTagProvider.SANTA_HATS)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion("has_santa_hats", conditionsFromTag(ModItemTagProvider.SANTA_HATS))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.LEATHER_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CHAINMAIL_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.IRON_NUGGET)
                .input('H', ModItemTagProvider.SANTA_HATS)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion("has_santa_hats", conditionsFromTag(ModItemTagProvider.SANTA_HATS))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.CHAINMAIL_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.IRON_INGOT)
                .input('H', ModItemTagProvider.SANTA_HATS)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion("has_santa_hats", conditionsFromTag(ModItemTagProvider.SANTA_HATS))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.IRON_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GOLD_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.GOLD_INGOT)
                .input('H', ModItemTagProvider.SANTA_HATS)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion("has_santa_hats", conditionsFromTag(ModItemTagProvider.SANTA_HATS))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.GOLD_SANTA_HAT)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIAMOND_SANTA_HAT, 1)
                .pattern("XXX")
                .pattern("XHX")
                .input('X', Items.DIAMOND)
                .input('H', ModItemTagProvider.SANTA_HATS)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion("has_santa_hats", conditionsFromTag(ModItemTagProvider.SANTA_HATS))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.DIAMOND_SANTA_HAT)));
        RecipeProvider.offerNetheriteUpgradeRecipe(recipeExporter, ModItems.DIAMOND_SANTA_HAT, RecipeCategory.COMBAT, ModItems.NETHERITE_SANTA_HAT);

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GUN_NUGGET, 1)
                .pattern("XXX")
                .pattern("X  ")
                .input('X', Items.IRON_NUGGET)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.GUN_NUGGET)));
        offerCompactingRecipe(recipeExporter, RecipeCategory.COMBAT, ModItems.GUN_INGOT, ModItems.GUN_NUGGET);
        offerCompactingRecipe(recipeExporter, RecipeCategory.COMBAT, ModItems.GUN_BLOCK, ModItems.GUN_INGOT);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.AM_RIFLE, 1)
                .pattern("XXX")
                .pattern("XN ")
                .input('X', ModItems.GUN_BLOCK)
                .input('N', ModItems.RED_HOT_NETHERITE_INGOT)
                .criterion(hasItem(ModItems.GUN_BLOCK), conditionsFromItem(ModItems.GUN_BLOCK))
                .criterion(hasItem(ModItems.RED_HOT_NETHERITE_INGOT), conditionsFromItem(ModItems.RED_HOT_NETHERITE_INGOT))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(ModItems.AM_RIFLE)));

        this.createLightsaberRecipe();
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 3000, Items.IRON_INGOT, ModItems.RED_HOT_IRON_INGOT, 0.5f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 6000, Items.NETHERITE_INGOT, ModItems.RED_HOT_NETHERITE_INGOT, 1.5f);
        offerCampfireHeatingRecipe(recipeExporter, "campfire_heating", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 6000, ModItems.KATANA_BLADE, ModItems.RED_HOT_KATANA_BLADE, 1.0f);
    }

    private static <T extends AbstractCookingRecipe> void offerCampfireHeatingRecipe(RecipeExporter recipeExporter, String cooker, RecipeSerializer<T> serializer, AbstractCookingRecipe.RecipeFactory<T> recipeFactory, int cookingTime, ItemConvertible items, ItemConvertible output, float experience) {
        CookingRecipeJsonBuilder.create(Ingredient.ofItems(items), RecipeCategory.TOOLS, output, experience, cookingTime, serializer, recipeFactory)
                .criterion(hasItem(items), conditionsFromItem(items))
                .offerTo(recipeExporter, Identifier.of(getRecipeName(output) + "_from_" + cooker));
    }

    private void createLightsaberRecipe() {

    }
}
