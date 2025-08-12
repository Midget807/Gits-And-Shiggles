package net.midget807.gitsnshiggles.registry.client;

import com.google.common.collect.Sets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class ModEntityModelLayers {
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();

    public static final EntityModelLayer ELF_MODEL_LAYER = registerMain("elf");

    public static EntityModelLayer registerMain(String name) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(GitsAndShigglesMain.id(name), "main");
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        } else {
            return entityModelLayer;
        }
    }
}
