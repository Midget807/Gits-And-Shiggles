package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.DoubleBoolComponent;
import net.midget807.gitsnshiggles.cca.primative.DoubleIntComponent;
import net.midget807.gitsnshiggles.cca.primative.HextupleIntComponent;
import net.midget807.gitsnshiggles.cca.primative.IntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class InfinityGauntletComponent implements HextupleIntComponent, IntComponent, DoubleBoolComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String HAS_REALITY_SHIELD = "hasRealityShield";
    public static final String HAS_SOUL_REVIVE = "hasSoulRevive";
    private final PlayerEntity player;
    private boolean hasRealityShield = false;
    private boolean hasSoulRevive = false;
    private int realityShieldDuration = 0;
    private int powerStoneCD = 0;
    private int spaceStoneCD = 0;
    private int realityStoneCD = 0;
    private int soulStoneCD = 0;
    private int timeStoneCD = 0;
    private int mindStoneCD = 0;

    public InfinityGauntletComponent(PlayerEntity player) {
        this.player = player;
    }

    public static InfinityGauntletComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.INFINITY_GAUNTLET.get(player);
    }

    private void sync() {
        ModCCAComponents.INFINITY_GAUNTLET.sync(player);
    }


    /** Returns {@link #hasRealityShield} */
    @Override
    public boolean getDoubleBool1() {
        return this.hasRealityShield;
    }

    /** Returns {@link #hasSoulRevive} */
    @Override
    public boolean getDoubleBool2() {
        return this.hasSoulRevive;
    }

    @Override
    public void setDoubleBool1(boolean value) {
        this.hasRealityShield = value;
    }

    @Override
    public void setDoubleBool2(boolean value) {
        this.hasSoulRevive = value;
    }

    @Override
    public int getInt() {
        return realityShieldDuration;
    }

    @Override
    public void setInt(int value) {
        this.realityShieldDuration = value;
    }

    @Override
    public void addToInt(int count) {
        this.realityShieldDuration += count;
    }

    @Override
    public void incrementInt() {
        this.realityShieldDuration++;
    }

    @Override
    public void decrementInt() {
        this.realityShieldDuration--;
    }

    @Override
    public int getHexValue1() {
        return this.powerStoneCD;
    }

    @Override
    public int getHexValue2() {
        return this.spaceStoneCD;
    }

    @Override
    public int getHexValue3() {
        return 0;
    }

    @Override
    public int getHexValue4() {
        return 0;
    }

    @Override
    public int getHexValue5() {
        return 0;
    }

    @Override
    public int getHexValue6() {
        return 0;
    }

    @Override
    public void setHexValue1(int value) {

    }

    @Override
    public void setHexValue2(int value) {

    }

    @Override
    public void setHexValue3(int value) {

    }

    @Override
    public void setHexValue4(int value) {

    }

    @Override
    public void setHexValue5(int value) {

    }

    @Override
    public void setHexValue6(int value) {

    }

    @Override
    public void tick() {

    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {

    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {

    }
}
