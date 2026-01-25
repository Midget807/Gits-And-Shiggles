package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.IntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class ElfCountComponent implements IntComponent, AutoSyncedComponent {
    public static final String KEY = "ElfCount";
    private final PlayerEntity player;
    private int elfCount = 0;

    public ElfCountComponent(PlayerEntity player) {
        this.player = player;
    }

    public static ElfCountComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.ELF_COUNT.get(player);
    }
    private void sync() {
        ModCCAComponents.ELF_COUNT.sync(player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.elfCount = nbtCompound.getInt(KEY);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt(KEY, this.elfCount);
    }

    @Override
    public int getInt() {
        return this.elfCount;
    }

    @Override
    public void setInt(int value) {
        this.elfCount = value;
        this.sync();
    }

    @Override
    public void addToInt(int count) {
        this.elfCount += count;
        this.sync();
    }

    @Override
    public void incrementInt() {
        this.elfCount++;
        this.sync();
    }

    @Override
    public void decrementInt() {
        this.elfCount--;
        this.sync();
    }
}