package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PlayerLockCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        return literal("assignPlayerToItem")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("set")
                        .then(argument("target", EntityArgumentType.player())
                                .then(argument("item", ModItemArgumentType.itemStack())
                                        .executes(context -> execute(
                                                context.getSource(),
                                                EntityArgumentType.getPlayer(context, "target"),
                                                ModItemArgumentType.getItem(context, "item")
                                        ))
                                )
                        )
                ).then(literal("clear")
                        .then(argument("target", EntityArgumentType.player())
                                .executes(context -> playerClear(
                                        context.getSource(),
                                        EntityArgumentType.getPlayer(context, "target")
                                ))
                        ).then(argument("item", new ModItemArgumentType())
                                .executes(context -> itemClear(
                                        context.getSource(),
                                        ModItemArgumentType.getItem(context, "item")
                                ))
                        )
                ).then(literal("query")
                        .then(argument("target", EntityArgumentType.player())
                                .executes(context -> playerQuery(
                                        context.getSource(),
                                        EntityArgumentType.getPlayer(context, "target")
                                ))
                        ).then(argument("item", new ModItemArgumentType())
                                .executes(context -> itemQuery(
                                        context.getSource(),
                                        ModItemArgumentType.getItem(context, "item")
                                ))
                        )
                );
    }

    private static int itemQuery(ServerCommandSource source, Item item) throws CommandSyntaxException {
        ItemStack stack = new ItemStack(item, 1);
        int player = 1;
        source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.item.success", stack.toHoverableText(), player), true);
        return 1;
    }

    private static int playerQuery(ServerCommandSource source, ServerPlayerEntity target) {
        int item = 1738;
        source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.player.success", item, target.getDisplayName()), true);
        return 1;
    }

    private static int itemClear(ServerCommandSource source, Item item) {
        return 1;
    }

    private static int playerClear(ServerCommandSource source, ServerPlayerEntity target) {
        return 1;
    }

    private static int execute(ServerCommandSource source, ServerPlayerEntity target, Item item) throws CommandSyntaxException {
        ItemStack stack = new ItemStack(item, 1);
        source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.set.success", stack.toHoverableText(), target.getDisplayName()), true);
        return 1;
    }
}
