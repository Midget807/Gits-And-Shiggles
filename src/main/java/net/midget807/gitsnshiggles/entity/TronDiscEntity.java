package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.ShapeContext;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class TronDiscEntity extends PersistentProjectileEntity {
    public static final TrackedData<Integer> REBOUNDS = DataTracker.registerData(TronDiscEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<BlockPos> ORIGIN = DataTracker.registerData(TronDiscEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private LivingEntity target;
    private LivingEntity exceptionTarget;
    public static final int MAX_DISTANCE = 80;

    public TronDiscEntity(World world) {
        super(ModEntities.TRON_DISC, world);
    }

    public TronDiscEntity(double x, double y, double z, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, x, y, z, world, stack, stack);
        this.dataTracker.set(ORIGIN, new BlockPos((int) x, (int) y, (int) z));
    }

    public TronDiscEntity(LivingEntity owner, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, owner, world, stack, null);
        this.dataTracker.set(ORIGIN, owner.getBlockPos());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(REBOUNDS, 0);
        builder.add(ORIGIN, BlockPos.ORIGIN);
    }

    @Override
    public ItemStack getWeaponStack() {
        return this.getItemStack();
    }

    public void setRebounds(int rebounds) {
        this.dataTracker.set(REBOUNDS, rebounds);
    }
    public int getRebounds() {
        return this.dataTracker.get(REBOUNDS);
    }

    public void setOrigin(BlockPos origin) {
        this.dataTracker.set(ORIGIN, origin);
    }
    public BlockPos getOrigin() {
        return this.dataTracker.get(ORIGIN);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.TRON_DISC);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == this.getOwner() && this.getOwner() != null) {
            this.dropItem(ModItems.TRON_DISC);
            this.getOwner().kill();
            return;
        }

        if (this.target != null && this.target == entity) {
            this.exceptionTarget = this.target;
            this.target = null;
        }

        entity.damage(this.getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 5.0f);
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != this.getOwner();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 20, entityPredicate);
        if (target != null && this.dataTracker.get(REBOUNDS) > 0) {
            this.setVelocity(target.getPos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.dataTracker.set(REBOUNDS, this.dataTracker.get(REBOUNDS) - 1);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Vec3d rotVec = this.getVelocity();
        Direction direction = blockHitResult.getSide();
        if (this.dataTracker.get(REBOUNDS) > 0) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                this.setVelocity(rotVec.x, -rotVec.y, rotVec.z);
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                this.setVelocity(-rotVec.x, rotVec.y, rotVec.z);
            } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                this.setVelocity(rotVec.x, rotVec.y, -rotVec.z);
            }
        } else {
            this.dropItem(ModItems.TRON_DISC);
            this.discard();
        }
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != this.getOwner();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 20, entityPredicate);
        if (target != null && this.dataTracker.get(REBOUNDS) > 0) {
            this.setVelocity(target.getPos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.dataTracker.set(REBOUNDS, this.dataTracker.get(REBOUNDS) - 1);
        }
    }

    public void getNearestEntityInViewPreferPlayer(Entity source, double x, double y, double z, double maxDistance, Predicate<LivingEntity> predicate) {
        double d = -1.0;
        List<LivingEntity> livingEntities = source.getWorld().getEntitiesByClass(LivingEntity.class, source.getBoundingBox().expand(maxDistance), predicate);
        List<PlayerEntity> playerEntities = source.getWorld().getEntitiesByClass(PlayerEntity.class, source.getBoundingBox().expand(maxDistance), predicate);
        if (!playerEntities.isEmpty()) {
            predicate = entity2 -> !entity2.isSpectator() && !((PlayerEntity)entity2).isCreative() && entity2 != this.getOwner();
        }
        for (LivingEntity livingEntity : playerEntities.isEmpty() ? livingEntities : playerEntities) {
            if (predicate == null || predicate.test(livingEntity)) {
                double squaredDistanceTo = livingEntity.squaredDistanceTo(x, y, z);
                if ((maxDistance < 0.0 || squaredDistanceTo < maxDistance * maxDistance) && (d == -1.0 || squaredDistanceTo < d)) {
                    RaycastContext raycastContext = new RaycastContext(source.getPos(), livingEntity.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, ShapeContext.of(source));
                    HitResult hitResult = source.getWorld().raycast(raycastContext);
                    if (hitResult.getType() == HitResult.Type.BLOCK) continue;
                    if (this.exceptionTarget != null && livingEntity == this.exceptionTarget) continue;
                    d = squaredDistanceTo;
                    this.target = livingEntity;
                }
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

}
