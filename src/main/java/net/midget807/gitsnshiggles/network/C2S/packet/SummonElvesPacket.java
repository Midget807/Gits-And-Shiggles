package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.cca.ElfCountComponent;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.network.C2S.payload.SummonElvesPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SummonElvesPacket {
    public static void receive(SummonElvesPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = context.player().getWorld();
            ElfCountComponent elfCountComponent = ElfCountComponent.get(player);

            if (elfCountComponent.getInt() < ElfEntity.MAX_ELF_COUNT) {
                ElfEntity elfEntity = new ElfEntity(world, player);
                elfEntity.setPosition(player.getPos());
                elfEntity.setOwner(player);
                elfEntity.setTamed(true, true);
                world.spawnEntity(elfEntity);
                elfCountComponent.incrementInt();
            } else {
                player.sendMessage(Text.translatable("key.gitsnshiggles.summonElves.too_many"), true);
            }

        });
    }
}
