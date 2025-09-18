package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static final SimpleParticleType TIME_STONE_RINGS = registerSimpleParticleType("time_stone_rings", true);

    private static SimpleParticleType registerSimpleParticleType(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, GitsAndShigglesMain.id(name), FabricParticleTypes.simple(alwaysShow));
    }

    public static void registerModParticles() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Particles");
    }
}
