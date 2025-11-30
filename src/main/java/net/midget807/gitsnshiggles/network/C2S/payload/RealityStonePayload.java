package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record RealityStonePayload(int timeTicksForTransform) implements CustomPayload {
    public static final Id<RealityStonePayload> PAYLOAD_ID = new Id<>(ModPackets.REALITY_STONE);

    public static final PacketCodec<RegistryByteBuf, RealityStonePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, RealityStonePayload::timeTicksForTransform,
            RealityStonePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
