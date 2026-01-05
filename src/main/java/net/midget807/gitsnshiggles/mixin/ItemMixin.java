package net.midget807.gitsnshiggles.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;canConsume(Z)Z", shift = At.Shift.BEFORE), cancellable = true)
    private void gitsnshiggles$santaDiet(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack itemStack) {
        if (user.getInventory().armor.get(3).isIn(ModItemTagProvider.SANTA_HATS)) {
            if (!itemStack.isIn(ModItemTagProvider.SANTA_DIET)) {
                cir.setReturnValue(TypedActionResult.fail(itemStack));
            }
        }
    }

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/component/type/FoodComponent;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.BEFORE))
    private static void gitsnshiggles$santaCheckForWhetherMilkShouldActAsFood(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir, @Local FoodComponent foodComponent) {
        if (user instanceof PlayerEntity player && stack.isOf(Items.MILK_BUCKET)) {
            if (player.getInventory().armor.get(3).isIn(ModItemTagProvider.SANTA_HATS)) {
                foodComponent = ModDataComponentTypes.MILK_BUCKET;
            } else {
                foodComponent = null;
            }
        }
    }
}
