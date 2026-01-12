package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {
    @Unique
    public int voidStringTransformTicks = 0;

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
            this.setVelocity(0.0, 5.0, 0.0);
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
        if (this.getStack().isOf(Items.STRING) && this.getWorld().getBlockState(this.getBlockPos()).isOf(Blocks.END_PORTAL_FRAME)) {
            this.voidStringTransformTicks++;
            if (this.voidStringTransformTicks >= 200) {
                int count = this.getStack().getCount();
                this.setStack(new ItemStack(ModItems.VOID_STRING).copyWithCount(count));
            }
            if (!this.getWorld().isClient) {
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 0.5, this.getZ(), 10, 0.1, 0.1, 0.1, 1.0);
            }
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void gitsnshiggles$lightsaberDrill(CallbackInfo ci) {
        if (this.getStack().isIn(ModItemTagProvider.LIGHTSABERS) ) {
            this.getWorld().breakBlock(this.getBlockPos().offset(Direction.DOWN), false);
            this.killOtherItems();
        }
    }

    @Unique
    private void killOtherItems() {
        List<ItemEntity> otherEntities = this.getWorld().getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.25, 0.0, 0.25), itemEntity -> itemEntity != (Object) this && !itemEntity.getStack().isIn(ModItemTagProvider.LIGHTSABERS));
        for (ItemEntity itemEntity : otherEntities) {
            if (this.isRemoved()) {
                break;
            }
            itemEntity.kill();
            this.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f);
        }
    }
}
