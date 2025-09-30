package net.midget807.gitsnshiggles.network.C2S.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.entity.RailgunBulletEntity;
import net.midget807.gitsnshiggles.item.RailgunItem;
import net.midget807.gitsnshiggles.network.C2S.payload.RailgunShootPayload;
import net.midget807.gitsnshiggles.registry.ModDamages;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.RailgunScalar;
import net.midget807.gitsnshiggles.util.inject.RailgunLoading;
import net.midget807.gitsnshiggles.util.inject.RailgunRecoil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RailgunShootPacket {
    public static void receive(RailgunShootPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            World world = context.player().getWorld();
            ItemStack railgunStack = payload.railgunStack();
            ItemStack projectile = ((RailgunLoading)player).getRailgunProjectile(railgunStack);
            float clientPitch = payload.pitch();
            float clientYaw = payload.yaw();
            int power = RailgunItem.PROJECTILE_POWERS.get(projectile.getItem());

            if (!projectile.isEmpty() && !railgunStack.isEmpty()) {
                /*if (projectile.isOf(Items.IRON_NUGGET)) {
                    shoot(clientPitch, clientYaw, power, player, projectile, world);
                } else {
                    raycast(clientPitch, clientYaw, player, world, projectile, power);
                }*/
                raycast(clientPitch, clientYaw, player, world, projectile, power);
                ((RailgunRecoil)player).setRailgunRecoil(true);
            }
        });
    }

    private static void raycast(float clientPitch, float clientYaw, ServerPlayerEntity player, World world, ItemStack projectile, int power) {
        Vec3d start = player.getEyePos();
        Vec3d direction = player.getRotationVector(clientPitch, clientYaw).normalize();
        double maxDistance = 512 + RailgunScalar.getScalar(power);
        Vec3d end = start.add(direction.multiply(maxDistance));

        HitResult hitResult = player.raycast(maxDistance, 0, false);
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            player.getWorld().createExplosion(
                    null,
                    null,
                    new AdvancedExplosionBehavior(false, true, Optional.empty(), Optional.empty()),
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ(),
                    Math.min(2.0f + (float) RailgunScalar.getScalar(power), 10.0f),
                    false,
                    World.ExplosionSourceType.TRIGGER,
                    ParticleTypes.EXPLOSION,
                    ParticleTypes.EXPLOSION_EMITTER,
                    SoundEvents.ENTITY_GENERIC_EXPLODE
            );
        }

        List<Entity> entities = world.getOtherEntities(
                player,
                player.getBoundingBox().stretch(direction.multiply(maxDistance)).expand(1.0),
                entity -> entity instanceof LivingEntity livingEntity && entity.isAlive() && !livingEntity.isSpectator()
        );
        List<EntityHitResult> hits = new ArrayList<>();
        for (Entity entity : entities) {
            Box box = entity.getBoundingBox().expand(0.2);
            Optional<Vec3d> intersection = box.raycast(start, end);
            if (intersection.isPresent()) {
                double distance = start.squaredDistanceTo(intersection.get());
                hits.add(new EntityHitResult(entity, intersection.get()));
            }
        }


        hits.sort(Comparator.comparingDouble(hit -> start.squaredDistanceTo(hit.getPos())));
        for (EntityHitResult hit : hits) {
            Entity target = hit.getEntity();
            target.damage(ModDamages.create(world, ModDamages.RAILGUN, player), (float) 5.0f + (float) RailgunScalar.getScalar(power));
        }

        projectile.decrement(player.getAbilities().creativeMode ? 0 : 1);
        player.getItemCooldownManager().set(ModItems.RAILGUN, 40);
    }

    public static void shoot(float clientPitch, float clientYaw, int power, ServerPlayerEntity player, ItemStack projectile, World world) {
        RailgunBulletEntity railgunBulletEntity = new RailgunBulletEntity(player, world);
        railgunBulletEntity.setStack(projectile);
        railgunBulletEntity.setPower(power);
        Vec3d vec3d = player.getRotationVector(clientPitch, clientYaw);
        railgunBulletEntity.setPitch(clientPitch);
        railgunBulletEntity.setYaw(clientYaw);
        railgunBulletEntity.setPosition(railgunBulletEntity.getPos().add(vec3d.normalize()));
        railgunBulletEntity.setVelocity(player, clientPitch, clientYaw, 0.0f, (float) (2.0 + RailgunScalar.getScalar(power)), 0);
        railgunBulletEntity.velocityModified = true;
        railgunBulletEntity.velocityDirty = true;/*
        Vec3d recoilVec = player.getRotationVector().negate();
        player.setVelocity(recoilVec.normalize().multiply(RailgunScalar.getScalar(power) + 2.0));
        player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player.getId(), player.getVelocity()));
        player.velocityModified = true;*/
        world.spawnEntity(railgunBulletEntity);
        projectile.decrement(player.getAbilities().creativeMode ? 0 : 1);

    }
}
