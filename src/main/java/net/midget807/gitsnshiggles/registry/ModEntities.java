package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.entity.RailgunBulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {

    public static final EntityType<RailgunBulletEntity> RAILGUN_BULLET = register(
            "railgun_bullet",
            EntityType.Builder.<RailgunBulletEntity>create((entityType, world) -> new RailgunBulletEntity(world), SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .maxTrackingRange(12)
                    .trackingTickInterval(10)
    );

    public static final EntityType<ElfEntity> ELF = register(
            "elf",
            EntityType.Builder.<ElfEntity>create((entityType, world) -> new ElfEntity(world), SpawnGroup.MISC)
                    .dimensions(0.25f, 1.0f)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, GitsAndShigglesMain.id(name), type.build());
    }

    public static void registerModEntities() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Entities");
    }
}
