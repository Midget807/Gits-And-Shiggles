package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.block.entity.LootableContainerBlockEntity;
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

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends LootableContainerBlockEntity implements LidOpenable {
    @Shadow private DefaultedList<ItemStack> inventory;

    @Shadow protected abstract void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup);

    protected ChestBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void gitsnshiggles$clearInventoryForLightsaber(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        if (this.inventory.contains(new ItemStack(ModItems.LIGHTSABER))) {
            this.inventory.clear();
            this.writeNbt(nbt, registryLookup);
        }
    }
}
