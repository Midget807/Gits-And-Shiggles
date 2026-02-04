package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.network.C2S.payload.*;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.ModParticles;
import net.midget807.gitsnshiggles.util.inject.ElfCount;
import net.midget807.gitsnshiggles.util.inject.RealityStoneTransform;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;

@Environment(EnvType.CLIENT)
public class ModKeyHandler {
    public static void runKeyBinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null) {
                return;
            }

            /** Checks for Santa Hat on player before handling keys*/
            if (player.getInventory().getArmorStack(3 /*Helmet slot*/).isIn(ModItemTagProvider.SANTA_HATS)) {
                while (ModKeyBindings.summonElves.wasPressed()) {
                    ClientPlayNetworking.send(new SummonElvesPayload(player.getBlockPos(), ((ElfCount) player).getElfCount()));
                }
            } else {
                if (ModKeyBindings.summonElves.wasPressed()) {
                    ModKeyBindings.summonElves.setPressed(false);
                }
            }


            /** Checks for the player holding Infinity Gauntlet before handling keys */
            if (player.isHolding(ModItems.INFINITY_GAUNTLET)) {
                client.player.getHandItems().forEach(itemStack -> {
                    if (itemStack.getItem() instanceof InfinityGauntletItem gauntletItem) {
                        while (ModKeyBindings.powerStone.wasPressed() && gauntletItem.powerStoneCD == 0) {
                            ClientPlayNetworking.send(new PowerStonePayload(player.getBlockPos()));
                            if (!player.getAbilities().creativeMode) ClientPlayNetworking.send(new ShuffleInventoryPayload(true));
                            ModParticleUtil.addExpandingRingOfParticles(player.getWorld(), player.getPos(), 1.0, 3, 400, ModUtil.Speed.getParticleSpeedForRadius(50, 8.0), ModParticles.POWER);
                        }
                        while (ModKeyBindings.spaceStone.wasPressed() && gauntletItem.spaceStoneCD == 0) {
                            InfinityStoneUtil.getSpaceStoneTeleport(player);
                            if (!player.getAbilities().creativeMode) ClientPlayNetworking.send(new ShuffleInventoryPayload(true));
                            ModParticleUtil.addCollapsingSphereOfParticles(player.getWorld(), player.getEyePos(), 50, ModUtil.Speed.getParticleSpeedForRadius(40, 1.5), ModParticles.SPACE_OUTLINE, 1.5);
                            ModParticleUtil.addCollapsingSphereOfParticles(player.getWorld(), player.getEyePos(), 50, ModUtil.Speed.getParticleSpeedForRadius(30, 1.2), ModParticles.SPACE, 1.2);
                        }
                        while (ModKeyBindings.realityStone.wasPressed() && gauntletItem.realityStoneCD == 0) {
                            ((RealityStoneTransform) client.player).setTimeTicksForTransform(InfinityStoneUtil.TIMER_REALITY_STONE);
                            ClientPlayNetworking.send(new RealityStonePayload(((RealityStoneTransform) client.player).getTimeTicksForTransform()));
                            if (!player.getAbilities().creativeMode) ClientPlayNetworking.send(new ShuffleInventoryPayload(true));
                            ModParticleUtil.addCollapsingSphereOfParticles(player.getWorld(), player.getPos(), 50, ModUtil.Speed.getParticleSpeedForRadius(20, 2.0), ModParticles.REALITY, 2.0);
                        }
                        /*Soul Stone Revive is Passive*/
                        while (ModKeyBindings.timeStone.wasPressed() && gauntletItem.timeStoneCD == 0) {
                            ClientPlayNetworking.send(new TimeStonePayload(true));
                            if (!player.getAbilities().creativeMode) ClientPlayNetworking.send(new ShuffleInventoryPayload(true));
                            ModParticleUtil.addExpandingSphereOfParticles(player.getWorld(), player.getPos(), 400, ModUtil.Speed.getParticleSpeedForRadius(60, 5.0), ModParticles.TIME);
                        }
                        while (ModKeyBindings.mindStone.wasPressed() && gauntletItem.mindStoneCD == 0) {
                            ClientPlayNetworking.send(new MindStonePayload(InfinityStoneUtil.TIMER_MIND_STONE));
                            if (!player.getAbilities().creativeMode) ClientPlayNetworking.send(new ShuffleInventoryPayload(true));
                            ModParticleUtil.addExpandingSphereOfParticles(player.getWorld(), player.getPos(), 800, ModUtil.Speed.getParticleSpeedForRadius(100, 10.0), ModParticles.MIND);
                        }
                    }
                });

            } else {
                if (ModKeyBindings.powerStone.wasPressed()) {
                    ModKeyBindings.powerStone.setPressed(false);
                }
                if (ModKeyBindings.spaceStone.wasPressed()) {
                    ModKeyBindings.spaceStone.setPressed(false);
                }
                if (ModKeyBindings.realityStone.wasPressed()) {
                    ModKeyBindings.realityStone.setPressed(false);
                }
                /*Soul Stone is passive*/
                if (ModKeyBindings.timeStone.wasPressed()) {
                    ModKeyBindings.timeStone.setPressed(false);
                }
                if (ModKeyBindings.mindStone.wasPressed()) {
                    ModKeyBindings.mindStone.setPressed(false);
                }
            }
        });
    }




}
