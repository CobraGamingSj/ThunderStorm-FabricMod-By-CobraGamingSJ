package net.cobra.storm.block;

import net.cobra.storm.ThunderStorm;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block THUNDER_CHARGE_BLOCK = register("thunder_charge_block", new ThunderChargeBlock(AbstractBlock.Settings.create()));

    public static Block register(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ThunderStorm.MOD_ID, id), block);
    }

    public static void registerBlockItem(String id, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ThunderStorm.MOD_ID, id), new BlockItem(block, new Item.Settings()));
    }

    public static void register() {

    }

}
