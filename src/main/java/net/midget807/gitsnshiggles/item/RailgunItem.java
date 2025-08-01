package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.entity.RailgunBulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class RailgunItem extends Item {
    public RailgunItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            RailgunBulletEntity railgunBulletEntity = new RailgunBulletEntity(world);
            railgunBulletEntity.setPosition(user.getEyePos().add(user.getRotationVector().normalize()));
            railgunBulletEntity.setVelocity(user.getRotationVector().normalize().multiply(10));
            world.spawnEntity(railgunBulletEntity);
            //user.getItemCooldownManager().set(stack.getItem(), 200);
        }

        return TypedActionResult.pass(stack);
    }
}
