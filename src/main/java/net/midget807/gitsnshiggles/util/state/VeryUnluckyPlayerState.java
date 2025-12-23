package net.midget807.gitsnshiggles.util.state;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class VeryUnluckyPlayerState extends PersistentState {
    public static final String VERY_UNLUCKY_PLAYER_UUID_KEY = "VeryUnluckyPlayer";

    public String veryUnluckyPlayerUuidString = "";

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putString(VERY_UNLUCKY_PLAYER_UUID_KEY, this.veryUnluckyPlayerUuidString);
        return nbt;
    }

    public static VeryUnluckyPlayerState createFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup registryLookup) {
        VeryUnluckyPlayerState state = new VeryUnluckyPlayerState();
        state.veryUnluckyPlayerUuidString = nbtCompound.getString(VERY_UNLUCKY_PLAYER_UUID_KEY);
        return state;
    }
    public static VeryUnluckyPlayerState createNew() {
        VeryUnluckyPlayerState state = new VeryUnluckyPlayerState();
        state.veryUnluckyPlayerUuidString = "";
        return state;
    }

    private static final Type<VeryUnluckyPlayerState> type = new Type<>(
            VeryUnluckyPlayerState::createNew,
            VeryUnluckyPlayerState::createFromNbt,
            null
    );

    public static VeryUnluckyPlayerState getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(ServerWorld.OVERWORLD);
        assert serverWorld != null;

        VeryUnluckyPlayerState state = serverWorld.getPersistentStateManager().getOrCreate(type, "very_unlucky_player");
        state.markDirty();
        return state;
    }
}
