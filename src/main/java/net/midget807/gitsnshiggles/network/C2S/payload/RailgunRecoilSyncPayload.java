package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.midget807.gitsnshiggles.util.ModPacketCodecs;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;

public record RailgunRecoilSyncPayload(Vec3d velocity) implements CustomPayload {
    public static final CustomPayload.Id<RailgunRecoilSyncPayload> PAYLOAD_ID = new CustomPayload.Id<>(ModPackets.RAILGUN_RECOIL_SYNC);

    public static final PacketCodec<RegistryByteBuf, RailgunRecoilSyncPayload> CODEC = PacketCodec.tuple(
            ModPacketCodecs.VEC3D, RailgunRecoilSyncPayload::velocity,
            RailgunRecoilSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
