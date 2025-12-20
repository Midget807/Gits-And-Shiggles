package net.midget807.gitsnshiggles.entity.goal;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class FollowOwnerElfGoal extends Goal {
    private final ElfEntity elf;
    private final WorldView world;
    private final double speed;
    private final EntityNavigation navigation;
    private final float maxDistance;
    private final float minDistance;
    private final boolean leavesAllowed;
    private LivingEntity owner;
    private int updateCountdownTicks;
    private float oldWaterPathfindingPenalty;

    public FollowOwnerElfGoal(ElfEntity elf, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.elf = elf;
        this.world = elf.getWorld();
        this.speed = speed;
        this.navigation = elf.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if (!(elf.getNavigation() instanceof MobNavigation) && !(elf.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.elf.getOwner();

        if (livingEntity == null) {
            return false;
        } else if (livingEntity.isSpectator()) {
            return false;
        } else if (this.elf.isSitting()) {
            return false;
        } else if (this.elf.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;
        } else {
            this.owner = livingEntity;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        } else if (this.elf.isSitting()) {
            return false;
        } else {
            return this.elf.squaredDistanceTo(this.owner) > (double) (this.maxDistance * this.maxDistance);
        }
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.elf.getPathfindingPenalty(PathNodeType.WATER);
        this.elf.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.elf.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.elf.getLookControl().lookAt(this.owner, 10.0F, (float) this.elf.getMaxLookPitchChange());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (!this.elf.isLeashed() && !this.elf.hasVehicle()) {
                if (this.elf.squaredDistanceTo(this.owner) >= 500D) {
                    this.tryTeleport();
                } else {
                    this.navigation.startMovingTo(this.owner, this.speed);
                }
            }
        }
    }

    private void tryTeleport() {
        this.elf.fallDistance = 0f;
        BlockPos blockPos = this.owner.getBlockPos();

        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3);
            int k = this.getRandomInt(-1, 1);
            int l = this.getRandomInt(-3, 3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (bl) {
                return;
            }
        }

    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double) x - this.owner.getX()) < 2.0D && Math.abs((double) z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.elf.refreshPositionAndAngles((double) x + 0.5D, y, (double) z + 0.5D, this.elf.getYaw(), this.elf.getPitch());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(new PathContext(this.world, this.elf), pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockState = this.world.getBlockState(pos.down());
            if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockPos = pos.subtract(this.elf.getBlockPos());
                return this.world.isSpaceEmpty(this.elf, this.elf.getBoundingBox().offset(blockPos));
            }
        }
    }

    private int getRandomInt(int min, int max) {
        return this.elf.getRandom().nextInt(max - min + 1) + min;
    }
}
