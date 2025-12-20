package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModDamages {
    public static final RegistryKey<DamageType> FLAMETHROWER = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, GitsAndShigglesMain.id("flamethrower"));
    public static final RegistryKey<DamageType> RAILGUN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, GitsAndShigglesMain.id("railgun"));
    public static final RegistryKey<DamageType> ELF = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, GitsAndShigglesMain.id("elf"));

    public static DamageSource create(World world, RegistryKey<DamageType> damageType, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(damageType), source, attacker);
    }
    public static DamageSource create(World world, RegistryKey<DamageType> damageType, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(damageType), attacker);
    }
    public static DamageSource create(World world, RegistryKey<DamageType> damageType) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(damageType));
    }
}
