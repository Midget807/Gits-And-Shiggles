package net.midget807.gitsnshiggles.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.network.C2S.packet.*;
import net.midget807.gitsnshiggles.network.C2S.payload.*;
import net.midget807.gitsnshiggles.network.S2C.packet.MindStoneInvertPacket;
import net.midget807.gitsnshiggles.network.S2C.packet.RenderHorsePacket;
import net.midget807.gitsnshiggles.network.S2C.packet.SoulStonePacket;
import net.midget807.gitsnshiggles.network.S2C.packet.TimeStoneSyncPacket;
import net.midget807.gitsnshiggles.network.S2C.payload.MindStoneInvertPayload;
import net.midget807.gitsnshiggles.network.S2C.payload.RenderHorsePayload;
import net.midget807.gitsnshiggles.network.S2C.payload.SoulStonePayload;
import net.midget807.gitsnshiggles.network.S2C.payload.TimeStoneSyncPayload;
import net.minecraft.util.Identifier;

public class ModPackets {
    /**C2S Packets*/
    public static final Identifier RAILGUN_SHOOT = registerC2SId("railgun_shoot");
    public static final Identifier SUMMON_ELVES = registerC2SId("summon_elves");
    public static final Identifier POWER_STONE = registerC2SId("power_stone");
    public static final Identifier SPACE_STONE = registerC2SId("space_stone");
    public static final Identifier REALITY_STONE = registerC2SId("reality_stone");
    public static final Identifier TIME_STONE = registerC2SId("time_stone");
    public static final Identifier MIND_STONE = registerC2SId("mind_stone");
    public static final Identifier MIND_STONE_CD_SYNC = registerC2SId("mind_stone_cd_sync");
    public static final Identifier RAILGUN_RECOIL_SYNC = registerC2SId("railgun_recoil_sync");
    public static final Identifier SHUFFLE_INVENTORY = registerC2SId("shuffle_inventory");

    /**S2C Packets*/
    public static final Identifier SOUL_STONE = registerS2CId("soul_stone");
    public static final Identifier TIME_STONE_SYNC = registerS2CId("time_stone_sync");
    public static final Identifier MIND_STONE_INVERT = registerS2CId("mind_stone_invert");
    public static final Identifier RENDER_HORSE = registerS2CId("render_horse");

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
        PayloadTypeRegistry.playC2S().register(MindStonePayload.PAYLOAD_ID, MindStonePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MindStonePayload.PAYLOAD_ID, MindStonePacket::receive);
        PayloadTypeRegistry.playC2S().register(MindStoneCDSyncPayload.PAYLOAD_ID, MindStoneCDSyncPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MindStoneCDSyncPayload.PAYLOAD_ID, MindStoneCDSyncPacket::receive);

        PayloadTypeRegistry.playC2S().register(RailgunRecoilSyncPayload.PAYLOAD_ID, RailgunRecoilSyncPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RailgunRecoilSyncPayload.PAYLOAD_ID, RailgunRecoilSyncPacket::receive);

        PayloadTypeRegistry.playC2S().register(ShuffleInventoryPayload.PAYLOAD_ID, ShuffleInventoryPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ShuffleInventoryPayload.PAYLOAD_ID, ShuffleInventoryPacket::receive);

    }

    public static void registerGlobalS2C() {
        PayloadTypeRegistry.playS2C().register(SoulStonePayload.PAYLOAD_ID, SoulStonePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SoulStonePayload.PAYLOAD_ID, SoulStonePacket::receive);
        PayloadTypeRegistry.playS2C().register(MindStoneInvertPayload.PAYLOAD_ID, MindStoneInvertPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(MindStoneInvertPayload.PAYLOAD_ID, MindStoneInvertPacket::receive);
        PayloadTypeRegistry.playS2C().register(TimeStoneSyncPayload.PAYLOAD_ID, TimeStoneSyncPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(TimeStoneSyncPayload.PAYLOAD_ID, TimeStoneSyncPacket::receive);
        PayloadTypeRegistry.playS2C().register(RenderHorsePayload.PAYLOAD_ID, RenderHorsePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(RenderHorsePayload.PAYLOAD_ID, RenderHorsePacket::receive);
    }

    private static Identifier registerC2SId(String name) {
        return GitsAndShigglesMain.id(name + "_c2s_packet");
    }
    private static Identifier registerS2CId(String name) {
        return GitsAndShigglesMain.id(name + "_s2c_packet");
    }
}
