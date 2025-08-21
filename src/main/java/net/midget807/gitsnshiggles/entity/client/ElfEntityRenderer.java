package net.midget807.gitsnshiggles.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ElfEntityRenderer extends MobEntityRenderer<ElfEntity, ElfEntityModel> {
    public static final Identifier TEXTURE = GitsAndShigglesMain.id("textures/entity/elf");
    public static final Identifier ANGRY_TEXTURE = GitsAndShigglesMain.id("textures/entity/elf_angry");
    public ElfEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ElfEntityModel(context.getPart(ModEntityModelLayers.ELF_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(ElfEntity entity) {
        return entity.hasAngerTime() ? ANGRY_TEXTURE : TEXTURE;
    }
}
