package top.yuhh.renewables.mixin;

import com.sun.jna.platform.win32.OaIdl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.yuhh.renewables.util.ModTags;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneMixin {
    @Shadow
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;

    @Shadow
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;

    @Shadow
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);


    /**
     * @author
     * @reason
     */
    @Overwrite
    private static Optional<PointedDripstoneBlock.FluidInfo> getFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
        return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map(p_221876_ -> {
//      For some reason, the original code has a .above() here and it doesn't work, even in vanilla...
            BlockPos blockpos = p_221876_;
            BlockState blockstate = level.getBlockState(blockpos);
            Fluid fluid;
            if (blockstate.is(Blocks.MUD) && !level.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = level.getFluidState(blockpos.above()).getType();
            }
            return new PointedDripstoneBlock.FluidInfo(blockpos, fluid, blockstate);
        });
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxIterations) {
        Direction direction = state.getValue(TIP_DIRECTION);
        BiPredicate<BlockPos, BlockState> bipredicate = (p_202015_, p_202016_) -> p_202016_.is(Blocks.POINTED_DRIPSTONE)
                && p_202016_.getValue(TIP_DIRECTION) == direction;
        return findBlockVertical(
                level, pos, direction.getOpposite().getAxisDirection(), bipredicate, p_154245_ -> !p_154245_.is(Blocks.POINTED_DRIPSTONE), maxIterations
        );
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static boolean isStalactite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.DOWN);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static boolean isPointedDripstoneWithDirection(BlockState state, Direction dir) {
        return state.is(Blocks.POINTED_DRIPSTONE) && state.getValue(TIP_DIRECTION) == dir;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static boolean isTip(BlockState state, boolean isTipMerge) {
        if (!state.is(Blocks.POINTED_DRIPSTONE)) {
            return false;
        } else {
            DripstoneThickness dripstonethickness = state.getValue(THICKNESS);
            return dripstonethickness == DripstoneThickness.TIP || isTipMerge && dripstonethickness == DripstoneThickness.TIP_MERGE;
        }
    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    private static Optional<BlockPos> findBlockVertical(
            LevelAccessor level,
            BlockPos pos,
            Direction.AxisDirection axis,
            BiPredicate<BlockPos, BlockState> positionalStatePredicate,
            Predicate<BlockState> statePredicate,
            int maxIterations
    ) {
        Direction direction = Direction.get(axis, Direction.Axis.Y);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        for (int i = 1; i < maxIterations; i++) {
            blockpos$mutableblockpos.move(direction);
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (statePredicate.test(blockstate)) {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }

            if (level.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !positionalStatePredicate.test(blockpos$mutableblockpos, blockstate)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxIterations, boolean isTipMerge) {
        if (isTip(state, isTipMerge)) {
            return pos;
        } else {
            Direction direction = state.getValue(TIP_DIRECTION);
            BiPredicate<BlockPos, BlockState> bipredicate = (p_202023_, p_202024_) -> p_202024_.is(Blocks.POINTED_DRIPSTONE)
                    && p_202024_.getValue(TIP_DIRECTION) == direction;
            return findBlockVertical(level, pos, direction.getAxisDirection(), bipredicate, p_154168_ -> isTip(p_154168_, isTipMerge), maxIterations)
                    .orElse(null);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isSolidRender(level, pos)) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelshape = state.getCollisionShape(level, pos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static boolean isValidPointedDripstonePlacement(LevelReader level, BlockPos pos, Direction dir) {
        BlockPos blockpos = pos.relative(dir.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, dir) || isPointedDripstoneWithDirection(blockstate, dir);
    }

    @Unique private static void growCalciteBelow(ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        for (int i = 0; i < 10; i++) {
            blockpos$mutableblockpos.move(Direction.DOWN);
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (!canDripThrough(level, blockpos$mutableblockpos, blockstate)) {
                if (level.getBlockState(blockpos$mutableblockpos.above()).isAir()) {
                    level.setBlockAndUpdate(blockpos$mutableblockpos.above(), Blocks.CALCITE.defaultBlockState());
                }
                return;
            }
        }
    }

    @Inject(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;findFillableCauldronBelowStalactiteTip(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/Fluid;)Lnet/minecraft/core/BlockPos;", shift = At.Shift.BEFORE), cancellable = true)
    private static void onfluid(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo ci) {
        Optional<PointedDripstoneBlock.FluidInfo> optional = getFluidAboveStalactite(level, pos, state);
        Fluid fluid = optional.get().fluid();
        BlockPos blockpos1 = findTip(state, level, pos, 11, false);
        if (optional.get().sourceState().is(ModTags.Blocks.DEAD_CORAL_BLOCKS) && fluid == Fluids.WATER) {
            if (blockpos1 != null) {
                growCalciteBelow(level, blockpos1);
            }
        }
    }
}