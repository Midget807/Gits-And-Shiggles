package net.midget807.gitsnshiggles.util;

public class RailgunScalar {
    public static double getScalar(int power) {
        return Math.exp(0.015 * (double) power);
    }
}
