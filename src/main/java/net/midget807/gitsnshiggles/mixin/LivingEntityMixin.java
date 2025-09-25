package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, RailgunRecoil {
    private boolean hasRailgunRecoil = false;

    @Shadow public abstract void playSound(@Nullable SoundEvent sound);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow protected abstract SoundEvent getFallSound(int distance);

    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @Shadow public abstract void remove(RemovalReason reason);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void setRailgunRecoil(boolean recoil) {
        this.hasRailgunRecoil = recoil;
    }

    @Override
    public boolean hasRailgunRecoil() {
        return this.hasRailgunRecoil;
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;"))
    private void gitsnshiggles$recoilDamage(Vec3d movementInput, CallbackInfo ci) {
        if (this.hasRailgunRecoil) {
            this.calculateRecoilDamage();
        }
    }

    @Unique
    private void calculateRecoilDamage() {
        Vec3d velocity = this.getVelocity();
        Vec3d rotVec = this.getRotationVector().negate();
        float pitchFunction = this.getPitch() * (float) (Math.PI / 180);
        double rotVecHorizontalLength = Math.sqrt(rotVec.x * rotVec.x + rotVec.z * rotVec.z);
        double velocityHorizontalLength = velocity.horizontalLength();
        double rotVecLength = rotVec.length();
        double cosOfPitch = Math.cos(pitchFunction);
        cosOfPitch = cosOfPitch * cosOfPitch * Math.min(1.0, rotVecLength / 4.0);
        velocity = this.getVelocity().add(0.0, this.getFinalGravity() * (-1.0 + cosOfPitch * 0.75), 0.0);
        if (velocity.y < 0.0 && rotVecHorizontalLength > 0.0) {
            double m = velocity.y * -0.1 * cosOfPitch;
            velocity = velocity.add(rotVec.x * m / rotVecHorizontalLength, m, rotVec.z * m / rotVecHorizontalLength);
        }

        if (pitchFunction < 0.0f && cosOfPitch > 0.0) {
            double m = velocityHorizontalLength * -MathHelper.sin(pitchFunction) * 0.04;
            velocity = velocity.add(-rotVec.x * m / rotVecHorizontalLength, m * 3.2, -rotVec.z * m / rotVecHorizontalLength);
        }

        if (cosOfPitch > 0.0) {
            velocity = velocity.add((rotVec.x / rotVecHorizontalLength * velocityHorizontalLength - velocity.x) * 0.1, 0.0, (rotVec.z / rotVecHorizontalLength * velocityHorizontalLength - velocity.z) * 0.1);
        }

        this.setVelocity(velocity.multiply(0.99f, 0.98f, 0.99f));
        this.move(MovementType.SELF, this.getVelocity());
        if (this.horizontalCollision && !this.getWorld().isClient) {
            double m = this.getVelocity().horizontalLength();
            double n = velocityHorizontalLength - m;
            float o = (float) (n *  10.0 - 3.0);
            if (o > 0.0f) {
                //this.playSound(this.gitsnshiggles$getFallSound((int)o), 1.0F, 1.0F);
                this.damage(this.getDamageSources().flyIntoWall(), o);
            }
        }
        if ((this.getVelocity().length() == 0 || this.isOnGround()) && !this.getWorld().isClient) {
            this.setFlag(Entity.FALL_FLYING_FLAG_INDEX, false);
            this.setRailgunRecoil(false);
        }

    }

    @Unique
    private SoundEvent gitsnshiggles$getFallSound(int o) {
        return null;
    }

    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$moreItemsThatDisableShield(CallbackInfoReturnable<Boolean> cir) {
        if (this.getWeaponStack().isIn(ModItemTagProvider.DISABLES_SHIELD)) {
            cir.setReturnValue(Boolean.TRUE);
        }
    }
}
