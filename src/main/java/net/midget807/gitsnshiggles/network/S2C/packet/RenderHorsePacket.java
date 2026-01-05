package net.midget807.gitsnshiggles.network.S2C.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.midget807.gitsnshiggles.mixin.accessor.EntityIdAccessor;
import net.midget807.gitsnshiggles.network.S2C.payload.MindStoneInvertPayload;
import net.midget807.gitsnshiggles.network.S2C.payload.RenderHorsePayload;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RenderHorsePacket {
    public static void receive(RenderHorsePayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            World world = context.client().world;
            HorseEntity horseEntity = new HorseEntity(EntityType.HORSE, world);
            horseEntity.setId(EntityIdAccessor.getCurrentId().incrementAndGet());
            horseEntity.updatePosition(payload.blockPos().getX(), payload.blockPos().getY(), payload.blockPos().getZ());

            HorseEntity horse = EntityType.HORSE.create(world);
            horse.setId(EntityIdAccessor.getCurrentId().incrementAndGet());
            horse.updatePosition(payload.blockPos().getX(), payload.blockPos().getY(), payload.blockPos().getZ());

            world.spawnEntity(horse);
            world.spawnEntity(horseEntity);

            Vec3d spawnPos = new Vec3d(payload.pos().x, payload.pos().y, payload.pos().z);

            WorldRenderEvents.AFTER_ENTITIES.register(worldContext -> {

            EntityRenderDispatcher dispatcher = context.client().getEntityRenderDispatcher();

            dispatcher.render(
                    horseEntity,
                    spawnPos.x,
                    spawnPos.y,
                    spawnPos.z,
                    player.getYaw(),
                    worldContext.tickCounter().getTickDelta(false),
                    worldContext.matrixStack(),
                    worldContext.consumers(),
                    dispatcher.getLight(horseEntity, worldContext.tickCounter().getTickDelta(false))
            );
            });
        });
    }
}
