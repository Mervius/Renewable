package top.yuhh.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.pow;
import static net.minecraft.util.Mth.*;


public class LacedGraphiteBlock extends Block {
    public static final MapCodec<LacedGraphiteBlock> CODEC = simpleCodec(LacedGraphiteBlock::new);

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;


    private static final Direction[] DIRECTIONS = Direction.values();

    @Override
    public @NotNull MapCodec<LacedGraphiteBlock> codec() { return CODEC; }


    public LacedGraphiteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(PERSISTENT, Boolean.FALSE)
        );
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(PERSISTENT)) {
            boolean touchinglava = false;
            BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
            for (Direction direction : DIRECTIONS) {
                mutableblockpos.setWithOffset(pos, direction);
                if (level.getBlockState(mutableblockpos).is(Blocks.LAVA) && level.getBlockState(mutableblockpos).getFluidState().isSource()) {
                    touchinglava = true;
                    break;
                }
            }
            revert(state, level, pos, random, touchinglava);
        }
    }
    public  static void revert(@NotNull BlockState ignoredState, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random, boolean lava) {
        int height = level.getMinBuildHeight();
        if (height > 0) height = 0;
        int chance = 1;
        if (lava) chance = 2;
        if (random.nextInt(floor(100000.0/pow((double) pos.getY() - (double)height + 1, 2.0)) * chance) == 0) {
            level.setBlockAndUpdate(pos, ModBlocks.GRAPHITE.get().defaultBlockState());
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(PERSISTENT, Boolean.TRUE);
    }

}
