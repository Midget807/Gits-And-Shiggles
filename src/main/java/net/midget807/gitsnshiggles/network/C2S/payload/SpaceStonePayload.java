package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record SpaceStonePayload(boolean isActive) implements CustomPayload {
    public static final Id<SpaceStonePayload> PAYLOAD_ID = new Id<>(ModPackets.SPACE_STONE);

    public static final PacketCodec<RegistryByteBuf, SpaceStonePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SpaceStonePayload::isActive,
            SpaceStonePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
