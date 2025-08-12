package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
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

    public FlamethrowerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack handStack = user.getStackInHand(hand);
        Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        ItemStack otherHandStack = user.getStackInHand(otherHand);
        if (otherHandStack.isIn(ModItemTagProvider.FLAMETHROWER_INSERTABLE)) {
            if (otherHandStack.isIn(ModItemTagProvider.FLAMETHROWER_POWER)) {
                this.extraDamageTicks = 200;
                user.getItemCooldownManager().set(Items.SOUL_SAND, 20);
                user.getItemCooldownManager().set(Items.SOUL_SOIL, 20);
                return TypedActionResult.success(handStack);
            } else if (otherHandStack.isOf(Items.MAGMA_BLOCK)) {
                this.extraRangeTicks = 200;
                user.getItemCooldownManager().set(Items.MAGMA_BLOCK, 20);
                return TypedActionResult.success(handStack);
            }
        }
        return TypedActionResult.pass(handStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        this.useTicks++;
        if (this.extraDamageTicks > 0) {
            this.extraDamageTicks--;
        }
        if (this.extraRangeTicks > 0) {
            this.extraRangeTicks--;
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
