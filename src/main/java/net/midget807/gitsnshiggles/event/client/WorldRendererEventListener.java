package net.midget807.gitsnshiggles.event.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaEntity;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;

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

        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();

        for (SchizophreniaEntity entity : SchizophreniaManager.get()) {
            LivingEntity livingEntity = entity.type.create(world);
            if (livingEntity == null) continue;

            livingEntity.setPos(entity.pos.x, entity.pos.y, entity.pos.z);
            livingEntity.setYaw((float) world.random.nextBetween(-180, 180));
            livingEntity.setPitch(entity.pitch);
            livingEntity.prevYaw = (float) world.random.nextBetween(-180, 180);
            livingEntity.prevPitch = entity.pitch;


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
}
