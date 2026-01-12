package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ShuffleInventoryPayload(boolean store) implements CustomPayload {
    public static final Id<ShuffleInventoryPayload> PAYLOAD_ID = new Id<>(ModPackets.SHUFFLE_INVENTORY);

    public static final PacketCodec<RegistryByteBuf, ShuffleInventoryPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, ShuffleInventoryPayload::store,
            ShuffleInventoryPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
