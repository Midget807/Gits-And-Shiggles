package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.network.C2S.packet.*;
import net.midget807.gitsnshiggles.network.C2S.payload.*;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier RAILGUN_SHOOT = registerC2SId("railgun_shoot");
    public static final Identifier SUMMON_ELVES = registerC2SId("summon_elves");
    public static final Identifier POWER_STONE = registerC2SId("power_stone");
    public static final Identifier SPACE_STONE = registerC2SId("space_stone");
    public static final Identifier REALITY_STONE = registerC2SId("reality_stone");
    public static final Identifier TIME_STONE = registerC2SId("time_stone");
    public static final Identifier MIND_STONE = registerC2SId("mind_stone");

    public static void registerGlobalC2S() {
        PayloadTypeRegistry.playC2S().register(RailgunShootPayload.PAYLOAD_ID, RailgunShootPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RailgunShootPayload.PAYLOAD_ID, RailgunShootPacket::receive);
        PayloadTypeRegistry.playC2S().register(SummonElvesPayload.PAYLOAD_ID, SummonElvesPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SummonElvesPayload.PAYLOAD_ID, SummonElvesPacket::receive);

        PayloadTypeRegistry.playC2S().register(PowerStonePayload.PAYLOAD_ID, PowerStonePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PowerStonePayload.PAYLOAD_ID, PowerStonePacket::receive);
        PayloadTypeRegistry.playC2S().register(SpaceStonePayload.PAYLOAD_ID, SpaceStonePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SpaceStonePayload.PAYLOAD_ID, SpaceStonePacket::receive);
        PayloadTypeRegistry.playC2S().register(RealityStonePayload.PAYLOAD_ID, RealityStonePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RealityStonePayload.PAYLOAD_ID, RealityStonePacket::receive);
        PayloadTypeRegistry.playC2S().register(TimeStonePayload.PAYLOAD_ID, TimeStonePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TimeStonePayload.PAYLOAD_ID, TimeStonePacket::receive);
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
