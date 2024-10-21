package net.cobra.storm.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PurpleHeartLeavesBlock extends LeavesBlock {
    protected static final MapCodec<PurpleHeartLeavesBlock> CODEC = PurpleHeartLeavesBlock.createCodec(PurpleHeartLeavesBlock::new);
    public PurpleHeartLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<PurpleHeartLeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(10) == 0) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (!isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
                ParticleUtil.spawnParticle(world, pos, random, ParticleTypes.ASH);
            }
        }
    }
}
