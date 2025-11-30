package net.midget807.gitsnshiggles.util.inject;

public interface RealityStoneTransform {
    boolean shouldTransformProjectiles();
    int getTimeTicksForTransform();
    void setTransformProjectiles(boolean shouldTransform);
    void setTimeTicksForTransform(int ticksForTransform);
}
