package net.midget807.gitsnshiggles.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import org.jetbrains.annotations.Nullable;

public record FlamethrowerContentsComponent(ItemStack stack) implements TooltipData {
    public static final FlamethrowerContentsComponent DEFAULT = new FlamethrowerContentsComponent(ItemStack.EMPTY);
    public static final Codec<FlamethrowerContentsComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ItemStack.CODEC.fieldOf("stack").forGetter(FlamethrowerContentsComponent::stack)
            ).apply(instance, FlamethrowerContentsComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, FlamethrowerContentsComponent> PACKET_CODEC = PacketCodec.tuple(
            ItemStack.PACKET_CODEC, FlamethrowerContentsComponent::stack,
            FlamethrowerContentsComponent::new
    );

    @Override
    public ItemStack stack() {
        return stack;
    }

    public static class Builder {
        private ItemStack stack;

        public Builder(FlamethrowerContentsComponent component) {
            this.stack = component.stack;
        }

        public FlamethrowerContentsComponent.Builder clear() {
            this.stack = ItemStack.EMPTY;
            return this;
        }

        public int add(ItemStack stack) {
            if (!stack.isEmpty() && stack.isIn(ModItemTagProvider.FLAMETHROWER_INSERTABLE)) {
                this.stack = stack.copyWithCount(1);
                stack.decrement(1);
                return 1;
            }
            return 0;
        }

        @Nullable
        public ItemStack remove() {
            if (this.stack.isEmpty()) {
                return null;
            } else {
                ItemStack itemStack = this.stack.copy();
                this.stack = ItemStack.EMPTY;
                return itemStack;
            }
        }

        public FlamethrowerContentsComponent build() {
            return new FlamethrowerContentsComponent(stack);
        }
    }
}
