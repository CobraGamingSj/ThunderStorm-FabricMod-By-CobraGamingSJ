package net.cobra.storm.datagen;

import net.cobra.storm.entity.ModEntityType;
import net.cobra.storm.registry.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class TagGen {

    public static class Item extends FabricTagProvider.ItemTagProvider{

        public Item(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        }
    }

    public static class Block extends FabricTagProvider.BlockTagProvider{

        public Block(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModBlockTags.THUNDER_STALKER_SUMMON_BASE_BLOCKS)
                    .add(Blocks.CRYING_OBSIDIAN);

            getOrCreateTagBuilder(ModBlockTags.THUNDER_STALKER_IMMUNE)
                    .add(Blocks.BARRIER)
                    .add(Blocks.END_PORTAL)
                    .add(Blocks.END_GATEWAY)
                    .add(Blocks.END_PORTAL_FRAME)
                    .add(Blocks.COMMAND_BLOCK)
                    .add(Blocks.REPEATING_COMMAND_BLOCK)
                    .add(Blocks.CHAIN_COMMAND_BLOCK)
                    .add(Blocks.STRUCTURE_BLOCK)
                    .add(Blocks.JIGSAW)
                    .add(Blocks.MOVING_PISTON)
                    .add(Blocks.LIGHT)
                    .add(Blocks.REINFORCED_DEEPSLATE);
        }
    }

    public static class Entity extends FabricTagProvider.EntityTypeTagProvider{

        public Entity(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(EntityTypeTags.WITHER_FRIENDS)
                    .forceAddTag(EntityTypeTags.UNDEAD)
                    .add(EntityType.BREEZE)
                    .add(EntityType.BLAZE)
                    .add(EntityType.WARDEN)
                    .add(EntityType.ENDER_DRAGON);

            getOrCreateTagBuilder(EntityTypeTags.DEFLECTS_PROJECTILES)
                    .add(ModEntityType.THUNDER_ENTITY);
        }
    }

}
