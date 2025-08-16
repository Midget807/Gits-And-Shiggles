package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.RailgunShootPayload;
import net.midget807.gitsnshiggles.network.C2S.payload.SummonElvesPayload;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

@Environment(EnvType.CLIENT)
public class ModKeyHandler {
    public static void runKeyBinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            assert client.player != null;
            while (ModKeyBindings.summonElves.wasPressed()) {
                ClientPlayNetworking.send(new SummonElvesPayload(player.getBlockPos()));
            }
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
        });
    }
}
