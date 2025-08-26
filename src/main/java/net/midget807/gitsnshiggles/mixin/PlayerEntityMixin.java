package net.midget807.gitsnshiggles.mixin;

import net.midget807.gitsnshiggles.item.RailgunItem;
import net.midget807.gitsnshiggles.util.inject.ElfCount;
import net.midget807.gitsnshiggles.util.inject.RailgunAds;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.inject.RailgunLoading;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements RailgunAds, RailgunLoading, ElfCount {
    @Shadow @Final private PlayerInventory inventory;
    @Shadow @Final private PlayerAbilities abilities;
    @Unique
    private float fovScale = RailgunItem.FOV_MULTIPLIER;
    @Unique
    private int elfCount = 0;

    @Override
    public void setUsingRailgun(boolean usingRailgun) {
    }

    @Override
    public void setFovScale(float fovScale) {
        this.fovScale = fovScale;
    }

    @Override
    public boolean isUsingRailgun() {
        return this.isUsingItem() && this.getActiveItem().isOf(ModItems.RAILGUN);
    }

    @Override
    public float getFovScale() {
        return this.fovScale;
    }

    @Override
    public int getElfCount() {
        return this.elfCount;
    }
    @Override
    public void setElfCount(int elfCount) {
        this.elfCount = elfCount;
    }

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getRailgunProjectile(ItemStack railgun) {
        if (!(railgun.getItem() instanceof RailgunItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = RailgunItem.ACCEPTABLE_PROJECTILES;
            ItemStack heldProjectile = RailgunItem.getHeldProjectiles(this, predicate);
            if (!heldProjectile.isEmpty()) {
                return heldProjectile;
            } else {
                for (int i = 0; i < this.inventory.size(); i++) {
                    ItemStack invStack = this.inventory.getStack(i);
                    if (predicate.test(invStack)) {
                        return invStack;
                    }
                }
                return this.abilities.creativeMode ? new ItemStack(Items.IRON_NUGGET) : ItemStack.EMPTY;
            }
        }
    }


}
