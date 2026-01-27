package com.asura_mod.registry;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

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
            new Item(new FabricItemSettings().rarity(net.minecraft.item.Rarity.RARE)));

    public static final Item STARFORGED_FRAME = registerItem("starforged_frame",
            new Item(new FabricItemSettings().rarity(net.minecraft.item.Rarity.EPIC)));

    // ========== STRUCTURE LOOT ITEMS ==========
    public static final Item LEYLINE_CATALYST = registerItem("leyline_catalyst",
            new Item(new FabricItemSettings().rarity(net.minecraft.item.Rarity.RARE)));

    public static final Item MAP_FRAGMENT_SHRINE = registerItem("map_fragment_shrine",
            new Item(new FabricItemSettings()));

    public static final Item RUNIC_PLATE = registerItem("runic_plate",
            new Item(new FabricItemSettings()));

    public static final Item RELIC_SCRAP = registerItem("relic_scrap",
            new Item(new FabricItemSettings()));

    public static final Item HEAT_TREATED_CORE = registerItem("heat_treated_core",
            new Item(new FabricItemSettings().rarity(net.minecraft.item.Rarity.UNCOMMON)));

    public static final Item WARDENED_ASH = registerItem("wardened_ash",
            new Item(new FabricItemSettings()));

    public static final Item NULL_CHARM = registerItem("null_charm",
            new Item(new FabricItemSettings().rarity(net.minecraft.item.Rarity.RARE)));

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
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "cinder_upgrade_template.applies_to")))
                        .formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "cinder_upgrade_template.ingredients")))
                        .formatted(Formatting.BLUE),
                Text.translatable(
                        Util.createTranslationKey("upgrade", new Identifier(Asura_mod.MOD_ID, "cinder_upgrade")))
                        .formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "cinder_upgrade_template.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "cinder_upgrade_template.additions_slot_description"))),
                List.of(new Identifier("item/empty_armor_slot_helmet")),
                List.of(new Identifier("item/empty_slot_ingot")));
    }

    /**
     * Create Astral upgrade smithing template
     */
    private static SmithingTemplateItem createAstralUpgradeTemplate() {
        return new SmithingTemplateItem(
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "astral_upgrade_template.applies_to")))
                        .formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "astral_upgrade_template.ingredients")))
                        .formatted(Formatting.BLUE),
                Text.translatable(
                        Util.createTranslationKey("upgrade", new Identifier(Asura_mod.MOD_ID, "astral_upgrade")))
                        .formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "astral_upgrade_template.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item",
                        new Identifier(Asura_mod.MOD_ID, "astral_upgrade_template.additions_slot_description"))),
                List.of(new Identifier("item/empty_armor_slot_chestplate")),
                List.of(new Identifier("item/empty_slot_ingot")));
    }

    /**
     * Register an item
     */
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Asura_mod.MOD_ID, name), item);
    }

    /**
     * Initialize all items - call this from Asura_mod.onInitialize()
     */
    public static void register() {
        Asura_mod.LOGGER.info("Registering items for " + Asura_mod.MOD_ID);
    }
}
