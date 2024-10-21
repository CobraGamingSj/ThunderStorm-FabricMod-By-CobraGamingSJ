package net.cobra.storm.world.gen.feature;

import net.cobra.storm.ThunderStorm;
import net.cobra.storm.block.ModBlocks;
import net.cobra.storm.world.gen.foliage.PurpleHeartFoliagePlacer;
import net.cobra.storm.world.gen.trunk.PurpleHeartTrunkPlacer;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModTreeConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> PURPLE_HEART = of("purple_heart");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, PURPLE_HEART, Feature.TREE, purple_heart().build());
    }

    private static TreeFeatureConfig.Builder purple_heart() {
        return new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.PURPLE_HEART_LOG),
                new PurpleHeartTrunkPlacer(
                        7,
                        1,
                        0,
                        new WeightedListIntProvider(
                                DataPool.<IntProvider>builder().add(ConstantIntProvider.create(1), 1).add(ConstantIntProvider.create(2), 1).add(ConstantIntProvider.create(3), 1).build()
                        ),
                        UniformIntProvider.create(2, 4),
                        UniformIntProvider.create(-4, -3),
                        UniformIntProvider.create(-1, 0)
                ),
                BlockStateProvider.of(ModBlocks.PURPLE_HEART_LEAVES),
                new PurpleHeartFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(0), ConstantIntProvider.create(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1,0, 2)
        ).ignoreVines();
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(ThunderStorm.MOD_ID, id));
    }

}
