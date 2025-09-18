package net.midget807.gitsnshiggles.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;

public class ModColorUtil {
    public static final int FUCKASS_COLOR_CONSTANT = 16777216;

    public static int getLightsaberOverlayColor(ItemStack stack, int defaultColor) {
        DyedColorComponent dyedColorComponent = stack.get(DataComponentTypes.DYED_COLOR);
        return dyedColorComponent != null ? ColorHelper.Argb.withAlpha(25, dyedColorComponent.rgb()) : ColorHelper.Argb.withAlpha(25, defaultColor);
    }
}
