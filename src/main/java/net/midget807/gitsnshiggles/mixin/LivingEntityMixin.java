package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, RailgunRecoil {
    @Unique
    private boolean hasRailgunRecoil = false;
    @Unique
    private Vec3d recoilVec = Vec3d.ZERO;

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

    @Override
    public void setRecoilVec(Vec3d recoilVec) {
        this.recoilVec = recoilVec;
    }

    @Override
    public Vec3d getRecoilVec() {
        return this.recoilVec;
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        ((LivingEntity)((Object)this)).sendMessage(Text.literal("collision: true"));
        if (this.hasRailgunRecoil) {
            this.calculateRecoilDamage();
            this.hasRailgunRecoil = false;
        }
    }

    @Unique
    private void calculateRecoilDamage() {
        this.damage(this.getDamageSources().flyIntoWall(), (float) (this.getVelocity().length() * 0.6f));
        this.playSound(getFallSound(10));
        ((LivingEntity)((Object)this)).sendMessage(Text.literal("Speed: " + this.getVelocity().length() * 0.6f));
        ((LivingEntity)((Object)this)).sendMessage(Text.literal("recoil: " + this.hasRailgunRecoil));
    }

    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$moreItemsThatDisableShield(CallbackInfoReturnable<Boolean> cir) {
        if (this.getWeaponStack().isIn(ModItemTagProvider.DISABLES_SHIELD)) {
            cir.setReturnValue(Boolean.TRUE);
        }
    }
}
