package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.network.C2S.packet.SummonElvesPacket;
import net.midget807.gitsnshiggles.network.C2S.payload.SummonElvesPayload;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier SUMMON_ELVES = registerC2SId("summon_elves");

    public static void registerGlobalC2S() {
        PayloadTypeRegistry.playC2S().register(SummonElvesPayload.PAYLOAD_ID, SummonElvesPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SummonElvesPayload.PAYLOAD_ID, SummonElvesPacket::receive);
    }

    public static void registerGlobalS2C() {

    }

    private static Identifier registerC2SId(String name) {
        return GitsAndShigglesMain.id(name + "_c2s_packet");
    }
    private static Identifier registerS2CId(String name) {
        return GitsAndShigglesMain.id(name + "_s2c_packet");
    }
}
