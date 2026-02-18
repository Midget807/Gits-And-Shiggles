package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.util.inject.SchizophreniaSpawnerHolder;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.ChunkManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerChunkManager.class)
public abstract class ServerChunkManagerMixin extends ChunkManager {
    @Shadow
    @Final
    private ServerWorld world;

    @Shadow
    private boolean spawnMonsters;

    @Shadow
    private boolean spawnAnimals;

    @Inject(method = "tickChunks", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tickSpawners(ZZ)V"))
    private void gitsnshiggles$tickCustomSpawner(CallbackInfo ci) {
        ((SchizophreniaSpawnerHolder)world).getSchizoSpawner().spawn(world, this.spawnMonsters, this.spawnAnimals);
    }
}
