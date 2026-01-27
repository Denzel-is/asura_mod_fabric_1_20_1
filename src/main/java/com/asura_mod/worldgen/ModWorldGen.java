package com.asura_mod.worldgen;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGen {

    public static final TagKey<Biome> HAS_STORMSILVER = TagKey.of(RegistryKeys.BIOME,
            new Identifier(Asura_mod.MOD_ID, "has_stormsilver"));

    public static void generateModWorldGen() {

        // OVERWORLD
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.AETHER_QUARTZ_ORE_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.RUNIC_IRON_ORE_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.tag(HAS_STORMSILVER),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STORMSILVER_ORE_PLACED);

        // NETHER
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.NETHER_WASTES,
                BiomeKeys.BASALT_DELTAS),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.CINDER_OPAL_ORE_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.SOUL_SAND_VALLEY),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.VOID_SALT_VEIN_PLACED);

        // END (Outer Islands)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.END_HIGHLANDS,
                BiomeKeys.END_MIDLANDS,
                BiomeKeys.END_BARRENS),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.LUMEN_BUD_PATCH_PLACED);
    }
}
