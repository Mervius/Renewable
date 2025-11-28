package top.yuhh.renewables.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class OreEnricherBlock extends Block {
    public static final MapCodec<OreEnricherBlock> CODEC = simpleCodec(OreEnricherBlock::new);

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    @Override
    public @NotNull MapCodec<OreEnricherBlock> codec() { return CODEC; }

    public OreEnricherBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(POWERED, false)
        );
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean flag = level.hasNeighborSignal(pos);
//        if (flag != state.getValue(POWERED)) {
//            if (!state.getValue(POWERED)) {
//                BlockState blockabove = level.getBlockState(pos.above());
//                BlockState blockbelow = level.getBlockState(pos.below());
//                ResourceLocation abovename = BuiltInRegistries.BLOCK.getKey(blockabove.getBlock());
//                ResourceLocation belowname = BuiltInRegistries.BLOCK.getKey(blockbelow.getBlock());
//                boolean isabovedeepslate = abovename.getPath().startsWith("deepslate_");
//                boolean isbelowdeepslate = belowname.getPath().startsWith("deepslate_");
//                boolean isaboveore = blockabove.is(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("c", "ores")));
//                boolean isbelowore = blockbelow.is(TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("c", "ores")));
//                if (isaboveore) {
//                    if (isabovedeepslate && blockbelow.is(Blocks.STONE)) {
//                        level.setBlockAndUpdate(pos.above(), Blocks.DEEPSLATE.defaultBlockState());
//                        level.setBlockAndUpdate(pos.below(), BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(abovename.getNamespace(), abovename.getPath().split("_", 2)[1])).defaultBlockState());
//                    } else if (!isabovedeepslate && blockbelow.is(Blocks.DEEPSLATE)) {
//                        level.setBlockAndUpdate(pos.above(), Blocks.STONE.defaultBlockState());
//                        level.setBlockAndUpdate(pos.below(), BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(abovename.getNamespace(), "deepslate_" + abovename.getPath())).defaultBlockState());
//                    }
//                } else if (isbelowore) {
//                    if (isbelowdeepslate && blockabove.is(Blocks.STONE)) {
//                        level.setBlockAndUpdate(pos.above(), BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(belowname.getNamespace(), belowname.getPath().split("_", 2)[1])).defaultBlockState());
//                        level.setBlockAndUpdate(pos.below(), Blocks.DEEPSLATE.defaultBlockState());
//                    } else if (!isbelowdeepslate && blockabove.is(Blocks.DEEPSLATE)) {
//                        level.setBlockAndUpdate(pos.above(), BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(belowname.getNamespace(), "deepslate_" + belowname.getPath())).defaultBlockState());
//                        level.setBlockAndUpdate(pos.below(), Blocks.STONE.defaultBlockState());
//                    }
//                }
//            }
//
//            level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(flag)), 3);
//        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
