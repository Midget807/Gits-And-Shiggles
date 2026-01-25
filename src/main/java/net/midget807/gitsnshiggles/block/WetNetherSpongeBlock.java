package net.midget807.gitsnshiggles.block;

import com.mojang.serialization.MapCodec;
import net.midget807.gitsnshiggles.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class WetNetherSpongeBlock extends Block {
    private static final Direction[] DIRECTIONS = Direction.values();
    public static final MapCodec<WetNetherSpongeBlock> CODEC = createCodec(WetNetherSpongeBlock::new);

    @Override
    protected MapCodec<WetNetherSpongeBlock> getCodec() {
        return CODEC;
    }

    public WetNetherSpongeBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.getDimension().ultrawarm()) {
            for (Direction direction : DIRECTIONS) {
                if (world.getBlockState(pos.offset(direction)).isIn(BlockTags.ICE)) {
                    world.setBlockState(pos, ModBlocks.NETHER_SPONGE.getDefaultState(), Block.NOTIFY_ALL);
                    world.syncWorldEvent(WorldEvents.WET_SPONGE_DRIES_OUT, pos, 0);
                    world.playSound(null, pos, SoundEvents.BLOCK_WET_SPONGE_DRIES, SoundCategory.BLOCKS, 1.0F, (1.0F + world.getRandom().nextFloat() * 0.2F) * 0.7F);
                    break;
                }
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = Direction.random(random);
        if (direction != Direction.UP) {
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            if (!state.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                double d = pos.getX();
                double e = pos.getY();
                double f = pos.getZ();
                if (direction == Direction.DOWN) {
                    e -= 0.05;
                    d += random.nextDouble();
                    f += random.nextDouble();
                } else {
                    e += random.nextDouble() * 0.8;
                    if (direction.getAxis() == Direction.Axis.X) {
                        f += random.nextDouble();
                        if (direction == Direction.EAST) {
                            d++;
                        } else {
                            d += 0.05;
                        }
                    } else {
                        d += random.nextDouble();
                        if (direction == Direction.SOUTH) {
                            f++;
                        } else {
                            f += 0.05;
                        }
                    }
                }

                world.addParticle(ParticleTypes.DRIPPING_LAVA, d, e, f, 0.0, 0.0, 0.0);
            }
        }
    }

}
