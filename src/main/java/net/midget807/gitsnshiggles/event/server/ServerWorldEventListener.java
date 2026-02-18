package net.midget807.gitsnshiggles.event.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.S2C.payload.AddSchizophreniaEntityPayload;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;

public class ServerWorldEventListener {
    private static int cooldown;

    public static void execute() {
        ServerWorldEvents.LOAD.register((server, world) -> {

            /*Random random = world.random;
            cooldown--;
            if (cooldown < 0) {
                cooldown = cooldown + 1*//*(60 + random.nextInt(60))*//* * 20;
                int i = 0;
                for (ServerPlayerEntity serverPlayerEntity : world.getPlayers()) {
                    if (!serverPlayerEntity.isSpectator()) {
                        BlockPos blockPos = serverPlayerEntity.getBlockPos();
                        ServerStatHandler serverStatHandler = serverPlayerEntity.getStatHandler();
                        int j = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                        int k = 24000;
                        if (random.nextInt(j) >= k * 5) {
                            BlockPos blockPos2 = serverPlayerEntity.getBlockPos().east(-3 + random.nextInt(7)).south(-3 + random.nextInt(7));
                            BlockState blockState = world.getBlockState(blockPos2);
                            FluidState fluidState = world.getFluidState(blockPos2);
                            if (isClearForSpawn(world, blockPos2, blockState, fluidState)) {
                                ServerPlayNetworking.send(serverPlayerEntity, new AddSchizophreniaEntityPayload(1000));
                                i++;
                            }
                        }
                    }
                }
            }*/
        });
    }

    private static boolean isClearForSpawn(ServerWorld blockView, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.isFullCube(blockView, pos)) {
            return false;
        } else if (state.emitsRedstonePower()) {
            return false;
        } else if (!fluidState.isEmpty()) {
            return false;
        } else {
            return !state.isIn(BlockTags.PREVENT_MOB_SPAWNING_INSIDE);
        }
    }


    private static BlockPos getBlockPosFromBehind(BlockPos blockPos, ServerPlayerEntity serverPlayerEntity, ServerWorld world) {
        Vec3d rotVec = serverPlayerEntity.getRotationVector().normalize().multiply(2);
        BlockPos blockPos2 = blockPos.add(new Vec3i((int) rotVec.x, serverPlayerEntity.getBlockY(), (int) rotVec.z));
        return blockPos2;
    }
}
