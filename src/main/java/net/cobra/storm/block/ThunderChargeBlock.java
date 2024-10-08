package net.cobra.storm.block;

import net.cobra.storm.entity.ModEntityType;
import net.cobra.storm.entity.ThunderStalkerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.TickPriority;

public class ThunderChargeBlock extends Block {
    private static final double RADIUS_SQ = 15.0 * 15.0;
    private boolean hasSpawned = false;
    private static final int CHECK_INTERVAL = 20;// Interval in ticks (1 second)

    public ThunderChargeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        scheduleTick(world, pos);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if (world.isClient) {
            return;
        }
        scheduleTick(world, pos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        checkAndSpawnEntity(world, pos);
        scheduleTick(world, pos); // Reschedule the tick
    }

    private void scheduleTick(World world, BlockPos pos) {
        OrderedTick<Block> orderedTick = new OrderedTick<>(this, pos, world.getTime() + CHECK_INTERVAL, TickPriority.NORMAL, 0l);
        world.getBlockTickScheduler().scheduleTick(orderedTick);
    }

    public void checkAndSpawnEntity(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (world.isReceivingRedstonePower(pos) && state.isOf(this)) {
            boolean isPlayerWithinRange = world.getPlayers().stream()
                    .anyMatch(player -> player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= RADIUS_SQ);

            if (isPlayerWithinRange) {
                spawnThunderStalkerEntity(world, pos);
                hasSpawned = true;
            }
        }else if (!world.isReceivingRedstonePower(pos)) {
            hasSpawned = false;
        }

    }

    private void spawnThunderStalkerEntity(World world, BlockPos pos) {
        ThunderStalkerEntity entity = ModEntityType.THUNDER_ENTITY.create(world);
        if (entity != null) {
            entity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entity);
        }
    }
}
