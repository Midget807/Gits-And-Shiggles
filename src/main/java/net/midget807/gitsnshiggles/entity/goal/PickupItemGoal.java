package net.midget807.gitsnshiggles.entity.goal;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class PickupItemGoal extends Goal {
    private final ElfEntity elf;
    public PickupItemGoal(ElfEntity elf) {
        this.elf = elf;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (!this.elf.isTamed() || !this.elf.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else if (this.elf.getTarget() == null && this.elf.getAttacker() == null) {
            if (this.elf.isSitting()) {
                return false;
            } else if (this.elf.getRandom().nextInt(10) != 0) {
                return false;
            } else {
                List<ItemEntity> list = this.elf.getWorld().getEntitiesByClass(ItemEntity.class, this.elf.getBoundingBox().expand(10.0D, 10.0D, 10.0D), ElfEntity.PICKABLE_DROP_FILTER);
                return !list.isEmpty() && this.elf.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        List<ItemEntity> list = this.elf.getWorld().getEntitiesByClass(ItemEntity.class, this.elf.getBoundingBox().expand(10.0D, 10.0D, 10.0D), ElfEntity.PICKABLE_DROP_FILTER);
        ItemStack itemStack = this.elf.getEquippedStack(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty() && !list.isEmpty()) {
            this.elf.getNavigation().startMovingTo(list.get(0), 1);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = this.elf.getWorld().getEntitiesByClass(ItemEntity.class, this.elf.getBoundingBox().expand(10.0D, 10.0D, 10.0D), ElfEntity.PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            this.elf.getNavigation().startMovingTo(list.get(0), 1);
        }
    }
}
