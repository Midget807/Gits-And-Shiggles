package net.midget807.gitsnshiggles.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.midget807.gitsnshiggles.util.inject.SoulStoneRevive;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
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
public abstract class LivingEntityMixin extends Entity implements Attackable, RailgunRecoil, SoulStoneRevive, InfinityStoneCooldown {
    @Unique
    private boolean hasRailgunRecoil = false;
    @Unique
    private int recoilPower = 0;

    @Unique
    private boolean isReviveAvailable = true;

    @Unique
    private int powerStoneCD = 0;
    @Unique
    private int spaceStoneCD = 0;
    @Unique
    private int realityStoneCD = 0;
    @Unique
    private int soulStoneCD = 0;
    @Unique
    private int timeStoneCD = 0;
    @Unique
    private int mindStoneCD = 0;

    @Override
    public boolean isReviveAvailable() {
        return this.isReviveAvailable;
    }
    @Override
    public void setReviveAvailable(boolean reviveAvailable) {
        this.isReviveAvailable = reviveAvailable;
    }

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

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void gitsnshiggles$railgunDamage(CallbackInfo ci) {
        this.getWorld().getProfiler().push("recoilDamage");

        if (this.isOnGround() && this.fallDistance > 0) {
            this.hasRailgunRecoil = false;
        }
        if (this.hasRailgunRecoil && (this.horizontalCollision || this.verticalCollision)) {
            this.calculateRecoilDamage();
            this.hasRailgunRecoil = false;
        }
        this.getWorld().getProfiler().pop();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void gitsnshiggles$tickVariables(CallbackInfo ci) {
        if (this.soulStoneCD > 0) {
            this.soulStoneCD--;
        } else if (this.soulStoneCD < 0) {
            this.soulStoneCD = 0;
        }
        this.isReviveAvailable = this.soulStoneCD <= 0;
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
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        } else {
            if (this.isReviveAvailable()) {
                this.setHealth(1.0f);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                this.soulStoneCD = 60 * 60 * 20;
                return true;
            } else {
                return !this.isReviveAvailable();
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

}
