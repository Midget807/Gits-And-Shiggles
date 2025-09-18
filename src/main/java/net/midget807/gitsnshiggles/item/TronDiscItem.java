package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.entity.TronDiscEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class TronDiscItem extends Item {
    public TronDiscItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != user;
        ItemStack stack = user.getStackInHand(hand);
        TronDiscEntity tronDiscEntity = new TronDiscEntity(user, world, stack);
        tronDiscEntity.getNearestEntityInViewPreferPlayer(user, user.getX(), user.getY(), user.getZ(), 20.0, entityPredicate);
        tronDiscEntity.setRebounds(2);
        tronDiscEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.0f, 0);
        if (!world.isClient) {
            world.spawnEntity(tronDiscEntity);
        }
        return TypedActionResult.pass(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
}
