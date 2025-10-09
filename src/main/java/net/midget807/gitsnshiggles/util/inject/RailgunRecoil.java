package net.midget807.gitsnshiggles.util.inject;

import net.minecraft.util.math.Vec3d;

public interface RailgunRecoil {
    Vec3d getRecoilVec();
    boolean hasRailgunRecoil();
    void setRailgunRecoil(boolean recoil);
    void setRecoilVec(Vec3d recoil);
}
