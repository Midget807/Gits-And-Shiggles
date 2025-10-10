package net.midget807.gitsnshiggles.util.inject;

import net.minecraft.util.math.Vec3d;

public interface RailgunRecoil {
    int getRecoilPower();
    boolean hasRailgunRecoil();
    void setRailgunRecoil(boolean recoil);
    void setRecoilPower(int power);
}
