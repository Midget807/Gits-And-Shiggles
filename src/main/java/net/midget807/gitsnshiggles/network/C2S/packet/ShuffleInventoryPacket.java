package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.ShuffleInventoryPayload;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Random;

public class ShuffleInventoryPacket {
    public static void receive(ShuffleInventoryPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            /** Stack indices:
             * 40 -> offHand
             * 39 - 36 -> armor
             * 0 - 9 -> hotbar
             * 10 - 35 -> inventory
             * */
            DefaultedList<ItemStack> inventory = DefaultedList.of();
            inventory.addAll(player.getInventory().main);
            inventory.addAll(player.getInventory().armor);
            inventory.addAll(player.getInventory().offHand);
            Collections.shuffle(inventory);
            player.getInventory().offHand.set(0, inventory.getLast());
            inventory.removeLast();
            for (int i = 0; i < 4; i++) {
                player.getInventory().armor.set(i, inventory.get(i + 36));
            }
            for (int i = 0; i < 36; i++) {
                player.getInventory().main.set(i, inventory.get(i));
            }
        });
    }

    private static DefaultedList<ItemStack> shuffle(DefaultedList<ItemStack> inventory, Random random) {
        for (int i = 0; i < inventory.size(); i++) {
            int randomIndex = random.nextInt(i + 1, inventory.size() - 1);
            ItemStack stack = inventory.get(i);
            ItemStack randomStack = inventory.get(randomIndex);
            inventory.set(i, randomStack);
            inventory.set(randomIndex, stack);
        }
        return inventory;
    }
}
