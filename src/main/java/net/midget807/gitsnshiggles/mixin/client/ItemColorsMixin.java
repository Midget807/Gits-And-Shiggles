package net.midget807.gitsnshiggles.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.component.type.DyedColorComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public abstract class ItemColorsMixin {
    @Inject(method = "create", at = @At("TAIL"))
    private static void gitsnshiggles$addItems(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, @Local ItemColors itemColors) {
        itemColors.register(
                (stack, tintIndex) -> tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -6265536),
                ModItems.WIZARD_HAT,
                ModItems.WIZARD_ROBE,
                ModItems.WIZARD_PANTS,
                ModItems.WIZARD_BOOTS,
                ModItems.LEATHER_SANTA_HAT
        );
    }
}
