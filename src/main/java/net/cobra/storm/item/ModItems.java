package net.cobra.storm.item;

import net.cobra.storm.ThunderStorm;
import net.cobra.storm.entity.ModEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item THUNDER_BOLT = register("thunder_charge", new ThunderChargeItem(new Item.Settings()));
    public static final Item THUNDER_STORM_SPAWN_EGG = register("thunder_storm_spawn_egg", new SpawnEggItem(ModEntityType.THUNDER_ENTITY, 5120881, 4063370, new Item.Settings()));

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ThunderStorm.MOD_ID, id), item);
    }

    public static void register() {

    }

}
