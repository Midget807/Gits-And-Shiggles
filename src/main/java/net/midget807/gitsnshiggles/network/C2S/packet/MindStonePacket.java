package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.MindStonePayload;
import net.midget807.gitsnshiggles.network.C2S.payload.TimeStonePayload;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MindStonePacket {
    public static void receive(MindStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            List<Entity> squareEntities = world.getOtherEntities(player, player.getBoundingBox().expand(20, 10, 20));
            ArrayList<PlayerEntity> squarePlayerEntities = new ArrayList<>(List.of());
            for (Entity entity : squareEntities) {
                if (entity instanceof PlayerEntity) {
                    squarePlayerEntities.add(player);
                }
            }
            ArrayList<PlayerEntity> sphereEntities = new ArrayList<>(List.of());
            for (PlayerEntity entity : squarePlayerEntities) {
                if (player.getPos().squaredDistanceTo(entity.getPos()) <= 100) {
                    sphereEntities.add(entity);
                }
            }
            squareEntities.forEach(entity -> ((MindStoneInvert)entity).setTimeTicksInverted(payload.timeTicksInverted()));
            ((InfinityStoneCooldown) player).setMindStoneCD(InfinityStoneUtil.MIND_STONE_CD);
        });
    }
}
