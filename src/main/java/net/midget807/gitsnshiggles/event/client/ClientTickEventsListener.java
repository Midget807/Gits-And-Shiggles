package net.midget807.gitsnshiggles.event.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.midget807.gitsnshiggles.item.DiceItem;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class ClientTickEventsListener {
    private static List<Double> xDeltas = new ArrayList<>();
    private static List<Double> yDeltas = new ArrayList<>();

    public static void execute() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            diceSpaz(client);
        });
    }

    private static void diceSpaz(MinecraftClient client) {
        Mouse mouse = client.mouse;
        double dx = mouse.cursorDeltaX;
        double dy = mouse.cursorDeltaY;
        xDeltas.add(dx);
        yDeltas.add(dy);
        if (xDeltas.size() > 10) xDeltas.removeFirst();
        if (yDeltas.size() > 10) xDeltas.removeFirst();

        int xChanges = getXShaking(xDeltas);
        int yChanges = getYShaking(yDeltas);

        boolean isShaking = xChanges >= 10 || yChanges >= 10 || (xChanges + yChanges) >= 20;
        if (client.player != null) {
            Hand hand = client.player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.DICE) ? Hand.MAIN_HAND : client.player.getStackInHand(Hand.OFF_HAND).isOf(ModItems.DICE) ? Hand.OFF_HAND : null;
            if (hand != null) {
                ItemStack stack = client.player.getStackInHand(hand);
                if (stack.getItem() instanceof DiceItem diceItem && !client.player.getItemCooldownManager().isCoolingDown(diceItem)) {
                    diceItem.setShaking(isShaking);
                }
            }
        }
    }

    private static int getYShaking(List<Double> yDeltas) {int changes = 0;
        for (int i = 0; i < yDeltas.size() - 1; i++) {
            if (Math.abs(yDeltas.get(i)) < 1) continue;
            if (yDeltas.get(i + 1) * yDeltas.get(i) < 0) {
                changes++;
            }
        }
        return changes;
    }

    private static int getXShaking(List<Double> xDeltas) {
        int changes = 0;
        for (int i = 0; i < xDeltas.size() - 1; i++) {
            if (Math.abs(xDeltas.get(i)) < 1) continue;
            if (xDeltas.get(i + 1) * xDeltas.get(i) < 0) {
                changes++;
            }
        }
        return changes;
    }
}
