package net.midget807.gitsnshiggles.network.S2C.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record MindStoneInvertPayload(int timeTicksInvert) implements CustomPayload {
    public static final Id<MindStoneInvertPayload> PAYLOAD_ID = new Id<>(ModPackets.MIND_STONE_INVERT);

    public static final PacketCodec<RegistryByteBuf, MindStoneInvertPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, MindStoneInvertPayload::timeTicksInvert,
            MindStoneInvertPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
