package top.yuhh.renewables.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import top.yuhh.renewables.Renewables;
import top.yuhh.renewables.block.ModBlocks;
import top.yuhh.renewables.item.ModItems;

import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        buildShapedCompressRecipe(RecipeCategory.MISC, Items.DIAMOND, ModItems.DIAMOND_SHARD, recipeOutput);

        buildShapedSmallCompressRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GRAPHITE, ModBlocks.GRAPHITE_BLOCK, 4, recipeOutput);
        buildShapedSmallCompressRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_BRICKS, ModBlocks.POLISHED_GRAPHITE, 4, recipeOutput);

        buildShapedStairRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_STAIRS, ModBlocks.GRAPHITE_BLOCK, recipeOutput);
        buildShapedStairRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GRAPHITE_STAIRS, ModBlocks.POLISHED_GRAPHITE, recipeOutput);
        buildShapedStairRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_BRICK_STAIRS, ModBlocks.GRAPHITE_BRICKS, recipeOutput);

        buildShapedSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_SLAB, ModBlocks.GRAPHITE_BLOCK, recipeOutput);
        buildShapedSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GRAPHITE_SLAB, ModBlocks.POLISHED_GRAPHITE, recipeOutput);
        buildShapedSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_BRICK_SLAB, ModBlocks.GRAPHITE_BRICKS, recipeOutput);


        buildShapedWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_WALL, ModBlocks.GRAPHITE_BLOCK, recipeOutput);
        buildShapedWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_GRAPHITE_WALL, ModBlocks.POLISHED_GRAPHITE, recipeOutput);
        buildShapedWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRAPHITE_BRICK_WALL, ModBlocks.GRAPHITE_BRICKS, recipeOutput);


        buildSmeltingBlastingRecipe(RecipeCategory.MISC, Items.DIAMOND, ModBlocks.RICH_GRAPHITE, 0.1F, 200, recipeOutput);
        buildSmeltingBlastingRecipe(RecipeCategory.MISC, Items.DIAMOND, ModBlocks.LACED_GRAPHITE, 0.1F, 200, recipeOutput);

        buildSmeltingRecipe(RecipeCategory.MISC, ModBlocks.CRACKED_GRAPHITE_BRICKS, ModBlocks.GRAPHITE_BRICKS, 0.1F, 200, recipeOutput);


        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_STAIRS, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_SLAB, ModBlocks.GRAPHITE_BLOCK, 2, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_WALL, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE_STAIRS, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE_SLAB, ModBlocks.GRAPHITE_BLOCK, 2, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE_WALL, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE_STAIRS, ModBlocks.POLISHED_GRAPHITE, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE_SLAB, ModBlocks.POLISHED_GRAPHITE, 2, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.POLISHED_GRAPHITE_WALL, ModBlocks.POLISHED_GRAPHITE, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICKS, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICKS, ModBlocks.POLISHED_GRAPHITE, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_STAIRS, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_SLAB, ModBlocks.GRAPHITE_BLOCK, 2, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_WALL, ModBlocks.GRAPHITE_BLOCK, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_STAIRS, ModBlocks.POLISHED_GRAPHITE, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_SLAB, ModBlocks.POLISHED_GRAPHITE, 2, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_WALL, ModBlocks.POLISHED_GRAPHITE, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_STAIRS, ModBlocks.GRAPHITE_BRICKS, 1, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_SLAB, ModBlocks.GRAPHITE_BRICKS, 2, recipeOutput);
        buildStonecuttingRecipe(RecipeCategory.MISC, ModBlocks.GRAPHITE_BRICK_WALL, ModBlocks.GRAPHITE_BRICKS, 1, recipeOutput);

//        TEMPORARY
        buildBlastingRecipe(RecipeCategory.MISC, Blocks.TUFF, Blocks.ANDESITE, 0.1F, 200, recipeOutput);
        buildBlastingRecipe(RecipeCategory.MISC, Blocks.BLACKSTONE, Blocks.COBBLESTONE, 0.1F, 200, recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.NETHERRACK, 2)
                .pattern("X_")
                .pattern("_X")
                .define('X', Blocks.COBBLESTONE)
                .define('_', Blocks.NETHER_WART)
                .unlockedBy("has_cobblestone",has(Blocks.COBBLESTONE))
                .unlockedBy("has_nether_wart", has(Blocks.NETHER_WART))
                .showNotification(true)
                .save(recipeOutput, MessageFormat.format("{0}:netherrack_from_cobblestone_and_nether_wart", Renewables.MOD_ID));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.ANCIENT_DEBRIS, 2)
                .pattern("_A_")
                .pattern("_X_")
                .pattern("_A_")
                .define('X', Items.NETHERITE_SCRAP)
                .define('_', Blocks.NETHERRACK)
                .define('A', ModBlocks.LACED_GRAPHITE)
                .unlockedBy("has_laced_graphite",has(ModBlocks.LACED_GRAPHITE))
                .unlockedBy("has_nether_wart", has(Blocks.NETHERRACK))
                .unlockedBy("has_netherite_scrap",has(Items.NETHERITE_SCRAP))
                .showNotification(true)
                .save(recipeOutput, MessageFormat.format("{0}:ancient_debris_from_crafting", Renewables.MOD_ID));

    }

    protected void buildStonecuttingRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, int count, RecipeOutput recipeOutput) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input),recipeCategory,result, count)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .save(recipeOutput, MessageFormat.format("{0}:{1}_from_stonecutting_{2}", Renewables.MOD_ID,BuiltInRegistries.ITEM.getKey(result.asItem()).getPath(), BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
    }

    protected void buildSmeltingBlastingRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, float experience, int cookingtime, RecipeOutput recipeOutput) {
        buildSmeltingRecipe(recipeCategory, result, input, experience, cookingtime, recipeOutput);
        buildBlastingRecipe(recipeCategory, result, input, experience, cookingtime/2, recipeOutput);
    }

    protected void buildSmeltingRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, float experience, int cookingtime, RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input),recipeCategory, result.asItem(),experience,cookingtime)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .save(recipeOutput, MessageFormat.format("{0}:{1}_from_smelting_{2}", Renewables.MOD_ID,BuiltInRegistries.ITEM.getKey(result.asItem()).getPath(), BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
    }

    protected void buildBlastingRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, float experience, int cookingtime, RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input),recipeCategory,result.asItem(),experience,cookingtime)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .save(recipeOutput, MessageFormat.format("{0}:{1}_from_blasting_{2}", Renewables.MOD_ID,BuiltInRegistries.ITEM.getKey(result.asItem()).getPath(), BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
    }

    protected void buildShapedCompressRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(recipeCategory, result, 1)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', input)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .showNotification(true)
                .save(recipeOutput, MessageFormat.format("{0}:{1}_from_{2}", Renewables.MOD_ID,BuiltInRegistries.ITEM.getKey(result.asItem()).getPath(), BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
    }

    protected void buildShapedSmallCompressRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, int count, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(recipeCategory, result, count)
                .pattern("XX")
                .pattern("XX")
                .define('X', input)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .showNotification(true)
                .save(recipeOutput, MessageFormat.format("{0}:{1}_from_{2}", Renewables.MOD_ID,BuiltInRegistries.ITEM.getKey(result.asItem()).getPath(), BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
    }

    protected void buildShapedStairRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(recipeCategory, result, 4)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', input)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .showNotification(true)
                .save(recipeOutput);
    }

    protected void buildShapedSlabRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(recipeCategory, result, 6)
                .pattern("XXX")
                .define('X', input)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .showNotification(true)
                .save(recipeOutput);
    }

    protected void buildShapedWallRecipe(RecipeCategory recipeCategory, ItemLike result, ItemLike input, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(recipeCategory, result, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', input)
                .unlockedBy(MessageFormat.format("has_{0}",BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()), has(input))
                .showNotification(true)
                .save(recipeOutput);
    }
}
