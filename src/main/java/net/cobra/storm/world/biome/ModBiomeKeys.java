package net.cobra.storm.world.biome;

import net.cobra.storm.ThunderStorm;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModBiomeKeys {
    public static final RegistryKey<Biome> BARRENLANDS = register("barrenlands");

    public static void bootstrap(Registerable<Biome> context) {
        RegistryEntryLookup<PlacedFeature> registryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> registryEntryLookup2 = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        context.register(BARRENLANDS, ModOverworldBiomeCreator.createBarrenlands(registryEntryLookup, registryEntryLookup2));
    }

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(ThunderStorm.MOD_ID, name));
    }
}
