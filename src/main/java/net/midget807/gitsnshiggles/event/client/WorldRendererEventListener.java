package net.midget807.gitsnshiggles.event.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaEntity;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaManager;
import net.midget807.gitsnshiggles.util.inject.Schizophrenia;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class WorldRendererEventListener {
    public static void execute() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            renderSchizophreniaEntities(context);
        });
    }

    private static void renderSchizophreniaEntities(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) return;
        if (client.player == null) return;

        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        SchizophreniaManager schizophreniaManager = ((Schizophrenia)client.player).getSchizophreniaManager();
        for (SchizophreniaEntity entity : schizophreniaManager.get()) {
            LivingEntity livingEntity = entity.type.create(world);
            if (livingEntity == null) continue;

            float randomYaw = (float) world.random.nextBetween(-180, 180);
            livingEntity.setPos(entity.pos.x, entity.pos.y, entity.pos.z);
            livingEntity.setYaw(entity.yaw);
            livingEntity.setPitch(entity.pitch);
            livingEntity.prevYaw = entity.yaw;
            livingEntity.prevPitch = entity.pitch;
            livingEntity.bodyYaw = entity.yaw;
            livingEntity.prevBodyYaw = entity.yaw;
            livingEntity.headYaw = entity.yaw;
            livingEntity.prevHeadYaw = entity.yaw;

            entity.shouldRender = isInCornerVision(client.player, entity);

            if (entity.shouldRender) {
                dispatcher.render(
                        livingEntity,
                        livingEntity.getPos().x -  context.camera().getPos().x,
                        livingEntity.getPos().y -  context.camera().getPos().y,
                        livingEntity.getPos().z -  context.camera().getPos().z,
                        livingEntity.getYaw(),
                        0.0f,
                        context.matrixStack(),
                        context.consumers(),
                        0xF000F0
                );
            }
        }
        schizophreniaManager.tick();
    }

    private static boolean isInCornerVision(ClientPlayerEntity player, SchizophreniaEntity entity) {
        Vec3d rotVec = player.getRotationVector().normalize();

        Vec3d camPos = player.getCameraPosVec(0.0f);
        Vec3d toEntity = entity.pos.subtract(camPos).normalize();

        double dotProduct = rotVec.dotProduct(toEntity);
        double angleDeg = Math.toDegrees(Math.acos(dotProduct));
        return angleDeg > 50 && angleDeg < 80;
    }
}
