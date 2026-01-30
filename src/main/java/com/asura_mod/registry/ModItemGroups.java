package com.asura_mod.registry;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {

    // Custom mod creative tab
    public static final CreativeModeTab ASURA_MOD_GROUP = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(Asura_mod.MOD_ID, "asura_mod_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.RUNIC_IRON_INGOT))
                    .title(Component.translatable("itemGroup.asura_mod.asura_mod_group"))
                    .displayItems((displayContext, entries) -> {
                        // === MATERIALS T1-T4 ===
                        entries.accept(ModItems.RUNE_DUST);
                        entries.accept(ModItems.AETHER_QUARTZ_SHARD);
                        entries.accept(ModItems.RAW_RUNIC_IRON);
                        entries.accept(ModItems.RUNIC_IRON_INGOT);
                        entries.accept(ModItems.STORMSILVER_NUGGET);
                        entries.accept(ModItems.STORMSILVER_INGOT);
                        entries.accept(ModItems.CINDER_OPAL);
                        entries.accept(ModItems.VOID_SALT);
                        entries.accept(ModItems.LUMEN_PEARL);
                        entries.accept(ModItems.GREATER_LUMEN_PEARL);
                        entries.accept(ModItems.STARFORGED_FRAME);

                        // === STRUCTURE LOOT ===
                        entries.accept(ModItems.LEYLINE_CATALYST);
                        entries.accept(ModItems.MAP_FRAGMENT_SHRINE);
                        entries.accept(ModItems.RUNIC_PLATE);
                        entries.accept(ModItems.RELIC_SCRAP);
                        entries.accept(ModItems.HEAT_TREATED_CORE);
                        entries.accept(ModItems.WARDENED_ASH);
                        entries.accept(ModItems.NULL_CHARM);

                        // === TOOLS - RUNIC ===
                        entries.accept(ModItems.RUNIC_PICKAXE);
                        entries.accept(ModItems.RUNIC_AXE);
                        entries.accept(ModItems.RUNIC_SHOVEL);
                        entries.accept(ModItems.RUNIC_HOE);
                        entries.accept(ModItems.RUNIC_SWORD);

                        // === TOOLS - STORMSILVER ===
                        entries.accept(ModItems.STORMSILVER_PICKAXE);
                        entries.accept(ModItems.STORMSILVER_AXE);
                        entries.accept(ModItems.STORMSILVER_SHOVEL);
                        entries.accept(ModItems.STORMSILVER_HOE);

                        // === WEAPONS ===
                        entries.accept(ModItems.RUNIC_BLADE);
                        entries.accept(ModItems.STORMSILVER_RAPIER);
                        entries.accept(ModItems.VOID_SALT_DAGGER);
                        entries.accept(ModItems.CINDER_PICKAXE);

                        // === ARMOR ===
                        entries.accept(ModItems.RUNIC_HELMET);
                        entries.accept(ModItems.RUNIC_CHESTPLATE);
                        entries.accept(ModItems.RUNIC_LEGGINGS);
                        entries.accept(ModItems.RUNIC_BOOTS);

                        // === CLOAKS ===
                        entries.accept(ModItems.STORMSILVER_CLOAK);
                        entries.accept(ModItems.CINDER_MANTLE);
                        entries.accept(ModItems.ASTRAL_CLOAK);

                        // === SMITHING TEMPLATES ===
                        entries.accept(ModItems.CINDER_UPGRADE_TEMPLATE);
                        entries.accept(ModItems.ASTRAL_UPGRADE_TEMPLATE);

                        // === SPELLBOOK SYSTEM ===
                        entries.accept(ModItems.SPELLBOOK);
                        entries.accept(ModItems.STAFF);

                        // === BLOCKS ===
                        entries.accept(ModBlocks.AETHER_QUARTZ_ORE);
                        entries.accept(ModBlocks.DEEPSLATE_AETHER_QUARTZ_ORE);
                        entries.accept(ModBlocks.RUNIC_IRON_ORE);
                        entries.accept(ModBlocks.DEEPSLATE_RUNIC_IRON_ORE);
                        entries.accept(ModBlocks.STORMSILVER_ORE);
                        entries.accept(ModBlocks.CINDER_OPAL_ORE);
                        entries.accept(ModBlocks.VOID_SALT_VEIN);
                        entries.accept(ModBlocks.LUMEN_BUD);
                        entries.accept(ModBlocks.RAW_RUNIC_BLOCK);
                    })
                    .build());

    public static void register() {
        Asura_mod.LOGGER.info("Registering item groups for " + Asura_mod.MOD_ID);

        // Also add items to vanilla tabs for discoverability
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.accept(ModItems.RUNE_DUST);
            entries.accept(ModItems.AETHER_QUARTZ_SHARD);
            entries.accept(ModItems.RAW_RUNIC_IRON);
            entries.accept(ModItems.RUNIC_IRON_INGOT);
            entries.accept(ModItems.STORMSILVER_NUGGET);
            entries.accept(ModItems.STORMSILVER_INGOT);
            entries.accept(ModItems.CINDER_OPAL);
            entries.accept(ModItems.VOID_SALT);
            entries.accept(ModItems.LUMEN_PEARL);
            entries.accept(ModItems.GREATER_LUMEN_PEARL);
            entries.accept(ModItems.STARFORGED_FRAME);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModItems.RUNIC_PICKAXE);
            entries.accept(ModItems.RUNIC_AXE);
            entries.accept(ModItems.RUNIC_SHOVEL);
            entries.accept(ModItems.RUNIC_HOE);
            entries.accept(ModItems.STORMSILVER_PICKAXE);
            entries.accept(ModItems.STORMSILVER_AXE);
            entries.accept(ModItems.STORMSILVER_SHOVEL);
            entries.accept(ModItems.STORMSILVER_HOE);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.accept(ModItems.RUNIC_SWORD);
            entries.accept(ModItems.RUNIC_BLADE);
            entries.accept(ModItems.STORMSILVER_RAPIER);
            entries.accept(ModItems.VOID_SALT_DAGGER);
            entries.accept(ModItems.CINDER_PICKAXE);
            entries.accept(ModItems.RUNIC_HELMET);
            entries.accept(ModItems.RUNIC_CHESTPLATE);
            entries.accept(ModItems.RUNIC_LEGGINGS);
            entries.accept(ModItems.RUNIC_BOOTS);
            entries.accept(ModItems.STORMSILVER_CLOAK);
            entries.accept(ModItems.CINDER_MANTLE);
            entries.accept(ModItems.ASTRAL_CLOAK);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.accept(ModBlocks.AETHER_QUARTZ_ORE);
            entries.accept(ModBlocks.DEEPSLATE_AETHER_QUARTZ_ORE);
            entries.accept(ModBlocks.RUNIC_IRON_ORE);
            entries.accept(ModBlocks.DEEPSLATE_RUNIC_IRON_ORE);
            entries.accept(ModBlocks.STORMSILVER_ORE);
            entries.accept(ModBlocks.CINDER_OPAL_ORE);
            entries.accept(ModBlocks.VOID_SALT_VEIN);
            entries.accept(ModBlocks.LUMEN_BUD);
            entries.accept(ModBlocks.RAW_RUNIC_BLOCK);
        });
    }
}
