package net.midget807.gitsnshiggles.network.S2C.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.network.S2C.payload.TimeStoneSyncPayload;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;

public class TimeStoneSyncPacket {
    public static void receive(TimeStoneSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            World world = player.getWorld();
            ((TimeStoneFreeze)player).setShouldTimeFreeze(payload.shouldFreeze());
        });
    }
}
