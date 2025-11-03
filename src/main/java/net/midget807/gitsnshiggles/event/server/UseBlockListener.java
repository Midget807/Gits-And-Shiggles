package net.midget807.gitsnshiggles.event.server;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import java.util.List;

public class UseBlockListener {
    public static void execute() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.getInventory().getArmorStack(3).isIn(ModItemTagProvider.SANTA_HATS) && player.isSneaking() && player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                ModDebugUtil.debugMessage(player, "interact", true);
                return elvesBreakBlock(player, world, hand, hitResult);
            }
            return ActionResult.PASS;
        });
    }

    private static ActionResult elvesBreakBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        List<ElfEntity> elves = world.getEntitiesByClass(ElfEntity.class, player.getBoundingBox().expand(128), elfEntity -> elfEntity.isTamed() && elfEntity.getOwner() != null && elfEntity.getOwner().equals(player));
        elves.forEach(elfEntity -> {
            elfEntity.sendMessage(Text.literal("hi"));
        });
        player.sendMessage(Text.literal("No. entities: " + elves.size()));
        return ActionResult.SUCCESS;
    }
}
