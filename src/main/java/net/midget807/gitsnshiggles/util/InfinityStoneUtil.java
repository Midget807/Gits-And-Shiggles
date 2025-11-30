package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.datagen.ModBlockTagProvider;
import net.midget807.gitsnshiggles.network.C2S.packet.SpaceStonePacket;
import net.midget807.gitsnshiggles.network.C2S.payload.SpaceStonePayload;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class InfinityStoneUtil {
    public static final int POWER_STONE_CD = 30 * 20; // 30 sec
    public static final int SPACE_STONE_CD = 20 * 20; // 20 sec
    public static final int REALITY_STONE_CD = 10 * 60 * 20; // 10 min
    public static final int SOUL_STONE_CD = 3 * 60 * 60 * 20; // 3 hour
    public static final int TIME_STONE_CD = 30 * 60 * 20; // 30 min
    public static final int MIND_STONE_CD = 10 * 60 * 20; // 10 min
    public static final int TIMER_REALITY_STONE = 10 * 20; // 10 sec
    public static final int TIMER_TIME_STONE = 3 * 20; // 3 sec
    public static final int TIMER_MIND_STONE = 5 * 20; // 5 sec

    @Environment(EnvType.CLIENT)
    public static void getSpaceStoneTeleport(ClientPlayerEntity player) {
        HitResult hitResult = player.raycast(SpaceStonePacket.MAX_DISTANCE, 0.0f, false);
        if (hitResult.getType() == HitResult.Type.MISS) {
            player.getItemCooldownManager().set(ModItems.INFINITY_GAUNTLET, 10);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos tpTarget = null;
            BlockPos hitPos = blockHitResult.getBlockPos();
            Direction hitSide = blockHitResult.getSide();
            BlockPos sidePos = hitPos.offset(hitSide);
            if (hitSide != Direction.DOWN && hitSide != Direction.UP) {
                if (stateIsSafe(player, sidePos)) {
                    if (!stateIsSafe(player, sidePos.offset(Direction.DOWN)) && stateIsSafe(player, sidePos.offset(Direction.UP))) {
                        tpTarget = sidePos;
                    }
                }
            } else if (hitSide == Direction.UP) {
                if (stateIsSafe(player, sidePos) && stateIsSafe(player, sidePos.offset(Direction.UP))) {
                    tpTarget = sidePos;
                }
            } else { /*hitSide == DOWN*/
                if (stateIsSafe(player, sidePos) && stateIsSafe(player, sidePos.offset(Direction.DOWN)) && !stateIsSafe(player, sidePos.offset(Direction.DOWN, 2))) {
                    tpTarget = sidePos.offset(Direction.DOWN);
                }
            }

            if (tpTarget != null) {
                ClientPlayNetworking.send(new SpaceStonePayload(tpTarget));
                ((InfinityStoneCooldown) player).setSpaceStoneCD(InfinityStoneUtil.SPACE_STONE_CD);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static boolean stateIsSafe(ClientPlayerEntity player, BlockPos blockPos) {
        BlockState state = player.getWorld().getBlockState(blockPos);
        return state.isIn(ModBlockTagProvider.SPACE_STONE_SAFE) || !state.isSolid();
    }

    public static int getCDTextureRatio(int timeTicks, int textureHeight) {

    }

}
