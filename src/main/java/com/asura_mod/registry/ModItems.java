package com.asura_mod.registry;

import com.asura_mod.Asura_mod;
import com.asura_mod.item.SpellbookItem;
import com.asura_mod.item.StaffItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.List;

public class ModItems {

        // ========== T1 MATERIALS (Overworld) ==========
        public static final Item RUNE_DUST = registerItem("rune_dust",
                        new Item(new FabricItemSettings()));

        public static final Item AETHER_QUARTZ_SHARD = registerItem("aether_quartz_shard",
                        new Item(new FabricItemSettings()));

        // ========== T2 MATERIALS (Overworld) ==========
        public static final Item RAW_RUNIC_IRON = registerItem("raw_runic_iron",
                        new Item(new FabricItemSettings()));

        public static final Item RUNIC_IRON_INGOT = registerItem("runic_iron_ingot",
                        new Item(new FabricItemSettings()));

        public static final Item STORMSILVER_NUGGET = registerItem("stormsilver_nugget",
                        new Item(new FabricItemSettings()));

        public static final Item STORMSILVER_INGOT = registerItem("stormsilver_ingot",
                        new Item(new FabricItemSettings()));

        // ========== T3 MATERIALS (Nether) ==========
        public static final Item CINDER_OPAL = registerItem("cinder_opal",
                        new Item(new FabricItemSettings()));

        public static final Item VOID_SALT = registerItem("void_salt",
                        new Item(new FabricItemSettings()));

        // ========== T4 MATERIALS (End) ==========
        public static final Item LUMEN_PEARL = registerItem("lumen_pearl",
                        new Item(new FabricItemSettings()));

        public static final Item GREATER_LUMEN_PEARL = registerItem("greater_lumen_pearl",
                        new Item(new FabricItemSettings().rarity(Rarity.RARE)));

        public static final Item STARFORGED_FRAME = registerItem("starforged_frame",
                        new Item(new FabricItemSettings().rarity(Rarity.EPIC)));

        // ========== STRUCTURE LOOT ITEMS ==========
        public static final Item LEYLINE_CATALYST = registerItem("leyline_catalyst",
                        new Item(new FabricItemSettings().rarity(Rarity.RARE)));

        public static final Item MAP_FRAGMENT_SHRINE = registerItem("map_fragment_shrine",
                        new Item(new FabricItemSettings()));

        public static final Item RUNIC_PLATE = registerItem("runic_plate",
                        new Item(new FabricItemSettings()));

        public static final Item RELIC_SCRAP = registerItem("relic_scrap",
                        new Item(new FabricItemSettings()));

        public static final Item HEAT_TREATED_CORE = registerItem("heat_treated_core",
                        new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)));

        public static final Item WARDENED_ASH = registerItem("wardened_ash",
                        new Item(new FabricItemSettings()));

        public static final Item NULL_CHARM = registerItem("null_charm",
                        new Item(new FabricItemSettings().rarity(Rarity.RARE)));

        // ========== RUNIC IRON TOOLS (T2) ==========
        public static final Item RUNIC_PICKAXE = registerItem("runic_pickaxe",
                        new PickaxeItem(ModToolMaterials.RUNIC_IRON, 1, -2.8F, new FabricItemSettings()));

        public static final Item RUNIC_AXE = registerItem("runic_axe",
                        new AxeItem(ModToolMaterials.RUNIC_IRON, 6.0F, -3.1F, new FabricItemSettings()));

        public static final Item RUNIC_SHOVEL = registerItem("runic_shovel",
                        new ShovelItem(ModToolMaterials.RUNIC_IRON, 1.5F, -3.0F, new FabricItemSettings()));

        public static final Item RUNIC_HOE = registerItem("runic_hoe",
                        new HoeItem(ModToolMaterials.RUNIC_IRON, -2, -1.0F, new FabricItemSettings()));

        public static final Item RUNIC_SWORD = registerItem("runic_sword",
                        new SwordItem(ModToolMaterials.RUNIC_IRON, 3, -2.4F, new FabricItemSettings()));

        // ========== STORMSILVER TOOLS (T2) ==========
        public static final Item STORMSILVER_PICKAXE = registerItem("stormsilver_pickaxe",
                        new PickaxeItem(ModToolMaterials.STORMSILVER, 1, -2.6F, new FabricItemSettings()));

        public static final Item STORMSILVER_AXE = registerItem("stormsilver_axe",
                        new AxeItem(ModToolMaterials.STORMSILVER, 5.0F, -2.9F, new FabricItemSettings()));

        public static final Item STORMSILVER_SHOVEL = registerItem("stormsilver_shovel",
                        new ShovelItem(ModToolMaterials.STORMSILVER, 1.5F, -2.8F, new FabricItemSettings()));

        public static final Item STORMSILVER_HOE = registerItem("stormsilver_hoe",
                        new HoeItem(ModToolMaterials.STORMSILVER, -3, -0.5F, new FabricItemSettings()));

        // ========== WEAPONS ==========
        public static final Item RUNIC_BLADE = registerItem("runic_blade",
                        new SwordItem(ModToolMaterials.RUNIC_IRON, 4, -2.4F, new FabricItemSettings()));

        public static final Item STORMSILVER_RAPIER = registerItem("stormsilver_rapier",
                        new SwordItem(ModToolMaterials.STORMSILVER, 2, -1.8F, new FabricItemSettings()));

        public static final Item VOID_SALT_DAGGER = registerItem("void_salt_dagger",
                        new SwordItem(ModToolMaterials.RUNIC_IRON, 1, -1.4F, new FabricItemSettings()));

        // ========== CINDER PICKAXE (T3 Upgrade) ==========
        public static final Item CINDER_PICKAXE = registerItem("cinder_pickaxe",
                        new PickaxeItem(ModToolMaterials.CINDER, 1, -2.8F,
                                        new FabricItemSettings().rarity(Rarity.UNCOMMON)));

        // ========== ARMOR - RUNIC IRON ==========
        public static final Item RUNIC_HELMET = registerItem("runic_helmet",
                        new ArmorItem(ModArmorMaterials.RUNIC_IRON, ArmorItem.Type.HELMET, new FabricItemSettings()));

        public static final Item RUNIC_CHESTPLATE = registerItem("runic_chestplate",
                        new ArmorItem(ModArmorMaterials.RUNIC_IRON, ArmorItem.Type.CHESTPLATE,
                                        new FabricItemSettings()));

        public static final Item RUNIC_LEGGINGS = registerItem("runic_leggings",
                        new ArmorItem(ModArmorMaterials.RUNIC_IRON, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));

        public static final Item RUNIC_BOOTS = registerItem("runic_boots",
                        new ArmorItem(ModArmorMaterials.RUNIC_IRON, ArmorItem.Type.BOOTS, new FabricItemSettings()));

        // ========== CLOAKS (Chestplate slot) ==========
        public static final Item STORMSILVER_CLOAK = registerItem("stormsilver_cloak",
                        new ArmorItem(ModArmorMaterials.STORMSILVER, ArmorItem.Type.CHESTPLATE,
                                        new FabricItemSettings().rarity(Rarity.UNCOMMON)));

        public static final Item CINDER_MANTLE = registerItem("cinder_mantle",
                        new ArmorItem(ModArmorMaterials.CINDER, ArmorItem.Type.CHESTPLATE,
                                        new FabricItemSettings().rarity(Rarity.RARE)));

        public static final Item ASTRAL_CLOAK = registerItem("astral_cloak",
                        new ArmorItem(ModArmorMaterials.ASTRAL, ArmorItem.Type.CHESTPLATE,
                                        new FabricItemSettings().rarity(Rarity.EPIC)));

        // ========== SMITHING TEMPLATES ==========
        public static final Item CINDER_UPGRADE_TEMPLATE = registerItem("cinder_upgrade_template",
                        createCinderUpgradeTemplate());

        public static final Item ASTRAL_UPGRADE_TEMPLATE = registerItem("astral_upgrade_template",
                        createAstralUpgradeTemplate());

        /**
         * Create Cinder upgrade smithing template
         */
        private static SmithingTemplateItem createCinderUpgradeTemplate() {
                return new SmithingTemplateItem(
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "cinder_upgrade_template.applies_to")))
                                                .withStyle(ChatFormatting.BLUE),
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "cinder_upgrade_template.ingredients")))
                                                .withStyle(ChatFormatting.BLUE),
                                Component.translatable(
                                                Util.makeDescriptionId("upgrade",
                                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                                "cinder_upgrade")))
                                                .withStyle(ChatFormatting.GRAY),
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "cinder_upgrade_template.base_slot_description"))),
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "cinder_upgrade_template.additions_slot_description"))),
                                List.of(new ResourceLocation("item/empty_armor_slot_helmet")),
                                List.of(new ResourceLocation("item/empty_slot_ingot")));
        }

        /**
         * Create Astral upgrade smithing template
         */
        private static SmithingTemplateItem createAstralUpgradeTemplate() {
                return new SmithingTemplateItem(
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "astral_upgrade_template.applies_to")))
                                                .withStyle(ChatFormatting.BLUE),
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "astral_upgrade_template.ingredients")))
                                                .withStyle(ChatFormatting.BLUE),
                                Component.translatable(
                                                Util.makeDescriptionId("upgrade",
                                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                                "astral_upgrade")))
                                                .withStyle(ChatFormatting.GRAY),
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "astral_upgrade_template.base_slot_description"))),
                                Component.translatable(Util.makeDescriptionId("item",
                                                new ResourceLocation(Asura_mod.MOD_ID,
                                                                "astral_upgrade_template.additions_slot_description"))),
                                List.of(new ResourceLocation("item/empty_armor_slot_chestplate")),
                                List.of(new ResourceLocation("item/empty_slot_ingot")));
        }

        // ========== SPELLBOOK SYSTEM (per SPELLBOOK_SPEC.md) ==========
        public static final Item SPELLBOOK = registerItem("spellbook",
                        new SpellbookItem(new FabricItemSettings().stacksTo(1).rarity(Rarity.UNCOMMON)));

        public static final Item STAFF = registerItem("staff",
                        new StaffItem(new FabricItemSettings().stacksTo(1).durability(500).rarity(Rarity.UNCOMMON)));

        /**
         * Register an item
         */
        private static Item registerItem(String name, Item item) {
                return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Asura_mod.MOD_ID, name), item);
        }

        /**
         * Initialize all items - call this from Asura_mod.onInitialize()
         */
        public static void register() {
                Asura_mod.LOGGER.info("Registering items for " + Asura_mod.MOD_ID);
        }
}
