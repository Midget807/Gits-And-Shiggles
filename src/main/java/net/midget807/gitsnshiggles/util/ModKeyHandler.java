package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.network.C2S.payload.*;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.midget807.gitsnshiggles.util.inject.RealityStoneTransform;
import net.midget807.gitsnshiggles.util.inject.TimeStoneFreeze;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

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
                    ClientPlayNetworking.send(new SummonElvesPayload(player.getBlockPos()));
                }
            } else {
                if (ModKeyBindings.summonElves.wasPressed()) {
                    ModKeyBindings.summonElves.setPressed(false);
                }
            }


            /** Checks for the player holding Infinity Gauntlet before handling keys */
            if (player.isHolding(ModItems.INFINITY_GAUNTLET)) {
                while (ModKeyBindings.powerStone.wasPressed() && !((InfinityStoneCooldown) player).isPowerStoneOnCD()) {
                    ClientPlayNetworking.send(new PowerStonePayload(player.getBlockPos()));
                    ((InfinityStoneCooldown) player).setPowerStoneCD(InfinityStoneUtil.POWER_STONE_CD);
                }
                while (ModKeyBindings.spaceStone.wasPressed() && !((InfinityStoneCooldown) player).isSpaceStoneOnCD()) {
                    InfinityStoneUtil.getSpaceStoneTeleport(player);
                    ((InfinityStoneCooldown) player).setSpaceStoneCD(InfinityStoneUtil.SPACE_STONE_CD);
                }
                while (ModKeyBindings.realityStone.wasPressed() && !((InfinityStoneCooldown) player).isRealityStoneOnCD()) {
                    ((RealityStoneTransform)client.player).setTimeTicksForTransform(InfinityStoneUtil.TIMER_REALITY_STONE);
                    ClientPlayNetworking.send(new RealityStonePayload(((RealityStoneTransform) client.player).getTimeTicksForTransform()));
                    ((InfinityStoneCooldown) player).setRealityStoneCD(InfinityStoneUtil.REALITY_STONE_CD);
                }
                /*Soul Stone Revive is Passive*/
                while (ModKeyBindings.timeStone.wasPressed() && !((InfinityStoneCooldown) player).isTimeStoneOnCD()) {
                    ClientPlayNetworking.send(new TimeStonePayload(true));
                    ((InfinityStoneCooldown) player).setTimeStoneCD(InfinityStoneUtil.TIMER_MIND_STONE);
                }
                while (ModKeyBindings.mindStone.wasPressed() && !((InfinityStoneCooldown) player).isMindStoneOnCD()) {
                    ClientPlayNetworking.send(new MindStonePayload(InfinityStoneUtil.TIMER_TIME_STONE));
                    ((InfinityStoneCooldown) player).setMindStoneCD(InfinityStoneUtil.MIND_STONE_CD);
                }
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
