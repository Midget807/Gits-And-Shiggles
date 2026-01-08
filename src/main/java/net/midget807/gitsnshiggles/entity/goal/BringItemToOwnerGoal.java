package net.midget807.gitsnshiggles.entity.goal;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class BringItemToOwnerGoal extends FollowOwnerGoal {
    private final ElfEntity elfEntity;

    public BringItemToOwnerGoal(TameableEntity tameable, double speed, ElfEntity elfEntity) {
        super(tameable, speed, 0.0f, elfEntity.getServer() != null ? elfEntity.getServer().getPlayerManager().getSimulationDistance() * 16.0f : 0.0f);
        this.elfEntity = elfEntity;
    }

    @Override
    public void tick() {
        super.tick();
        if (elfEntity.getOwner() != null && elfEntity.getOwner().isAlive() && ((PlayerEntity) elfEntity.getOwner()).getInventory().getEmptySlot() >= 0) {
            if (elfEntity.squaredDistanceTo(elfEntity.getOwner()) <= 3.0f && elfEntity.getOwner() instanceof PlayerEntity) {
                elfEntity.dropStack(elfEntity.getEquippedStack(EquipmentSlot.MAINHAND));
                elfEntity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !elfEntity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && elfEntity.isTamed() && elfEntity.getOwner() != null && elfEntity.getOwner().isAlive() && ((PlayerEntity) elfEntity.getOwner()).getInventory().getEmptySlot() >= 0;
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !elfEntity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && elfEntity.isTamed() && elfEntity.getOwner() != null && elfEntity.getOwner().isAlive() && ((PlayerEntity) elfEntity.getOwner()).getInventory().getEmptySlot() >= 0;
    }
}
