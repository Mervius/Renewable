package top.yuhh.renewables.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import top.yuhh.renewables.Renewables;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> GRAPHITE = registerKey("graphite_placed");
    public static final ResourceKey<PlacedFeature> RICH_GRAPHITE = registerKey("rich_graphite_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, GRAPHITE, configuredFeatures.getOrThrow(ModConfiguredFeatures.GRAPHITE_KEY),
                ModOrePlacement.commonOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(20))));
        register(context, RICH_GRAPHITE, configuredFeatures.getOrThrow(ModConfiguredFeatures.RICH_GRAPHITE_KEY),
                ModOrePlacement.rareOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(20))));

    }


    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Renewables.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> ke, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifier) {

    }
}