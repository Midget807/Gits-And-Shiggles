package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.component.FlamethrowerContentsComponent;
import net.midget807.gitsnshiggles.component.OverheatComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<OverheatComponent> OVERHEAT = register("overheat", builder -> builder.codec(OverheatComponent.CODEC).packetCodec(OverheatComponent.PACKET_CODEC));
    public static final ComponentType<FlamethrowerContentsComponent> FLAMETHROWER_CONTENTS = register("overheat", builder -> builder.codec(FlamethrowerContentsComponent.CODEC).packetCodec(FlamethrowerContentsComponent.PACKET_CODEC));

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderUnaryOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, GitsAndShigglesMain.id(name), builderUnaryOperator.apply(ComponentType.builder()).build());
    }

    public static void registerModDataComponents() {
        GitsAndShigglesMain.LOGGER.info("Registering Mod Data Component Types");
    }
}
