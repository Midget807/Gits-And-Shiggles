package net.midget807.gitsnshiggles.util.inject;

public interface InfinityStoneCooldown {

    int getPowerStoneCD();
    int getSpaceStoneCD();
    int getRealityStoneCD();
    int getSoulStoneCD();
    int getTimeStoneCD();
    int getMindStoneCD();
    void setPowerStoneCD(int cooldown);
    void setSpaceStoneCD(int cooldown);
    void setRealityStoneCD(int cooldown);
    void setSoulStoneCD(int cooldown);
    void setTimeStoneCD(int cooldown);
    void setMindStoneCD(int cooldown);
    boolean isPowerStoneOnCD();
    boolean isSpaceStoneOnCD();
    boolean isRealityStoneOnCD();
    boolean isSoulStoneOnCD();
    boolean isTimeStoneOnCD();
    boolean isMindStoneOnCD();
}
