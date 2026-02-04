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
    public static final EntityModelLayer TRON_DISC_MODEL_LAYER = registerMain("tron_disc");

    public static final EntityModelLayer REALITY_STONE_SHIELD = createOuterArmor("reality_stone_shield");
    public static final EntityModelLayer REALITY_STONE_SHIELD_SLIM = createOuterArmor("reality_stone_shield_slim");

    public static EntityModelLayer register(String name, String layer) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(GitsAndShigglesMain.id(name), layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        } else {
            return entityModelLayer;
        }
    }
    public static EntityModelLayer registerMain(String name) {
        return register(name, "main");
    }

    public static EntityModelLayer createInnerArmor(String id) {
        return register(id, "inner_armor");
    }

    private static EntityModelLayer createOuterArmor(String id) {
        return register(id, "outer_armor");
    }
}
