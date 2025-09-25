package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.network.C2S.payload.SummonElvesPayload;
import net.midget807.gitsnshiggles.registry.ModItems;
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
            }
            /** Checks for the player holding Infinity Gauntlet before handling keys */
            if (player.isHolding(ModItems.INFINITY_GAUNTLET)) {
                while (ModKeyBindings.powerStone.wasPressed()) {
                    client.player.sendMessage(Text.literal("power stone pressed"), false);
                }
                while (ModKeyBindings.spaceStone.wasPressed()) {
                    client.player.sendMessage(Text.literal("space stone pressed"), false);
                }
                while (ModKeyBindings.realityStone.wasPressed()) {
                    client.player.sendMessage(Text.literal("reality stone pressed"), false);
                }
                while (ModKeyBindings.soulStone.wasPressed()) {
                    client.player.sendMessage(Text.literal("soul stone pressed"), false);
                }
                while (ModKeyBindings.timeStone.wasPressed()) {
                    client.player.sendMessage(Text.literal("time stone pressed"), false);
                }
                while (ModKeyBindings.mindStone.wasPressed()) {
                    client.player.sendMessage(Text.literal("mind stone pressed"), false);
                }
            }
        });
    }
}
