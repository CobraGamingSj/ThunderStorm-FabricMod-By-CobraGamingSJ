package net.cobra.storm.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        ModTreePlacedFeatures.bootstrap(featureRegisterable);
        ModVegetationPlacedFeatures.bootstrap(featureRegisterable);
    }

}
