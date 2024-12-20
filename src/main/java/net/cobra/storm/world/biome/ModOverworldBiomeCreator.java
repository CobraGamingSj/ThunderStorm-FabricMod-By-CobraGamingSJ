package net.cobra.storm.world.biome;

import net.cobra.storm.world.gen.feature.ModTreePlacedFeatures;
import net.cobra.storm.world.gen.feature.ModVegetationPlacedFeatures;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModOverworldBiomeCreator {
    public static Biome createPurpleHeartForest(RegistryEntryLookup<PlacedFeature> placedFeature, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarver) {
        Biome.Builder builder = new Biome.Builder();
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(placedFeature, configuredCarver);
        DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder);
        DefaultBiomeFeatures.addMineables(lookupBackedBuilder);
        addPurpleHeartTrees(lookupBackedBuilder);
        BiomeEffects biomeEffects = new BiomeEffects.Builder().grassColor(6314911).fogColor(14539515).foliageColor(9590783).waterFogColor(11840197).skyColor(5832959).waterColor(6663127).build();
        return builder
                .precipitation(true)
                .effects(biomeEffects)
                .downfall(0.5f)
                .temperature(0.5f)
                .spawnSettings(spawnSettings.build())
                .generationSettings(lookupBackedBuilder.build())
                .build();
    }

    public static void addPurpleHeartTrees(GenerationSettings.LookupBackedBuilder lookupBackedBuilder) {
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PURPLE_HEART_VEGETATION);
    }
}
