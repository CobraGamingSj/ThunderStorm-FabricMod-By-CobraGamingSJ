package net.cobra.storm;

import net.cobra.storm.entity.ModEntityType;
import net.cobra.storm.entity.ThunderEntity;
import net.cobra.storm.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThunderStorm implements ModInitializer {
	public static final String MOD_ID = "storm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((entries) -> {
			entries.addAfter(Items.WIND_CHARGE, ModItems.THUNDER_CHARGE);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register((spawn_eggs) -> {
			spawn_eggs.addAfter(Items.BLAZE_SPAWN_EGG, ModItems.THUNDER_STORM_SPAWN_EGG);
		});

		ModItems.register();
		ModEntityType.register();
		FabricDefaultAttributeRegistry.register(ModEntityType.THUNDER_ENTITY, ThunderEntity.createThunderStormAttributes());
	}
}