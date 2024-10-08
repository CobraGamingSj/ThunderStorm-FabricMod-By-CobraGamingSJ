package net.cobra.storm.registry.tag;

import net.cobra.storm.ThunderStorm;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {

    public static final TagKey<Block> THUNDER_STALKER_IMMUNE = create("thunder_charge_immune");
    public static final TagKey<Block> THUNDER_STALKER_SUMMON_BASE_BLOCKS = create("thunder_stalker_summon_base_blocks");

    private static TagKey<Block> create(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(ThunderStorm.MOD_ID, id));
    }

}
