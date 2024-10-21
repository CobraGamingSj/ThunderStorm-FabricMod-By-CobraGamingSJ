package net.cobra.storm.block;

import net.cobra.storm.ThunderStorm;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModBlocks {

    public static final Block THUNDER_CHARGE_BLOCK = register("thunder_charge_block", new ThunderChargeBlock(AbstractBlock.Settings.create()));
    public static final Block PURPLE_HEART_LOG = register("purple_heart_log", createLogBlock(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, BlockSoundGroup.CHERRY_WOOD));
    public static final Block PURPLE_HEART_WOOD = register("purple_heart_wood", new PillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.CHERRY_WOOD).burnable()));
    public static final Block PURPLE_HEART_LEAVES = register("purple_heart_leaves", new PurpleHeartLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).strength(0.2f).ticksRandomly().nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).burnable().sounds(BlockSoundGroup.CHERRY_SAPLING)));
    public static final Block PURPLE_HEART_SAPLING = register("purple_heart_sapling", new SaplingBlock(ModSaplingGenerator.PURPLE_HEART, AbstractBlock.Settings.create().sounds(BlockSoundGroup.CHERRY_SAPLING)));

    public static Block register(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ThunderStorm.MOD_ID, id), block);
    }

    public static Block createLogBlock(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup soundGroup) {
        return new PillarBlock(
                AbstractBlock.Settings.create()
                        .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sounds(soundGroup)
                        .burnable()
        );
    }

    public static void registerBlockItem(String id, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ThunderStorm.MOD_ID, id), new BlockItem(block, new Item.Settings()));
    }

    public static void register() {

    }

}
