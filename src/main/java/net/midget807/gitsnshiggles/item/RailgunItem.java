package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.entity.RailgunBulletEntity;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class RailgunItem extends Item {
    public static final float FOV_MULTIPLIER = 0.05f;
    public static final List<Item> RAILGUN_PROJECTILES = List.of(Items.IRON_NUGGET, Items.IRON_INGOT, Items.IRON_BLOCK, Items.ANVIL, Items.CHIPPED_ANVIL, Items.DAMAGED_ANVIL);
    public static final Map<Item, Integer> PROJECTILE_POWERS = Map.of(
            Items.IRON_NUGGET, 1,
            Items.IRON_INGOT, 9,
            Items.IRON_BLOCK, 9 * 9,
            Items.ANVIL, 9 * 9 * 3 + 4,
            Items.CHIPPED_ANVIL, 9 * 9 * 3 + 4 - 5,
            Items.DAMAGED_ANVIL, 9 * 9 * 3 + 4 - 10
    );
    public static final Predicate<ItemStack> ACCEPTABLE_PROJECTILES = stack -> RAILGUN_PROJECTILES.contains(stack.getItem());

    public RailgunItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0f, 1.0f);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.consumeHeldItem(world, user, hand);

    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0f, 1.0f);
        return stack;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        user.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0f, 1.0f);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public static ItemStack getHeldProjectiles(LivingEntity entity, Predicate<ItemStack> predicate) {
        if (predicate.test(entity.getStackInHand(Hand.OFF_HAND))) {
            return entity.getStackInHand(Hand.OFF_HAND);
        } else {
            return predicate.test(entity.getStackInHand(Hand.MAIN_HAND)) ? entity.getStackInHand(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

}
