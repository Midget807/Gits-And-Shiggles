package net.midget807.gitsnshiggles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.gitsnshiggles.entity.client.RailgunBulletEntityRenderer;
import net.midget807.gitsnshiggles.registry.ModEntities;

public class GitsAndShigglesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.RAILGUN_BULLET, RailgunBulletEntityRenderer::new);
    }
}
