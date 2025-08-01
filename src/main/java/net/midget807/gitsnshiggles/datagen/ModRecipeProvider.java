package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
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


    }
}
