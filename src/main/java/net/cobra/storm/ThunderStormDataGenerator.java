package net.cobra.storm;

import net.cobra.storm.datagen.DynamicRegistryGen;
import net.cobra.storm.datagen.ModelGen;
import net.cobra.storm.datagen.TagGen;
import net.cobra.storm.world.biome.ModBiomeKeys;
import net.cobra.storm.world.gen.feature.ModConfiguredFeatures;
import net.cobra.storm.world.gen.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ThunderStormDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelGen::new);
		pack.addProvider(TagGen.Block::new);
		pack.addProvider(TagGen.Item::new);
		pack.addProvider(TagGen.Entity::new);
		pack.addProvider(DynamicRegistryGen::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomeKeys::bootstrap);
//		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
//		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
