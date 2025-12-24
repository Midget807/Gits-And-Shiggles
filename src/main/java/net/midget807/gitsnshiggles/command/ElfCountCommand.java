package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.midget807.gitsnshiggles.util.state.ElfCountState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ElfCountCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        return literal("elfCount")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("add")
                        .then(argument("number", IntegerArgumentType.integer(-30, 30))
                                .executes(context -> executeAdd(
                                        context.getSource(),
                                        IntegerArgumentType.getInteger(context, "number")
                                ))
                        )
                )
                .then(literal("query")
                        .executes(context -> executeQuery(
                                context.getSource()
                        ))
                ).then(literal("set")

                        .then(argument("number", IntegerArgumentType.integer(0, 30))
                                .executes(context -> executeSet(
                                        context.getSource(),
                                        IntegerArgumentType.getInteger(context, "number")
                                ))
                        )
                );
    }

    private static int executeQuery(ServerCommandSource source) {
        ElfCountState state = ElfCountState.getServerState(source.getServer());
        source.sendFeedback(() -> Text.literal("Elf count is " + state.elfCount), true);
        return 1;
    }

    private static int executeAdd(ServerCommandSource source, int number) {
        ElfCountState state = ElfCountState.getServerState(source.getServer());
        state.elfCount += number;
        state.markDirty();
        source.sendFeedback(() -> Text.literal("Elf count is now " + state.elfCount), true);
        return 1;
    }

    private static int executeSet(ServerCommandSource source, int number) {
        ElfCountState state = ElfCountState.getServerState(source.getServer());
        state.elfCount = number;
        state.markDirty();
        source.sendFeedback(() -> Text.literal("Elf count is now " + state.elfCount), true);
        return 1;
    }
}
