package net.midget807.gitsnshiggles.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.network.S2C.payload.SoulStonePayload;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
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
    private boolean isReviveAvailable;

    @Unique
    private int powerStoneCD;
    @Unique
    private int spaceStoneCD;
    @Unique
    private int realityStoneCD;
    @Unique
    private int soulStoneCD;
    @Unique
    private int timeStoneCD;
    @Unique
    private int mindStoneCD;

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

    @Shadow
    public abstract Iterable<ItemStack> getHandItems();

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

    @Override
    public boolean isPowerStoneOnCD() {
        return this.powerStoneCD > 0;
    }
    @Override
    public boolean isSpaceStoneOnCD() {
        return this.spaceStoneCD > 0;
    }
    @Override
    public boolean isRealityStoneOnCD() {
        return this.realityStoneCD > 0;
    }
    @Override
    public boolean isSoulStoneOnCD() {
        return this.soulStoneCD > 0;
    }
    @Override
    public boolean isTimeStoneOnCD() {
        return this.timeStoneCD > 0;
    }
    @Override
    public boolean isMindStoneOnCD() {
        return this.mindStoneCD > 0;
    }

    @Override
    public void setPowerStoneCD(int powerStoneCD) {
        this.powerStoneCD = powerStoneCD;
    }
    @Override
    public void setSpaceStoneCD(int spaceStoneCD) {
        this.spaceStoneCD = spaceStoneCD;
    }
    @Override
    public void setRealityStoneCD(int realityStoneCD) {
        this.realityStoneCD = realityStoneCD;
    }
    @Override
    public void setSoulStoneCD(int soulStoneCD) {
        this.soulStoneCD = soulStoneCD;
    }
    @Override
    public void setTimeStoneCD(int timeStoneCD) {
        this.timeStoneCD = timeStoneCD;
    }
    @Override
    public void setMindStoneCD(int mindStoneCD) {
        this.mindStoneCD = mindStoneCD;
    }

    @Override
    public int getPowerStoneCD() {
        return this.powerStoneCD;
    }
    @Override
    public int getSpaceStoneCD() {
        return this.spaceStoneCD;
    }
    @Override
    public int getRealityStoneCD() {
        return this.realityStoneCD;
    }
    @Override
    public int getSoulStoneCD() {
        return this.soulStoneCD;
    }
    @Override
    public int getTimeStoneCD() {
        return this.timeStoneCD;
    }
    @Override
    public int getMindStoneCD() {
        return this.mindStoneCD;
    }

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void gitsnshiggles$railgunDamage(CallbackInfo ci) {
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
        if (this.powerStoneCD > 0) {
            this.powerStoneCD--;
        } else if (powerStoneCD < 0) {
            this.powerStoneCD = 0;
        }
        if (this.spaceStoneCD > 0) {
            this.spaceStoneCD--;
        } else if (spaceStoneCD < 0) {
            this.spaceStoneCD = 0;
        }
        if (this.realityStoneCD > 0) {
            this.realityStoneCD--;
        } else if (realityStoneCD < 0) {
            this.realityStoneCD = 0;
        }
        if (this.soulStoneCD > 0) {
            this.soulStoneCD--;
        } else if (this.soulStoneCD < 0) {
            this.soulStoneCD = 0;
        }
        this.getHandItems().forEach(itemStack -> {
            if (itemStack.getItem() instanceof InfinityGauntletItem gauntletItem) {
                this.isReviveAvailable = gauntletItem.realityStoneCD == 0;
            } else {
                this.isReviveAvailable = false;
            }
        });
        if (this.timeStoneCD > 0) {
            this.timeStoneCD--;
        } else if (timeStoneCD < 0) {
            this.timeStoneCD = 0;
        }
        if (this.mindStoneCD > 0) {
            this.mindStoneCD--;
        } else if (mindStoneCD < 0) {
            this.mindStoneCD = 0;
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
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        } else {
            if (this.isReviveAvailable()) {
                this.setHealth(1.0f);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                if (((LivingEntity)((Object)this)) instanceof ServerPlayerEntity player) {
                    InfinityStoneUtil.setStoneCooldown(player, InfinityStoneUtil.Stones.SOUL);
                    ServerPlayNetworking.send(player, new SoulStonePayload(this.soulStoneCD));
                }
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

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void gitsnshiggles$writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt(InfinityStoneUtil.POWER_STONE_CD_KEY, this.powerStoneCD);
        nbt.putInt(InfinityStoneUtil.SPACE_STONE_CD_KEY, this.spaceStoneCD);
        nbt.putInt(InfinityStoneUtil.REALITY_STONE_CD_KEY, this.realityStoneCD);
        nbt.putInt(InfinityStoneUtil.SOUL_STONE_CD_KEY, this.soulStoneCD);
        nbt.putInt(InfinityStoneUtil.TIME_STONE_CD_KEY, this.timeStoneCD);
        nbt.putInt(InfinityStoneUtil.MIND_STONE_CD_KEY, this.mindStoneCD);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void gitsnshiggles$readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.powerStoneCD = nbt.getInt(InfinityStoneUtil.POWER_STONE_CD_KEY);
        this.spaceStoneCD = nbt.getInt(InfinityStoneUtil.SPACE_STONE_CD_KEY);
        this.realityStoneCD = nbt.getInt(InfinityStoneUtil.REALITY_STONE_CD_KEY);
        this.soulStoneCD = nbt.getInt(InfinityStoneUtil.SOUL_STONE_CD_KEY);
        this.timeStoneCD = nbt.getInt(InfinityStoneUtil.TIME_STONE_CD_KEY);
        this.mindStoneCD = nbt.getInt(InfinityStoneUtil.MIND_STONE_CD_KEY);
    }
}
