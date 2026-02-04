package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.network.C2S.payload.PowerStonePayload;
import net.midget807.gitsnshiggles.network.C2S.payload.RealityStonePayload;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.RealityStoneTransform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class RealityStonePacket {

    public static void receive(RealityStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            ((RealityStoneTransform)player).setTimeTicksForTransform(payload.timeTicksForTransform());
            if (!player.getAbilities().creativeMode) InfinityStoneUtil.setStoneCooldown(player, InfinityStoneUtil.Stones.REALITY);
            player.getHandItems().forEach(itemStack -> {
                if (itemStack.getItem() instanceof InfinityGauntletItem gauntletItem) {
                    gauntletItem.realityStoneTimer = payload.timeTicksForTransform();
                }
            });
        });
    }
}
