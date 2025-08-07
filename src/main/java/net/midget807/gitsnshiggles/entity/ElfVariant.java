package net.midget807.gitsnshiggles.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.midget807.gitsnshiggles.registry.ModRegistryKeys;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.function.UnaryOperator;

public final class ElfVariant {
    public static final Codec<ElfVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("tame_texture").forGetter(elfVariant -> elfVariant.tameId),
                    Identifier.CODEC.fieldOf("angry_texture").forGetter(elfVariant -> elfVariant.angryId)
            ).apply(instance, ElfVariant::new)
    );
    public static final PacketCodec<RegistryByteBuf, ElfVariant> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, ElfVariant::getTameTextureId,
            Identifier.PACKET_CODEC, ElfVariant::getAngryTextureId,
            ElfVariant::new
    );
    public static final Codec<RegistryEntry<ElfVariant>> ENTRY_CODEC = RegistryElementCodec.of(ModRegistryKeys.ELF_VARIANT, CODEC);
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<ElfVariant>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(ModRegistryKeys.ELF_VARIANT, PACKET_CODEC);

    private final Identifier tameId;
    private final Identifier angryId;
    private final Identifier tameTextureId;
    private final Identifier angryTextureId;

    public ElfVariant(Identifier tameId, Identifier angryId) {
        this.tameId = tameId;
        this.tameTextureId = getTextureId(tameId);
        this.angryId = angryId;
        this.angryTextureId = getTextureId(angryId);
    }

    private static Identifier getTextureId(Identifier id) {
        return id.withPath((UnaryOperator<String>)(oldPath -> "textures/" + oldPath + ".png"));
    }

    public Identifier getTameTextureId() {
        return this.tameTextureId;
    }

    public Identifier getAngryTextureId() {
        return this.angryTextureId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            return !(o instanceof ElfVariant elfVariant)
                    ? false
                    : Objects.equals(this.tameId, elfVariant.tameId)
                    && Objects.equals(this.angryId, elfVariant.angryId);
        }
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.tameId.hashCode();
        return 31 * i + this.angryId.hashCode();
    }
}
