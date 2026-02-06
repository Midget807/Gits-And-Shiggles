package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.cca.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModCCAComponents implements EntityComponentInitializer {
    public static final ComponentKey<ElfCountComponent> ELF_COUNT = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("elf_count"), ElfCountComponent.class);
    public static final ComponentKey<WizardVanishComponent> WIZARD_VANISH = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("wizard_vanish"), WizardVanishComponent.class);
    public static final ComponentKey<FlamethrowerComponent> FLAMETHROWER = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("flamethrower"), FlamethrowerComponent.class);
    public static final ComponentKey<DiceRollComponent> DICE_ROLL = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("dice_roll"), DiceRollComponent.class);
    public static final ComponentKey<KatanaBlockingComponent> BLOCKING = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("blocking"), KatanaBlockingComponent.class);
    public static final ComponentKey<TimeStopComponent> TIME_STOP = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("time_stop"), TimeStopComponent.class);
    public static final ComponentKey<InfinityGauntletComponent> INFINITY_GAUNTLET = ComponentRegistry.getOrCreate(GitsAndShigglesMain.id("infinity_gauntlet"), InfinityGauntletComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, ELF_COUNT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(ElfCountComponent::new);
        registry.beginRegistration(PlayerEntity.class, WIZARD_VANISH).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(WizardVanishComponent::new);
        registry.beginRegistration(PlayerEntity.class, FLAMETHROWER).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(FlamethrowerComponent::new);
        registry.beginRegistration(PlayerEntity.class, DICE_ROLL).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(DiceRollComponent::new);
        registry.beginRegistration(PlayerEntity.class, BLOCKING).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(KatanaBlockingComponent::new);
        registry.beginRegistration(Entity.class, TIME_STOP).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(TimeStopComponent::new);
        registry.beginRegistration(LivingEntity.class, INFINITY_GAUNTLET).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(InfinityGauntletComponent::new);
    }
}
