package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.network.C2S.payload.*;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.inject.ElfCount;
import net.midget807.gitsnshiggles.util.inject.RealityStoneTransform;
import net.minecraft.client.network.ClientPlayerEntity;

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
                    ((ElfCount)player).setElfCount(((ElfCount) player).getElfCount() + 1);
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
                        }
                        while (ModKeyBindings.spaceStone.wasPressed() && gauntletItem.spaceStoneCD == 0) {
                            InfinityStoneUtil.getSpaceStoneTeleport(player);
                        }
                        while (ModKeyBindings.realityStone.wasPressed() && gauntletItem.realityStoneCD == 0) {
                            ((RealityStoneTransform) client.player).setTimeTicksForTransform(InfinityStoneUtil.TIMER_REALITY_STONE);
                            ClientPlayNetworking.send(new RealityStonePayload(((RealityStoneTransform) client.player).getTimeTicksForTransform()));
                        }
                        /*Soul Stone Revive is Passive*/
                        while (ModKeyBindings.timeStone.wasPressed() && gauntletItem.timeStoneCD == 0) {
                            ClientPlayNetworking.send(new TimeStonePayload(true));
                        }
                        while (ModKeyBindings.mindStone.wasPressed() && gauntletItem.mindStoneCD == 0) {
                            ClientPlayNetworking.send(new MindStonePayload(InfinityStoneUtil.TIMER_MIND_STONE));
                            //ClientPlayNetworking.send(new MindStoneCDSyncPayload(InfinityStoneUtil.MIND_STONE_CD));
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
