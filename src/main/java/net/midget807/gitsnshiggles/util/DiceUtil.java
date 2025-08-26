package net.midget807.gitsnshiggles.util;

import java.util.UUID;

public class DiceUtil {
    private static UUID unluckyPersonUuid;
    private static UUID veryUnluckyPersonUuid;

    public static UUID getUnluckyPersonUuid() {
        return unluckyPersonUuid;
    }
    public void setUnluckyPersonUuid(UUID unluckyPersonUuid) {
        DiceUtil.unluckyPersonUuid = unluckyPersonUuid;
    }

    public static UUID getVeryUnluckyPersonUuid() {
        return veryUnluckyPersonUuid;
    }
    public static void setVeryUnluckyPersonUuid(UUID veryUnluckyPersonUuid) {
        DiceUtil.veryUnluckyPersonUuid = veryUnluckyPersonUuid;
    }
}
