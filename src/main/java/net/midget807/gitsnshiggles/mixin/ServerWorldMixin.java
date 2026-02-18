package net.midget807.gitsnshiggles.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.SpecialSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Mutable
    @Shadow
    @Final
    private List<SpecialSpawner> spawners;

    @Shadow
    public abstract List<ServerPlayerEntity> getPlayers();

}
