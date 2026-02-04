package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.SpaceStonePayload;
import net.midget807.gitsnshiggles.registry.ModParticles;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.ModParticleUtil;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpaceStonePacket {
    public static final int MAX_DISTANCE = 128;
    public static void receive(SpaceStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            BlockPos blockPos = payload.tpBlockPos();
            if (blockPos != null) {
                player.teleport(
                        blockPos.getX() + 0.5,
                        blockPos.getY(),
                        blockPos.getZ() + 0.5,
                        false
                );
            }
            if (!player.getAbilities().creativeMode) InfinityStoneUtil.setStoneCooldown(player, InfinityStoneUtil.Stones.SPACE);
        });
    }
}
