package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.DoubleBoolComponent;
import net.midget807.gitsnshiggles.cca.primative.HextupleIntComponent;
import net.midget807.gitsnshiggles.cca.primative.IntComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import static net.midget807.gitsnshiggles.util.InfinityStoneUtil.*;

public class InfinityGauntletComponent implements HextupleIntComponent, IntComponent, DoubleBoolComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String HAS_REALITY_SHIELD = "hasRealityShield";
    public static final String HAS_SOUL_REVIVE = "hasSoulRevive";
    private final LivingEntity user;
    private boolean hasRealityShield = false;
    private boolean hasSoulRevive = false;
    private int realityShieldDuration = 0;
    private int powerStoneCD = 0;
    private int spaceStoneCD = 0;
    private int realityStoneCD = 0;
    private int soulStoneCD = 0;
    private int timeStoneCD = 0;
    private int mindStoneCD = 0;

    public InfinityGauntletComponent(LivingEntity user) {
        this.user = user;
    }

    public static InfinityGauntletComponent get(@NotNull LivingEntity user) {
        return ModCCAComponents.INFINITY_GAUNTLET.get(user);
    }

    private void sync() {
        ModCCAComponents.INFINITY_GAUNTLET.sync(user);
    }


    /** Returns {@link #hasRealityShield} */
    @Override
    public boolean getDoubleBool1() {
        this.hasRealityShield = this.realityShieldDuration > 0;
        return this.hasRealityShield;
    }

    /** Returns {@link #hasSoulRevive} */
    @Override
    public boolean getDoubleBool2() {
        this.hasSoulRevive = this.soulStoneCD <= 0;
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
        this.timeStoneCD = value;
        this.sync();
    }

    @Override
    public void setHexValue6(int value) {
        this.mindStoneCD = value;
        this.sync();
    }

    @Override
    public void addToHexValue1(int count) {
        this.powerStoneCD += count;
        this.sync();
    }

    @Override
    public void addToHexValue2(int count) {
        this.spaceStoneCD += count;
        this.sync();
    }

    @Override
    public void addToHexValue3(int count) {
        this.realityStoneCD += count;
        this.sync();
    }

    @Override
    public void addToHexValue4(int count) {
        this.soulStoneCD += count;
        this.sync();
    }

    @Override
    public void addToHexValue5(int count) {
        this.timeStoneCD += count;
        this.sync();
    }

    @Override
    public void addToHexValue6(int count) {
        this.mindStoneCD += count;
        this.sync();
    }

    @Override
    public void incrementHexValue1() {
        this.powerStoneCD++;
        this.sync();
    }

    @Override
    public void incrementHexValue2() {
        this.spaceStoneCD++;
        this.sync();
    }

    @Override
    public void incrementHexValue3() {
        this.realityStoneCD++;
        this.sync();
    }

    @Override
    public void incrementHexValue4() {
        this.soulStoneCD++;
        this.sync();
    }

    @Override
    public void incrementHexValue5() {
        this.timeStoneCD++;
        this.sync();
    }

    @Override
    public void incrementHexValue6() {
        this.mindStoneCD++;
        this.sync();
    }

    @Override
    public void decrementHexValue1() {
        this.powerStoneCD--;
        this.sync();
    }

    @Override
    public void decrementHexValue2() {
        this.spaceStoneCD--;
        this.sync();
    }

    @Override
    public void decrementHexValue3() {
        this.realityStoneCD--;
    }

    @Override
    public void decrementHexValue4() {
        this.soulStoneCD--;
        this.sync();
    }

    @Override
    public void decrementHexValue5() {
        this.timeStoneCD--;
    }

    @Override
    public void decrementHexValue6() {
        this.mindStoneCD--;
    }

    @Override
    public void tick() {
        if (this.realityShieldDuration > 0) {
            this.decrementInt();
        } else if (this.realityShieldDuration < 0) {
            this.setInt(0);
        }
        if (this.powerStoneCD > 0) {
            this.decrementHexValue1();
        } else if (this.powerStoneCD < 0) {
            this.setHexValue1(0);
        }
        if (this.spaceStoneCD > 0) {
            this.decrementHexValue2();
        } else if (this.spaceStoneCD < 0) {
            this.setHexValue2(0);
        }
        if (this.realityStoneCD > 0) {
            this.decrementHexValue3();
        } else if (this.realityStoneCD < 0) {
            this.setHexValue3(0);
        }
        if (this.soulStoneCD > 0) {
            this.decrementHexValue4();
        } else if (this.soulStoneCD < 0) {
            this.setHexValue4(0);
        }
        if (this.timeStoneCD > 0) {
            this.decrementHexValue5();
        } else if (this.timeStoneCD < 0) {
            this.setHexValue5(0);
        }
        if (this.mindStoneCD > 0) {
            this.decrementHexValue6();
        } else if (this.mindStoneCD < 0) {
            this.setHexValue6(0);
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.hasRealityShield = tag.getBoolean(HAS_REALITY_SHIELD);
        this.hasSoulRevive = tag.getBoolean(HAS_SOUL_REVIVE);
        this.realityShieldDuration = tag.getInt(REALITY_STONE_TIMER_KEY);

        this.powerStoneCD = tag.getInt(POWER_STONE_CD_KEY);
        this.spaceStoneCD = tag.getInt(SPACE_STONE_CD_KEY);
        this.realityStoneCD = tag.getInt(REALITY_STONE_CD_KEY);
        this.soulStoneCD = tag.getInt(SOUL_STONE_CD_KEY);
        this.timeStoneCD = tag.getInt(TIME_STONE_CD_KEY);
        this.mindStoneCD = tag.getInt(MIND_STONE_CD_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean(HAS_REALITY_SHIELD, this.hasRealityShield);
        tag.putBoolean(HAS_SOUL_REVIVE, this.hasSoulRevive);
        tag.putInt(REALITY_STONE_TIMER_KEY, this.realityShieldDuration);

        tag.putInt(POWER_STONE_CD_KEY, this.powerStoneCD);
        tag.putInt(SPACE_STONE_CD_KEY, this.spaceStoneCD);
        tag.putInt(REALITY_STONE_CD_KEY, this.realityStoneCD);
        tag.putInt(SOUL_STONE_CD_KEY, this.soulStoneCD);
        tag.putInt(TIME_STONE_CD_KEY, this.timeStoneCD);
        tag.putInt(MIND_STONE_CD_KEY, this.mindStoneCD);
    }
}
