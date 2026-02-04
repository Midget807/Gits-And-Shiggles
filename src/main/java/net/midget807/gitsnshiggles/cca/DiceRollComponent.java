package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.DoubleIntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class DiceRollComponent implements DoubleIntComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String ROLL_KEY = "DiceRoll";
    public static final String FLY_KEY = "FlyTime";
    public static final int FLY_DURATION = 600;
    private final PlayerEntity player;
    private int diceRoll = 0;
    private int flyTime = 0;

    public DiceRollComponent(PlayerEntity player) {
        this.player = player;
    }

    public static DiceRollComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.DICE_ROLL.get(player);
    }

    private void sync() {
        ModCCAComponents.DICE_ROLL.sync(player);
    }

    /** Returns {@link #diceRoll} */
    @Override
    public int getDoubleIntValue1() {
        return this.diceRoll;
    }

    /** Returns {@link #flyTime} */
    @Override
    public int getDoubleIntValue2() {
        return this.flyTime;
    }

    @Override
    public void setDoubleIntValue1(int value) {
        this.diceRoll = value;
        this.sync();
    }

    @Override
    public void setDoubleIntValue2(int value) {
        this.flyTime = value;
        this.sync();
    }

    @Override
    public void addToDoubleIntValue1(int count) {
        this.diceRoll += count;
        this.sync();
    }

    @Override
    public void addToDoubleIntValue2(int count) {
        this.flyTime += count;
        this.sync();
    }

    @Override
    public void incrementDoubleIntValue1() {
        this.diceRoll++;
        this.sync();
    }

    @Override
    public void incrementDoubleIntValue2() {
        this.flyTime++;
        this.sync();
    }

    @Override
    public void decrementDoubleIntValue1() {
        this.diceRoll--;
        this.sync();
    }

    @Override
    public void decrementDoubleIntValue2() {
        this.flyTime--;
        this.sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.diceRoll = tag.getInt(ROLL_KEY);
        this.flyTime = tag.getInt(FLY_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt(ROLL_KEY, this.diceRoll);
        tag.putInt(FLY_KEY, this.flyTime);
    }

    @Override
    public void tick() {
        if (this.flyTime > 0) {
            if (!player.getAbilities().allowFlying) {
                player.getAbilities().allowFlying = true;
            }
            this.decrementDoubleIntValue2();
        }
        if (this.flyTime <= 0) {
            if (!player.getAbilities().creativeMode && !player.isSpectator()) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
            }
        }
        if (this.flyTime < 0) {
            this.setDoubleIntValue2(0);
        }
    }
}
