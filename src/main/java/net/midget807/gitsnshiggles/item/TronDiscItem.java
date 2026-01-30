package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.entity.TronDiscEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class TronDiscItem extends Item {
    public TronDiscItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Predicate<LivingEntity> entityPredicate = entity2 -> !entity2.isSpectator() && entity2 != user;
        TronDiscEntity tronDiscEntity = new TronDiscEntity(user, world, stack, user instanceof PlayerEntity player ? player.getInventory().getSlotWithStack(stack) : -1);
        tronDiscEntity.getNearestEntityInViewPreferPlayer(user, user.getX(), user.getY(), user.getZ(), 32.0d, entityPredicate);
        tronDiscEntity.setRebounds(reboundsForPullProgress(user));
        tronDiscEntity.setPitch(user.getPitch());
        tronDiscEntity.prevPitch = user.prevPitch;
        tronDiscEntity.prevYaw = user.prevYaw;
        tronDiscEntity.setYaw(user.getYaw());
        tronDiscEntity.canReturn = false;
        tronDiscEntity.isReturning = false;
        tronDiscEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.0f, 0);
        if (!world.isClient) {
            world.spawnEntity(tronDiscEntity);
            if (user instanceof PlayerEntity player) {
                player.getInventory().removeOne(stack);
            }
        }
    }

    public static float getPullProgress(int useTicks) {
        /*Ticks in seconds*/
        float f  = useTicks / 20.0f;
        /*Sub f into function fo (x^2+2x)/3*/
        f = (f * f + f * 2.0f) / 3.0f;
        /*Maxes f at 1.0f*/
        if (f > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    public static int reboundsForPullProgress(@NotNull LivingEntity user) {
        int useTicks = user.getItemUseTime();
        float f = getPullProgress(useTicks);
        return Math.clamp(Math.round(f / 0.2f), 0, 5);
    }

}
