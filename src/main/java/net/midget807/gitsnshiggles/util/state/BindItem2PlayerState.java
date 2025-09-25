package net.midget807.gitsnshiggles.util.state;

import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.PersistentState;

import java.util.List;
import java.util.UUID;

public class BindItem2PlayerState extends PersistentState {
    public static final String LINKED_PLAYER_UUIDS_KEY = "LinkedPlayerUuids";
    public static final String LINKED_ITEMS_KEY = "LinkedItems";
    public List<UUID> linkedPlayerUuids;
    public List<Item> linkedItems;


    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtList linkedPlayerUuidsNbtList = new NbtList();
        NbtList linkedItemsNbtList = new NbtList();
        linkedPlayerUuids.forEach(uuid -> {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putUuid("UUID", uuid);
            linkedPlayerUuidsNbtList.add(0, nbtCompound);
        });
        linkedItems.forEach(item -> {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putString("Item", Registries.ITEM.getId(item).toString());
            linkedItemsNbtList.add(0, nbtCompound);
        });

        nbt.put(LINKED_PLAYER_UUIDS_KEY, linkedPlayerUuidsNbtList);
        nbt.put(LINKED_ITEMS_KEY, linkedItemsNbtList);
        return nbt;
    }

    /*public static BindItem2PlayerState createFrom(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        BindItem2PlayerState state = new BindItem2PlayerState();
        List<UUID> playerUuidsFromNbt = List.of();
        nbt.getList(LINKED_PLAYER_UUIDS_KEY, NbtElement.COMPOUND_TYPE).forEach(nbtElement -> {
            playerUuidsFromNbt.add(0, nbtElement)
        });
        state.linkedPlayerUuids = ;
    }*/
}
