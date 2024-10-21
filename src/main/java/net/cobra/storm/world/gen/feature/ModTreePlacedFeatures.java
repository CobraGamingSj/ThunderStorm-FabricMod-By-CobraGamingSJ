package net.cobra.storm.world.gen.feature;

import net.cobra.storm.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class ModTreePlacedFeatures {

    public static final RegistryKey<PlacedFeature> PURPLE_HEART_CHECKED = of("purple_heart_checked");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> registryEntry9 = registryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PURPLE_HEART);
        PlacedFeatures.register(featureRegisterable, PURPLE_HEART_CHECKED, registryEntry9, PlacedFeatures.wouldSurvive(ModBlocks.PURPLE_HEART_SAPLING));
    }

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.ofVanilla(id));
    }

}
