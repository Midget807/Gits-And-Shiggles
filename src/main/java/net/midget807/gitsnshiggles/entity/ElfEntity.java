package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.entity.goal.ElfMeleeAttackGoal;
import net.midget807.gitsnshiggles.entity.goal.FollowOwnerElfGoal;
import net.midget807.gitsnshiggles.entity.goal.PickupItemGoal;
import net.midget807.gitsnshiggles.registry.ModDamages;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.util.inject.ElfCount;
import net.midget807.gitsnshiggles.util.state.ElfCountState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class ElfEntity extends TameableEntity implements Angerable, Tameable {
    public static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (itemEntity) -> !itemEntity.cannotPickup() && itemEntity.isAlive();
    public static final int MAX_ELF_COUNT = 30;
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(ElfEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private static final TrackedData<Integer> SLOT = DataTracker.registerData(ElfEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final float MAX_HEALTH = 10.0f;
    @Nullable
    private UUID angryAt;
    public Goal goal;
    public int goalTimer = 0;

    public ElfEntity(World world) {
        super(ModEntities.ELF, world);
    }

    public ElfEntity(World world, PlayerEntity owner) {
        this(world);
        this.setOwner(owner);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new ElfMeleeAttackGoal(this, 1.0d, true));
        this.goalSelector.add(2, new PickupItemGoal(this));
        this.goalSelector.add(6, new FollowOwnerElfGoal(this, 1.0D, 20.0F, 2.0F, false));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANGER_TIME, 0);
        builder.add(SLOT, 0);
    }

    public static DefaultAttributeContainer.Builder createElfAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.getWorld(), nbt);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
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
        if (this.getOwner() == null) {
            this.discard();
        }
        super.tick();

        if (this.hasStatusEffect(StatusEffects.SATURATION)) {
            StatusEffectInstance saturation = this.getStatusEffect(StatusEffects.SATURATION);
            if (saturation != null)
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, saturation.getDuration(), saturation.getAmplifier(), saturation.isAmbient(), saturation.shouldShowParticles(), saturation.shouldShowIcon()));
        }

    }

    @Override
    protected void mobTick() {
        if (this.isSitting() && !this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        // reset
        if (this.goalTimer <= 0 && this.goal != null) {
            this.removeCurrentGoal();
        }

        if (this.goal != null && this.goalTimer > 0) {
            this.goalTimer--;
        }
    }
    @Override
    public boolean canTarget(EntityType<?> type) {
        return type != EntityType.CREEPER;
    }

    @Override
    protected void tickStatusEffects() {
        super.tickStatusEffects();
    }


    @Override
    public void onDeath(DamageSource damageSource) {
        if (this.getServer() != null) {
            ElfCountState state = ElfCountState.getServerState(this.getServer());
            if (state.elfCount > 0) {
                state.elfCount--;
                state.markDirty();
            }
            if (state.elfCount < 0) {
                state.elfCount = 0;
                state.markDirty();
            }
        }
        super.onDeath(damageSource);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.COOKIE);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        ElfEntity elfEntity = ModEntities.ELF.create(world);
        if (elfEntity != null && entity instanceof ElfEntity elfEntity2) {

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

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (fallDistance > 20 && !this.isBaby()) {
            this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1.0f, (float) (1.0f + this.random.nextGaussian() / 10f));
        }
        return false;
    }
    public void setGoal(Goal goal) {
        this.removeCurrentGoal();
        this.goalTimer = 300;
        this.goal = goal;
        this.goalSelector.add(4, goal);
    }

    public void removeCurrentGoal() {
        this.goalSelector.remove(this.goal);
        this.goal = null;
    }

    @Override
    public boolean tryAttack(Entity target) {
        float damage = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        target.timeUntilRegen = 0;
        if (target.damage(ModDamages.create(this.getWorld(), ModDamages.ELF), damage)) {
            this.onAttacking(target);
            return true;
        }
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return super.canHaveStatusEffect(effect);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            this.setSitting(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }
            return super.damage(source, amount);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource instanceof DamageSource entityDamageSource) {
            if (entityDamageSource.getAttacker() == this.getOwner()) {
                return true;
            }
        }
        if (damageSource == this.getDamageSources().genericKill()) {
            return false;
        }
        if (damageSource == this.getDamageSources().cactus() || damageSource == this.getDamageSources().sweetBerryBush() || damageSource.getAttacker() instanceof EnderDragonEntity || damageSource == this.getDamageSources().cramming()) {
            return true;
        } else {
            return super.isInvulnerableTo(damageSource);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
    }

    @Override
    protected void pushAway(Entity entity) {
        this.pushAwayFrom(entity);
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.getMainHandStack().isEmpty()) {
            this.equipStack(EquipmentSlot.MAINHAND, itemStack);
            this.triggerItemPickedUpByEntityCriteria(item);
            this.sendPickup(item, itemStack.getCount());
            item.remove(RemovalReason.DISCARDED);
        }
    }

}
