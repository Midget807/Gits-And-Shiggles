package net.midget807.gitsnshiggles.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.Vec3d;

public interface ModPacketCodecs {
    PacketCodec<ByteBuf, Vec3d> VEC3D = new PacketCodec<ByteBuf, Vec3d>() {
        @Override
        public Vec3d decode(ByteBuf buf) {
            return Buf.readVec3d(buf);
        }

        @Override
        public void encode(ByteBuf buf, Vec3d value) {
            Buf.writeVec3d(buf, value);
        }
    };

    public static class Buf {
        public static Vec3d readVec3d(ByteBuf buf) {
            return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }
        public static void writeVec3d(ByteBuf buf, Vec3d vector) {
            buf.writeDouble(vector.getX());
            buf.writeDouble(vector.getY());
            buf.writeDouble(vector.getZ());
        }


    }
}
