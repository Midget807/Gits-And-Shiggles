package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.cca.TimeStopComponent;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract World getWorld();

    @Shadow public abstract void emitGameEvent(RegistryEntry<GameEvent> event);

    @Shadow public abstract Vec3d getVelocity();

    @Shadow private Vec3d velocity;
    @Shadow public boolean velocityModified;
    @Shadow public boolean velocityDirty;

    @Shadow public abstract void setNoGravity(boolean noGravity);

    @Shadow protected abstract void tryCheckBlockCollision();

    @Shadow public abstract void setVelocity(Vec3d velocity);


    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", shift = At.Shift.AFTER), cancellable = true)
    private void gitsnshiggles$timeStoneFreezesEntity(CallbackInfo ci) {
        TimeStopComponent timeStopComponent = TimeStopComponent.get((Entity)((Object)this));
        this.setNoGravity(timeStopComponent.getBool());
        if (timeStopComponent.getBool()) {
            this.setVelocity(Vec3d.ZERO);
            ci.cancel();
            return;
        }

    }
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$timeStoneFreezesMovement(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        TimeStopComponent timeStopComponent = TimeStopComponent.get((Entity)((Object)this));
        if (timeStopComponent.getBool()) {
            ci.cancel();
            return;
        }
    }
}
