package net.midget807.gitsnshiggles.network.S2C.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.network.S2C.payload.SoulStonePayload;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;

public class SoulStonePacket {
    public static void receive(SoulStonePayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            World world = player.getWorld();
            ((InfinityStoneCooldown)player).setSoulStoneCD(payload.timeSync());
        });
    }
}
