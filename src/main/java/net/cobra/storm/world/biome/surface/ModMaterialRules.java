//package net.cobra.storm.world.biome.surface;
//
//import net.cobra.storm.block.ModBlocks;
//import net.cobra.storm.world.biome.ModBiomeKeys;
//import net.minecraft.block.Block;
//import net.minecraft.block.Blocks;
//import net.minecraft.util.math.VerticalSurfaceType;
//import net.minecraft.world.gen.surfacebuilder.MaterialRules;
//
//public class ModMaterialRules {
//    private static final MaterialRules.MaterialRule OBSIDIAN = makeStateRule(Blocks.CRYING_OBSIDIAN);
//    private static final MaterialRules.MaterialRule SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
//    private static final MaterialRules.MaterialRule MOSS = makeStateRule(Blocks.MOSS_BLOCK);
//    private static final MaterialRules.MaterialRule THUNDER_CHARGE = makeStateRule(ModBlocks.THUNDER_CHARGE_BLOCK);
//
//    public static MaterialRules.MaterialRule makeRules() {
//        // Water condition (to ensure it generates correctly at or above water level)
//        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);
//
//        // Define the layer rules
//        MaterialRules.MaterialRule topSoulSandLayer = MaterialRules.condition(isAtOrAboveWaterLevel, SOUL_SAND);
//        MaterialRules.MaterialRule cryingObsidianLayer = MaterialRules.condition(MaterialRules.stoneDepth(0, true, VerticalSurfaceType.CEILING), OBSIDIAN);
//        MaterialRules.MaterialRule mossLayer = MaterialRules.condition(MaterialRules.stoneDepth(-2, true, VerticalSurfaceType.CEILING), MOSS);
//        MaterialRules.MaterialRule thunderChargeLayer = MaterialRules.condition(MaterialRules.stoneDepth(-4, true, VerticalSurfaceType.CEILING), THUNDER_CHARGE);
//
//        return MaterialRules.sequence(
//                // Top Soul Sand Layer
//                topSoulSandLayer,
//                // Below Soul Sand: Crying Obsidian Layer
//                cryingObsidianLayer,
//                // Below Crying Obsidian: Moss Layer
//                mossLayer,
//                // Below Moss: Thunder Charge Layer
//                thunderChargeLayer
//        );
//    }
//
//    private static MaterialRules.MaterialRule makeStateRule(Block block) {
//        return MaterialRules.block(block.getDefaultState());
//    }
//
//}
