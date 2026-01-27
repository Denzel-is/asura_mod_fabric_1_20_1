package com.asura_mod.worldgen;

import com.asura_mod.Asura_mod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> AETHER_QUARTZ_ORE_PLACED = registerKey("aether_quartz_ore_placed");
    public static final ResourceKey<PlacedFeature> RUNIC_IRON_ORE_PLACED = registerKey("runic_iron_ore_placed");
    public static final ResourceKey<PlacedFeature> STORMSILVER_ORE_PLACED = registerKey("stormsilver_ore_placed");

    public static final ResourceKey<PlacedFeature> CINDER_OPAL_ORE_PLACED = registerKey("cinder_opal_ore_placed");
    public static final ResourceKey<PlacedFeature> VOID_SALT_VEIN_PLACED = registerKey("void_salt_vein_placed");

    public static final ResourceKey<PlacedFeature> LUMEN_BUD_PATCH_PLACED = registerKey("lumen_bud_patch_placed");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Asura_mod.MOD_ID, name));
    }
}
