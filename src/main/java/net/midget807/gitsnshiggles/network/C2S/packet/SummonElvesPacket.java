package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.network.C2S.payload.SummonElvesPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class SummonElvesPacket {
    public static void receive(SummonElvesPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = context.player().getWorld();

            ElfEntity elfEntity = new ElfEntity(world);
            elfEntity.setPosition(player.getPos());
            world.spawnEntity(elfEntity);
        });
    }
}
