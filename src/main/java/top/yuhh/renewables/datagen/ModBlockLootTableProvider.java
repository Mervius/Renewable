package top.yuhh.renewables.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import top.yuhh.renewables.block.ModBlocks;
import top.yuhh.renewables.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
//        dropSelf(ModBlocks.NETHERITE_CATALYST.get());
        dropSelf(ModBlocks.GRAPHITE_BLOCK.get());
        dropSelf(ModBlocks.GRAPHITE_SLAB.get());
        dropSelf(ModBlocks.GRAPHITE_STAIRS.get());
        dropSelf(ModBlocks.GRAPHITE_WALL.get());
        dropSelf(ModBlocks.POLISHED_GRAPHITE.get());
        dropSelf(ModBlocks.POLISHED_GRAPHITE_SLAB.get());
        dropSelf(ModBlocks.POLISHED_GRAPHITE_STAIRS.get());
        dropSelf(ModBlocks.POLISHED_GRAPHITE_WALL.get());
        dropSelf(ModBlocks.GRAPHITE_BRICKS.get());
        dropSelf(ModBlocks.GRAPHITE_BRICK_SLAB.get());
        dropSelf(ModBlocks.GRAPHITE_BRICK_STAIRS.get());
        dropSelf(ModBlocks.GRAPHITE_BRICK_WALL.get());
        dropSelf(ModBlocks.CRACKED_GRAPHITE_BRICKS.get());
        add(ModBlocks.LACED_GRAPHITE.get(),
                block -> createMultipleOreDrops(ModBlocks.LACED_GRAPHITE.get(), ModItems.DIAMOND_SHARD.get(), 2.0F, 4.0F));
        add(ModBlocks.RICH_GRAPHITE.get(),
                block -> createSingleItemTable(Items.DIAMOND));

    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }
}
