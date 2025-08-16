package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModDamages;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;

import java.util.Optional;

public class RailgunBulletEntity extends ThrownItemEntity {
    public ItemStack stack;
    public int power;

    public RailgunBulletEntity(World world) {
        super(ModEntities.RAILGUN_BULLET, world);
    }

    public RailgunBulletEntity(double x, double y, double z, World world) {
        this(world);
        this.setPosition(x, y, z);
    }

    public RailgunBulletEntity(LivingEntity owner, World world) {
        this(owner.getX(), owner.getEyeY() - 0.125f, owner.getZ(), world);
        this.setOwner(owner);
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        this.setItem(stack);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d)) {
            d = 4.0;
        }

        d *= 128.0;
        return distance < d * d;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient) {
            BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            this.getWorld().createExplosion(
                    null,
                    null,
                    new AdvancedExplosionBehavior(false, true, Optional.of(1.0f), Optional.empty()),
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ(),
                    Math.min(5.0f + (float) RailgunScalar.getScalar(power), 10.0f),
                    false,
                    World.ExplosionSourceType.TRIGGER,
                    ParticleTypes.EXPLOSION,
                    ParticleTypes.EXPLOSION_EMITTER,
                    SoundEvents.ENTITY_GENERIC_EXPLODE
            );
        }
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() != null) {
            entity.damage(ModDamages.create(this.getWorld(), ModDamages.RAILGUN, this, this.getOwner()), (float) 5.0f + (float) RailgunScalar.getScalar(power));
        } else {
            entity.damage(ModDamages.create(this.getWorld(), ModDamages.RAILGUN, this), (float) 5.0f + (float) RailgunScalar.getScalar(power));
        }
    }

    @Override
    public void tick() {
        this.baseTick(); //Entity
        // ProjectileEntity
        if (!this.shot) {
            this.emitGameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.shot = true;
        }
        if (!this.leftOwner) {
            this.leftOwner = this.shouldLeaveOwner();
        }
        //ThrownEntity
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.hitOrDeflect(hitResult);
        }
        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; i++) {
                this.getWorld().addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
            }
        }
        this.setVelocity(vec3d.multiply(0.99));
        this.setPosition(d, e, f);



        if (this.age >= 30) {
            this.discard();
        }
        if (this.getWorld().isClient) {
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }

    }

    private boolean shouldLeaveOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for (Entity entity2 : this.getWorld()
                    .getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), entityx -> !entityx.isSpectator() && entityx.canHit())) {
                if (entity2.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isInsideWall() {
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return this.stack == null ? Items.IRON_NUGGET : this.stack.getItem();
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
