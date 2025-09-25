package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.datagen.ModItemTagProvider;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(LockableContainerBlockEntity.class)
public abstract class LockableContainerBlockEntityMixin extends BlockEntity implements Inventory {

    public LockableContainerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow protected abstract void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup);


    @Shadow protected abstract DefaultedList<ItemStack> getHeldStacks();

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void gitsnshiggles$clearInventoryForLightsaber(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        if (this.getHeldStacks().stream().anyMatch(itemStack -> itemStack.isIn(ModItemTagProvider.LIGHTSABERS))) {
            if (this.getWorld() != null && (this.getWorld().getBlockState(this.getPos()).hasBlockEntity() && this.getWorld().getBlockState(this.getPos()).getBlock() != Blocks.AIR)) {
                this.getWorld().breakBlock(this.getPos(), true);
            }
        }
    }
}
