package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {
    @Unique
    public int voidStringTransformTicks = 0;
    @Unique
    public int powerStoneCraftingTicks = 0;
    @Unique
    public int spaceStoneCraftingTicks = 0;
    @Unique
    public int realityStoneCraftingTicks = 0;
    @Unique
    public int soulStoneCraftingTicks = 0;
    @Unique
    public int timeStoneCraftingTicks = 0;
    @Unique
    public int mindStoneCraftingTicks = 0;
    @Unique
    public boolean shouldCraft = false; //For infinity stone particles
    @Unique
    public boolean canCraft = false;

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
        if (this.getStack().isOf(ModItems.EMPTY_STONE) && this.getWorld().getBlockState(this.getBlockPos()).isOf(Blocks.END_PORTAL_FRAME) && this.getWorld().getBlockState(this.getBlockPos().offset(Direction.DOWN)).isOf(Blocks.ENCHANTING_TABLE)) {
            List<ItemEntity> itemEntities = this.getWorld().getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.5), itemEntity -> !itemEntity.isRemoved() && itemEntity.getItemAge() < 4500 && !itemEntity.getStack().isOf(ModItems.EMPTY_STONE));
            DefaultedList<ItemEntity> powerStoneCraft = DefaultedList.ofSize(2);
            DefaultedList<ItemEntity> spaceStoneCraft = DefaultedList.ofSize(2);
            DefaultedList<ItemEntity> realityStoneCraft = DefaultedList.ofSize(2);
            DefaultedList<ItemEntity> soulStoneCraft = DefaultedList.ofSize(2);
            DefaultedList<ItemEntity> timeStoneCraft = DefaultedList.ofSize(2);
            DefaultedList<ItemEntity> mindStoneCraft = DefaultedList.ofSize(2);
            for (ItemEntity entity : itemEntities) {
                if (entity.getStack().isOf(ModItems.EMPTY_STONE)) {
                    itemEntities.remove(entity);
                    continue;
                }
                if (entity.getStack().isOf(Items.END_CRYSTAL) && entity.getStack().getCount() >= 8) {
                    powerStoneCraft.add(entity);
                    powerStoneCraft.add((ItemEntity)((Object)(this)));
                } else if (entity.getStack().isOf(Items.ENDER_PEARL) && entity.getStack().getCount() == 16) {
                    spaceStoneCraft.add(entity);
                    spaceStoneCraft.add((ItemEntity)((Object)(this)));
                } else if (entity.getStack().isOf(Items.PHANTOM_MEMBRANE) && entity.getStack().getCount() >= 32) {
                    realityStoneCraft.add(entity);
                    realityStoneCraft.add((ItemEntity)((Object)(this)));
                } else if (entity.getStack().isOf(Items.SCULK_CATALYST) && entity.getStack().getCount() >= 16) {
                    soulStoneCraft.add(entity);
                    soulStoneCraft.add((ItemEntity)((Object)(this)));
                } else if (entity.getStack().isOf(Items.CLOCK) && entity.getStack().getCount() == 64) {
                    timeStoneCraft.add(entity);
                    timeStoneCraft.add((ItemEntity)((Object)(this)));
                } else if (entity.getStack().isOf(Items.HEART_OF_THE_SEA) && entity.getStack().getCount() >= 4) {
                    mindStoneCraft.add(entity);
                    mindStoneCraft.add((ItemEntity)((Object)(this)));
                }
            }
            if (!powerStoneCraft.isEmpty() && powerStoneCraft.getFirst().getStack().isOf(Items.END_CRYSTAL) && powerStoneCraft.getLast().getStack().isOf(ModItems.EMPTY_STONE)) {
                this.powerStoneCraftingTicks++;
                if (!shouldCraft) {
                    shouldCraft = true;
                }
            } else {
                this.powerStoneCraftingTicks = 0;
            }
            if (!spaceStoneCraft.isEmpty() && spaceStoneCraft.getFirst().getStack().isOf(Items.ENDER_PEARL) && spaceStoneCraft.getLast().getStack().isOf(ModItems.EMPTY_STONE)) {
                this.spaceStoneCraftingTicks++;
                if (!shouldCraft) {
                    shouldCraft = true;
                }
            } else {
                this.spaceStoneCraftingTicks = 0;
            }
            if (!realityStoneCraft.isEmpty() && realityStoneCraft.getFirst().getStack().isOf(Items.PHANTOM_MEMBRANE) && realityStoneCraft.getLast().getStack().isOf(ModItems.EMPTY_STONE)) {
                this.realityStoneCraftingTicks++;
                if (!shouldCraft) {
                    shouldCraft = true;
                }
            } else {
                this.realityStoneCraftingTicks = 0;
            }
            if (!soulStoneCraft.isEmpty() && soulStoneCraft.getFirst().getStack().isOf(Items.SCULK_CATALYST) && soulStoneCraft.getLast().getStack().isOf(ModItems.EMPTY_STONE)) {
                this.soulStoneCraftingTicks++;
                if (!shouldCraft) {
                    shouldCraft = true;
                }
            } else {
                this.soulStoneCraftingTicks = 0;
            }
            if (!timeStoneCraft.isEmpty() && timeStoneCraft.getFirst().getStack().isOf(Items.CLOCK) && timeStoneCraft.getLast().getStack().isOf(ModItems.EMPTY_STONE)) {
                this.timeStoneCraftingTicks++;
                if (!shouldCraft) {
                    shouldCraft = true;
                }
            } else {
                this.timeStoneCraftingTicks = 0;
            }
            if (!mindStoneCraft.isEmpty() && mindStoneCraft.getFirst().getStack().isOf(Items.HEART_OF_THE_SEA) && mindStoneCraft.getLast().getStack().isOf(ModItems.EMPTY_STONE)) {
                this.mindStoneCraftingTicks++;
                if (!shouldCraft) {
                    shouldCraft = true;
                }
            } else {
                this.mindStoneCraftingTicks = 0;
            }
            if (this.powerStoneCraftingTicks >= 1200) {
                powerStoneCraft.getLast().setStack(new ItemStack(ModItems.POWER_STONE));
                ItemStack ingredientStack = powerStoneCraft.getFirst().getStack();
                ingredientStack.decrement(8);
                powerStoneCraft.getFirst().setStack(ingredientStack);
                this.powerStoneCraftingTicks = 0;
                shouldCraft = false;
            }
            if (this.spaceStoneCraftingTicks >= 1200) {
                spaceStoneCraft.getLast().setStack(new ItemStack(ModItems.SPACE_STONE));
                spaceStoneCraft.getFirst().discard();
                this.spaceStoneCraftingTicks = 0;
                shouldCraft = false;
            }
            if (this.realityStoneCraftingTicks >= 1200) {
                realityStoneCraft.getLast().setStack(new ItemStack(ModItems.REALITY_STONE));
                ItemStack ingredientStack = realityStoneCraft.getFirst().getStack();
                ingredientStack.decrement(32);
                realityStoneCraft.getFirst().setStack(ingredientStack);
                this.realityStoneCraftingTicks = 0;
                shouldCraft = false;
            }
            if (this.soulStoneCraftingTicks >= 1200) {
                soulStoneCraft.getLast().setStack(new ItemStack(ModItems.SOUL_STONE));
                ItemStack ingredientStack = soulStoneCraft.getFirst().getStack();
                ingredientStack.decrement(16);
                soulStoneCraft.getFirst().setStack(ingredientStack);
                this.soulStoneCraftingTicks = 0;
                shouldCraft = false;
            }
            if (this.timeStoneCraftingTicks >= 1200) {
                timeStoneCraft.getLast().setStack(new ItemStack(ModItems.TIME_STONE));
                timeStoneCraft.getFirst().discard();
                this.timeStoneCraftingTicks = 0;
                shouldCraft = false;
            }
            if (this.mindStoneCraftingTicks >= 1200) {
                mindStoneCraft.getLast().setStack(new ItemStack(ModItems.MIND_STONE));
                ItemStack ingredientStack = mindStoneCraft.getFirst().getStack();
                ingredientStack.decrement(4);
                mindStoneCraft.getFirst().setStack(ingredientStack);
                this.mindStoneCraftingTicks = 0;
                shouldCraft = false;
            }
            if (!this.getWorld().isClient && shouldCraft) {
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.ENCHANT, this.getX(), this.getY() + 0.5, this.getZ(), 4, 0.1, 0.1, 0.1, 1.0);
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 0.5, this.getZ(), 4, 0.1, 0.1, 0.1, 1.0);
            }
        }
        if (this.getStack().isOf(ModItems.RED_HOT_GOLD_ALLOY_INGOT) && this.getWorld().getBlockState(this.getBlockPos()).isIn(BlockTags.ANVIL)) {
            int count = this.getStack().getCount();
            this.setStack(new ItemStack(ModItems.RED_HOT_GOLD_ALLOY_PLATE, count));
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
