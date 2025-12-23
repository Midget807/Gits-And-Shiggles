package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.midget807.gitsnshiggles.util.state.UnluckyPlayerState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class UnluckyPlayerCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        return literal("unluckyPlayer")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("set")
                        .then(argument("target", EntityArgumentType.player())
                                .executes(context -> executeSetUnluckyPlayer(
                                        context.getSource(),
                                        EntityArgumentType.getPlayer(context, "target")
                                ))
                        )
                ).then(literal("clear")
                        .executes(context -> executeClearUnluckyPlayer(
                                context.getSource()
                        ))
                );
    }

    private static int executeSetUnluckyPlayer(ServerCommandSource source, ServerPlayerEntity target) {
        int i = 0;

        UnluckyPlayerState state = UnluckyPlayerState.getServerState(source.getServer());
        state.unluckyPlayerUuidString = target.getUuidAsString();
        sendFeedback(source, target);
        state.markDirty();
        i++;
        return i;
    }

    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity target) {
        String text = "unlucky";
        if (source.getEntity() == target) {
            source.sendFeedback(() -> Text.literal("You are now " + text), true);
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {

                target.sendMessage(Text.literal("Your luck level is now ").append(Text.literal(text).formatted(Formatting.BOLD)));
            }

            source.sendFeedback(() -> Text.literal("Set " + target.getDisplayName().getString() + " luck level to " + text), true);
        }
    }

    private static int executeClearUnluckyPlayer(ServerCommandSource source) {
        UnluckyPlayerState state = UnluckyPlayerState.getServerState(source.getServer());
        state.unluckyPlayerUuidString = "";
        state.markDirty();
        source.sendFeedback(() -> Text.literal("Player is no longer unlucky"), true);
        return 1;
    }
}
