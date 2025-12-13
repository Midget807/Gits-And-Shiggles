package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.RailgunRecoilSyncPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class RailgunRecoilSyncPacket {
    public static void receive(RailgunRecoilSyncPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            player.setVelocity(payload.velocity());
        });
    }
}
