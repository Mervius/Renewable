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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
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
            grow(level, pos, random, true);
        }
        grow(level, pos, random, touchinglava);

    }

    public static void grow(@NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random, boolean lava) {
        int minheight = level.getMinBuildHeight();
        int maxheight = level.getMaxBuildHeight();
        int height = pos.getY();
/*
    This essentially will resolve to Y = (991^((1/A-B)(X-B)) + 9) * (-1.25^(-X+B-0.5) + 1) where X is the block's height, A is the maximum build limit, and B is the minimum build limit.
    The clamp is to avoid any accidental overflows and negatives
    This should always return 1 at the minimum build height and 1000 at the maximum build heigh, regardless of what they are(as long as the maximum is greater than the minimum)
*/
        if (random.nextInt(clamp(floor(((pow(991, (double) (height - minheight) /(maxheight-minheight)) + 9) * (-pow(1.25,-height + minheight - 0.5) + 1))),1,1000)) == 0) {
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
                if (blocks.is(ModBlocks.LACED_GRAPHITE) && !blocks.getValue(BlockStateProperties.PERSISTENT)) {
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
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(blockstate1));
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
