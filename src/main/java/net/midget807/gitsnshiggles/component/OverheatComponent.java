package net.midget807.gitsnshiggles.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record OverheatComponent(int useTime, boolean isOverheating) {
    public static final OverheatComponent DEFAULT = new OverheatComponent(0, false);
    public static final Codec<OverheatComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("use_time").forGetter(OverheatComponent::useTime),
                    Codec.BOOL.fieldOf("is_overheating").forGetter(OverheatComponent::isOverheating)
            ).apply(instance, OverheatComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, OverheatComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, OverheatComponent::useTime,
            PacketCodecs.BOOL, OverheatComponent::isOverheating,
            OverheatComponent::new
    );

    @Override
    public int useTime() {
        return useTime;
    }

    @Override
    public boolean isOverheating() {
        return isOverheating;
    }
}
