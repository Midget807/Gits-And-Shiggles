package net.midget807.gitsnshiggles.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityRemoverItem extends Item {
    public EntityRemoverItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        List<Entity> entities = world.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(3), entity -> {
           if (entity instanceof LivingEntity livingEntity && (livingEntity.isDead() || !livingEntity.isAlive())) {
               return true;
           } else return !entity.isAlive();
        });
        entities.forEach(entity -> {
            entity.remove(Entity.RemovalReason.DISCARDED);
        });
        return TypedActionResult.success(stack);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
