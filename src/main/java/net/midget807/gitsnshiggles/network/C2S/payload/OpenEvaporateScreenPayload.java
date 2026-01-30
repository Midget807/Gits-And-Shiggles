package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record OpenEvaporateScreenPayload(BlockPos pos, int screenIndex) implements CustomPayload {
    public static final Id<OpenEvaporateScreenPayload> PAYLOAD_ID = new Id<>(ModPackets.OPEN_EVAPORATE_SCREEN);

    public static final PacketCodec<RegistryByteBuf, OpenEvaporateScreenPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, OpenEvaporateScreenPayload::pos,
            PacketCodecs.INTEGER, OpenEvaporateScreenPayload::screenIndex,
            OpenEvaporateScreenPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
