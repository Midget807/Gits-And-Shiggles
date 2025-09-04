package net.midget807.gitsnshiggles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.gitsnshiggles.entity.client.ElfEntityModel;
import net.midget807.gitsnshiggles.entity.client.ElfEntityRenderer;
import net.midget807.gitsnshiggles.entity.client.TronDiscEntityModel;
import net.midget807.gitsnshiggles.entity.client.TronDiscEntityRenderer;
import net.midget807.gitsnshiggles.event.client.ClientPreAttackListener;
import net.midget807.gitsnshiggles.event.client.ClientTickEventsListener;
import net.midget807.gitsnshiggles.event.client.HudRenderListener;
import net.midget807.gitsnshiggles.item.client.WizardArmorModel;
import net.midget807.gitsnshiggles.item.client.WizardArmorRenderer;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.midget807.gitsnshiggles.registry.ModPackets;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.util.ModKeyBindings;
import net.midget807.gitsnshiggles.util.ModKeyHandler;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class GitsAndShigglesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPreAttackListener.execute();
        ClientTickEventsListener.execute();
        HudRenderListener.execute();

        ModKeyBindings.registerModKeyBindings();
        ModKeyHandler.runKeyBinds();
        ModPackets.registerGlobalS2C();

        EntityRendererRegistry.register(ModEntities.RAILGUN_BULLET, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.ELF_MODEL_LAYER, ElfEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ELF, ElfEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLAMETHROWER_FIRE, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.RANDOM_EGG, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TRON_DISC_MODEL_LAYER, TronDiscEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TRON_DISC, TronDiscEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(WizardArmorModel.MODEL_LAYER, WizardArmorModel::getTexturedModelData);
        ArmorRenderer.register(new WizardArmorRenderer(), ModItems.WIZARD_HAT, ModItems.WIZARD_ROBE, ModItems.WIZARD_PANTS, ModItems.WIZARD_BOOTS);
    }


}
