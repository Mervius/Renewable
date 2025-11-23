package top.yuhh.renewables.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import top.yuhh.renewables.Renewables;
import top.yuhh.renewables.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRAPHITE_KEY = registerKey("graphite");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest graphiteReplaceables = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest diamondReplaceablesStone = new BlockMatchTest(Blocks.DIAMOND_ORE);
        RuleTest diamondReplaceablesDeepslate = new BlockMatchTest(Blocks.DEEPSLATE_DIAMOND_ORE);

        List<OreConfiguration.TargetBlockState> graphite = List.of(OreConfiguration.target(graphiteReplaceables, ModBlocks.GRAPHITE_BLOCK.get().defaultBlockState()),OreConfiguration.target(diamondReplaceablesStone, ModBlocks.RICH_GRAPHITE.get().defaultBlockState()), OreConfiguration.target(diamondReplaceablesDeepslate, ModBlocks.RICH_GRAPHITE.get().defaultBlockState()));

        register(context, GRAPHITE_KEY, Feature.ORE, new OreConfiguration(graphite, 64));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Renewables.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
