package net.cobra.storm;

import net.cobra.storm.datagen.ModelGen;
import net.cobra.storm.datagen.TagGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ThunderStormDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelGen::new);
		pack.addProvider(TagGen.Block::new);
		pack.addProvider(TagGen.Item::new);
		pack.addProvider(TagGen.Entity::new);
	}
}
