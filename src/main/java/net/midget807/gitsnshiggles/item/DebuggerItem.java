package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebuggerItem extends Item {

    public DebuggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.getAbilities().allowFlying = !player.getAbilities().allowFlying;
        if (!player.getAbilities().allowFlying) {
            player.getAbilities().flying = false;
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player) {

        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
