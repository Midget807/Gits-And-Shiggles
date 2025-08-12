package net.midget807.gitsnshiggles.item.client;

import net.midget807.gitsnshiggles.component.FlamethrowerContentsComponent;
import net.minecraft.item.tooltip.TooltipData;

public record FlamethrowerTooltipData(FlamethrowerContentsComponent component) implements TooltipData {
}
