package net.midget807.gitsnshiggles.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModFluidTags extends FabricTagProvider.FluidTagProvider {
    public static final TagKey<Fluid> ETHANOL_EXTRACTOR = TagKey.of(RegistryKeys.FLUID, GitsAndShigglesMain.id("extracts_with_ethanol"));

    public ModFluidTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(ETHANOL_EXTRACTOR)
                .addOptional(Identifier.of("brewinandchewin:vodka"))
                .addOptionalTag(Identifier.of("c:ethanol"));
    }
}
