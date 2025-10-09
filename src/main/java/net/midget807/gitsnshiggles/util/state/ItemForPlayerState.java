package net.midget807.gitsnshiggles.util.state;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.util.state.data.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class ItemForPlayerState extends PersistentState {
    public static final String ITEM_NAMESPACE_KEY = "PlayerLinkedItemNameSpace";
    public static final String ITEM_PATH_KEY = "PlayerLinkedItemPath";
    private Item item;
    private String itemNamespace = "";
    private String itemPath = "";

    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if (itemNamespace != null && itemPath != null) {
            nbt.putString(ITEM_NAMESPACE_KEY, itemNamespace);
            nbt.putString(ITEM_PATH_KEY, itemPath);
        }
        return nbt;
    }

    public static ItemForPlayerState createFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup registryLookup) {
        ItemForPlayerState state = new ItemForPlayerState();
        state.item = Registries.ITEM.get(Identifier.of(nbtCompound.getString(ITEM_NAMESPACE_KEY), nbtCompound.getString(ITEM_PATH_KEY)));
        return state;
    }
    public static ItemForPlayerState createNew() {
        ItemForPlayerState state = new ItemForPlayerState();
        state.item = null;
        return state;
    }

    private static final Type<ItemForPlayerState> type = new Type<>(
            ItemForPlayerState::createNew,
            ItemForPlayerState::createFromNbt,
            null
    );

    public static ItemForPlayerState getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(ServerWorld.OVERWORLD);
        assert serverWorld != null;

        ItemForPlayerState state = serverWorld.getPersistentStateManager().getOrCreate(type, GitsAndShigglesMain.MOD_ID);
        state.markDirty();
        return state;
    }

    public static PlayerData getPlayerState(@NotNull LivingEntity player) {
        ItemForPlayerState serverState = getServerState(player.getWorld().getServer());
        PlayerData playerData = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
        return playerData;
    }
}