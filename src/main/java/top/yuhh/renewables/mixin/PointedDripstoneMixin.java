package top.yuhh.renewables.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.yuhh.renewables.util.ModTags;

import java.util.Optional;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneMixin {

    @Unique
    private static Optional<PointedDripstoneBlock.FluidInfo> renewable$getBlockandFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
        return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map(p_221876_ -> {
            BlockState blockstate = level.getBlockState(p_221876_);
            Fluid fluid;
            fluid = level.getFluidState(p_221876_.above()).getType();
            return new PointedDripstoneBlock.FluidInfo(p_221876_, fluid, blockstate);
        });
    }

    @Shadow
    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxIterations) {
        return Optional.empty();
    }

    @Shadow
    private static boolean isStalactite(BlockState state) {
        return false;
    }

    @Shadow
    private static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxIterations, boolean isTipMerge) {
        return pos;
    }

    @Shadow
    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        return false;
    }

    @Unique private static void renewable$growCalciteBelow(ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        for (int i = 0; i < 10; i++) {
            blockpos$mutableblockpos.move(Direction.DOWN);
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (!canDripThrough(level, blockpos$mutableblockpos, blockstate)) {
                if (level.getBlockState(blockpos$mutableblockpos.above()).isAir() && !(level.getBlockState(blockpos$mutableblockpos).getBlock() instanceof AbstractCauldronBlock)) {
                    level.setBlockAndUpdate(blockpos$mutableblockpos.above(), Blocks.CALCITE.defaultBlockState());
                }
                return;
            }
        }
    }

    @Inject(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;findFillableCauldronBelowStalactiteTip(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/Fluid;)Lnet/minecraft/core/BlockPos;", shift = At.Shift.BEFORE))
    private static void onfluid(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo ci) {
        Optional<PointedDripstoneBlock.FluidInfo> optional = renewable$getBlockandFluidAboveStalactite(level, pos, state);
        Fluid fluid = optional.get().fluid();
        BlockPos blockpos1 = findTip(state, level, pos, 11, false);
        if (optional.get().sourceState().is(ModTags.Blocks.DEAD_CORAL_BLOCKS) && fluid == Fluids.WATER && fluid.isSource(fluid.defaultFluidState())) {
            if (blockpos1 != null) {
                renewable$growCalciteBelow(level, blockpos1);
            }
        }
    }
}