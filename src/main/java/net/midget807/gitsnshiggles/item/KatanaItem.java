package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class KatanaItem extends SwordItem {
    public int useTicks = 100;
    public static final int USE_TICK_DECREMENT = 2;

    public KatanaItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed, float blockReach, float entityReach) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                ).add(
                        EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, blockReach, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, entityReach, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public int getUseTicks() {
        return this.useTicks;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return Integer.MAX_VALUE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (hand == Hand.OFF_HAND) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            itemStack.set(ModDataComponentTypes.BLOCKING, true);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        stack.set(ModDataComponentTypes.BLOCKING, false);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player && !world.isClient) {
            if (!(player.isUsingItem() && player.getActiveItem().isOf(ModItems.KATANA)) && this.useTicks < 100) {
                this.useTicks++;
            }
            if (this.useTicks <= 0) {
                player.getItemCooldownManager().set(stack.getItem(), 100);
                stack.set(ModDataComponentTypes.BLOCKING, false);
            }
            ModDebugUtil.debugMessage(player, "blocking: " + stack.get(ModDataComponentTypes.BLOCKING), true);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        if (this.useTicks > 0) {
            this.useTicks -= USE_TICK_DECREMENT;
        }
    }
}
