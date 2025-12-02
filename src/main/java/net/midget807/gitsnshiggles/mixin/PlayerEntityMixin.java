package net.midget807.gitsnshiggles.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.item.RailgunItem;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.midget807.gitsnshiggles.util.inject.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements RailgunAds, RailgunLoading, ElfCount, WizardGamba, RealityStoneTransform, MindStoneInvert {
    @Shadow @Final private PlayerInventory inventory;
    @Shadow @Final private PlayerAbilities abilities;

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract boolean canBeHitByProjectile();

    @Shadow public abstract void onDeath(DamageSource damageSource);

    @Unique
    private float fovScale = RailgunItem.FOV_MULTIPLIER;
    @Unique
    private int elfCount = 0;
    @Unique
    private boolean isGambing = false;
    @Unique
    private int gambingAnimationTicks = 0;
    @Unique
    private boolean shouldTransformProjectiles = false;
    @Unique
    private boolean isInvertedControls = false;
    @Unique
    private int timeTicksInverted = 0;
    @Unique
    private int timeTicksForTransform = 0;

    @Override
    public void setUsingRailgun(boolean usingRailgun) {

    }

    @Override
    public void setFovScale(float fovScale) {
        this.fovScale = fovScale;
    }

    @Override
    public boolean isUsingRailgun() {
        return this.isUsingItem() && this.getActiveItem().isOf(ModItems.RAILGUN);
    }

    @Override
    public float getFovScale() {
        return this.fovScale;
    }

    @Override
    public int getElfCount() {
        return this.elfCount;
    }
    @Override
    public void setElfCount(int elfCount) {
        this.elfCount = elfCount;
    }

    @Override
    public boolean isGambing() {
        return this.isGambing;
    }
    @Override
    public void setGambing(boolean gambing) {
        this.isGambing = gambing;
    }

    @Override
    public int getGambingAnimationTicks() {
        return this.gambingAnimationTicks;
    }
    @Override
    public void setGambingAnimationTicks(int gambingAnimationTicks) {
        this.gambingAnimationTicks = gambingAnimationTicks;
    }

    @Override
    public boolean isInverted() {
        return this.isInvertedControls;
    }
    @Override
    public void setInverted(boolean inverted) {
        this.isInvertedControls = inverted;
    }

    @Override
    public int getTimeTicksInverted() {
        return this.timeTicksInverted;
    }
    @Override
    public void setTimeTicksInverted(int timeTicksInverted) {
        this.timeTicksInverted = timeTicksInverted;
    }

    @Override
    public boolean shouldTransformProjectiles() {
        return this.shouldTransformProjectiles;
    }
    @Override
    public void setTransformProjectiles(boolean shouldTransform) {
        this.shouldTransformProjectiles = shouldTransform;
    }
    @Override
    public int getTimeTicksForTransform() {
        return this.timeTicksForTransform;
    }
    @Override
    public void setTimeTicksForTransform(int timeTicksForTransform) {
        this.timeTicksForTransform = timeTicksForTransform;
    }

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    private void gitsnshiggles$writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt(InfinityStoneUtil.REALITY_STONE_TIMER_KEY, this.timeTicksForTransform);
        nbt.putInt(InfinityStoneUtil.MIND_STONE_TIMER_KEY, this.timeTicksInverted);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    private void gitsnshiggles$readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.timeTicksForTransform =nbt.getInt(InfinityStoneUtil.REALITY_STONE_TIMER_KEY);
        this.timeTicksInverted = nbt.getInt(InfinityStoneUtil.MIND_STONE_TIMER_KEY);
    }

    @Override
    public ItemStack getRailgunProjectile(ItemStack railgun) {
        if (!(railgun.getItem() instanceof RailgunItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = RailgunItem.ACCEPTABLE_PROJECTILES;
            ItemStack heldProjectile = RailgunItem.getHeldProjectiles(this, predicate);
            if (!heldProjectile.isEmpty()) {
                return heldProjectile;
            } else {
                for (int i = 0; i < this.inventory.size(); i++) {
                    ItemStack invStack = this.inventory.getStack(i);
                    if (predicate.test(invStack)) {
                        return invStack;
                    }
                }
                return this.abilities.creativeMode ? new ItemStack(Items.IRON_NUGGET) : ItemStack.EMPTY;
            }
        }
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        this.isGambing = true /*WizardRobesItem.hasFullSuitOfArmor(((PlayerEntity)((Object) this))) && !this.isGambing*/;
        super.onDamaged(damageSource);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void gitsnshiggles$tickVariables(CallbackInfo ci) {
        if (this.isGambing) {
            this.gambingAnimationTicks++;
        } else if (this.gambingAnimationTicks != -1) {
            this.gambingAnimationTicks = -1;
        }
        this.shouldTransformProjectiles = this.timeTicksForTransform > 0;
        if (this.shouldTransformProjectiles) {
            this.transformProjectiles();
        }
        if (this.timeTicksForTransform > 0) {
            this.timeTicksForTransform--;
        } else if (this.timeTicksForTransform < 0) {
            this.timeTicksForTransform = 0;
        }
        this.isInvertedControls = this.timeTicksInverted > 0;
        if (this.timeTicksInverted > 0) {
            this.timeTicksInverted--;
        } else if (this.timeTicksInverted < 0) {
            this.timeTicksInverted = 0;
        }
    }

    @Unique
    private void transformProjectiles() {
        List<ProjectileEntity> projectiles = this.getWorld().getEntitiesByClass(
                ProjectileEntity.class,
                this.getBoundingBox().expand(1.25),
                projectileEntity -> {
                    if (projectileEntity.getOwner() != null) {
                        return !projectileEntity.getOwner().equals((PlayerEntity)((Object)this));
                    }
                    return true;
                }
        );
        for (ProjectileEntity projectileEntity : projectiles) {
            Vec3d pos = projectileEntity.getPos();
            World world = this.getWorld();
            if (world.isClient) {
                for (int i = 0; i < 5; i++) {
                    DustParticleEffect dustParticleEffect = new DustParticleEffect(new Vector3f(0.592f, 0, 0.0667f), 1.0f);
                    world.addParticle(
                            dustParticleEffect,
                            pos.getX() + world.random.nextFloat(),
                            pos.getY() + world.random.nextFloat(),
                            pos.getZ() + world.random.nextFloat(),
                            0,
                            0,
                            0
                    );
                    world.addParticle(
                            ParticleTypes.BUBBLE,
                            pos.getX() + world.random.nextFloat(),
                            pos.getY() + world.random.nextFloat(),
                            pos.getZ() + world.random.nextFloat(),
                            0,
                            0.7,
                            0
                    );
                }
            }
            projectileEntity.discard();
        }
    }

    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$noProjectileDamage(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.isIn(DamageTypeTags.IS_PROJECTILE)) {
            cir.setReturnValue(this.shouldTransformProjectiles);
        }
    }


}
