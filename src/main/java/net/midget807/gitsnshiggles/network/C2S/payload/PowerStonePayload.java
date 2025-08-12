package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record PowerStonePayload(BlockPos blockPos) implements CustomPayload {
    public static final CustomPayload.Id<PowerStonePayload> PAYLOAD_ID = new CustomPayload.Id<>(ModPackets.POWER_STONE);

    public static final PacketCodec<RegistryByteBuf, PowerStonePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, PowerStonePayload::blockPos,
            PowerStonePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
