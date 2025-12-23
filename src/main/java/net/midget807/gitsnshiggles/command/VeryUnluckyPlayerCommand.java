package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.midget807.gitsnshiggles.util.state.VeryUnluckyPlayerState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class VeryUnluckyPlayerCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        return literal("veryUnluckyPlayer")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("set")
                        .then(argument("target", EntityArgumentType.player())
                                .executes(context -> executeSetVeryUnluckyPlayer(
                                        context.getSource(),
                                        EntityArgumentType.getPlayer(context, "target")
                                ))
                        )
                ).then(literal("clear")
                        .executes(context -> executeClearVeryUnluckyPlayer(
                                context.getSource()
                        ))
                );
    }

    private static int executeSetVeryUnluckyPlayer(ServerCommandSource source, ServerPlayerEntity target) {
        int i = 0;

        VeryUnluckyPlayerState state = VeryUnluckyPlayerState.getServerState(source.getServer());
        state.veryUnluckyPlayerUuidString = target.getUuidAsString();
        sendFeedback(source, target);
        state.markDirty();
        i++;
        return i;
    }

    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity target) {
        String text = "very unlucky";

        if (source.getEntity() == target) {
            source.sendFeedback(() -> Text.literal("You are now " + text), true);
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {

                target.sendMessage(Text.literal("Your luck level is now ").append(Text.literal(text).formatted(Formatting.BOLD)));
            }

            source.sendFeedback(() -> Text.literal("Set " + target.getDisplayName().getString() + " luck level to " + text), true);
        }
    }

    private static int executeClearVeryUnluckyPlayer(ServerCommandSource source) {
        VeryUnluckyPlayerState state = VeryUnluckyPlayerState.getServerState(source.getServer());
        state.veryUnluckyPlayerUuidString = "";
        state.markDirty();
        source.sendFeedback(() -> Text.literal("Player is no longer very unlucky"), true);
        return 1;
    }
}
