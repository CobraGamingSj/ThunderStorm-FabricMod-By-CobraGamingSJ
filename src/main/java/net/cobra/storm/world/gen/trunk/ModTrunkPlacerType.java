package net.cobra.storm.world.gen.trunk;

import com.mojang.serialization.MapCodec;
import net.cobra.storm.ThunderStorm;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerType{
    private static TrunkPlacerType register(String id, MapCodec codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, Identifier.of(ThunderStorm.MOD_ID, id), new TrunkPlacerType(codec));
    }

    public static final TrunkPlacerType<PurpleHeartTrunkPlacer> TRUNK_PLACER_TRUNK_PLACER_TYPE = register("purple_heart_trunk_placer_type", PurpleHeartTrunkPlacer.CODEC);
}
