package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.BoolComponent;
import net.midget807.gitsnshiggles.cca.primative.IntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class KatanaBlockingComponent implements BoolComponent, IntComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String BLOCKING_KEY = "IsBlocking";
    public static final String PARRY_TIME_KEY = "ParryTime";
    public static final int MAX_BLOCKING_TIME = 20; //5 secs (100t) for total recharge. 2.5 secs (50t) for actual parry window
    public static final float MAX_PARRY_DMG = 10.0f;
    private final PlayerEntity player;
    private int parryTime = 100;
    private boolean isBlocking = false;

    public KatanaBlockingComponent(PlayerEntity player) {
        this.player = player;
    }

    public static KatanaBlockingComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.BLOCKING.get(player);
    }

    private void sync() {
        ModCCAComponents.BLOCKING.sync(player);
    }

    @Override
    public boolean getBool() {
        if (!this.player.getWorld().isClient && (!this.player.getMainHandStack().isOf(ModItems.KATANA) || !this.player.isUsingItem())) {
            this.setBool(false);
        }
        return this.isBlocking;
    }

    @Override
    public void setBool(boolean value) {
        this.isBlocking = value;
        this.sync();
    }

    @Override
    public int getInt() {
        return this.parryTime;
    }

    @Override
    public void setInt(int value) {
        this.parryTime = value;
        this.sync();
    }

    @Override
    public void addToInt(int count) {
        this.parryTime += count;
        this.sync();
    }

    @Override
    public void incrementInt() {
        this.parryTime++;
        this.sync();
    }

    @Override
    public void decrementInt() {
        this.parryTime--;
        this.sync();
    }

    @Override
    public void tick() {
        if (this.parryTime < MAX_BLOCKING_TIME) {
            if (player.isUsingItem()) {
                if (player.getActiveItem() != null && !player.getActiveItem().isOf(ModItems.KATANA)) {
                    this.incrementInt();
                }
            } else {
                this.incrementInt();
            }
        }
        if (this.parryTime > MAX_BLOCKING_TIME) {
            this.setInt(MAX_BLOCKING_TIME);
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.isBlocking = tag.getBoolean(BLOCKING_KEY);
        this.parryTime = tag.getInt(PARRY_TIME_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean(BLOCKING_KEY, this.isBlocking);
        tag.putInt(PARRY_TIME_KEY, this.parryTime);
    }
}
