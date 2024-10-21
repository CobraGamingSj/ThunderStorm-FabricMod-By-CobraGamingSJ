package net.cobra.storm.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class PurpleHeartFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<PurpleHeartFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> fillFoliagePlacerFields(instance)
                    .<IntProvider, Float, Float, Float, Float>and(
                            instance.group(
                                    IntProvider.createValidatingCodec(4, 16).fieldOf("height").forGetter(foliagePlacer -> foliagePlacer.height),
                                    Codec.floatRange(0.0F, 1.0F).fieldOf("wide_bottom_layer_hole_chance").forGetter(foliagePlacer -> foliagePlacer.wideBottomLayerHoleChance),
                                    Codec.floatRange(0.0F, 1.0F).fieldOf("corner_hole_chance").forGetter(foliagePlacer -> foliagePlacer.wideBottomLayerHoleChance),
                                    Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter(foliagePlacer -> foliagePlacer.hangingLeavesChance),
                                    Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter(foliagePlacer -> foliagePlacer.hangingLeavesExtensionChance)
                            )
                    )
                    .apply(instance, PurpleHeartFoliagePlacer::new)
    );
    private final IntProvider height;
    private final float wideBottomLayerHoleChance;
    private final float cornerHoleChance;
    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public PurpleHeartFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height, float wideBottomLayerHoleChance, float cornerHoleChance, float hangingLeavesChance, float hangingLeavesExtensionChance) {
        super(radius, offset);
        this.height = height;
        this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
        this.cornerHoleChance = cornerHoleChance;
        this.hangingLeavesChance = hangingLeavesChance;
        this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerType.PURPLE_HEART_FOLIAGE_PLACER_TYPE;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {

    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
