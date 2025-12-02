package net.midget807.gitsnshiggles.network.S2C.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record SoulStonePayload(int timeSync) implements CustomPayload {
    public static final Id<SoulStonePayload> PAYLOAD_ID = new Id<>(ModPackets.SPACE_STONE);

    public static final PacketCodec<RegistryByteBuf, SoulStonePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SoulStonePayload::timeSync,
            SoulStonePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
