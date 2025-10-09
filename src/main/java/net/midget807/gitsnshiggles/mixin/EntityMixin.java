package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.entity.Entity;
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

    @Unique
    private boolean isTimeFrozen = false;
    @Unique
    private boolean shouldTimeFreeze = false;
    @Unique
    private int timeTicksFrozen = 0;

    @Override
    public boolean isTimeFrozen() {
        return this.isTimeFrozen;
    }

    @Override
    public void setTimeFrozen(boolean timeFrozen) {
        this.isTimeFrozen = timeFrozen;
    }

    @Override
    public boolean shouldTimeFreeze() {
        return this.shouldTimeFreeze;
    }

    @Override
    public void setTimeFreeze(boolean shouldTimeFreeze) {
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
        if (this.shouldTimeFreeze) {
            this.timeTicksFrozen = 100; //Five seconds
            this.isTimeFrozen = true;
            this.shouldTimeFreeze = false;
        } else {
            if (this.timeTicksFrozen > 0) {
                this.timeTicksFrozen--;
            } else {
                this.isTimeFrozen = false;
            }
        }
        this.getWorld().getProfiler().pop();
    }
    //todo here be dragons
    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true)
    private void gitsnshiggles$timeStoneFreezesEntity(CallbackInfo ci) {
        if (this.isTimeFrozen) ci.cancel();
    }
}
