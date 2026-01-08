package net.midget807.gitsnshiggles.registry;

import com.mojang.serialization.Codec;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.component.FlamethrowerContentsComponent;
import net.midget807.gitsnshiggles.component.OverheatComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<OverheatComponent> OVERHEAT = register("overheat", builder -> builder.codec(OverheatComponent.CODEC).packetCodec(OverheatComponent.PACKET_CODEC));
    public static final ComponentType<FlamethrowerContentsComponent> FLAMETHROWER_CONTENTS = register("overheat", builder -> builder.codec(FlamethrowerContentsComponent.CODEC).packetCodec(FlamethrowerContentsComponent.PACKET_CODEC));
    public static final ComponentType<Integer> DICE_ROLL = register("dice_roll", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
    public static final ComponentType<Boolean> BLOCKING = register("blocking", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    public static final ComponentType<Integer> PARRY_TIME = register("parry_time", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
    public static final ComponentType<Integer> PARRY_DAMAGE = register("parry_damage", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
    public static final ComponentType<Integer> ELF_COUNT = register("elf_count", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));

    public static final FoodComponent MILK_BUCKET = new FoodComponent.Builder().nutrition(6).saturationModifier(0.8f).build();

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderUnaryOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, GitsAndShigglesMain.id(name), builderUnaryOperator.apply(ComponentType.builder()).build());
    }

    public static void registerModDataComponents() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Data Component Types");
    }
}
