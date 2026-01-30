package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.block.entity.ChemistryWorkbenchBlockEntity;
import net.midget807.gitsnshiggles.network.C2S.payload.OpenEvaporateScreenPayload;
import net.midget807.gitsnshiggles.screen.ChemistryWorkbenchScreenHandler;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.OptionalInt;

public class OpenEvaporateScreenPacket {
    public static void receive(OpenEvaporateScreenPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();
            ChemistryWorkbenchBlockEntity blockEntity = (ChemistryWorkbenchBlockEntity) world.getBlockEntity(payload.pos());
            NamedScreenHandlerFactory namedScreenHandlerFactory = blockEntity;

            if (namedScreenHandlerFactory != null) {
                //openHandledScreen(namedScreenHandlerFactory, player, blockEntity, payload.pos());
            }

            if (player.currentScreenHandler instanceof ChemistryWorkbenchScreenHandler screenHandler) {
                screenHandler.currentScreen = 3;
                screenHandler.buildEvaporateSlots();
                screenHandler.sendContentUpdates();
            }
        });
    }

    /*public static OptionalInt openHandledScreen(NamedScreenHandlerFactory namedScreenHandlerFactory, ServerPlayerEntity player, ChemistryWorkbenchBlockEntity blockEntity, BlockPos pos) {
        if (namedScreenHandlerFactory == null) {
            return OptionalInt.empty();
        } else {
            if (player.currentScreenHandler != player.playerScreenHandler) {
                player.closeHandledScreen();
            }
            player.incrementScreenHandlerSyncId();
            ScreenHandler screenHandler = new ChemistryWorkbenchScreenHandler(player.screenHandlerSyncId, player.getInventory(), player.getServerWorld().getBlockEntity(pos), blockEntity.timePropertyDelegate, blockEntity.modePropertyDelegate);
            if (screenHandler == null) {
                if (player.isSpectator()) {
                    player.sendMessage(Text.translatable("container.spectatorCantOpen").formatted(Formatting.RED), true);
                }
                return OptionalInt.empty();
            } else {
                player.networkHandler.sendPacket(new OpenScreenS2CPacket(screenHandler.syncId, screenHandler.getType(), namedScreenHandlerFactory.getDisplayName()));
                player.onScreenHandlerOpened(screenHandler);
                player.currentScreenHandler = screenHandler;
                return OptionalInt.of(player.screenHandlerSyncId);
            }
        }
    }*/
}
