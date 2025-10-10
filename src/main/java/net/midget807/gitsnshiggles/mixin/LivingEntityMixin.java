package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, RailgunRecoil {
    @Unique
    private boolean hasRailgunRecoil = false;
    @Unique
    private int recoilPower = 0;

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
    public void setRecoilPower(int power) {
        this.recoilPower = power;
    }

    @Override
    public int getRecoilPower() {
        return this.recoilPower;
    }

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void gitsnshiggles$railgunDamage(CallbackInfo ci) {
        this.getWorld().getProfiler().push("recoilDamage");
        if (((LivingEntity)((Object)this)) instanceof PlayerEntity player) {
            player.sendMessage(Text.literal("horiColl: " + this.horizontalCollision + "   recoil: " + this.hasRailgunRecoil), true);
        }
        if (this.isOnGround() && this.fallDistance == 0) {
            this.hasRailgunRecoil = false;
        }
        if (this.hasRailgunRecoil && (this.horizontalCollision || this.verticalCollision)) {
            this.calculateRecoilDamage();
            this.hasRailgunRecoil = false;
        }
        this.getWorld().getProfiler().pop();
    }


    @Unique
    private void calculateRecoilDamage() {
        this.damage(this.getDamageSources().flyIntoWall(), (float) (RailgunScalar.getScalar(this.recoilPower) * 4.0f));
        this.playSound(getFallSound(10));
    }

    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    private void gitsnshiggles$moreItemsThatDisableShield(CallbackInfoReturnable<Boolean> cir) {
        if (this.getWeaponStack().isIn(ModItemTagProvider.DISABLES_SHIELD)) {
            cir.setReturnValue(Boolean.TRUE);
        }
    }
}
