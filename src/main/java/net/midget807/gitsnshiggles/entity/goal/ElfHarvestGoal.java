package net.midget807.gitsnshiggles.entity.goal;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ElfHarvestGoal extends Goal {
    protected final ElfEntity elf;
    protected BlockPos targetBlockPos;

    public ElfHarvestGoal(ElfEntity elf) {
        this.setControls(EnumSet.of(Control.MOVE));
        this.elf = elf;
    }

    @Override
    public boolean canStart() {
        this.targetBlockPos = null;

        if (this.elf.getTarget() == null && this.elf.getAttacker() == null && !this.elf.isSitting() && this.elf.isTamed() && (this.elf.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() || (this.elf.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof AliasedBlockItem && ((AliasedBlockItem) this.elf.getEquippedStack(EquipmentSlot.MAINHAND).getItem()).getBlock() instanceof CropBlock) || this.elf.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof BoneMealItem)) {
            ItemStack itemStack = this.elf.getEquippedStack(EquipmentSlot.MAINHAND);

            for (BlockPos blockPos : BlockPos.iterateOutwards(this.elf.getBlockPos(), 8, 2, 8)) {
                if (itemStack.isEmpty()) {
                    // harvest
                    BlockState blockState = this.elf.getWorld().getBlockState(blockPos);
                    if (blockState.getBlock() instanceof CropBlock && ((CropBlock) blockState.getBlock()).isMature(blockState)) {
                        if (this.elf.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1f)) {
                            this.targetBlockPos = blockPos;
                            return true;
                        }
                    }
                } else if (itemStack.getItem() instanceof AliasedBlockItem && ((AliasedBlockItem) itemStack.getItem()).getBlock() instanceof CropBlock) {
                    // plant
                    BlockState blockState = this.elf.getWorld().getBlockState(blockPos.add(0, -1, 0));
                    if (blockState.getBlock() instanceof FarmlandBlock && this.elf.getWorld().getBlockState(blockPos).isAir()) {
                        if (this.elf.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1f)) {
                            this.targetBlockPos = blockPos;
                            return true;
                        }
                    }
                } else if (itemStack.getItem() instanceof BoneMealItem) {
                    // bonemeal
                    BlockState blockState = this.elf.getWorld().getBlockState(blockPos);
                    if (blockState.getBlock() instanceof CropBlock && ((CropBlock) blockState.getBlock()).isFertilizable(this.elf.getWorld(), blockPos, this.elf.getWorld().getBlockState(blockPos))) {
                        if (this.elf.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1f)) {
                            this.targetBlockPos = blockPos;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
