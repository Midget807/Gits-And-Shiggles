package net.midget807.gitsnshiggles.cca;

import com.mojang.datafixers.kinds.IdF;
import net.midget807.gitsnshiggles.cca.primative.TripleIntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class FlamethrowerComponent implements TripleIntComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String OVERHEAT_KEY = "Overheat";
    public static final String RANGE_KEY = "ExtraRange";
    public static final String DAMAGE_KEY = "ExtraDamage";
    public static final int MAX_OVERHEAT = 120;
    public static final int MAX_MODIFIER = 200;
    private final PlayerEntity player;
    private int overheat = 0;
    private int extraRange = 0;
    private int extraDamage = 0;

    public FlamethrowerComponent(PlayerEntity player) {
        this.player = player;
    }

    public static FlamethrowerComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.FLAMETHROWER.get(player);
    }

    private void sync() {
        ModCCAComponents.FLAMETHROWER.sync(player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.overheat = nbtCompound.getInt(OVERHEAT_KEY);
        this.extraRange = nbtCompound.getInt(RANGE_KEY);
        this.extraDamage = nbtCompound.getInt(DAMAGE_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt(OVERHEAT_KEY, this.overheat);
        nbtCompound.putInt(RANGE_KEY, this.extraRange);
        nbtCompound.putInt(DAMAGE_KEY, this.extraDamage);
    }

    @Override
    public void clientTick() {
        CommonTickingComponent.super.clientTick();
    }

    @Override
    public void serverTick() {
        CommonTickingComponent.super.serverTick();
    }

    @Override
    public void tick() {
        if (this.overheat >= MAX_OVERHEAT) {
            this.setValue1(MAX_OVERHEAT);
            player.getItemCooldownManager().set(ModItems.FLAMETHROWER, 200);
            player.setOnFireFor(5);
        }
        if (this.overheat > 0 && player.getActiveItem() != null && !player.getActiveItem().isOf(ModItems.FLAMETHROWER)) {
            this.decrementValue1();
        }
        if (this.extraRange > 0) {
            this.decrementValue2();
        }
        if (this.extraRange < 0) {
            this.setValue2(0);
        }
        if (this.extraDamage > 0) {
            this.decrementValue3();
        }
        if (this.extraDamage < 0) {
            this.setValue3(0);
        }
    }

    /** Returns {@link #overheat} */
    @Override
    public int getValue1() {
        return this.overheat;
    }

    /** Returns {@link #extraRange} */
    @Override
    public int getValue2() {
        return this.extraRange;
    }

    /** Returns {@link #extraDamage} */
    @Override
    public int getValue3() {
        return extraDamage;
    }

    @Override
    public void setValue1(int value) {
        this.overheat = value;
        this.sync();
    }

    @Override
    public void setValue2(int value) {
        this.extraRange = value;
        this.sync();
    }

    @Override
    public void setValue3(int value) {
        this.extraDamage = value;
        this.sync();
    }

    @Override
    public void addToValue1(int count) {
        this.overheat += count;
        this.sync();
    }

    @Override
    public void addToValue2(int count) {
        this.extraRange += count;
        this.sync();
    }

    @Override
    public void addToValue3(int count) {
        this.extraDamage += count;
        this.sync();
    }

    @Override
    public void incrementValue1() {
        this.overheat++;
        this.sync();
    }

    @Override
    public void incrementValue2() {
        this.extraRange++;
        this.sync();
    }

    @Override
    public void incrementValue3() {
        this.extraDamage++;
        this.sync();
    }

    @Override
    public void decrementValue1() {
        this.overheat--;
        this.sync();
    }

    @Override
    public void decrementValue2() {
        this.extraRange--;
        this.sync();
    }

    @Override
    public void decrementValue3() {
        this.extraDamage--;
        this.sync();
    }
}
