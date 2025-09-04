package net.midget807.gitsnshiggles.event.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.midget807.gitsnshiggles.util.inject.WizardGamba;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class HudRenderListener {

    public static void execute() {
        /*HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayer = client.player;
            int centreX = drawContext.getScaledWindowWidth() / 2;
            int centreY = drawContext.getScaledWindowHeight() / 2;
            if (clientPlayer != null) {
                double gambaAnimationLerpAmount = Math.clamp(Math.exp(0.02 * renderTickCounter.getTickDelta(false) + 1.0), 0.0d, 150.0d);
                ((WizardGamba)clientPlayer).setGambingAnimationTicks(((WizardGamba)clientPlayer).getGambingAnimationTicks() + 1);
                if (renderTickCounter.getTickDelta(false) == 150) {
                    drawContext.drawTexture(ModTextureIds.WIZARD_GAMBA, centreX, centreY, 0, (int) (0 + renderTickCounter.getTickDelta(false)), 32, (int) (32 + renderTickCounter.getTickDelta(false)));
                }
                if (((WizardGamba) clientPlayer).getGambingAnimationTicks() % gambaAnimationLerpAmount == 0 && renderTickCounter.getTickDelta(false) <= 200) {
                    drawContext.drawTexture(ModTextureIds.WIZARD_GAMBA, centreX, centreY, 0, 0, 32, 32);
                }
            }
        });*/
    }
}
