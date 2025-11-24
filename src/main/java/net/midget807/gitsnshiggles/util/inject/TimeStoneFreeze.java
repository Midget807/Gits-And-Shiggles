package net.midget807.gitsnshiggles.util.inject;

public interface TimeStoneFreeze {
    boolean isTimeFrozen();
    boolean shouldTimeFreeze();
    int getTimeTicksFrozen();
    void setTimeFrozen(boolean timeFrozen);
    void setShouldTimeFreeze(boolean timeFreeze);
    void setTimeTicksFrozen(int ticksFrozen);
}
