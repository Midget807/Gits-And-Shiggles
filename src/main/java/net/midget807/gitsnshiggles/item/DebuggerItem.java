package net.midget807.gitsnshiggles.item;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaEntity;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaManager;
import net.midget807.gitsnshiggles.network.C2S.payload.ShuffleInventoryPayload;
import net.midget807.gitsnshiggles.network.S2C.payload.RenderHorsePayload;
import net.midget807.gitsnshiggles.registry.ModParticles;
import net.midget807.gitsnshiggles.util.InfinityStoneUtil;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.midget807.gitsnshiggles.util.ModParticleUtil;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class DebuggerItem extends Item {

    public DebuggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {

        }
        if (world.isClient) {
            /*SchizophreniaManager.add(new SchizophreniaEntity(
                    EntityType.HORSE,
                    player.getPos(),
                    (float) world.random.nextBetween(-180, 180),
                    200
            ));*/

            ModParticleUtil.addCollapsingSphereOfParticles(player.getWorld(), player.getEyePos(), 50, ModUtil.Speed.getParticleSpeedForRadius(40, 1.5), ModParticles.SPACE_OUTLINE, 1.5);
            ModParticleUtil.addCollapsingSphereOfParticles(player.getWorld(), player.getEyePos(), 50, ModUtil.Speed.getParticleSpeedForRadius(30, 1.0), ModParticles.SPACE, 1.0);
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player) {

        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
