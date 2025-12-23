package net.midget807.gitsnshiggles.util.state;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class UnluckyPlayerState extends PersistentState {
    public static final String UNLUCKY_PLAYER_UUID_KEY = "UnluckyPlayer";

    public String unluckyPlayerUuidString = "";

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putString(UNLUCKY_PLAYER_UUID_KEY, this.unluckyPlayerUuidString);
        return nbt;
    }

    public static UnluckyPlayerState createFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup registryLookup) {
        UnluckyPlayerState state = new UnluckyPlayerState();
        state.unluckyPlayerUuidString = nbtCompound.getString(UNLUCKY_PLAYER_UUID_KEY);
        return state;
    }
    public static UnluckyPlayerState createNew() {
        UnluckyPlayerState state = new UnluckyPlayerState();
        state.unluckyPlayerUuidString = "";
        return state;
    }

    private static final Type<UnluckyPlayerState> type = new Type<>(
            UnluckyPlayerState::createNew,
            UnluckyPlayerState::createFromNbt,
            null
    );

    public static UnluckyPlayerState getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(ServerWorld.OVERWORLD);
        assert serverWorld != null;

        UnluckyPlayerState state = serverWorld.getPersistentStateManager().getOrCreate(type, "unlucky_player");
        state.markDirty();
        return state;
    }
}
