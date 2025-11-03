package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.PowerStonePayload;
import net.midget807.gitsnshiggles.network.C2S.payload.RealityStonePayload;
import net.midget807.gitsnshiggles.util.inject.RealityStoneTransform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class RealityStonePacket {
    public static final int MAX_RADIUS = 8;

    public static void receive(RealityStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            ((RealityStoneTransform)player).setTransformProjectiles(payload.shouldTransform());
        });
    }
}
