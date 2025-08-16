package net.midget807.gitsnshiggles.event.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.midget807.gitsnshiggles.item.RailgunItem;
import net.midget807.gitsnshiggles.network.C2S.payload.RailgunShootPayload;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.midget807.gitsnshiggles.util.inject.RailgunLoading;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ClientPreAttackListener {
    public static void execute() {
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            return railgunRecoil(client, player);

        });
    }
    private static boolean railgunRecoil(MinecraftClient client, ClientPlayerEntity player) {
        ItemStack railgunStack = player.getStackInHand(Hand.MAIN_HAND);
        if (railgunStack.isOf(ModItems.RAILGUN)) {

            ItemStack projectile = ((RailgunLoading)player).getRailgunProjectile(railgunStack);
            int power = RailgunItem.PROJECTILE_POWERS.get(projectile.getItem());
            /*Vec3d recoilVec = player.getRotationVector().negate().normalize();
            player.setVelocity(recoilVec.multiply(RailgunScalar.getScalar(power) + 2.0f));
            player.velocityModified = true;*/
            ClientPlayNetworking.send(new RailgunShootPayload(player.getStackInHand(Hand.MAIN_HAND), player.getPitch(), player.getYaw()));
            return true;
        }
        return false;
    }
}
