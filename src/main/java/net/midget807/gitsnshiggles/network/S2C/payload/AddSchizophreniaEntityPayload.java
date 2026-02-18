package net.midget807.gitsnshiggles.network.S2C.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record AddSchizophreniaEntityPayload(int maxAge) implements CustomPayload {
    public static final Id<AddSchizophreniaEntityPayload> PAYLOAD_ID = new Id<>(ModPackets.ADD_SCHIZOPHRENIA_ENTITY);

    public static final PacketCodec<RegistryByteBuf, AddSchizophreniaEntityPayload> CODEC = PacketCodec.of(
            (value, buf) -> buf.writeInt(value.maxAge()),
            buf -> new AddSchizophreniaEntityPayload(buf.readInt())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
