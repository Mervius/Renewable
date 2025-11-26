package top.yuhh.renewables.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
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
            BlockPos blockpos = p_221876_.above();
            BlockState blockstate = level.getBlockState(blockpos);
            Fluid fluid;
            if (blockstate.is(ModTags.Blocks.DEAD_CORAL_BLOCKS) && !level.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = level.getFluidState(blockpos).getType();
            }

            return new PointedDripstoneBlock.FluidInfo(blockpos, fluid, blockstate);
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

    @Inject(method = "maybeTransferFluid", at = @At(value = "TAIL"))
    private static void onfluid(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo ci) {
        Optional<PointedDripstoneBlock.FluidInfo> optional = renewable$getBlockandFluidAboveStalactite(level, pos, state);
        Fluid fluid = optional.get().fluid();
        BlockPos blockpos1 = findTip(state, level, pos, 11, false);
        if (blockpos1 != null) {
            if (optional.get().sourceState().is(ModTags.Blocks.DEAD_CORAL_BLOCKS) && fluid == Fluids.WATER) {
                BlockState blockstate1 = Blocks.CALCITE.defaultBlockState();
                level.setBlockAndUpdate(optional.get().pos(), blockstate1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, optional.get().pos(), GameEvent.Context.of(blockstate1));
                level.levelEvent(1504, blockpos1, 0);
            }
        }
    }
}