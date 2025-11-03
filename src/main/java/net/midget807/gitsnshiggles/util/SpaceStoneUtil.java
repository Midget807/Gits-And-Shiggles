package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.packet.SpaceStonePacket;
import net.midget807.gitsnshiggles.network.C2S.payload.SpaceStonePayload;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class SpaceStoneUtil {
    @Environment(EnvType.CLIENT)
    public static void runSpaceStoneTeleport(ClientPlayerEntity player) {
        HitResult hitResult = player.raycast(SpaceStonePacket.MAX_DISTANCE, 0.0f, false);
        if (hitResult.getType() == HitResult.Type.MISS) {
            player.getItemCooldownManager().set(ModItems.INFINITY_GAUNTLET, 10);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos tpTarget = null;
            BlockPos hitPos = blockHitResult.getBlockPos();
            Direction hitSide = blockHitResult.getSide();
            BlockPos sidePos = hitPos.offset(hitSide);
            if (stateIsAirOrFluid(player, sidePos) && hitSide != Direction.DOWN && hitSide != Direction.UP) {
                if (!stateIsAirOrFluid(player, sidePos.offset(Direction.DOWN)) && stateIsAirOrFluid(player, sidePos.offset(Direction.UP))) {
                    tpTarget = sidePos;
                }
            } else if (hitSide == Direction.UP) {
                if (stateIsAirOrFluid(player, sidePos) && stateIsAirOrFluid(player, sidePos.offset(Direction.UP))) {
                    tpTarget = sidePos;
                }
            } else if (hitSide == Direction.DOWN) {
                if (stateIsAirOrFluid(player, sidePos) && stateIsAirOrFluid(player, sidePos.offset(Direction.DOWN)) && !stateIsAirOrFluid(player, sidePos.offset(Direction.DOWN, 2))) {
                    tpTarget = sidePos.offset(Direction.DOWN);
                }
            }
            if (tpTarget != null) {
                ClientPlayNetworking.send(new SpaceStonePayload(tpTarget));
            } else {
                ModDebugUtil.debugMessage(player, "tpTarget null");
            }
        }
    }

    public static boolean stateIsAirOrFluid(ClientPlayerEntity player, BlockPos blockPos) {
        BlockState state = player.getWorld().getBlockState(blockPos);
        return !state.isAir() && !(state.getBlock() instanceof FluidBlock);
    }
}
