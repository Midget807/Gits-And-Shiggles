package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record MindStonePayload(int timeTicksInverted) implements CustomPayload {
    public static final Id<MindStonePayload> PAYLOAD_ID = new Id<>(ModPackets.MIND_STONE);

    public static final PacketCodec<RegistryByteBuf, MindStonePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, MindStonePayload::timeTicksInverted,
            MindStonePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
