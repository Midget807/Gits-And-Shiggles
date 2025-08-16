package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.*;
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
                    .eyeHeight(0.13f)
                    .maxTrackingRange(64)
                    .trackingTickInterval(1)
    );

    public static final EntityType<ElfEntity> ELF = register(
            "elf",
            EntityType.Builder.<ElfEntity>create((entityType, world) -> new ElfEntity(world), SpawnGroup.MISC)
                    .dimensions(0.25f, 1.0f)
                    .maxTrackingRange(10)
    );

    public static final EntityType<AreaDamageEntity> AREA_DAMAGE = register(
            "area_damage",
            EntityType.Builder.<AreaDamageEntity>create((entityType, world) -> new AreaDamageEntity(world), SpawnGroup.MISC)
                    .makeFireImmune()
                    .dimensions(7.0f, 0.5f)
                    .maxTrackingRange(10)
                    .trackingTickInterval(Integer.MAX_VALUE)
    );

    public static final EntityType<FlamethrowerFireEntity> FLAMETHROWER_FIRE = register(
            "flamethrower_fire",
            EntityType.Builder.<FlamethrowerFireEntity>create((entityType, world) -> new FlamethrowerFireEntity(world), SpawnGroup.MISC)
                    .makeFireImmune()
                    .dimensions(0.25f, 1.0f)
                    .maxTrackingRange(8)
                    .trackingTickInterval(20)
    );

    public static final EntityType<RandomEggEntity> RANDOM_EGG = register(
            "random_egg",
            EntityType.Builder.<RandomEggEntity>create((entityType, world) -> new RandomEggEntity(world), SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .makeFireImmune()
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
