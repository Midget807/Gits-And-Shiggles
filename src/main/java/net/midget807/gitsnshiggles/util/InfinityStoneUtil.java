package net.midget807.gitsnshiggles.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.cca.InfinityGauntletComponent;
import net.midget807.gitsnshiggles.datagen.ModBlockTagProvider;
import net.midget807.gitsnshiggles.item.InfinityGauntletItem;
import net.midget807.gitsnshiggles.network.C2S.packet.SpaceStonePacket;
import net.midget807.gitsnshiggles.network.C2S.payload.MindStonePayload;
import net.midget807.gitsnshiggles.network.C2S.payload.SpaceStonePayload;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.inject.InfinityStoneCooldown;
import net.midget807.gitsnshiggles.util.inject.MindStoneInvert;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class InfinityStoneUtil {
    public static final int POWER_STONE_CD = 30 * 20; // 30 sec
    public static final int SPACE_STONE_CD = 20 * 20; // 20 sec
    public static final int REALITY_STONE_CD = 10 * 60 * 20; // 10 min
    public static final int SOUL_STONE_CD = 3 * 60 * 60 * 20; // 3 hour
    public static final int TIME_STONE_CD = 30 * 60 * 20; // 30 min
    public static final int MIND_STONE_CD = 10 * 60 * 20; // 10 min
    public static final int TIMER_REALITY_STONE = 10 * 20; // 10 sec
    public static final int TIMER_TIME_STONE = 3 * 20; // 3 sec
    public static final int TIMER_MIND_STONE = 5 * 20; // 5 sec

    public static final String POWER_STONE_CD_KEY = "PowerStoneCD";
    public static final String SPACE_STONE_CD_KEY = "SpaceStoneCD";
    public static final String REALITY_STONE_CD_KEY = "RealityStoneCD";
    public static final String SOUL_STONE_CD_KEY = "SoulStoneCD";
    public static final String TIME_STONE_CD_KEY = "TimeStoneCD";
    public static final String MIND_STONE_CD_KEY = "MindStoneCD";
    public static final String REALITY_STONE_TIMER_KEY = "RealityStoneTimer";
    public static final String TIME_STONE_TIMER_KEY = "TimeStoneTimer";
    public static final String TIME_STONE_BOOL_KEY = "TimeStoneBoolean";
    public static final String MIND_STONE_TIMER_KEY = "MindStoneTimer";

    @Environment(EnvType.CLIENT)
    public static void getSpaceStoneTeleport(ClientPlayerEntity player) {
        HitResult hitResult = player.raycast(SpaceStonePacket.MAX_DISTANCE, 0.0f, false);
        if (hitResult.getType() == HitResult.Type.MISS) {
            player.getItemCooldownManager().set(ModItems.INFINITY_GAUNTLET, 10);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos tpTarget = null;
            BlockPos hitPos = blockHitResult.getBlockPos();
            Direction hitSide = blockHitResult.getSide();
            BlockPos sidePos = hitPos.offset(hitSide);
            if (hitSide != Direction.DOWN && hitSide != Direction.UP) {
                if (stateIsSafe(player, sidePos)) {
                    if (!stateIsSafe(player, sidePos.offset(Direction.DOWN)) && stateIsSafe(player, sidePos.offset(Direction.UP))) {
                        tpTarget = sidePos;
                    }
                }
            } else if (hitSide == Direction.UP) {
                if (stateIsSafe(player, sidePos) && stateIsSafe(player, sidePos.offset(Direction.UP))) {
                    tpTarget = sidePos;
                }
            } else { /*hitSide == DOWN*/
                if (stateIsSafe(player, sidePos) && stateIsSafe(player, sidePos.offset(Direction.DOWN)) && !stateIsSafe(player, sidePos.offset(Direction.DOWN, 2))) {
                    tpTarget = sidePos.offset(Direction.DOWN);
                }
            }

            if (tpTarget != null) {
                ClientPlayNetworking.send(new SpaceStonePayload(tpTarget));
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Environment(EnvType.CLIENT)
    public static boolean stateIsSafe(ClientPlayerEntity player, BlockPos blockPos) {
        BlockState state = player.getWorld().getBlockState(blockPos);
        return state.isIn(ModBlockTagProvider.SPACE_STONE_SAFE) || !state.isSolid();
    }

    public static int getCDTextureRatio(int timeTicks, int cooldownTime, int textureHeight) {
        double time = (double) timeTicks / cooldownTime;
        return (int) Math.floor(time * textureHeight);
    }
    public static long getCDTextureRatio(long timeTicks, long cooldownTime, int textureHeight) {
        double time = (double) timeTicks / cooldownTime;
        return (long) Math.floor(time * textureHeight);
    }

    @Environment(EnvType.CLIENT)
    public static void syncClientMindStoneStatus(ClientPlayerEntity player) {
        World world = player.getWorld();
        List<Entity> squareEntities = world.getOtherEntities(player, player.getBoundingBox().expand(20, 10, 20));
        ArrayList<PlayerEntity> squarePlayerEntities = new ArrayList<>(List.of());
        for (Entity entity : squareEntities) {
            if (entity instanceof ClientPlayerEntity) {
                squarePlayerEntities.add(player);
            }
        }
        ArrayList<PlayerEntity> sphereEntities = new ArrayList<>(List.of());
        for (PlayerEntity entity : squarePlayerEntities) {
            if (player.getPos().squaredDistanceTo(entity.getPos()) <= 100) {
                sphereEntities.add(entity);
            }
        }
        sphereEntities.forEach(entity -> ((MindStoneInvert)entity).setTimeTicksInverted(TIMER_MIND_STONE));
        sphereEntities.forEach(entity -> ClientPlayNetworking.send(new MindStonePayload(TIMER_MIND_STONE)));
    }

    public static void setStoneCooldown(LivingEntity user, Stones stone) {
        InfinityGauntletComponent infinityGauntletComponent = InfinityGauntletComponent.get(user);
        switch (stone) {
            case POWER -> infinityGauntletComponent.setHexValue1(POWER_STONE_CD);
            case SPACE -> infinityGauntletComponent.setHexValue2(SPACE_STONE_CD);
            case REALITY -> infinityGauntletComponent.setHexValue3(REALITY_STONE_CD);
            case SOUL -> infinityGauntletComponent.setHexValue4(SOUL_STONE_CD);
            case TIME -> infinityGauntletComponent.setHexValue5(TIME_STONE_CD);
            case MIND -> infinityGauntletComponent.setHexValue6(MIND_STONE_CD);
        }
    }

    public static DustParticleEffect getDustParticleForStone(Stones stone, float scale) {
        return switch (stone) {
            case POWER -> new DustParticleEffect(ModParticleUtil.colorToVec3f(0x872DF4), scale);
            case SPACE -> new DustParticleEffect(ModParticleUtil.colorToVec3f(0x3238FB), scale);
            case REALITY -> new DustParticleEffect(ModParticleUtil.colorToVec3f(0xF72C23), scale);
            case SOUL -> new DustParticleEffect(ModParticleUtil.colorToVec3f(0xF78A23), scale);
            case TIME -> new DustParticleEffect(ModParticleUtil.colorToVec3f(0x03C500), scale);
            case MIND -> new DustParticleEffect(ModParticleUtil.colorToVec3f(0xEEDF1C), scale);
        };
    }

    public static enum Stones {
        POWER,
        SPACE,
        REALITY,
        SOUL,
        TIME,
        MIND
    }

}
