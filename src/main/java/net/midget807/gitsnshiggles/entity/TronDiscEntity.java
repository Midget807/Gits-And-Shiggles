package net.midget807.gitsnshiggles.entity;

import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ColoredItemUtil;
import net.minecraft.block.ShapeContext;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
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
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(TronDiscEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int rebounds = 5;
    private LivingEntity target;
    private LivingEntity exceptionTarget;
    private ColoredItemUtil.Colors color;

    public TronDiscEntity(World world) {
        super(ModEntities.TRON_DISC, world);
    }

    public TronDiscEntity(double x, double y, double z, World world, ItemStack stack, ColoredItemUtil.Colors color) {
        super(ModEntities.TRON_DISC, x, y, z, world, stack, stack);
        this.color = color;
        this.initColor();
    }

    public TronDiscEntity(LivingEntity owner, World world, ItemStack stack, ColoredItemUtil.Colors color) {
        super(ModEntities.TRON_DISC, owner, world, stack, null);
        this.color = color;
        this.initColor();
    }

    private void initColor() {
        DyedColorComponent dyedColorComponent = this.getColorComponent();
        this.dataTracker.set(COLOR, dyedColorComponent == null ? 0xFFFFFF : dyedColorComponent.rgb());
    }

    private DyedColorComponent getColorComponent() {
        return this.getItemStack().get(DataComponentTypes.DYED_COLOR);
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }
    public void setColor(int rgb) {
        this.dataTracker.set(COLOR, rgb);
    }

    public ColoredItemUtil.Colors getEnumColor() {
        return this.color;
    }
    public void setEnumColor(ColoredItemUtil.Colors color) {
        this.color = color;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(COLOR, 0xFFFFFF);
    }

    @Override
    public ItemStack getWeaponStack() {
        return this.getItemStack();
    }

    public void setRebounds(int rebounds) {
        this.rebounds = rebounds;
    }
    public int getRebounds() {
        return rebounds;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ColoredItemUtil.getTronDiscByColor(this.color));
    }

    @Override
    public void tick() {
        if (this.age > 80) {
            this.dropItem(ColoredItemUtil.getTronDiscByColor(this.color));
            this.discard();
            return;
        }
        super.tick();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() != null && entity == this.getOwner()) {
            this.dropItem(ColoredItemUtil.getTronDiscByColor(this.color));
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
        if (target != null && this.rebounds > 0) {
            this.setVelocity(target.getPos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.rebounds--;
            this.age = 0;
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Vec3d rotVec = this.getVelocity();
        Direction direction = blockHitResult.getSide();
        if (this.rebounds > 0) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                this.setVelocity(rotVec.x, -rotVec.y, rotVec.z);
                this.rebounds--;
                this.age = 0;
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                this.setVelocity(-rotVec.x, rotVec.y, rotVec.z);
                this.rebounds--;
                this.age = 0;
            } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                this.setVelocity(rotVec.x, rotVec.y, -rotVec.z);
                this.rebounds--;
                this.age = 0;
            }
        } else {
            this.dropItem(ColoredItemUtil.getTronDiscByColor(this.color));
            this.discard();
            return;
        }
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != this.getOwner();
        this.getNearestEntityInViewPreferPlayer(this, this.getX(), this.getY(), this.getZ(), 20, entityPredicate);
        if (target != null && this.rebounds > 0) {
            this.setVelocity(target.getPos().subtract(this.getPos()).normalize().multiply(2));
            this.updateRotation();
            this.rebounds--;
            this.age = 0;
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
