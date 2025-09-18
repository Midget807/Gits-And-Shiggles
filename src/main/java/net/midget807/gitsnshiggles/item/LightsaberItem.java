package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class LightsaberItem extends Item {
    public LightsaberItem(Settings settings) {
        super(settings);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(this, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }
    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 99.0f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.0f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }


    public static DyedColorComponent createColorComponent() {
        return new DyedColorComponent(0x0000FF, true);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (entity instanceof PlayerEntity player) {
                if (isInPlayerInventory(player)) {
                    player.getInventory().clear();
                    player.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean isInPlayerInventory(PlayerEntity player) {
        return player.getInventory().indexOf(new ItemStack(ModItems.LIGHTSABER)) >= 9;
    }
}
