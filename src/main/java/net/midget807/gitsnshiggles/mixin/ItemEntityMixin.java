package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {

    @Shadow public abstract ItemStack getStack();

    @Shadow public abstract void setStack(ItemStack stack);

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void tickInVoid() {
        if (this.getStack().isOf(Items.DIAMOND)) {
            ItemStack oldStack = this.getStack();
            this.setStack(new ItemStack(ModItems.KYBER_CRYSTAL, oldStack.getCount()));
            this.setVelocity(Vec3d.ZERO);
            this.setNoGravity(true);
            this.setVelocity(0.0, 3.0, 0.0);
            this.velocityDirty = true;
        } else {
            this.discard();
        }
    }
    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ItemEntity;velocityDirty:Z", ordinal = 0))
    private void gitsnshiggles$particles(CallbackInfo ci) {
        if (this.getWorld().isClient && this.getStack().isOf(ModItems.KYBER_CRYSTAL)) {
            this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX() + this.random.nextFloat() * 0.4, this.getY(), this.getZ() + this.random.nextFloat() * 0.4, 0, -0.1, 0);
        }
    }
}
