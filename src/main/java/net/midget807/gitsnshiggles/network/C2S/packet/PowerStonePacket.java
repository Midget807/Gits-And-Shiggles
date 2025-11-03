package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.network.C2S.payload.PowerStonePayload;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class PowerStonePacket {
    public static final int MAX_RADIUS = 8;

    public static void receive(PowerStonePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = player.getWorld();

            Predicate<Entity> predicate = entity -> !entity.isSpectator() && entity.isAlive() && entity.canHit();

            List<Entity> squareEntities = world.getEntitiesByClass(Entity.class, player.getBoundingBox().expand(8, 1, 8), entity -> {
                if (entity instanceof PlayerEntity playerEntity) {
                    return !playerEntity.getAbilities().creativeMode && predicate.test(entity);
                }
                return predicate.test(entity);
            });
            List<Entity> radiusEntities = List.of();
            squareEntities.forEach(entity -> {
                if (entity.distanceTo(player) <= MAX_RADIUS) {
                    radiusEntities.add(entity);
                }
            });
            radiusEntities.forEach(entity -> {
                entity.damage(player.getDamageSources().magic(), 3);
                knockbackEntity(entity, player);
            });
            player.sendMessage(Text.literal("No Entities PS: " + radiusEntities.size()));
        });
    }

    private static void knockbackEntity(Entity entity, ServerPlayerEntity player) {
        Vec3d kbVec = entity.getPos().subtract(player.getPos());
        double length = kbVec.lengthSquared();
        kbVec = kbVec.normalize().multiply(length);
        entity.setVelocityClient(kbVec.getX(), kbVec.getY() < 0 ? -0.1 : 0.1, kbVec.getZ());//todo check for threaded anvil bullshit
    }
}
