package net.midget807.gitsnshiggles.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;

public class AFKCuzImCodingItem extends WrittenBookItem {
    public AFKCuzImCodingItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
