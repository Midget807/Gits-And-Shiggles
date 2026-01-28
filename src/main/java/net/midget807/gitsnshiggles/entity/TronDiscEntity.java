package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ModColorUtil;
import net.minecraft.block.ShapeContext;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class TronDiscEntity extends PersistentProjectileEntity {
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(TronDiscEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int targetNumber = 5;
    private LivingEntity target;
    private LivingEntity exceptionTarget;
    private boolean isReturning = false;

    public TronDiscEntity(World world) {
        super(ModEntities.TRON_DISC, world);
    }

    public TronDiscEntity(double x, double y, double z, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, x, y, z, world, stack, stack);
        this.initColor();
    }


    public TronDiscEntity(LivingEntity owner, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, owner, world, stack, null);
        this.initColor();
    }

    @Override
    protected void setStack(ItemStack stack) {
        super.setStack(stack);
        this.initColor();
    }

    private void initColor() {
        this.dataTracker.set(COLOR, DyedColorComponent.getColor(this.getItemStack(), 0x00FFFF - ModColorUtil.FUCKASS_COLOR_CONSTANT));
    }
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(COLOR, -1);
    }

    @Override
    public ItemStack getWeaponStack() {
        return this.getItemStack();
    }

    public void setRebounds(int rebounds) {
        this.targetNumber = rebounds;
    }
    public int getRebounds() {
        return targetNumber;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.TRON_DISC);
    }
    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    @Override
    public void tick() {
        if (this.isNoClip() && !this.isReturning) {
            this.setNoClip(false);
        }
        if (this.age > 80 && !this.isReturning) {
            this.dropItem(this.getItemStack().getItem());
            this.isReturning = false;
            this.discard();
            return;
        }
        if (target == null && this.targetNumber > 0 && this.getOwner() != null) {
            this.returnToOwner(this.getOwner());
            if (!this.isReturning) {
                this.isReturning = true;
            }
        }
        if (this.getOwner() != null && this.isReturning && this.squaredDistanceTo(this.getOwner()) <= 4 && this.getOwner() instanceof PlayerEntity player) {
            player.getInventory().insertStack(this.getDefaultItemStack());
            this.isReturning = false;
            this.discard();
            return;
        }
        super.tick();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() != null && entity == this.getOwner() && !this.isReturning) {
            this.dropItem(this.getItemStack().getItem());
            this.getOwner().kill();
            return;
        }

        if (this.target != null && this.target == entity) {
            this.exceptionTarget = this.target;
            this.target = null;
        }

        if (entity.damage(this.getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 24.0f)) {
            this.setVelocity(Vec3d.ZERO);
        }
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && !entity2.equals(entity) && entity2 != this.getOwner();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 32, entityPredicate);
        if (target != null && this.targetNumber > 0) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.setVelocity(target.getEyePos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.targetNumber--;
            this.age = 0;
        } else if (target == null && this.targetNumber > 0 && this.getOwner() != null) {
            this.returnToOwner(this.getOwner());
            if (!this.isReturning) {
                this.isReturning = true;
            }
        }
    }

    private void returnToOwner(Entity owner) {
        this.setNoClip(true);
        this.setVelocity(this.getOwner().getEyePos().subtract(this.getPos()).normalize().multiply(5));
        this.updateRotation();
        this.age = 0;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Vec3d rotVec = this.getVelocity();
        Direction direction = blockHitResult.getSide();
        if (direction == Direction.UP || direction == Direction.DOWN) {
            this.setVelocity(rotVec.x, -rotVec.y, rotVec.z);
            this.age = 0;
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            this.setVelocity(-rotVec.x, rotVec.y, rotVec.z);
            this.age = 0;
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            this.setVelocity(rotVec.x, rotVec.y, -rotVec.z);
            this.age = 0;
        }
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != this.getOwner();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 32, entityPredicate);
        if (target != null && this.targetNumber > 0) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.setVelocity(target.getEyePos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.targetNumber--;
            this.age = 0;
        } else if (target == null && this.targetNumber > 0 && this.getOwner() != null) {
            this.returnToOwner(this.getOwner());
            if (!this.isReturning) {
                this.isReturning = true;
            }
        }
    }

    public void getNearestEntityInViewPreferPlayer(Entity source, double x, double y, double z, double maxDistance, Predicate<LivingEntity> predicate) {
        double d = -1.0;
        Predicate<PlayerEntity> ownerPredicate = entity2 -> entity2 == this.getOwner();
        List<LivingEntity> livingEntities = source.getWorld().getEntitiesByClass(LivingEntity.class, source.getBoundingBox().expand(maxDistance), predicate);
        List<PlayerEntity> playerEntities = source.getWorld().getEntitiesByClass(PlayerEntity.class, source.getBoundingBox().expand(maxDistance), predicate);
        if (!playerEntities.isEmpty()) {
            predicate = entity2 -> !entity2.isSpectator() && !((PlayerEntity)entity2).isCreative() && entity2 != this.getOwner();
        }
        for (LivingEntity livingEntity : playerEntities.isEmpty() ? livingEntities : playerEntities) {
            if (livingEntities.isEmpty() && playerEntities.size() == 1) {
                if (ownerPredicate.test((PlayerEntity) livingEntity)) {
                    this.target = livingEntity;
                }
            }
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

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.target != null) nbt.putInt("Target", this.target.getId());
        if (this.exceptionTarget != null) nbt.putInt("TargetException", this.exceptionTarget.getId());
        nbt.putInt("TargetNumber", this.targetNumber);
        nbt.putBoolean("Returning", this.isReturning);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Target")) this.target = (LivingEntity) this.getWorld().getEntityById(nbt.getInt("Target"));
        if (nbt.contains("TargetException")) this.exceptionTarget = (LivingEntity) this.getWorld().getEntityById(nbt.getInt("TargetException"));
        this.targetNumber = nbt.getInt("TargetNumber");
        this.isReturning = nbt.getBoolean("Returning");
    }
}
