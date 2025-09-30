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
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
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
        ItemStack projectile = ((RailgunLoading)player).getRailgunProjectile(railgunStack);
        if (railgunStack.isOf(ModItems.RAILGUN) && !player.getItemCooldownManager().isCoolingDown(ModItems.RAILGUN) && projectile != null && !projectile.isEmpty()) {

            int power = RailgunItem.PROJECTILE_POWERS.get(projectile.getItem());

            /*Smoke Particle*/
            Vec3d start = player.getEyePos();
            double maxDistance = 512 + RailgunScalar.getScalar(power);
            Vec3d direction = player.getRotationVector(player.getPitch(), player.getYaw()).normalize();
            HitResult hitResult = player.raycast(maxDistance, 0, false);
            double particleDistance = maxDistance;
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                particleDistance = player.getPos().distanceTo(blockHitResult.getPos());
            }

            Vec3d current = start;
            for (double d = 0; d < particleDistance; d += 2) {
                current = start.add(direction.multiply(d));
                assert MinecraftClient.getInstance().player != null;
                player.getWorld().addParticle(
                    ParticleTypes.SMOKE,
                    current.x,
                    current.y,
                    current.z,
                    0,
                    0,
                    0
                );
            }
            /*Moves Player*/
            Vec3d recoilVec = player.getRotationVector().negate().normalize();
            player.setVelocity(recoilVec.multiply(RailgunScalar.getScalar(power) + 2.0f));
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()));
            ClientPlayNetworking.send(new RailgunShootPayload(player.getStackInHand(Hand.MAIN_HAND), player.getPitch(), player.getYaw()));
            return true;
        }
        return false;
    }
}
