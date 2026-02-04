package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.BoolComponent;
import net.midget807.gitsnshiggles.cca.primative.IntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class TimeStopComponent implements IntComponent, BoolComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String IS_TIME_STOPPED_KEY = "isTimeStopped";
    public static final String TIME_STOP_DURATION_KEY = "timeStopDuration";
    public static final int MAX_DURATION = 60; // 3 seconds
    private final Entity entity;
    private boolean isTimeStopped = false;
    private int duration = 0;

    public TimeStopComponent(Entity entity) {
        this.entity = entity;
    }

    public static TimeStopComponent get(@NotNull Entity entity) {
        return ModCCAComponents.TIME_STOP.get(entity);
    }

    private void sync() {
        ModCCAComponents.TIME_STOP.sync(this.entity);
    }

    @Override
    public boolean getBool() {
        this.setBool(this.getInt() > 0);
        return this.isTimeStopped;
    }

    @Override
    public void setBool(boolean value) {
        this.isTimeStopped = value;
        this.sync();
    }

    @Override
    public int getInt() {
        return this.duration;
    }

    @Override
    public void setInt(int value) {
        this.duration = value;
        this.sync();
    }

    @Override
    public void addToInt(int count) {
        this.duration += count;
        this.sync();
    }

    @Override
    public void incrementInt() {
        this.duration++;
        this.sync();
    }

    @Override
    public void decrementInt() {
        this.duration--;
        this.sync();
    }

    @Override
    public void tick() {
        if (this.duration > 0) {
            this.decrementInt();
        }
        if (this.duration <= 0) {
            this.setInt(0);
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.isTimeStopped = tag.getBoolean(IS_TIME_STOPPED_KEY);
        this.duration = tag.getInt(TIME_STOP_DURATION_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean(IS_TIME_STOPPED_KEY, this.isTimeStopped);
        tag.putInt(TIME_STOP_DURATION_KEY, this.duration);
    }
}
