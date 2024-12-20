package net.cobra.storm.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ModConfiguredFeatures {

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ModTreeConfiguredFeatures.bootstrap(featureRegisterable);
        ModVegetationConfiguredFeatures.bootstrap(featureRegisterable);
    }

}
