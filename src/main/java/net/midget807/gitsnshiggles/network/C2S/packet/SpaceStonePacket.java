package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.SpaceStonePayload;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class SpaceStonePacket {
    public static final int MAX_DISTANCE = 128;
    public static void receive(SpaceStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            ArmorStandEntity armorStandEntity = new ArmorStandEntity(EntityType.ARMOR_STAND, world);
            armorStandEntity.setPosition(
                    payload.blockPos().getX(),
                    payload.blockPos().getY(),
                    payload.blockPos().getZ()
            );
            world.spawnEntity(armorStandEntity);
            /*player.teleport(
                    payload.blockPos().getX(),
                    payload.blockPos().getY(),
                    payload.blockPos().getZ(),
                    false
            );*/
        });
    }
}
