package net.midget807.gitsnshiggles.command;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStringReader;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ModItemArgumentType implements ArgumentType<Item>, ArgumentSerializer.ArgumentTypeProperties<ModItemArgumentType> {
    private static final Collection<String> EXAMPLES = Arrays.asList("dice", "gitsnshiggles:dice", "dice{foo=bar}");

    @Override
    public Item parse(StringReader stringReader) throws CommandSyntaxException {
        Identifier id = Identifier.of(stringReader.readQuotedString());
        Item item = Registries.ITEM.get(id);
        if (item == Items.AIR || !id.getNamespace().equals("gitsnshiggles")) {
            throw new CommandSyntaxException(
                    CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                    Text.literal("Invalid item or not from Gits & Shiggles")
            );
        }
        return item;
    }

    public static ModItemArgumentType itemStack() {
        return new ModItemArgumentType();
    }

    public static <S> Item getItem(CommandContext<S> context, String name) {
        return context.getArgument(name, Item.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String input = builder.getRemaining().toLowerCase();
        Registries.ITEM.stream()
                .filter(item -> Registries.ITEM.getId(item).getNamespace().equals("gitsnshiggles"))
                .map(item -> Registries.ITEM.getId(item).toString())
                .filter(id -> id.startsWith(input))
                .forEach(builder::suggest);
        return builder.buildFuture();
    }


    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public ModItemArgumentType createType(CommandRegistryAccess commandRegistryAccess) {
        return this;
    }

    @Override
    public ArgumentSerializer<ModItemArgumentType, ?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements ArgumentSerializer<ModItemArgumentType, ModItemArgumentType> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {}

        @Override
        public void writePacket(ModItemArgumentType properties, PacketByteBuf buf) {
        }

        @Override
        public ModItemArgumentType fromPacket(PacketByteBuf buf) {
            return new ModItemArgumentType();
        }

        @Override
        public void writeJson(ModItemArgumentType properties, JsonObject json) {

        }

        @Override
        public ModItemArgumentType getArgumentTypeProperties(ModItemArgumentType argumentType) {
            return argumentType;
        }

    }
}
