package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class ModKeyBindings {
    
    public static final KeyBinding summonElves = registerMain("key.gitsnshiggles.summonElves", InputUtil.UNKNOWN_KEY.getCode());

    public static final KeyBinding powerStone = registerMain("key.gitsnshiggles.powerStone", InputUtil.UNKNOWN_KEY.getCode());
    public static final KeyBinding spaceStone = registerMain("key.gitsnshiggles.spaceStone", InputUtil.UNKNOWN_KEY.getCode());
    public static final KeyBinding realityStone = registerMain("key.gitsnshiggles.realityStone", InputUtil.UNKNOWN_KEY.getCode());
    public static final KeyBinding soulStone = registerMain("key.gitsnshiggles.soulStone", InputUtil.UNKNOWN_KEY.getCode());
    public static final KeyBinding timeStone = registerMain("key.gitsnshiggles.timeStone", InputUtil.UNKNOWN_KEY.getCode());
    public static final KeyBinding mindStone = registerMain("key.gitsnshiggles.mindStone", InputUtil.UNKNOWN_KEY.getCode());

    private static KeyBinding registerKeyBinding(String keyTranslation, int keyCode, String categoryTranslation) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(keyTranslation, keyCode, categoryTranslation));
    }
    
    public static KeyBinding registerMain(String keyTranslation, int keyCode) {
        return registerKeyBinding(keyTranslation, keyCode, Categories.MAIN_CATEGORY);
    }

    public static void registerModKeyBindings() {
        GitsAndShigglesMain.LOGGER.info("(Client) Registering Mod Key Bindings");
    }

    public static class Categories {
        public static final String MAIN_CATEGORY = "key.gitsnshiggles.category.main";
    }
}
