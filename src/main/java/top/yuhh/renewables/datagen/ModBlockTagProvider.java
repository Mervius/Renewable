package top.yuhh.renewables.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.yuhh.renewables.Renewables;
import top.yuhh.renewables.block.ModBlocks;
import top.yuhh.renewables.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Renewables.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Blocks.DEAD_CORAL_BLOCKS)
                .add(Blocks.DEAD_BRAIN_CORAL_BLOCK)
                .add(Blocks.DEAD_BUBBLE_CORAL_BLOCK)
                .add(Blocks.DEAD_FIRE_CORAL_BLOCK)
                .add(Blocks.DEAD_HORN_CORAL_BLOCK)
                .add(Blocks.DEAD_TUBE_CORAL_BLOCK);
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.GRAPHITE_BLOCK.get())
                .add(ModBlocks.GRAPHITE_STAIRS.get())
                .add(ModBlocks.GRAPHITE_SLAB.get())
                .add(ModBlocks.GRAPHITE_WALL.get())
                .add(ModBlocks.POLISHED_GRAPHITE.get())
                .add(ModBlocks.POLISHED_GRAPHITE_STAIRS.get())
                .add(ModBlocks.POLISHED_GRAPHITE_SLAB.get())
                .add(ModBlocks.POLISHED_GRAPHITE_WALL.get())
                .add(ModBlocks.LACED_GRAPHITE.get())
                .add(ModBlocks.RICH_GRAPHITE.get())
                .add(ModBlocks.GRAPHITE_BRICKS.get())
                .add(ModBlocks.GRAPHITE_BRICK_STAIRS.get())
                .add(ModBlocks.GRAPHITE_BRICK_SLAB.get())
                .add(ModBlocks.GRAPHITE_BRICK_WALL.get())
                .add(ModBlocks.CRACKED_GRAPHITE_BRICKS.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LACED_GRAPHITE.get())
                .add(ModBlocks.RICH_GRAPHITE.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.GRAPHITE_STAIRS.get())
                .add(ModBlocks.POLISHED_GRAPHITE_STAIRS.get())
                .add(ModBlocks.GRAPHITE_BRICK_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.GRAPHITE_SLAB.get())
                .add(ModBlocks.POLISHED_GRAPHITE_SLAB.get())
                .add(ModBlocks.GRAPHITE_BRICK_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.GRAPHITE_WALL.get())
                .add(ModBlocks.POLISHED_GRAPHITE_WALL.get())
                .add(ModBlocks.GRAPHITE_BRICK_WALL.get());
    }
}
