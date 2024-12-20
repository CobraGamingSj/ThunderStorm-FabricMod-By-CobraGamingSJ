package net.cobra.storm.world.gen.feature;

import net.cobra.storm.ThunderStorm;
import net.cobra.storm.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModTreePlacedFeatures {

    public static final RegistryKey<PlacedFeature> PURPLE_HEART_PLACED = of("purple_heart_placed");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        var configuredFeatures = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        var purpleHeartConfig = configuredFeatures.getOrThrow(ModTreeConfiguredFeatures.PURPLE_HEART);
        register(featureRegisterable, PURPLE_HEART_PLACED, purpleHeartConfig,
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f, 2), ModBlocks.PURPLE_HEART_SAPLING));
    }

    public static void register(Registerable<PlacedFeature> featureRegisterable, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }

    public static void register(Registerable<PlacedFeature> featureRegisterable, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        register(featureRegisterable, key, feature, List.of(modifiers));
    }

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, ThunderStorm.setModId(id));
    }

}
