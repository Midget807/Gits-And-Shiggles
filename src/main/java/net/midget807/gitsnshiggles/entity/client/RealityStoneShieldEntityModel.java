package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class RealityStoneShieldEntityModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public static final Identifier TEXTURE = Identifier.of(GitsAndShigglesMain.MOD_ID, "textures/entity/reality_stone_shield.png");
    public static final Identifier TEXTURE_SLIM = GitsAndShigglesMain.id("textures/entity/reality_stone_shield_slim.png");

    public RealityStoneShieldEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getSlimTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(new Dilation(1.02f), 0.0f);
        ModelPartData modelPartData = modelData.getRoot();
        return TexturedModelData.of(modelData, 64, 32);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(new Dilation(1.04f), 0.0f);
        ModelPartData modelPartData = modelData.getRoot();
        return TexturedModelData.of(modelData, 64, 32);
    }
}
