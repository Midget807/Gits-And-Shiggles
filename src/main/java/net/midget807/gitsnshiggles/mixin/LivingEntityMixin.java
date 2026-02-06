package net.midget807.gitsnshiggles.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.cca.InfinityGauntletComponent;
import net.midget807.gitsnshiggles.cca.TimeStopComponent;
import net.midget807.gitsnshiggles.cca.WizardVanishComponent;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.network.S2C.payload.SoulStonePayload;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.ModParticles;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.ModParticleUtil;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.midget807.gitsnshiggles.util.inject.SoulStoneRevive;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, RailgunRecoil {
    @Unique
    private boolean hasRailgunRecoil = false;
    @Unique
    private int recoilPower = 0;

    @Shadow public abstract void playSound(@Nullable SoundEvent sound);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow protected abstract SoundEvent getFallSound(int distance);

    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean isDead();

    @Shadow public abstract void onDeath(DamageSource damageSource);

    @Shadow protected abstract @Nullable SoundEvent getDeathSound();

    @Shadow protected abstract void playHurtSound(DamageSource damageSource);

    @Shadow
    public abstract Iterable<ItemStack> getHandItems();

    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow
    public abstract ItemStack getOffHandStack();

    @Shadow
    public abstract ItemStack getMainHandStack();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void setRailgunRecoil(boolean recoil) {
        this.hasRailgunRecoil = recoil;
    }

    @Override
    public boolean hasRailgunRecoil() {
        return this.hasRailgunRecoil;
    }

    @Override
    public void setRecoilPower(int power) {
        this.recoilPower = power;
    }

    @Override
    public int getRecoilPower() {
        return this.recoilPower;
    }

    @Inject(method = "baseTick", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$railgunDamage(CallbackInfo ci) {
        this.setNoGravity(this.hasStatusEffect(ModEffects.TIME_STOP));
        if (this.hasStatusEffect(ModEffects.TIME_STOP)) {
            this.setVelocity(Vec3d.ZERO);
            this.hasRailgunRecoil = false;
            ci.cancel();
            return;
        }
        this.getWorld().getProfiler().push("recoilDamage");
        if (this.isOnGround() && this.fallDistance > 0) {
            this.hasRailgunRecoil = false;
        }
        if (this.hasRailgunRecoil && this.getVelocity().length() == 0) {
            this.hasRailgunRecoil = false;
        }
        if (this.horizontalCollision || this.verticalCollision) {
            if (this.verticalCollision && this.isOnGround() && !this.horizontalCollision) {
                this.hasRailgunRecoil = false;
            }
            if (this.hasRailgunRecoil) {
                this.calculateRecoilDamage();
                this.hasRailgunRecoil = false;
            }
        }
        this.getWorld().getProfiler().pop();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void gitsnshiggles$tickVariables(CallbackInfo ci) {

    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$timeStopCancelsTravel(Vec3d movementInput, CallbackInfo ci) {
        TimeStopComponent timeStopComponent = TimeStopComponent.get((LivingEntity)((Object)this));
        if (timeStopComponent.getBool()) {
            ci.cancel();
            return;
        }
    }
    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$timeStopCancelsMovementInput(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        TimeStopComponent timeStopComponent = TimeStopComponent.get((LivingEntity)((Object)this));
        if (timeStopComponent.getBool()) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }

    @Unique
    private void calculateRecoilDamage() {
        this.damage(this.getDamageSources().flyIntoWall(), (float) (RailgunScalar.getScalar(this.recoilPower) * 4.0f));
        this.playSound(getFallSound(10));
    }

    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$moreItemsThatDisableShield(CallbackInfoReturnable<Boolean> cir) {
        if (this.getWeaponStack().isIn(ModItemTagProvider.DISABLES_SHIELD)) {
            cir.setReturnValue(Boolean.TRUE);
        }
    }

    @Unique
    private boolean tryUseSoulStoneRevive(DamageSource source) {
        InfinityGauntletComponent infinityGauntletComponent = InfinityGauntletComponent.get((LivingEntity)((Object)(this)));
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        } else {
            if (infinityGauntletComponent.getDoubleBool2()) {
                this.setHealth(1.0f);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                InfinityStoneUtil.setStoneCooldown((LivingEntity) ((Object)(this)), InfinityStoneUtil.Stones.SOUL);

                return true;
            } else {
                return !infinityGauntletComponent.getDoubleBool2();
            }
        }
    }
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isDead()Z", ordinal = 1, shift = At.Shift.BEFORE))
    private void gitsnshiggles$extraRevive(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) boolean bl2) {
        if (this.isDead()) {
            if (!tryUseSoulStoneRevive(source)) {
                if (bl2) {
                    this.playSound(this.getDeathSound());
                }
                this.onDeath(source);
            }
        } else if (bl2) {
            this.playHurtSound(source);
        }
    }

    @Unique
    private DamageSource lastSource;
    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void mischief$logSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.lastSource = source;
    }

    @WrapOperation(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"))
    private void mischief$lessKnockback(LivingEntity entity, double x, double y, double z, Operation<Void> operation) {
        if (this.lastSource instanceof DamageSource entityDamageSource) {
            if (entityDamageSource.getAttacker() instanceof ElfEntity) {
                return;
            }
        }
        operation.call(entity, x, y, z);
    }

    @WrapOperation(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"))
    private float gitsnshiggles$katanaExtraDamage(LivingEntity entity, DamageSource source, float amount, Operation<Float> original) {
        float base = (Float) original.call(entity, source, amount);
        if (source.getAttacker() instanceof PlayerEntity player && player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.KATANA)) {
            ItemStack katana = player.getStackInHand(Hand.MAIN_HAND);
            if (katana.get(ModDataComponentTypes.PARRY_DAMAGE) != null) {
                float katanaDamage = katana.getOrDefault(ModDataComponentTypes.PARRY_DAMAGE, 0.0f);
                katana.set(ModDataComponentTypes.PARRY_DAMAGE, 0.0f);
                return base + katanaDamage;
            }
        }
        return base;
    }

    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$untargetVanishedPlayers(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof PlayerEntity player) {
            WizardVanishComponent wizardVanishComponent = WizardVanishComponent.get(player);
            if (wizardVanishComponent.getBool()) {
                cir.setReturnValue(false);
            }
        }
    }

}
