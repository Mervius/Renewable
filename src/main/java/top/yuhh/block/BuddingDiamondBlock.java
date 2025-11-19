//package top.yuhh.block;
//
//import com.mojang.serialization.MapCodec;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.block.*;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.Fluids;
//import org.jetbrains.annotations.NotNull;
//
//import static net.minecraft.util.Mth.*;
//
//public class BuddingDiamondBlock extends AmethystBlock {
//    public static final MapCodec<BuddingDiamondBlock> CODEC = simpleCodec(BuddingDiamondBlock::new);
//    public static final int GROWTH_CHANCE = 10;
//    private static final Direction[] DIRECTIONS = Direction.values();
//
//    @Override
//    public @NotNull MapCodec<BuddingDiamondBlock> codec() { return CODEC; }
//
//
//    public BuddingDiamondBlock(BlockBehaviour.Properties properties) { super(properties); }
//
//    @Override
//    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random) {
//        if (random.nextInt(5) == 0) {
//            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
//            BlockPos blockpos = pos.relative(direction);
//            BlockState blockstate = level.getBlockState(blockpos);
//            Block block = null;
//            if (canClusterGrowAtState(blockstate)) {
//                block = ModBlocks.SMALL_DIAMOND_BUD.get();
//            } else if (blockstate.is(ModBlocks.SMALL_DIAMOND_BUD) && blockstate.getValue(DiamondClusterBlock.FACING) == direction) {
//                block = ModBlocks.MEDIUM_DIAMOND_BUD.get();
//            } else if (blockstate.is(ModBlocks.MEDIUM_DIAMOND_BUD) && blockstate.getValue(DiamondClusterBlock.FACING) == direction) {
//                block = ModBlocks.MEDIUM_DIAMOND_BUD.get();
//            } else if (blockstate.is(ModBlocks.MEDIUM_DIAMOND_BUD) && blockstate.getValue(DiamondClusterBlock.FACING) == direction) {
//                block = ModBlocks.DIAMOND_CLUSTER.get();
//            }
//
//            if (block != null) {
//                BlockState blockstate1 = block.defaultBlockState()
//                        .setValue(DiamondClusterBlock.FACING, direction)
//                        .setValue(DiamondClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
//                level.setBlockAndUpdate(blockpos, blockstate1);
//            }
//        }
//    }
//    public static boolean canClusterGrowAtState(BlockState state) {
//        return (state.isAir() || (state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8));
//    }
//}
