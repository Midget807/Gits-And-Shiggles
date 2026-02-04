package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.DoubleBoolComponent;
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
        this.sync();
    }

    @Override
    public void setDoubleBool2(boolean value) {
        this.hasSoulRevive = value;
        this.sync();
    }

    @Override
    public int getInt() {
        return realityShieldDuration;
    }

    @Override
    public void setInt(int value) {
        this.realityShieldDuration = value;
        this.sync();
    }

    @Override
    public void addToInt(int count) {
        this.realityShieldDuration += count;
        this.sync();
    }

    @Override
    public void incrementInt() {
        this.realityShieldDuration++;
        this.sync();
    }

    @Override
    public void decrementInt() {
        this.realityShieldDuration--;
        this.sync();
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
        return this.realityStoneCD;
    }

    @Override
    public int getHexValue4() {
        return this.soulStoneCD;
    }

    @Override
    public int getHexValue5() {
        return this.timeStoneCD;
    }

    @Override
    public int getHexValue6() {
        return this.mindStoneCD;
    }

    @Override
    public void setHexValue1(int value) {
        this.powerStoneCD = value;
        this.sync();
    }

    @Override
    public void setHexValue2(int value) {
        this.spaceStoneCD = value;
        this.sync();
    }

    @Override
    public void setHexValue3(int value) {
        this.realityStoneCD = value;
        this.sync();
    }

    @Override
    public void setHexValue4(int value) {
        this.soulStoneCD = value;
        this.sync();
    }

    @Override
    public void setHexValue5(int value) {
        this.sync();
        this.timeStoneCD = value;
    }

    @Override
    public void setHexValue6(int value) {
        this.sync();
        this.mindStoneCD = value;
    }

    @Override
    public void addToHexValue1(int count) {
        //todo
    }

    @Override
    public void addToHexValue2(int count) {

    }

    @Override
    public void addToHexValue3(int count) {

    }

    @Override
    public void addToHexValue4(int count) {

    }

    @Override
    public void addToHexValue5(int count) {

    }

    @Override
    public void addToHexValue6(int count) {

    }

    @Override
    public void incrementHexValue1() {

    }

    @Override
    public void incrementHexValue2() {

    }

    @Override
    public void incrementHexValue3() {

    }

    @Override
    public void incrementHexValue4() {

    }

    @Override
    public void incrementHexValue5() {

    }

    @Override
    public void incrementHexValue6() {

    }

    @Override
    public void decrementHexValue1() {

    }

    @Override
    public void decrementHexValue2() {

    }

    @Override
    public void decrementHexValue3() {

    }

    @Override
    public void decrementHexValue4() {

    }

    @Override
    public void decrementHexValue5() {

    }

    @Override
    public void decrementHexValue6() {

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
