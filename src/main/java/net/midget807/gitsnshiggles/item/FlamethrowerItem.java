package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.component.FlamethrowerContentsComponent;
import net.midget807.gitsnshiggles.component.OverheatComponent;
import net.midget807.gitsnshiggles.item.client.FlamethrowerTooltipData;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class FlamethrowerItem extends Item {
    private static int useTime = 0;
    private static boolean isOverheating = false;

    public FlamethrowerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        user.sendMessage(Text.literal("shooting fire"));
        OverheatComponent overheatComponent = stack.get(ModDataComponentTypes.OVERHEAT);
        if (overheatComponent == null) {
            stack.set(ModDataComponentTypes.OVERHEAT, OverheatComponent.DEFAULT);
        } else {
            if (overheatComponent.useTime() >= 20) {
                isOverheating = true;
            }
            stack.set(ModDataComponentTypes.OVERHEAT, new OverheatComponent(useTime++, isOverheating));
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            FlamethrowerContentsComponent flamethrowerContentsComponent = stack.get(ModDataComponentTypes.FLAMETHROWER_CONTENTS);
            if (flamethrowerContentsComponent == null) {
                return false;
            } else {
                ItemStack clickedSlotStack = slot.getStack();
                FlamethrowerContentsComponent.Builder builder = new FlamethrowerContentsComponent.Builder(flamethrowerContentsComponent);
                if (clickedSlotStack.isEmpty()) {
                    this.playRemoveSound(player);
                    ItemStack storedStack = builder.remove();
                    if (storedStack != null) {
                        ItemStack itemStack3 = slot.insertStack(storedStack);
                        builder.add(itemStack3);
                    }
                } else {
                    int i = builder.add(clickedSlotStack);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }
                stack.set(ModDataComponentTypes.FLAMETHROWER_CONTENTS, builder.build());
                return true;
            }
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            FlamethrowerContentsComponent flamethrowerContentsComponent = stack.get(ModDataComponentTypes.FLAMETHROWER_CONTENTS);
            if (flamethrowerContentsComponent == null) {
                return false;
            } else {
                FlamethrowerContentsComponent.Builder builder = new FlamethrowerContentsComponent.Builder(flamethrowerContentsComponent);
                if (otherStack.isEmpty()) { /*Taking out item and adding it to cursor*/
                    ItemStack itemStack = builder.remove();
                    if (itemStack != null) {
                        this.playRemoveSound(player);
                        cursorStackReference.set(itemStack);
                    }
                } else { /*Add cursor stack*/
                    int i = builder.add(otherStack);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }
                stack.set(ModDataComponentTypes.FLAMETHROWER_CONTENTS, builder.build());
                return true;
            }
        } else {
            return false;
        }
    }

    private void playInsertSound(PlayerEntity player) {
        player.playSound(SoundEvents.ITEM_BUNDLE_INSERT);
    }

    private void playRemoveSound(PlayerEntity player) {
        player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return !stack.contains(DataComponentTypes.HIDE_TOOLTIP) &&!stack.contains(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)
                ? Optional.ofNullable(stack.get(ModDataComponentTypes.FLAMETHROWER_CONTENTS)).map(FlamethrowerTooltipData::new)
                : Optional.empty();
    }

}
