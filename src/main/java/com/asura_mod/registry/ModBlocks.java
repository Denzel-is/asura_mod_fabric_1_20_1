package com.asura_mod.registry;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // OVERWORLD ORES
    public static final Block AETHER_QUARTZ_ORE = registerBlock("aether_quartz_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()));

    public static final Block DEEPSLATE_AETHER_QUARTZ_ORE = registerBlock("deepslate_aether_quartz_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_IRON_ORE)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()));

    public static final Block RUNIC_IRON_ORE = registerBlock("runic_iron_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()));

    public static final Block DEEPSLATE_RUNIC_IRON_ORE = registerBlock("deepslate_runic_iron_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_IRON_ORE)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()));

    public static final Block STORMSILVER_ORE = registerBlock("stormsilver_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()));

    // NETHER ORES
    public static final Block CINDER_OPAL_ORE = registerBlock("cinder_opal_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHER_GOLD_ORE)
                    .sounds(BlockSoundGroup.NETHER_ORE)
                    .requiresTool()));

    public static final Block VOID_SALT_VEIN = registerBlock("void_salt_vein",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHER_QUARTZ_ORE)
                    .sounds(BlockSoundGroup.NETHER_ORE)
                    .requiresTool()));

    // END RESOURCES
    public static final Block LUMEN_BUD = registerBlock("lumen_bud",
            new Block(FabricBlockSettings.create()
                    .strength(0.5f, 0.5f)
                    .sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                    .luminance(state -> 8)
                    .nonOpaque()));

    /**
     * Register a block with automatic BlockItem registration
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Asura_mod.MOD_ID, name), block);
    }

    /**
     * Register BlockItem for a block
     */
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(Asura_mod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    /**
     * Initialize all blocks - call this from Asura_mod.onInitialize()
     */
    public static void register() {
        Asura_mod.LOGGER.info("Registering blocks for " + Asura_mod.MOD_ID);
    }
}
