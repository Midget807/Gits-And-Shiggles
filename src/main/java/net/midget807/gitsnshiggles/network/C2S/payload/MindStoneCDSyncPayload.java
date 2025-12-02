package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record MindStoneCDSyncPayload(int cooldown) implements CustomPayload {
    public static final Id<MindStoneCDSyncPayload> PAYLOAD_ID = new Id<>(ModPackets.MIND_STONE_CD_SYNC);

    public static final PacketCodec<RegistryByteBuf, MindStoneCDSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, MindStoneCDSyncPayload::cooldown,
            MindStoneCDSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
