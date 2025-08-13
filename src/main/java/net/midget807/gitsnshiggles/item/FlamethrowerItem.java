package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.entity.FlamethrowerFireEntity;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FlamethrowerItem extends Item {
    public int extraRangeTicks = 0;
    public int extraDamageTicks = 0;
    public int useTicks = 0;
    public boolean isOverheating = false;
    public boolean isUsing = true;

    public FlamethrowerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack handStack = user.getStackInHand(hand);
        Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        ItemStack otherHandStack = user.getStackInHand(otherHand);
        user.setCurrentHand(hand);
        if (otherHandStack.isIn(ModItemTagProvider.FLAMETHROWER_INSERTABLE)) {
            if (otherHandStack.isIn(ModItemTagProvider.FLAMETHROWER_POWER) && this.extraDamageTicks == 0) {
                this.extraDamageTicks = 200;
                user.getItemCooldownManager().set(ModItems.FLAMETHROWER, 10);
                return TypedActionResult.success(handStack);
            } else if (otherHandStack.isOf(Items.MAGMA_BLOCK) && this.extraRangeTicks == 0) {
                this.extraRangeTicks = 200;
                user.getItemCooldownManager().set(ModItems.FLAMETHROWER, 10);
                return TypedActionResult.success(handStack);
            }
        }
        return TypedActionResult.fail(handStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (this.useTicks < 600) {
            this.useTicks += 2;
        } else {
            this.useTicks = 600;
        }
        if (!this.isUsing) {
            this.isUsing = true;
        }
        if (this.extraDamageTicks > 0) {
            this.extraDamageTicks--;
        }
        if (this.extraRangeTicks > 0) {
            this.extraRangeTicks--;
        }
        if (!world.isClient()) {
            FlamethrowerFireEntity flamethrowerFire = new FlamethrowerFireEntity(user, world);
            flamethrowerFire.setVelocity(user, 30.0f, 1.0f);
            if (getExtraRangeTicks() > 0) {
                flamethrowerFire.setExtraRange(true);
            }
            if (getExtraRangeTicks() <= 0) {
                flamethrowerFire.setExtraRange(false);
            }
            if (getExtraDamageTicks() > 0) {
                flamethrowerFire.setExtraDamage(true);
            }
            if (getExtraDamageTicks() <= 0) {
                flamethrowerFire.setExtraDamage(false);
            }
            world.spawnEntity(flamethrowerFire);
        }
        if (this.useTicks >= 600) {
            this.isOverheating = true;
            if (user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(ModItems.FLAMETHROWER, 100);
            }
        } else {
            this.isOverheating = false;
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        this.isUsing = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (this.isOverheating) {
            entity.setOnFireFor(5);
        }
        if (this.useTicks < 600) {
            this.isOverheating = false;
        }
        if (!this.isUsing) {
            this.useTicks--;
        }
        if (this.extraRangeTicks > 0) {
            this.extraRangeTicks--;
        }
        if (this.extraDamageTicks > 0) {
            this.extraDamageTicks--;
        }
    }

    public int getExtraDamageTicks() {
        return this.extraDamageTicks;
    }
    public void setExtraDamageTicks(int extraDamageTicks) {
        this.extraDamageTicks = extraDamageTicks;
    }

    public int getExtraRangeTicks() {
        return this.extraRangeTicks;
    }
    public void setExtraRangeTicks(int extraRangeTicks) {
        this.extraRangeTicks = extraRangeTicks;
    }

    public int getUseTicks() {
        return this.useTicks;
    }
    public void setUseTicks(int useTicks) {
        this.useTicks = useTicks;
    }

    public boolean isOverheating() {
        return this.isOverheating;
    }
    public void setOverheating(boolean overheating) {
        isOverheating = overheating;
    }

}
