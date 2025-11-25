package top.yuhh.renewables.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import top.yuhh.renewables.Renewables;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> DEAD_CORAL_BLOCKS = createTag("dead_coral_blocks");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Renewables.MOD_ID, name));
        }

    }
    public static class Items {

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Renewables.MOD_ID, name));
        }
    }
}
