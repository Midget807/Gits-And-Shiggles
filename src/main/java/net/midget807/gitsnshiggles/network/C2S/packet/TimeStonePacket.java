package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.cca.TimeStopComponent;
import net.midget807.gitsnshiggles.network.C2S.payload.TimeStonePayload;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TimeStonePacket {
    public static void receive(TimeStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            List<Entity> squareEntities = world.getOtherEntities(player, player.getBoundingBox().expand(5));
            ArrayList<Entity> sphereEntities = new ArrayList<>(List.of());
            for (Entity entity : squareEntities) {
                if (player.getPos().squaredDistanceTo(entity.getPos()) <= 25) {
                    sphereEntities.add(entity);
                }
            }
            squareEntities.forEach(entity -> {
                TimeStopComponent timeStopComponent = TimeStopComponent.get(entity);
                timeStopComponent.setInt(TimeStopComponent.MAX_DURATION);
            });
            if (!player.getAbilities().creativeMode) InfinityStoneUtil.setStoneCooldown(player, InfinityStoneUtil.Stones.TIME);
        });
    }
}
