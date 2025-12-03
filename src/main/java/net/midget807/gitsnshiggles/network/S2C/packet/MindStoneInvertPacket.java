package net.midget807.gitsnshiggles.network.S2C.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.network.S2C.payload.MindStoneInvertPayload;
import net.midget807.gitsnshiggles.network.S2C.payload.SoulStonePayload;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;

public class MindStoneInvertPacket {
    public static void receive(MindStoneInvertPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            World world = player.getWorld();
            ((MindStoneInvert)player).setTimeTicksInverted(payload.timeTicksInvert());
        });
    }
}
