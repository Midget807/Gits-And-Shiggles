package net.midget807.gitsnshiggles.entity.client;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.S2C.payload.AddSchizophreniaEntityPayload;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.midget807.gitsnshiggles.util.inject.Schizophrenia;
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
import net.minecraft.world.spawner.SpecialSpawner;


public class SchizophreniaSpawner implements SpecialSpawner {
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        Random random = world.random;
        this.cooldown--;
        if (this.cooldown > 0) {
            return 0;
        } else {
            this.cooldown = this.cooldown + 1/*(60 + random.nextInt(60))*/ * 20;
            int i = 0;
            for (ServerPlayerEntity serverPlayerEntity : world.getPlayers()) {
                if (!serverPlayerEntity.isSpectator()) {
                    BlockPos blockPos = serverPlayerEntity.getBlockPos();
                    ServerStatHandler serverStatHandler = serverPlayerEntity.getStatHandler();
                    int j = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                    int k = 24000;
                    if (random.nextInt(j) >= k * 10) {
                        BlockPos blockPos2 = getBlockPosFromBehind(blockPos, serverPlayerEntity, world);
                        BlockState blockState = world.getBlockState(blockPos2);
                        FluidState fluidState = world.getFluidState(blockPos2);
                        if (isClearForSpawn(world, blockPos2, blockState, fluidState)) {
                            ServerPlayNetworking.send(serverPlayerEntity, new AddSchizophreniaEntityPayload(1000));
                            SchizophreniaManager schizophreniaManager = ((Schizophrenia) serverPlayerEntity).getSchizophreniaManager();
                            schizophreniaManager.add(new SchizophreniaEntity(
                                    ModUtil.getSpawnableMobs().get(i),
                                    serverPlayerEntity.getPos(),
                                    (float) world.random.nextBetween(-180, 180),
                                    1000
                            ));
                            i++;
                        }
                    }
                }
            }
            return i;
        }
    }

    private boolean isClearForSpawn(ServerWorld blockView, BlockPos pos, BlockState state, FluidState fluidState) {
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

    private BlockPos getBlockPosFromBehind(BlockPos blockPos, ServerPlayerEntity serverPlayerEntity, ServerWorld world) {
        Vec3d rotVec = serverPlayerEntity.getRotationVector().normalize().multiply(2);
        BlockPos blockPos2 = blockPos.add(new Vec3i((int) rotVec.x, serverPlayerEntity.getBlockY(), (int) rotVec.z));
        return blockPos2;
    }
}
