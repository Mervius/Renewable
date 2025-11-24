package top.yuhh.renewables.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.pow;
import static net.minecraft.util.Mth.*;


public class LacedGraphiteBlock extends DropExperienceBlock {
    public static final MapCodec<LacedGraphiteBlock> CODEC = RecordCodecBuilder.mapCodec(
            p_308822_ -> p_308822_.group(IntProvider.codec(0, 10).fieldOf("experience").forGetter(p_304879_ -> p_304879_.xpRange), propertiesCodec())
                    .apply(p_308822_, LacedGraphiteBlock::new)
    );
    private final IntProvider xpRange;

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;


    private static final Direction[] DIRECTIONS = Direction.values();

    @Override
    public @NotNull MapCodec<LacedGraphiteBlock> codec() { return CODEC; }


    public LacedGraphiteBlock(IntProvider xpRange, BlockBehaviour.Properties properties) {
        super(xpRange, properties);
        this.xpRange = xpRange;
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(PERSISTENT, Boolean.FALSE)
        );
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
//  Skip if placed by a player.
        if (!state.getValue(PERSISTENT)) {
            boolean touchinglava = false;
            BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
//      Test if the block is adjacent to a lava source.
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
/*
    This essentially will resolve to Y = 100000/(((X+64)^2)+1) where X is the block's height in any world without a modified minimum build limit. But I didn't hardcode in +64 just in case.
    The +1 is to avoid the divide by zero value that occurs at x=-64
    The chance is halved if it is adjacent to a lava source.
*/
        if (random.nextInt(floor(100000.0/pow((double) pos.getY() - (double)height + 1, 2.0)) * chance) == 0) {
            level.setBlockAndUpdate(pos, ModBlocks.GRAPHITE_BLOCK.get().defaultBlockState());
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
