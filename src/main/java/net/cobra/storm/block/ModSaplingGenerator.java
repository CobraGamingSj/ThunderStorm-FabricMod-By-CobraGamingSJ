package net.cobra.storm.block;

import net.cobra.storm.world.gen.feature.ModTreeConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerator {
    public static final SaplingGenerator PURPLE_HEART = new SaplingGenerator("purple_heart", 0f, Optional.empty(), Optional.empty(),
            Optional.of(ModTreeConfiguredFeatures.PURPLE_HEART), Optional.empty(), Optional.empty(), Optional.empty());

}
