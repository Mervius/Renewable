package top.yuhh.renewables.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SaplingBlock.class)
public class SaplingMixin {

    @Shadow
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
    }

    /**
     * @author Daniel Hegemeier
     * @reason Add Dead Bush mechanics
     */
    @Overwrite
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        int randint = random.nextInt(7);
        int brightness = level.getMaxLocalRawBrightness(pos.above());
        if (randint == 0) {
            if (brightness >= 9) {
                this.advanceTree(level, pos, state, random);
            } else if (brightness == 0) {
                level.setBlockAndUpdate(pos, Blocks.DEAD_BUSH.defaultBlockState());
            }
        }
    }
}