package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.MindStoneCDSyncPayload;
import net.midget807.gitsnshiggles.network.C2S.payload.MindStonePayload;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MindStoneCDSyncPacket {
    public static void receive(MindStoneCDSyncPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            InfinityStoneUtil.setStoneCooldown(player, InfinityStoneUtil.Stones.MIND);
        });
    }
}
