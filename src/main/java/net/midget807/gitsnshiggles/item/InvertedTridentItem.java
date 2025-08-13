package net.midget807.gitsnshiggles.item;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class InvertedTridentItem extends TridentItem {
    public InvertedTridentItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
            if (i >= 10) {
                float f = 3.0f;
                if (!isAboutToBreak(stack)) {
                    RegistryEntry<SoundEvent> registryEntry = (RegistryEntry<SoundEvent>)EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND)
                            .orElse(SoundEvents.ITEM_TRIDENT_RIPTIDE_3);
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

                    float g = playerEntity.getYaw();
                    float h = playerEntity.getPitch();
                    float j = -MathHelper.sin(g * (float) (Math.PI / 180.0)) * MathHelper.cos(h * (float) (Math.PI / 180.0));
                    float k = -MathHelper.sin(h * (float) (Math.PI / 180.0));
                    float l = MathHelper.cos(g * (float) (Math.PI / 180.0)) * MathHelper.cos(h * (float) (Math.PI / 180.0));
                    float m = MathHelper.sqrt(j * j + k * k + l * l);
                    j *= f / m;
                    k *= f / m;
                    l *= f / m;
                    playerEntity.addVelocity(-j, -k, -l);
                    playerEntity.useRiptide(20, 8.0F, stack);
                    if (playerEntity.isOnGround()) {
                        float n = 1.1999999F;
                        playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999F, 0.0));
                    }

                    world.playSoundFromEntity(null, playerEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isAboutToBreak(itemStack)) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    private static boolean isAboutToBreak(ItemStack stack) {
        return stack.getDamage() >= stack.getMaxDamage() - 1;
    }
}
