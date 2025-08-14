package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class RailgunBulletEntity extends ThrownItemEntity {
    public ItemStack stack;

    public RailgunBulletEntity(World world) {
        super(ModEntities.RAILGUN_BULLET, world);
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
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(null, blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), 10.0f, false, World.ExplosionSourceType.MOB);
        }
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() != null) {
            entity.damage(entity.getDamageSources().sonicBoom(this.getOwner()), (float) 10.0f);
        } else {
            entity.damage(entity.getDamageSources().magic(), (float) 10.0f);
        }
    }

    @Override
    public void tick() {
        if (this.age >= 30) {
            this.discard();
        }
        super.tick();
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
