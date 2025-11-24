package top.yuhh.renewables.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.yuhh.renewables.item.ModItems;
import top.yuhh.renewables.Renewables;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Renewables.MOD_ID);

    public static final DeferredBlock<Block> LACED_GRAPHITE = registerBlock("laced_graphite", () -> new LacedGraphiteBlock(BlockBehaviour.Properties.of().sound(SoundType.TUFF).randomTicks().requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> RICH_GRAPHITE = registerBlock("rich_graphite", () -> new RichGraphiteBlock(BlockBehaviour.Properties.of().sound(SoundType.TUFF).randomTicks().requiresCorrectToolForDrops().strength(3.5F,3F)));

    public static final DeferredBlock<Block> GRAPHITE_BLOCK = registerBlock("graphite_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> GRAPHITE_SLAB = registerBlock("graphite_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> GRAPHITE_STAIRS = registerBlock("graphite_stairs", () -> new StairBlock(ModBlocks.GRAPHITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> GRAPHITE_WALL = registerBlock("graphite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(GRAPHITE_BLOCK.get()).forceSolidOn()));

    public static final DeferredBlock<Block> POLISHED_GRAPHITE = registerBlock("polished_graphite", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> POLISHED_GRAPHITE_SLAB = registerBlock("polished_graphite_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> POLISHED_GRAPHITE_STAIRS = registerBlock("polished_graphite_stairs", () -> new StairBlock(ModBlocks.POLISHED_GRAPHITE.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> POLISHED_GRAPHITE_WALL = registerBlock("polished_graphite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(POLISHED_GRAPHITE.get()).forceSolidOn()));

    public static final DeferredBlock<Block> GRAPHITE_BRICKS = registerBlock("graphite_bricks", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> GRAPHITE_BRICK_SLAB = registerBlock("graphite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> GRAPHITE_BRICK_STAIRS = registerBlock("graphite_brick_stairs", () -> new StairBlock(ModBlocks.GRAPHITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.TUFF).requiresCorrectToolForDrops().strength(4.5F,4F)));

    public static final DeferredBlock<Block> GRAPHITE_BRICK_WALL = registerBlock("graphite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofLegacyCopy(GRAPHITE_BLOCK.get()).forceSolidOn()));

    private  static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
