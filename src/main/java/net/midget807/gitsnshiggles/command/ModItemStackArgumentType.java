package net.midget807.gitsnshiggles.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.argument.ItemStringReader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ModItemStackArgumentType extends ItemStackArgumentType {
    private static final Collection<String> EXAMPLES = Arrays.asList("dice", "gitsnshiggles:dice", "dice{foo=bar}");

    public ModItemStackArgumentType(CommandRegistryAccess commandRegistryAccess) {
        super(commandRegistryAccess);
    }

    @Override
    public ItemStackArgument parse(StringReader stringReader) throws CommandSyntaxException {
        ItemStringReader.ItemResult itemResult = this.reader.consume(stringReader);
        if (itemResult.item() == null || itemResult.item().value() == Items.AIR || !Registries.ITEM.getId(itemResult.item().value()).getNamespace().equals("gitsnshiggles")) {
            throw new CommandSyntaxException(
                    CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                    Text.literal("Invalid item or not from Gits & Shiggles")
            );
        } else {
            return new ItemStackArgument(itemResult.item(), itemResult.components());
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
