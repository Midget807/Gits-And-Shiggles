package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.entity.RailgunBulletEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class RailgunBulletEntityRenderer extends EntityRenderer<RailgunBulletEntity> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/shulker/spark.png");

    public RailgunBulletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(RailgunBulletEntity entity) {
        return TEXTURE;
    }
}
