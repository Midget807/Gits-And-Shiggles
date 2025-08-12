package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.PowerStonePayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class PowerStonePacket {
    public static void receive(PowerStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();


        });
    }
}
