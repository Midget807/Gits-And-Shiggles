package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class GiveEntityRemover {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        return literal("giveEntityRemover")
                .executes(context -> execute(
                        context.getSource()
                ));
    }

    private static int execute(ServerCommandSource source) {
        Objects.requireNonNull(source.getPlayer()).getInventory().insertStack(new ItemStack(ModItems.ENTITY_REMOVER));
        source.sendFeedback(() -> Text.literal("Gave ").append(source.getDisplayName()).append(Text.literal(" Entity Remover")), true);
        return 1;
    }
}
