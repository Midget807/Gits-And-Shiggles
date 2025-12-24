package net.midget807.gitsnshiggles.util.state;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class ElfCountState extends PersistentState {
    public static final String ELF_COUNT_KEY = "ElfCount";
    public int elfCount = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt(ELF_COUNT_KEY, this.elfCount);
        return nbt;
    }

    public static ElfCountState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        ElfCountState state = new ElfCountState();
        state.elfCount = nbt.getInt(ELF_COUNT_KEY);
        return state;
    }

    public static ElfCountState createNew() {
        ElfCountState state = new ElfCountState();
        state.elfCount = 0;
        return state;
    }

    private static final Type<ElfCountState> type = new Type<>(
            ElfCountState::createNew,
            ElfCountState::createFromNbt,
            null
    );

    public static ElfCountState getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(ServerWorld.OVERWORLD);
        assert serverWorld != null;

        ElfCountState state = serverWorld.getPersistentStateManager().getOrCreate(type, "elfCount");
        state.markDirty();
        return state;
    }
}
