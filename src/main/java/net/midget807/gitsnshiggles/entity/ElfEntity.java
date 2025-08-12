package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModDataHandlers;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModRegistryKeys;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class ElfEntity extends TameableEntity implements Angerable, VariantHolder<RegistryEntry<ElfVariant>> {
    public static final RegistryKey<ElfVariant> DEFAULT = RegistryKey.of(ModRegistryKeys.ELF_VARIANT, GitsAndShigglesMain.id("default"));
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(ElfEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private static final TrackedData<RegistryEntry<ElfVariant>> VARIANT = DataTracker.registerData(ElfEntity.class, ModDataHandlers.ELF_VARIANT);
    private static final float MAX_HEALTH = 10.0f;
    @Nullable
    private UUID angryAt;

    public ElfEntity(World world) {
        super(ModEntities.ELF, world);
    }

    @Override
    protected void initGoals() {

    }

    public Identifier getTextureId() {
        ElfVariant elfVariant = this.getVariant().value();
        if (this.isTamed()) {
            return elfVariant.getTameTextureId();
        } else {
            return this.hasAngerTime() ? elfVariant.getAngryTextureId() : elfVariant.getTameTextureId();
        }

    }

    @Override
    public void setVariant(RegistryEntry<ElfVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    public static DefaultAttributeContainer.Builder createElfAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.5f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        DynamicRegistryManager dynamicRegistryManager = this.getRegistryManager();
        Registry<ElfVariant> registry = dynamicRegistryManager.get(ModRegistryKeys.ELF_VARIANT); //todo fix
        builder.add(VARIANT, (RegistryEntry<ElfVariant>) registry.getEntry(DEFAULT).or(registry::getDefaultEntry).orElseThrow());
        builder.add(ANGER_TIME, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.getVariant().getKey().ifPresent(registryKey -> nbt.putString("variant", registryKey.getValue().toString()));
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Optional.ofNullable(Identifier.tryParse(nbt.getString("variant")))
                .map(variantId -> RegistryKey.of(ModRegistryKeys.ELF_VARIANT, variantId))
                .flatMap(variantKey -> this.getRegistryManager().get(ModRegistryKeys.ELF_VARIANT).getEntry(variantKey))
                .ifPresent(this::setVariant);
        this.readAngerFromNbt(this.getWorld(), nbt);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        RegistryEntry<ElfVariant> registryEntry;
        if (entityData instanceof ElfEntity.ElfData elfData) {
            registryEntry = elfData.variant;
        } else {
            Registry<ElfVariant> registry = this.getRegistryManager().get(ModRegistryKeys.ELF_VARIANT);
            registryEntry = registry.getEntry(RegistryKey.of(ModRegistryKeys.ELF_VARIANT, GitsAndShigglesMain.id("default"))).or(registry::getDefaultEntry).orElseThrow();
            entityData = new ElfEntity.ElfData(registryEntry);
        }
        this.setVariant(registryEntry);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if (this.hasAngerTime()) {
            return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
        } else {
            return SoundEvents.ENTITY_VILLAGER_AMBIENT;
        }
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3f;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
        }
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
    }

    @Override
    public RegistryEntry<ElfVariant> getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.COOKIE);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        ElfEntity elfEntity = ModEntities.ELF.create(world);
        if (elfEntity != null && entity instanceof ElfEntity elfEntity2) {
            if (this.random.nextBoolean()) {
                elfEntity.setVariant(this.getVariant());
            } else {
                elfEntity.setVariant(elfEntity2.getVariant());
            }
            if (this.isTamed()) {
                elfEntity.setOwnerUuid(this.getOwnerUuid());
                elfEntity.setTamed(true, true);
            }
        }
        return elfEntity;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    @Override
    public @Nullable UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof CreeperEntity || target instanceof GhastEntity || target instanceof ArmorStandEntity) {
            return false;
        } else if (target instanceof ElfEntity elfEntity) {
            return !elfEntity.isTamed() || elfEntity.getOwner() != owner;
        } else if (target instanceof PlayerEntity playerEntity && owner instanceof PlayerEntity playerEntity2 && !playerEntity2.shouldDamagePlayer(playerEntity)) {
            return false;
        } else {
            return target instanceof AbstractHorseEntity abstractHorseEntity && abstractHorseEntity.isTame()
                    ? false
                    : !(target instanceof TameableEntity tameableEntity && tameableEntity.isTamed());
        }
    }

    @Override
    public boolean canBeLeashed() {
        return !this.hasAngerTime();
    }

    @Override
    protected Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.6F * this.getStandingEyeHeight(), this.getWidth() * 0.4F);
    }

    public static class ElfData extends PassiveEntity.PassiveData {
        public final RegistryEntry<ElfVariant> variant;

        public ElfData(RegistryEntry<ElfVariant> variant) {
            super(false);
            this.variant = variant;
        }
    }
}
