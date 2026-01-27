package com.asura_mod.worldgen;

import com.asura_mod.Asura_mod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> AETHER_QUARTZ_ORE_PLACED = registerKey("aether_quartz_ore_placed");
    public static final RegistryKey<PlacedFeature> RUNIC_IRON_ORE_PLACED = registerKey("runic_iron_ore_placed");
    public static final RegistryKey<PlacedFeature> STORMSILVER_ORE_PLACED = registerKey("stormsilver_ore_placed");

    public static final RegistryKey<PlacedFeature> CINDER_OPAL_ORE_PLACED = registerKey("cinder_opal_ore_placed");
    public static final RegistryKey<PlacedFeature> VOID_SALT_VEIN_PLACED = registerKey("void_salt_vein_placed");

    public static final RegistryKey<PlacedFeature> LUMEN_BUD_PATCH_PLACED = registerKey("lumen_bud_patch_placed");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Asura_mod.MOD_ID, name));
    }
}
