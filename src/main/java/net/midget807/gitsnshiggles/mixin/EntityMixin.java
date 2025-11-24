package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements TimeStoneFreeze {
    @Shadow public abstract World getWorld();

    @Shadow public abstract void emitGameEvent(RegistryEntry<GameEvent> event);

    @Shadow public abstract Vec3d getVelocity();

    @Shadow private Vec3d velocity;
    @Shadow public boolean velocityModified;
    @Shadow public boolean velocityDirty;

    @Shadow public abstract void setNoGravity(boolean noGravity);

    @Shadow protected abstract void tryCheckBlockCollision();

    @Shadow public abstract void setVelocity(Vec3d velocity);

    @Unique
    private boolean shouldTimeFreeze = false;
    @Unique
    private int timeTicksFrozen = 0;

    @Override
    public boolean shouldTimeFreeze() {
        return this.shouldTimeFreeze;
    }

    @Override
    public void setShouldTimeFreeze(boolean shouldTimeFreeze) {
        this.shouldTimeFreeze = shouldTimeFreeze;
    }

    @Override
    public int getTimeTicksFrozen() {
        return this.timeTicksFrozen;
    }

    @Override
    public void setTimeTicksFrozen(int timeTicksFrozen) {
        this.timeTicksFrozen = timeTicksFrozen;
    }

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void gitsnshiggles$independentlyTickTimeFreeze(CallbackInfo ci) {
        this.getWorld().getProfiler().push("entityTimeFreezeBaseTick");
        if (this.shouldTimeFreeze && this.timeTicksFrozen <= 0) {
            this.timeTicksFrozen = 100; //Five seconds
            this.shouldTimeFreeze = false;
        }
        if (this.timeTicksFrozen > 0) {
            this.timeTicksFrozen--;
        }
        this.getWorld().getProfiler().pop();
    }
    //todo here be dragons
    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true)
    private void gitsnshiggles$timeStoneFreezesEntity(CallbackInfo ci) {
        this.setNoGravity(this.timeTicksFrozen > 0);
        if (this.timeTicksFrozen > 0) {
            this.setVelocity(Vec3d.ZERO);
            ci.cancel();
            return;
        }

    }
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$timeStoneFreezesMovement(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        if (this.timeTicksFrozen > 0) {
            ci.cancel();
            return;
        }
    }
}
