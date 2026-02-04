package net.midget807.gitsnshiggles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.gitsnshiggles.entity.client.ElfEntityModel;
import net.midget807.gitsnshiggles.entity.client.ElfEntityRenderer;
import net.midget807.gitsnshiggles.entity.client.RealityStoneShieldEntityModel;
import net.midget807.gitsnshiggles.entity.client.TronDiscEntityModel;
import net.midget807.gitsnshiggles.entity.client.TronDiscEntityRenderer;
import net.midget807.gitsnshiggles.event.client.ClientPreAttackListener;
import net.midget807.gitsnshiggles.event.client.ClientTickEventsListener;
import net.midget807.gitsnshiggles.event.client.HudRenderListener;
import net.midget807.gitsnshiggles.event.client.WorldRendererEventListener;
import net.midget807.gitsnshiggles.item.client.SantaHatModel;
import net.midget807.gitsnshiggles.item.client.SantaHatRenderer;
import net.midget807.gitsnshiggles.item.client.WizardArmorModel;
import net.midget807.gitsnshiggles.item.client.WizardArmorRenderer;
import net.midget807.gitsnshiggles.particle.MindParticle;
import net.midget807.gitsnshiggles.particle.PowerParticle;
import net.midget807.gitsnshiggles.particle.RealityParticle;
import net.midget807.gitsnshiggles.particle.SoulParticle;
import net.midget807.gitsnshiggles.particle.SpaceOutlineParticle;
import net.midget807.gitsnshiggles.particle.SpaceParticle;
import net.midget807.gitsnshiggles.particle.TimeParticle;
import net.midget807.gitsnshiggles.particle.TimeStoneRingsParticle;
import net.midget807.gitsnshiggles.registry.ModEntities;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.ModPackets;
import net.midget807.gitsnshiggles.registry.ModParticles;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.midget807.gitsnshiggles.registry.client.ModModelPredicateProviderRegistry;
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
        WorldRendererEventListener.execute();

        ModKeyBindings.registerModKeyBindings();
        ModKeyHandler.runKeyBinds();
        ModPackets.registerGlobalS2C();

        ModModelPredicateProviderRegistry.registerModelPredicates();

        ParticleFactoryRegistry.getInstance().register(ModParticles.TIME_STONE_RINGS, TimeStoneRingsParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.POWER, PowerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SPACE, SpaceParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SPACE_OUTLINE, SpaceOutlineParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.REALITY, RealityParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOUL, SoulParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TIME, TimeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MIND, MindParticle.Factory::new);

        EntityRendererRegistry.register(ModEntities.RAILGUN_BULLET, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.ELF_MODEL_LAYER, ElfEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ELF, ElfEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLAMETHROWER_FIRE, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.RANDOM_EGG, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TRON_DISC_MODEL_LAYER, TronDiscEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TRON_DISC, TronDiscEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.REALITY_STONE_SHIELD, RealityStoneShieldEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.REALITY_STONE_SHIELD_SLIM, RealityStoneShieldEntityModel::getSlimTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(WizardArmorModel.MODEL_LAYER, WizardArmorModel::getTexturedModelData);
        ArmorRenderer.register(new WizardArmorRenderer(), ModItems.WIZARD_HAT, ModItems.WIZARD_ROBE, ModItems.WIZARD_PANTS, ModItems.WIZARD_BOOTS);
        EntityModelLayerRegistry.registerModelLayer(SantaHatModel.MODEL_LAYER, SantaHatModel::getTexturedModelData);
        ArmorRenderer.register(new SantaHatRenderer(), ModItems.SANTA_HAT, ModItems.LEATHER_SANTA_HAT, ModItems.CHAINMAIL_SANTA_HAT, ModItems.GOLD_SANTA_HAT, ModItems.IRON_SANTA_HAT, ModItems.DIAMOND_SANTA_HAT, ModItems.NETHERITE_SANTA_HAT);
    }


}
