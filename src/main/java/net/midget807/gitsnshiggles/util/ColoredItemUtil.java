package net.midget807.gitsnshiggles.util;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Optional;

public class ColoredItemUtil {
    public static enum Colors {
        WHITE("white"),
        ORANGE("orange"),
        MAGENTA("magenta"),
        LIGHT_BLUE("light_blue"),
        YELLOW("yellow"),
        LIME("lime"),
        PINK("pink"),
        GRAY("gray"),
        LIGHT_GRAY("light_gray"),
        CYAN("cyan"),
        PURPLE("purple"),
        BLUE("blue"),
        BROWN("brown"),
        GREEN("green"),
        RED("red"),
        BLACK("black");

        private final String color;

        private Colors(final String color) {
            this.color = color;
        }
        public String getColor() {
            return this.color;
        }
    }

    public static Item getTronDiscByColor(Colors color) {
        Optional<Item> item = Registries.ITEM.getOrEmpty(GitsAndShigglesMain.id("tron_disc_" + color.getColor()));
        return item.orElse(ModItems.TRON_DISC_WHITE);
    }

    public static Item getLightsaberByColor(Colors color) {
        Optional<Item> item = Registries.ITEM.getOrEmpty(GitsAndShigglesMain.id("lightsaber_" + color.getColor()));
        return item.orElse(null);
    }
}
