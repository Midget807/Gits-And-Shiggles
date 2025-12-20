package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record SummonElvesPayload(BlockPos blockPos, int elfCount) implements CustomPayload {
    public static final CustomPayload.Id<SummonElvesPayload> PAYLOAD_ID = new CustomPayload.Id<>(ModPackets.SUMMON_ELVES);

    public static final PacketCodec<RegistryByteBuf, SummonElvesPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, SummonElvesPayload::blockPos,
            PacketCodecs.INTEGER, SummonElvesPayload::elfCount,
            SummonElvesPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
