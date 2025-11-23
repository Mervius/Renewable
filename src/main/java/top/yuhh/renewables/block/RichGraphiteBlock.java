package top.yuhh.renewables.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.pow;
import static net.minecraft.util.Mth.*;


public class RichGraphiteBlock extends Block {
    public static final MapCodec<RichGraphiteBlock> CODEC = simpleCodec(RichGraphiteBlock::new);
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


    public RichGraphiteBlock(Properties properties) { super(properties); }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        boolean touchinglava = false;
        BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
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
        if (random.nextInt(clamp(floor(pow(1.0244, pos.getY() + clamp(abs(level.getMinBuildHeight()),Double.NEGATIVE_INFINITY,0))),0,10000) + 2) == 0) {
            Block block = null;
            BlockPos blockpos = pos;
            int step = 0;
            int verticalstep = 0;
            int horizontalstep = 0;
            int dirint;
            Direction direction;
            AABB box = new AABB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3);
            long counts = level.getBlockStates(box)
                    .filter(blockstate -> blockstate.getBlock() == ModBlocks.LACED_GRAPHITE.get())
                    .count();
            long extra = level.getBlockStates(box)
                    .filter(blockstate -> blockstate.getBlock() == ModBlocks.RICH_GRAPHITE.get())
                    .count();
            if (counts < (MAX_COUNT * extra)) {
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
