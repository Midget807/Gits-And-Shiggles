package net.midget807.gitsnshiggles.util.inject;

public interface RailgunAds {
    boolean gitsAndShiggles$isUsingRailgun();
    boolean isNotUsingRailgun();
    float getFovScale();
    void setUsingRailgun(boolean isUsing);
    void setFovScale(float scale);
}
