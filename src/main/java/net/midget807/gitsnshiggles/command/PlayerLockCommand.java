package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.midget807.gitsnshiggles.util.state.ItemForPlayerState;
import net.midget807.gitsnshiggles.util.state.data.PlayerData;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PlayerLockCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        return literal("assignPlayerToItem")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("set")
                        .then(argument("target", EntityArgumentType.player())
                                .then(argument("item", ModItemStackArgumentType.itemStack(commandRegistryAccess))
                                        .executes(context -> execute(
                                                context.getSource(),
                                                EntityArgumentType.getPlayer(context, "target"),
                                                ModItemStackArgumentType.getItemStackArgument(context, "item")
                                        ))
                                )
                        )
                ).then(literal("clear")
                        .then(argument("target", EntityArgumentType.player())
                                .executes(context -> playerClear(
                                        context.getSource(),
                                        EntityArgumentType.getPlayer(context, "target")
                                ))
                        ).then(argument("item", ModItemStackArgumentType.itemStack(commandRegistryAccess))
                                .executes(context -> itemClear(
                                        context.getSource(),
                                        ModItemStackArgumentType.getItemStackArgument(context, "item")
                                ))
                        )
                ).then(literal("query")
                        .then(argument("target", EntityArgumentType.player())
                                .executes(context -> playerQuery(
                                        context.getSource(),
                                        EntityArgumentType.getPlayer(context, "target")
                                ))
                        ).then(argument("item", ModItemStackArgumentType.itemStack(commandRegistryAccess))
                                .executes(context -> itemQuery(
                                        context.getSource(),
                                        ModItemStackArgumentType.getItemStackArgument(context, "item")
                                ))
                        )
                );
    }

    private static int itemQuery(ServerCommandSource source, ItemStackArgument itemStack) throws CommandSyntaxException {
        Item item = null;
        PlayerEntity itemOwner = null;
        for (PlayerEntity player : source.getServer().getOverworld().getPlayers()) {
            PlayerData playerData = ItemForPlayerState.getPlayerState(player);
            item = playerData.bindedItem;
            itemOwner = player;
            if (item != null && item == itemStack.getItem()) break;
        }

        if (itemOwner != null && item != null) {
            Item finalItem = item;
            PlayerEntity finalItemOwner = itemOwner;
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.item.success", finalItem.getDefaultStack().toHoverableText(), finalItemOwner.getDisplayName()), true);
        } else if (itemOwner == null) {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.item.fail.player").formatted(Formatting.RED), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.item.fail.item.null").formatted(Formatting.RED), true);
        }
        return 1;
    }

    private static int playerQuery(ServerCommandSource source, ServerPlayerEntity target) {
        PlayerData playerData = ItemForPlayerState.getPlayerState(target);
        Item item = playerData.bindedItem;
        if (item == null) {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.player.fail", target.getDisplayName()).formatted(Formatting.RED), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.query.player.success", item.getDefaultStack().toHoverableText(), target.getDisplayName()), true);
        }
        return 1;
    }

    private static int itemClear(ServerCommandSource source, ItemStackArgument item) {
        Item item2 = null;
        for (PlayerEntity player : source.getServer().getOverworld().getPlayers()) {
            PlayerData playerData = ItemForPlayerState.getPlayerState(player);
            item2 = playerData.bindedItem;
            if (item2 != null && item2 == item.getItem()) {
                playerData.bindedItem = null;
                Item finalItem = item2;
                source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.clear.item.success", finalItem.getDefaultStack().toHoverableText()), true);
                break;
            }
        }
        if (item2 == null) {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.clear.item.fail.null").formatted(Formatting.RED), true);
        } else if (item2 != item.getItem()) {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.clear.item.fail"), true);
        }

        return 1;
    }

    private static int playerClear(ServerCommandSource source, ServerPlayerEntity target) {
        PlayerData playerData = ItemForPlayerState.getPlayerState(target);
        if (playerData.bindedItem != null) {
            Item item = playerData.bindedItem;
            playerData.bindedItem = null;
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.clear.player.success", item.getDefaultStack().toHoverableText(), target.getDisplayName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.clear.player.fail", target.getDisplayName()).formatted(Formatting.RED), true);
        }
        return 1;
    }

    private static int execute(ServerCommandSource source, ServerPlayerEntity target, ItemStackArgument item) throws CommandSyntaxException {
        PlayerData playerData = ItemForPlayerState.getPlayerState(target);
        playerData.bindedItem = item.getItem();
        ItemStack stack = new ItemStack(item.getItem(), 1);
        source.sendFeedback(() -> Text.translatable("commands.gitsnshiggles.player_lock.set.success", stack.toHoverableText(), target.getDisplayName()), true);
        return 1;
    }
}
