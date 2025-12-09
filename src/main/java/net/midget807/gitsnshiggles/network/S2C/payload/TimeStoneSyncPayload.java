package net.midget807.gitsnshiggles.network.S2C.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record TimeStoneSyncPayload(boolean shouldFreeze) implements CustomPayload {
    public static final Id<TimeStoneSyncPayload> PAYLOAD_ID = new Id<>(ModPackets.TIME_STONE_SYNC);

    public static final PacketCodec<RegistryByteBuf, TimeStoneSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, TimeStoneSyncPayload::shouldFreeze,
            TimeStoneSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
