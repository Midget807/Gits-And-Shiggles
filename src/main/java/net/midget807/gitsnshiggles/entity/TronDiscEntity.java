package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TronDiscEntity extends PersistentProjectileEntity {
    public static final TrackedData<Integer> REBOUNDS = DataTracker.registerData(TronDiscEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<BlockPos> ORIGIN = DataTracker.registerData(TronDiscEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private List<LivingEntity> targets;
    private LivingEntity target;
    private boolean searching = false;
    private boolean targeting = false;
    public static final int MAX_DISTANCE = 80;

    public TronDiscEntity(World world) {
        super(ModEntities.TRON_DISC, world);
    }

    public TronDiscEntity(double x, double y, double z, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, x, y, z, world, stack, stack);
        this.dataTracker.set(ORIGIN, new BlockPos((int) x, (int) y, (int) z));
        this.searching = true;
    }

    public TronDiscEntity(LivingEntity owner, World world, ItemStack stack) {
        super(ModEntities.TRON_DISC, owner, world, stack, null);
        this.dataTracker.set(ORIGIN, owner.getBlockPos());
        this.searching = true;
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

    public void setSearching(boolean searching) {
        this.searching = searching;
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
    public void tick() {
        super.tick();
        LivingEntity owner = (LivingEntity) this.getOwner();
        if (searching) {
            targets = this.getTargets();
            this.targeting = !targets.isEmpty();
            this.searching = false;
        }
        if (targeting) {
            TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(MAX_DISTANCE).setPredicate(livingEntity -> livingEntity.isAlive() && !livingEntity.isSpectator());
            target = this.getWorld().getClosestEntity(targets, targetPredicate, owner, this.getX(), this.getY(), this.getZ());
            this.targeting = false;
        }
        if (target != null && this.getRebounds() > 0) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.setVelocity(target.getPos().subtract(this.getPos()).normalize().multiply(2));
        } else {
            if (owner != null) {
                this.setOrigin(owner.getBlockPos());
            }
            this.returnToOrigin();
        }
        //todo fix rotation in super tick method
    }

    private void returnToOrigin() {
        Vec3d originVec = new Vec3d(this.getOrigin().getX(), this.getOrigin().getY(), this.getOrigin().getZ());
        this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, originVec);
        this.setVelocity(originVec.subtract(this.getPos()).normalize().multiply(2));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity == this.getOwner() && this.getOwner() != null) {
            this.dropItem(ModItems.TRON_DISC);
            this.getOwner().kill();
            this.discard();
            return;
        }

        if (targets != null && !targets.isEmpty() && target != null) {
            this.targets.remove(target);
            this.setVelocity(Vec3d.ZERO);
            this.targeting = true;

        }

    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
    }

    private List<LivingEntity> getTargets() {
        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(MAX_DISTANCE), entity -> entity.isAlive() && !entity.isSpectator());
        entities.removeIf(entity -> this.distanceTo(entity) > MAX_DISTANCE || entity == this.getOwner());
        return entities;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
