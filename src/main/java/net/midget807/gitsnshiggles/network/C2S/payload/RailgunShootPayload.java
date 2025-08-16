package net.midget807.gitsnshiggles.network.C2S.payload;

import net.midget807.gitsnshiggles.registry.ModPackets;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record RailgunShootPayload(ItemStack railgunStack, float pitch, float yaw) implements CustomPayload {
    public static final Id<RailgunShootPayload> PAYLOAD_ID = new Id<>(ModPackets.RAILGUN_SHOOT);

    public static final PacketCodec<RegistryByteBuf, RailgunShootPayload> CODEC = PacketCodec.tuple(
            ItemStack.PACKET_CODEC, RailgunShootPayload::railgunStack,
            PacketCodecs.FLOAT, RailgunShootPayload::pitch,
            PacketCodecs.FLOAT, RailgunShootPayload::yaw,
            RailgunShootPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
