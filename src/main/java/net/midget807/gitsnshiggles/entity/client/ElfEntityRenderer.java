package net.midget807.gitsnshiggles.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.registry.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ElfEntityRenderer extends MobEntityRenderer<ElfEntity, ElfEntityModel> {
    public ElfEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ElfEntityModel(context.getPart(ModEntityModelLayers.ELF_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(ElfEntity entity) {
        return entity.getTextureId();
    }
}
