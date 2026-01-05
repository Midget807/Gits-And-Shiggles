package net.midget807.gitsnshiggles.network.S2C.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3f;

public record RenderHorsePayload(BlockPos blockPos, Vector3f pos) implements CustomPayload {
    public static final Id<RenderHorsePayload> PAYLOAD_ID = new Id<>(ModPackets.RENDER_HORSE);

    public static final PacketCodec<RegistryByteBuf, RenderHorsePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, RenderHorsePayload::blockPos,
            PacketCodecs.VECTOR3F, RenderHorsePayload::pos,
            RenderHorsePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
