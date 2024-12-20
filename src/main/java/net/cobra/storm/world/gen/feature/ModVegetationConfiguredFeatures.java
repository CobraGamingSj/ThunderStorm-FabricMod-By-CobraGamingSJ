package net.cobra.storm.world.gen.feature;

import net.cobra.storm.ThunderStorm;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModVegetationConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PURPLE_HEART_VEGETATION = of("purple_heart_vegetation");



    private static RegistryKey<ConfiguredFeature<?,?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ThunderStorm.setModId(id));
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        RegistryEntryLookup<PlacedFeature> registryEntryLookup2 = featureRegisterable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntry<PlacedFeature> registryEntry7 = registryEntryLookup2.getOrThrow(ModTreePlacedFeatures.PURPLE_HEART_PLACED);
        ConfiguredFeatures.register(
                featureRegisterable,
                PURPLE_HEART_VEGETATION,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfig(List.of(new RandomFeatureEntry(registryEntry7, 0.1F), new RandomFeatureEntry(registryEntry7, 0.9F)), registryEntry7)
        );
    }
}
