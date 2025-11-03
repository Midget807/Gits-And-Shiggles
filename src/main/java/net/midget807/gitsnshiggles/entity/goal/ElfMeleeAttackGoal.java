package net.midget807.gitsnshiggles.entity.goal;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class ElfMeleeAttackGoal extends Goal {
    protected final ElfEntity elfEntity;
    private final double speed;
    private final boolean pauseWhenMobIdle;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int cooldown;
    private final int attackIntervalTicks = 20;
    private long lastUpdateTime;
    private static final long MAX_ATTACK_TIME = 20L;

    public ElfMeleeAttackGoal(ElfEntity elfEntity, double speed, boolean pauseWhenMobIdle) {
        this.elfEntity = elfEntity;
        this.speed = speed;
        this.pauseWhenMobIdle = pauseWhenMobIdle;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        long time = this.elfEntity.getWorld().getTime();
        if (time - this.lastUpdateTime < MAX_ATTACK_TIME) {
            return false;
        } else {
            this.lastUpdateTime = time;
            LivingEntity target = this.elfEntity.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                this.path = this.elfEntity.getNavigation().findPathTo(target, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getSquaredMaxAttackDistance(target) >= this.elfEntity.squaredDistanceTo(target.getPos());
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity livingEntity = this.elfEntity.getTarget();
        if (livingEntity == null) {
            return false;
        } else if (!livingEntity.isAlive()) {
            return false;
        } else if (!this.pauseWhenMobIdle) {
            return !this.elfEntity.getNavigation().isIdle();
        } else if (!this.elfEntity.isInWalkTargetRange(livingEntity.getBlockPos())) {
            return false;
        } else {
            return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();
        }
    }

    @Override
    public void start() {
        this.elfEntity.getNavigation().startMovingAlong(this.path, this.speed);
        this.elfEntity.setAttacking(true);
        this.updateCountdownTicks = 0;
        this.cooldown = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingEntity = this.elfEntity.getTarget();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
            this.elfEntity.setTarget(null);
        }

        this.elfEntity.setAttacking(false);
        this.elfEntity.getNavigation().stop();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.elfEntity.getTarget();
        if (livingEntity != null) {
            this.elfEntity.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
            double d = this.elfEntity.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
            if ((this.pauseWhenMobIdle || this.elfEntity.getVisibilityCache().canSee(livingEntity))
                    && this.updateCountdownTicks <= 0
                    && (
                    this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                            || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0
                            || this.elfEntity.getRandom().nextFloat() < 0.05F
            )) {
                this.targetX = livingEntity.getX();
                this.targetY = livingEntity.getY();
                this.targetZ = livingEntity.getZ();
                this.updateCountdownTicks = 4 + this.elfEntity.getRandom().nextInt(7);
                if (d > 1024.0) {
                    this.updateCountdownTicks += 10;
                } else if (d > 256.0) {
                    this.updateCountdownTicks += 5;
                }

                if (!this.elfEntity.getNavigation().startMovingTo(livingEntity, this.speed)) {
                    this.updateCountdownTicks += 15;
                }

                this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
            }

            this.cooldown = Math.max(this.cooldown - 1, 0);
            this.attack(livingEntity, d);
        }
    }

    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        if ((squaredDistance <= d || target.getFirstPassenger() == this.elfEntity) && this.cooldown <= 0) {
            this.resetCooldown();
            this.elfEntity.swingHand(Hand.MAIN_HAND);
            this.elfEntity.tryAttack(target);
        }

    }

    protected void resetCooldown() {
        this.cooldown = this.getTickCount(20);
    }

    protected boolean isCooledDown() {
        return this.cooldown <= 0;
    }

    protected int getCooldown() {
        return this.cooldown;
    }

    protected int getMaxCooldown() {
        return this.getTickCount(20);
    }

    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.elfEntity.getWidth() * 2.0F * this.elfEntity.getWidth() * 2.0F + entity.getWidth();
    }
}
