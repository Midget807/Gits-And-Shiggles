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
import net.minecraft.entity.player.PlayerInventory;
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
    public boolean canReturn = false;
    public boolean isReturning = false;
    public final int preferredSlot;

    public TronDiscEntity(World world) {
        super(ModEntities.TRON_DISC, world);
        this.preferredSlot = -1;
    }

    public TronDiscEntity(double x, double y, double z, World world, ItemStack stack, int preferredSlot) {
        super(ModEntities.TRON_DISC, x, y, z, world, stack, stack);
        this.initColor();
        this.preferredSlot = preferredSlot;
    }
    public TronDiscEntity(double x, double y, double z, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, x, y, z, world, stack, stack);
        this.initColor();
        this.preferredSlot = -1;
    }


    public TronDiscEntity(LivingEntity owner, World world, ItemStack stack, int preferredSlot) {
        super(ModEntities.TRON_DISC, owner, world, stack, null);
        this.initColor();
        this.preferredSlot = preferredSlot;
    }
    public TronDiscEntity(LivingEntity owner, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, owner, world, stack, null);
        this.initColor();
        this.preferredSlot = -1;
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
        super.tick();
        if (this.getOwner() == null) {
            this.dropAsItem();
            return;
        }
        if (this.isNoClip() && !this.isReturning) {
            this.setNoClip(false);
        }
        if (this.isInsideWall()) {
            this.forceRecall(this.getOwner());
        }
        if (this.targetNumber <= 0) {
            if (this.getOwner() != null) {
                this.isReturning = true;
                this.returnToOwner(this.getOwner());
            } else {
                this.dropAsItem();
            }
        }
        if (this.getVelocity().length() < 0.00001d) {
            if (this.getOwner() != null) {
                this.isReturning = true;
                this.returnToOwner(this.getOwner());
            } else {
                this.dropAsItem();
            }
        }
        if (this.age > 80) {
            if (!this.canReturn) {
                this.canReturn = true;
            } else if (this.squaredDistanceTo(this.getOwner()) > 9.0d) {
                this.forceRecall(this.getOwner());
                return;
            }
        }
        if (this.target == null) {
            if (this.canReturn && this.getOwner() != null) {
                if (!this.isReturning) this.isReturning = true;
            } else if (this.targetNumber > 0 && !this.isReturning) {
                Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != this.getOwner() && entity2.isAlive();
                this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 32.0d, entityPredicate);
            }
        }
        if (this.isReturning) {
            if (this.getOwner() != null) this.returnToOwner(this.getOwner());
        }

        if (this.getOwner() != null && this.isReturning && this.squaredDistanceTo(this.getOwner()) <= 9.0d && this.getOwner() instanceof PlayerEntity player) {
            this.insertInPlayerInventory(player);
            this.isReturning = false;
            this.discard();
            return;
        }

        if (this.getOwner() != null && this.distanceTo(this.getOwner()) >= 64.0d && this.getOwner() instanceof PlayerEntity player) {
            this.forceRecall(player);
            return;
        }
    }

    private void insertInPlayerInventory(PlayerEntity player) {
        this.insertInPlayerInventory(player, this.preferredSlot);
    }

    private void insertInPlayerInventory(PlayerEntity player, int preferredSlot) {
        if (preferredSlot < 0) {
            player.getInventory().insertStack(this.asItemStack());
        } else if (!player.getInventory().getStack(preferredSlot).isEmpty()) {
            player.getInventory().insertStack(this.asItemStack());
        } else {
            if (preferredSlot == PlayerInventory.OFF_HAND_SLOT && player.getInventory().offHand.isEmpty()) {
                player.getInventory().offHand.set(0, this.asItemStack());
                return;
            }
            player.getInventory().insertStack(preferredSlot, this.asItemStack());
        }
    }

    private void forceRecall(Entity owner) {
        if (owner instanceof  PlayerEntity player) {
            this.insertInPlayerInventory(player);
        } else {
            this.dropAsItem();
        }
        this.discard();
    }

    private void dropAsItem() {
        this.dropItem(this.getItemStack().getItem());
        this.isReturning = false;
        this.discard();
    }


    private void returnToOwner(Entity owner) {
        if (!this.isReturning) this.isReturning = true;
        this.setNoClip(true);
        this.setVelocity(owner.getEyePos().subtract(this.getPos()).normalize().multiply(7.0d));
        this.updateRotation();
        this.age = 0;
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

        if (entity.damage(this.getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 20.0f)) {
            this.setVelocity(Vec3d.ZERO);
        }
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && !entity2.equals(entity) && entity2 != this.getOwner() && entity2.isAlive();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 32.0d, entityPredicate);
        if (target != null && this.targetNumber > 0 && !this.canReturn && !this.isReturning) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.setVelocity(target.getEyePos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.targetNumber--;
            this.age = 0;
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Vec3d rotVec = this.getVelocity();
        Direction direction = blockHitResult.getSide();
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != this.getOwner() && entity2.isAlive();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 32.0d, entityPredicate);
        if (target != null && this.targetNumber > 0 && !this.canReturn && !this.isReturning) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.setVelocity(target.getEyePos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.targetNumber--;
            this.age = 0;
        }
        if (this.target == null) {
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
        }
    }

    public void getNearestEntityInViewPreferPlayer(Entity source, double x, double y, double z, double maxDistance, Predicate<LivingEntity> predicate) {
        double d = -1.0d;
        Predicate<PlayerEntity> ownerPredicate = entity2 -> entity2 == this.getOwner();
        List<LivingEntity> livingEntities = source.getWorld().getEntitiesByClass(LivingEntity.class, source.getBoundingBox().expand(maxDistance), predicate);
        List<PlayerEntity> playerEntities = source.getWorld().getEntitiesByClass(PlayerEntity.class, source.getBoundingBox().expand(maxDistance), predicate);
        if (!playerEntities.isEmpty()) {
            predicate = entity2 -> !entity2.isSpectator() && !((PlayerEntity)entity2).isCreative() && entity2 != this.getOwner();
        }
        for (LivingEntity livingEntity : playerEntities.isEmpty() ? livingEntities : playerEntities) {
            if (livingEntities.isEmpty() && playerEntities.size() > 1) {
                if (ownerPredicate.test((PlayerEntity) livingEntity)) {
                    continue;
                }
            }
            if (predicate == null || predicate.test(livingEntity)) {
                double squaredDistanceTo = livingEntity.squaredDistanceTo(x, y, z);
                if ((maxDistance < 0.0d || squaredDistanceTo < maxDistance * maxDistance) && (d == -1.0d || squaredDistanceTo < d)) {
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
        nbt.putBoolean("CanReturn", this.canReturn);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Target")) this.target = (LivingEntity) this.getWorld().getEntityById(nbt.getInt("Target"));
        if (nbt.contains("TargetException")) this.exceptionTarget = (LivingEntity) this.getWorld().getEntityById(nbt.getInt("TargetException"));
        this.targetNumber = nbt.getInt("TargetNumber");
        this.isReturning = nbt.getBoolean("Returning");
        this.canReturn = nbt.getBoolean("CanReturn");
    }
}
