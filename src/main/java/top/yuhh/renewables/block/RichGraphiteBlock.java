package top.yuhh.renewables.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.pow;
import static net.minecraft.util.Mth.*;


public class RichGraphiteBlock extends DropExperienceBlock {
    public static final MapCodec<RichGraphiteBlock> CODEC = RecordCodecBuilder.mapCodec(
            p_308822_ -> p_308822_.group(IntProvider.codec(0, 10).fieldOf("experience").forGetter(p_304879_ -> p_304879_.xpRange), propertiesCodec())
                    .apply(p_308822_, RichGraphiteBlock::new)
    );
    private final IntProvider xpRange;
//    public static final int GROWTH_CHANCE = 5;
    public static final int HORIZONTAL_CHANCE = 5;
    public static final int VERTICAL_CHANCE = 1;
    public static final int MAX_STEPS_VERTICAL = 2;
    public static final int MAX_STEPS_HORIZONTAL = 3;
    public static final int MAX_STEPS = 5;
    public static final int MAX_COUNT = 6;
    private static final Direction[] DIRECTIONS = Direction.values();

    @Override
    public @NotNull MapCodec<RichGraphiteBlock> codec() { return CODEC; }


    public RichGraphiteBlock(IntProvider xpRange, BlockBehaviour.Properties properties) {
        super(xpRange, properties);
        this.xpRange = xpRange;
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        boolean touchinglava = false;
        BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
//  Test if the block is adjacent to a lava source.
        for (Direction direction : DIRECTIONS) {
            mutableblockpos.setWithOffset(pos, direction);
            if (level.getBlockState(mutableblockpos).is(Blocks.LAVA) && level.getBlockState(mutableblockpos).getFluidState().isSource()) {
                touchinglava = true;
                break;
            }
        }
        if (touchinglava)  {
            grow(state, level, pos, random, touchinglava);
        }
        grow(state, level, pos, random, touchinglava);

    }

    public static void grow(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random, boolean lava) {
        int height = level.getMinBuildHeight();
        if (height > 0) height = 0;
/*
    This essentially will resolve to Y = 1.0244^(X+64) where X is the block's height in any world without a modified minimum build limit. But I didn't hardcode in +64 just in case.
    The clamp is to avoid any accidental overflows and negatives
*/
        if (random.nextInt(clamp(floor(pow(1.0244,pos.getY() - height)),1,10000) + 2) == 0) {
            Block block = null;
            BlockPos blockpos = pos;
            int step = 0;
            int verticalstep = 0;
            int horizontalstep = 0;
            int dirint;
            Direction direction;
            AABB box = new AABB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3);

            int[] counts = new int[2]; // counts[0] = LACED, counts[1] = RICH
            level.getBlockStates(box).forEach(blocks ->{
                if (blocks.is(ModBlocks.LACED_GRAPHITE)) {
                    counts[0]++;
                } else if (blocks.is(ModBlocks.RICH_GRAPHITE)) {
                    counts[1]++;
                }
                    });
            if (counts[0] < (MAX_COUNT * counts[1])) {
                while (step < MAX_STEPS) {
                    if (random.nextInt(HORIZONTAL_CHANCE + VERTICAL_CHANCE) < HORIZONTAL_CHANCE) {
                        dirint = horizontalIfPossible(horizontalstep, verticalstep, random, false);
                    } else {
                        dirint = verticalIfPossible(verticalstep, horizontalstep, random, false);
                    }
                    if (dirint != -1) {
                        direction = DIRECTIONS[dirint];
                        blockpos = blockpos.relative(direction);
                        if (dirint > 2) {
                            horizontalstep += 1;
                        } else {
                            verticalstep += 1;
                        }
                    } else {
                        break;
                    }
                    BlockState blockstate = level.getBlockState(blockpos);
                    if (blockstate.is(ModBlocks.GRAPHITE_BLOCK)) {
                        if (random.nextInt(lava ? 1000 : 5000) != 0) {
                            block = ModBlocks.LACED_GRAPHITE.get();
                        } else {
                            block = ModBlocks.RICH_GRAPHITE.get();
                        }
                        break;
                    } else if (blockstate.is(ModBlocks.LACED_GRAPHITE) || blockstate.is(ModBlocks.RICH_GRAPHITE)) {
                        step += 1;
                    } else {
                        break;
                    }

                }

                if (block != null) {
                    BlockState blockstate1 = block.defaultBlockState();
                    level.setBlockAndUpdate(blockpos, blockstate1);
                }
            }
        }
    }

    public static int horizontalIfPossible(int stephorizontal, int stepvertical, RandomSource random, boolean calledfromtest) {
        if (!calledfromtest) {
            if (stephorizontal < MAX_STEPS_HORIZONTAL) {
                return random.nextIntBetweenInclusive(2,5);
            } else {
                return verticalIfPossible(stepvertical, stephorizontal, random, true);
            }
        } else {
            return -1;
        }
    }

    public static int verticalIfPossible(int stepvertical, int stephorizontal, RandomSource random, boolean calledfromtest) {
        if (!calledfromtest) {
            if (stepvertical < MAX_STEPS_VERTICAL) {
                return random.nextInt(2);
            } else {
                return horizontalIfPossible(stephorizontal, stepvertical, random, true);
            }
        } else {
            return -1;
        }
    }
}
