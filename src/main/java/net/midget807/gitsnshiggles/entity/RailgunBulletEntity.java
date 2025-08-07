package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class RailgunBulletEntity extends ProjectileEntity implements FlyingItemEntity {
    public ItemStack stack;

    public RailgunBulletEntity(World world) {
        super(ModEntities.RAILGUN_BULLET, world);
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

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
            entity.damage(entity.getDamageSources().sonicBoom(this.getOwner()), (float) this.getVelocity().length());
        } else {
            entity.damage(entity.getDamageSources().magic(), (float) this.getVelocity().length());
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
    public ItemStack getStack() {
        return this.stack == null ? new ItemStack(Items.IRON_NUGGET) : this.stack;
    }

}
