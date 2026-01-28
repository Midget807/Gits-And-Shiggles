package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.fluid.EthanolFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModFluids {
    public static final FlowableFluid ETHANOL_STILL = registerFluid("ethanol", new EthanolFluid.Still());
    public static final FlowableFluid ETHANOL_FLOWING = registerFluid("ethanol_flowing", new EthanolFluid.Flowing());

    private static <T extends Fluid> T registerFluid(String name, T fluid) {
        return Registry.register(Registries.FLUID, GitsAndShigglesMain.id(name), fluid);
    }

    public static void registerModFluids() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Fluids");
    }
}
