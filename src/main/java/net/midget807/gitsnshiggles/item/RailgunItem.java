package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.entity.RailgunBulletEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
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
            railgunBulletEntity.setStack(new ItemStack(Items.IRON_NUGGET));
            Vec3d vec3d = user.getRotationVector();
            railgunBulletEntity.setPosition(user.getX() + vec3d.x * 1.5, user.getEyeY()  + vec3d.y * 1.5, user.getZ()  + vec3d.z * 1.5);
            railgunBulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.0f, 0.0f);
            world.spawnEntity(railgunBulletEntity);
            //user.getItemCooldownManager().set(stack.getItem(), 200);
        }

        return TypedActionResult.pass(stack);
    }
}
