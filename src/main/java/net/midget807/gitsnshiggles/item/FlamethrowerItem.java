package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.cca.FlamethrowerComponent;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.entity.FlamethrowerFireEntity;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class FlamethrowerItem extends Item {

    public FlamethrowerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack handStack = user.getStackInHand(hand);
        Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        ItemStack otherHandStack = user.getStackInHand(otherHand);
        user.setCurrentHand(hand);
        FlamethrowerComponent flamethrowerComponent = FlamethrowerComponent.get(user);
        if (otherHandStack.isIn(ModItemTagProvider.FLAMETHROWER_INSERTABLE)) {
            if (otherHandStack.isIn(ModItemTagProvider.FLAMETHROWER_POWER) && flamethrowerComponent.getValue3() == 0) {
                flamethrowerComponent.setValue3(FlamethrowerComponent.MAX_MODIFIER);
                user.getItemCooldownManager().set(ModItems.FLAMETHROWER, 10);
                return TypedActionResult.success(handStack);
            } else if (otherHandStack.isOf(Items.MAGMA_BLOCK) && flamethrowerComponent.getValue2() == 0) {
                flamethrowerComponent.setValue2(FlamethrowerComponent.MAX_MODIFIER);
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
        if (user instanceof PlayerEntity player) {
            FlamethrowerComponent flamethrowerComponent = FlamethrowerComponent.get(player);
            if (player.getItemCooldownManager().isCoolingDown(ModItems.FLAMETHROWER)) {
                player.stopUsingItem();
            }
            if (!world.isClient() && !player.getItemCooldownManager().isCoolingDown(ModItems.FLAMETHROWER)) {
                FlamethrowerFireEntity flamethrowerFire = new FlamethrowerFireEntity(user, world);
                flamethrowerFire.setVelocity(user, 30.0f, 1.0f);
                if (flamethrowerComponent.getValue2() > 0) {
                    flamethrowerFire.setExtraRange(true);
                } else if (flamethrowerComponent.getValue2() <= 0) {
                    flamethrowerFire.setExtraRange(false);
                }

                if (flamethrowerComponent.getValue3() > 0) {
                    flamethrowerFire.setExtraDamage(true);
                } else if (flamethrowerComponent.getValue3() <= 0) {
                    flamethrowerFire.setExtraDamage(false);
                }
                world.spawnEntity(flamethrowerFire);
            } //Spawns flame entities
            if (flamethrowerComponent.getValue1() < (FlamethrowerComponent.MAX_OVERHEAT) && !world.isClient) {
                if (flamethrowerComponent.getValue2() > 0) {
                    flamethrowerComponent.addToValue1(3);
                }
                if (flamethrowerComponent.getValue3() > 0) {
                    flamethrowerComponent.addToValue1(4);
                }
                if (flamethrowerComponent.getValue2() <= 0 && flamethrowerComponent.getValue3() <= 0){
                    flamethrowerComponent.addToValue1(2);
                }
            }
        }
    }

}
