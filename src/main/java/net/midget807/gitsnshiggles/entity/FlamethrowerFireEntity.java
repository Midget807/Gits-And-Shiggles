package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModDamages;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.stream.Stream;

public class FlamethrowerFireEntity extends ThrownItemEntity {
    public static final TrackedData<Boolean> EXTRA_RANGE = DataTracker.registerData(FlamethrowerFireEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> EXTRA_DAMAGE = DataTracker.registerData(FlamethrowerFireEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public FlamethrowerFireEntity(World world) {
        super(ModEntities.FLAMETHROWER_FIRE, world);
    }

    public FlamethrowerFireEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.FLAMETHROWER_FIRE, livingEntity, world);
        this.setPosition(this.getX(), this.getY() - (double)(this.getHeight() / 2), this.getZ());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EXTRA_RANGE, false);
        builder.add(EXTRA_DAMAGE, false);
    }

    public void setExtraRange(boolean extraRange) {
        this.dataTracker.set(EXTRA_RANGE, extraRange);
    }
    public boolean hasExtraRange() {
        return (Boolean)this.dataTracker.get(EXTRA_RANGE);
    }

    public void setExtraDamage(boolean extraDamage) {
        this.dataTracker.set(EXTRA_DAMAGE, extraDamage);
    }
    public boolean hasExtraDamage() {
        return (Boolean)this.dataTracker.get(EXTRA_DAMAGE);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            this.onBlockHit(blockHitResult);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > (this.hasExtraRange() ? 30 : 15)) {
            this.discard();
        }
        if (!this.getWorld().isClient) {
            Stream<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.25f), entity -> !this.isOwner(entity)).stream();
            Objects.requireNonNull(LivingEntity.class);
            entities.filter(LivingEntity.class::isInstance).forEach(entity -> {
                entity.damage(ModDamages.create(this.getWorld(), ModDamages.FLAMETHROWER, this, this.getOwner()), Math.max(1.5F, 6.0F - (float)this.age * 0.15F));
                if (!this.isWet()) {
                    entity.setOnFireFor(2);
                }
            });
        } else {
            double rangeFactor = this.hasExtraRange() ? 0.8d : 1.0d;
            Vec3d particleSpeed = this.getVelocity().multiply(this.random.nextDouble() * rangeFactor).add(this.random.nextDouble() * rangeFactor * 0.1, this.random.nextDouble() * rangeFactor * 0.1, this.random.nextDouble() * rangeFactor * 0.1);
            this.getWorld().addParticle(this.hasExtraDamage() ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, this.getParticleX(this.getWidth()), this.getRandomBodyY(), this.getParticleZ(this.getWidth()), particleSpeed.getX(), particleSpeed.getY(), particleSpeed.getZ());
            if (this.isInsideWaterOrBubbleColumn()) {
                this.getWorld().addParticle(ParticleTypes.BUBBLE, this.getParticleX(this.getWidth()), this.getRandomBodyY(), this.getParticleZ(this.getWidth()), this.getVelocity().getX() * this.random.nextDouble(), this.getVelocity().getY() * this.random.nextDouble(), this.getVelocity().getZ() * this.random.nextDouble());
            }
        }
    }
    public void setVelocity(Entity user, float divergence, float speed) {
        float xRot = user.getPitch();
        float yRot = user.getYaw() + (this.random.nextFloat() - 0.5f) * divergence;
        Vec3d direction = this.getRotationVector(xRot, yRot).normalize();
        this.setVelocity(direction.multiply(speed));
        this.setPitch(xRot);
        this.setYaw(yRot);
    }
}
