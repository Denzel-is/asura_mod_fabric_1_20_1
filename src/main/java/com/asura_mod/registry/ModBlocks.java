package com.asura_mod.registry;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

public class ModBlocks {

        // OVERWORLD ORES
        public static final Block AETHER_QUARTZ_ORE = registerBlock("aether_quartz_ore",
                        new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)
                                        .sound(SoundType.STONE)
                                        .requiresCorrectToolForDrops()));

        public static final Block DEEPSLATE_AETHER_QUARTZ_ORE = registerBlock("deepslate_aether_quartz_ore",
                        new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_IRON_ORE)
                                        .sound(SoundType.DEEPSLATE)
                                        .requiresCorrectToolForDrops()));

        public static final Block RUNIC_IRON_ORE = registerBlock("runic_iron_ore",
                        new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)
                                        .sound(SoundType.STONE)
                                        .requiresCorrectToolForDrops()));

        public static final Block DEEPSLATE_RUNIC_IRON_ORE = registerBlock("deepslate_runic_iron_ore",
                        new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_IRON_ORE)
                                        .sound(SoundType.DEEPSLATE)
                                        .requiresCorrectToolForDrops()));

        public static final Block STORMSILVER_ORE = registerBlock("stormsilver_ore",
                        new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)
                                        .sound(SoundType.STONE)
                                        .requiresCorrectToolForDrops()));

        // NETHER ORES
        public static final Block CINDER_OPAL_ORE = registerBlock("cinder_opal_ore",
                        new Block(FabricBlockSettings.copyOf(Blocks.NETHER_GOLD_ORE)
                                        .sound(SoundType.NETHER_ORE)
                                        .requiresCorrectToolForDrops()));

        public static final Block VOID_SALT_VEIN = registerBlock("void_salt_vein",
                        new Block(FabricBlockSettings.copyOf(Blocks.NETHER_QUARTZ_ORE)
                                        .sound(SoundType.NETHER_ORE)
                                        .requiresCorrectToolForDrops()));

        // END RESOURCES
        public static final Block LUMEN_BUD = registerBlock("lumen_bud",
                        new Block(FabricBlockSettings.create()
                                        .strength(0.5f, 0.5f)
                                        .sound(SoundType.AMETHYST_CLUSTER)
                                        .lightLevel(state -> 8)
                                        .noOcclusion()));

        // STORAGE BLOCKS
        public static final Block RAW_RUNIC_BLOCK = registerBlock("raw_runic_block",
                        new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK)
                                        .sound(SoundType.METAL)
                                        .requiresCorrectToolForDrops()));

        /**
         * Register a block with automatic BlockItem registration
         */
        private static Block registerBlock(String name, Block block) {
                registerBlockItem(name, block);
                return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Asura_mod.MOD_ID, name), block);
        }

        /**
         * Register BlockItem for a block
         */
        private static void registerBlockItem(String name, Block block) {
                Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Asura_mod.MOD_ID, name),
                                new BlockItem(block, new FabricItemSettings()));
        }

        /**
         * Initialize all blocks - call this from Asura_mod.onInitialize()
         */
        public static void register() {
                Asura_mod.LOGGER.info("Registering blocks for " + Asura_mod.MOD_ID);
        }
}
