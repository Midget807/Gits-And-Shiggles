package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin extends Item {
    public MilkBucketItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z", shift = At.Shift.AFTER))
    private static void gitsnshiggles$addSantaSat(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (user instanceof PlayerEntity player && !world.isClient) {
            if (player.getInventory().armor.get(3).isIn(ModItemTagProvider.SANTA_HATS)) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 10, 19, false, false, false));
            }
        }
    }
}
