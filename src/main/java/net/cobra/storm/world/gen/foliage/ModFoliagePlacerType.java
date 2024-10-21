package net.cobra.storm.world.gen.foliage;

import com.mojang.serialization.MapCodec;
import net.cobra.storm.ThunderStorm;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerType {
    public static final FoliagePlacerType<PurpleHeartFoliagePlacer> PURPLE_HEART_FOLIAGE_PLACER_TYPE = register("purple_heart_foliage_pacer_type", PurpleHeartFoliagePlacer.CODEC);

    private static FoliagePlacerType register(String id, MapCodec codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, Identifier.of(ThunderStorm.MOD_ID, id), new FoliagePlacerType<>(codec));
    }
}
