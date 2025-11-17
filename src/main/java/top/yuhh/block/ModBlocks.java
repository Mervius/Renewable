package top.yuhh.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.yuhh.item.ModItems;
import top.yuhh.renewables.Renewables;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Renewables.MOD_ID);

    public static final DeferredBlock<Block> BUDDING_DIAMOND = registerBlock("budding_diamond", () -> new BuddingDiamondBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> DIAMOND_CLUSTER = registerBlock("diamond_cluster", () -> new DiamondClusterBlock(7.0F, 3.0F, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).forceSolidOn().noOcclusion().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel($$0 -> 5).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> LARGE_DIAMOND_BUD = registerBlock("large_diamond_bud", () -> new DiamondClusterBlock(5.0F, 3.0F, BlockBehaviour.Properties.ofLegacyCopy(DIAMOND_CLUSTER.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).lightLevel($$0 -> 4)));

    public static final DeferredBlock<Block> MEDIUM_DIAMOND_BUD = registerBlock("medium_diamond_bud", () -> new DiamondClusterBlock(4.0F, 3.0F, BlockBehaviour.Properties.ofLegacyCopy(DIAMOND_CLUSTER.get()).sound(SoundType.LARGE_AMETHYST_BUD).lightLevel($$0 -> 2)));

    public static final DeferredBlock<Block> SMALL_DIAMOND_BUD = registerBlock("small_diamond_bud", () -> new DiamondClusterBlock(3.0F, 4.0F, BlockBehaviour.Properties.ofLegacyCopy(DIAMOND_CLUSTER.get()).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel($$0 -> 1)));

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
