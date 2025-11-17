package top.yuhh.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;

public class BuddingDiamondBlock extends AmethystBlock {
    public static final MapCodec<BuddingDiamondBlock> CODEC = simpleCodec(BuddingDiamondBlock::new);

    public static final int GROWTH_CHANCE = 10;

    public MapCodec<BuddingDiamondBlock> codec() {
        return CODEC;
    }

    private static final Direction[] DIRECTIONS = Direction.values();


    public BuddingDiamondBlock(BlockBehaviour.Properties $$0) {
        super($$0);
    }

    protected void  randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
        if ($$3.nextInt(GROWTH_CHANCE) != 0)
            return;
        Direction $$4 = DIRECTIONS[$$3.nextInt(DIRECTIONS.length)];
        BlockPos $$5 = $$2.relative($$4);
        BlockState $$6 = $$1.getBlockState($$5);
        Block $$7 = null;
        if (canClusterGrowAtState($$6)) {
            $$7 = ModBlocks.SMALL_DIAMOND_BUD.get();
        } else if ($$6.is(ModBlocks.SMALL_DIAMOND_BUD) && $$6.getValue((Property) DiamondClusterBlock.FACING) == $$4) {
            $$7 = ModBlocks.MEDIUM_DIAMOND_BUD.get();
        } else if ($$6.is(ModBlocks.MEDIUM_DIAMOND_BUD) && $$6.getValue((Property)DiamondClusterBlock.FACING) == $$4) {
            $$7 = ModBlocks.LARGE_DIAMOND_BUD.get();
        } else if ($$6.is(ModBlocks.LARGE_DIAMOND_BUD) && $$6.getValue((Property)DiamondClusterBlock.FACING) == $$4) {
            $$7 = ModBlocks.DIAMOND_CLUSTER.get();
        }
        if ($$7 != null) {
            BlockState $$8 = (BlockState)((BlockState)$$7.defaultBlockState().setValue((Property)DiamondClusterBlock.FACING, (Comparable)$$4)).setValue((Property)DiamondClusterBlock.WATERLOGGED, Boolean.valueOf(($$6.getFluidState().getType() == Fluids.WATER)));
            $$1.setBlockAndUpdate($$5, $$8);
        }
    }
    public static boolean canClusterGrowAtState(BlockState $$0) {
        return ($$0.isAir() || ($$0.is(Blocks.WATER) && $$0.getFluidState().getAmount() == 8));
    }
}
