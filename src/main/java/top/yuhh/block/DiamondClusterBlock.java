package top.yuhh.block;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DiamondClusterBlock extends AmethystBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<DiamondClusterBlock> CODEC;

    static {
        CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group(
               Codec.FLOAT.fieldOf("height").forGetter(block -> block.height),
                Codec.FLOAT.fieldOf("aabb_offset").forGetter(block -> block.aabbOffset),
                propertiesCodec()).apply((Applicative) $$0, AmethystClusterBlock::new));
    }

    public MapCodec<DiamondClusterBlock> codec() {
        return CODEC;
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private final float height;

    private final float aabbOffset;

    protected final VoxelShape northAabb;

    protected final VoxelShape southAabb;

    protected final VoxelShape eastAabb;

    protected final VoxelShape westAabb;

    protected final VoxelShape upAabb;

    protected final VoxelShape downAabb;

    public DiamondClusterBlock(float $$0, float $$1, BlockBehaviour.Properties $$2) {
        super($$2);
        registerDefaultState((BlockState)((BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)FACING, (Comparable)Direction.UP));
        this.upAabb = Block.box($$1, 0.0D, $$1, (16.0F - $$1), $$0, (16.0F - $$1));
        this.downAabb = Block.box($$1, (16.0F - $$0), $$1, (16.0F - $$1), 16.0D, (16.0F - $$1));
        this.northAabb = Block.box($$1, $$1, (16.0F - $$0), (16.0F - $$1), (16.0F - $$1), 16.0D);
        this.southAabb = Block.box($$1, $$1, 0.0D, (16.0F - $$1), (16.0F - $$1), $$0);
        this.eastAabb = Block.box(0.0D, $$1, $$1, $$0, (16.0F - $$1), (16.0F - $$1));
        this.westAabb = Block.box((16.0F - $$0), $$1, $$1, 16.0D, (16.0F - $$1), (16.0F - $$1));
        this.height = $$0;
        this.aabbOffset = $$1;
    }

    protected VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        Direction $$4 = (Direction)$$0.getValue((Property)FACING);
        switch ($$4) {
            case NORTH:
                return this.northAabb;
            case SOUTH:
                return this.southAabb;
            case EAST:
                return this.eastAabb;
            case WEST:
                return this.westAabb;
            case DOWN:
                return this.downAabb;
        }
        return this.upAabb;
    }

    protected boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
        Direction $$3 = (Direction)$$0.getValue((Property)FACING);
        BlockPos $$4 = $$2.relative($$3.getOpposite());
        return $$1.getBlockState($$4).isFaceSturdy((BlockGetter)$$1, $$4, $$3);
    }

    protected BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
        if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue())
            $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
        if ($$1 == ((Direction)$$0.getValue((Property)FACING)).getOpposite() && !$$0.canSurvive((LevelReader)$$3, $$4))
            return Blocks.AIR.defaultBlockState();
        return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        Level level = $$0.getLevel();
        BlockPos $$2 = $$0.getClickedPos();
        return (BlockState)((BlockState)defaultBlockState()
                .setValue((Property)WATERLOGGED, Boolean.valueOf((level.getFluidState($$2).getType() == Fluids.WATER))))
                .setValue((Property)FACING, (Comparable)$$0.getClickedFace());
    }

    protected BlockState rotate(BlockState $$0, Rotation $$1) {
        return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
    }

    protected BlockState mirror(BlockState $$0, Mirror $$1) {
        return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
    }

    protected FluidState getFluidState(BlockState $$0) {
        if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue())
            return Fluids.WATER.getSource(false);
        return super.getFluidState($$0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        $$0.add(new Property[] { (Property)WATERLOGGED, (Property)FACING });
    }
}
