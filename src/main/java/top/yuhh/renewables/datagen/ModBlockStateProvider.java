package top.yuhh.renewables.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import top.yuhh.renewables.Renewables;
import top.yuhh.renewables.block.ModBlocks;

import java.text.MessageFormat;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Renewables.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.GRAPHITE_BLOCK);
        blockWithItem(ModBlocks.POLISHED_GRAPHITE);
        blockWithItem(ModBlocks.GRAPHITE_BRICKS);
        blockWithItem(ModBlocks.LACED_GRAPHITE);
        blockWithItem(ModBlocks.RICH_GRAPHITE);
        slabBlockWithItem(ModBlocks.GRAPHITE_SLAB, "graphite_block");
        slabBlockWithItem(ModBlocks.POLISHED_GRAPHITE_SLAB, "polished_graphite");
        slabBlockWithItem(ModBlocks.GRAPHITE_BRICK_SLAB, "graphite_bricks");
        stairBlockWithItem(ModBlocks.GRAPHITE_STAIRS, "graphite_block");
        stairBlockWithItem(ModBlocks.POLISHED_GRAPHITE_STAIRS, "polished_graphite");
        stairBlockWithItem(ModBlocks.GRAPHITE_BRICK_STAIRS, "graphite_bricks");
        wallBlockWithItem(ModBlocks.GRAPHITE_WALL, "graphite_block");
        wallBlockWithItem(ModBlocks.POLISHED_GRAPHITE_WALL, "polished_graphite");
        wallBlockWithItem(ModBlocks.GRAPHITE_BRICK_WALL, "graphite_bricks");
        blockWithItem(ModBlocks.CRACKED_GRAPHITE_BRICKS);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
    private void slabBlockWithItem(DeferredBlock<?> deferredBlock, String texture) {
        slabBlock((SlabBlock) deferredBlock.get(), modLoc(MessageFormat.format("block/{0}", texture)),modLoc(MessageFormat.format("block/{0}", texture)));
        simpleBlockItem(deferredBlock.get(),models().slab(deferredBlock.getRegisteredName(), modLoc(MessageFormat.format("block/{0}", texture)), modLoc(MessageFormat.format("block/{0}", texture)), modLoc(MessageFormat.format("block/{0}", texture))));
    }

    private void stairBlockWithItem(DeferredBlock<?> deferredBlock, String texture) {
        stairsBlock((StairBlock) deferredBlock.get(), modLoc(MessageFormat.format("block/{0}", texture)));
        simpleBlockItem(deferredBlock.get(),models().stairs(deferredBlock.getRegisteredName(), modLoc(MessageFormat.format("block/{0}", texture)), modLoc(MessageFormat.format("block/{0}", texture)), modLoc(MessageFormat.format("block/{0}", texture))));
    }
    private void wallBlockWithItem(DeferredBlock<?> deferredBlock, String texture) {
        wallBlock((WallBlock) deferredBlock.get(), modLoc(MessageFormat.format("block/{0}", texture)));
        simpleBlockItem(deferredBlock.get(),models().wallInventory(deferredBlock.getRegisteredName(), modLoc(MessageFormat.format("block/{0}", texture))));
    }
}
