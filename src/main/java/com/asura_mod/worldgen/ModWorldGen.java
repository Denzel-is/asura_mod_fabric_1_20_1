package com.asura_mod.worldgen;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModWorldGen {

        public static final TagKey<Biome> HAS_STORMSILVER = TagKey.create(Registries.BIOME,
                        new ResourceLocation(Asura_mod.MOD_ID, "has_stormsilver"));

        public static void generateModWorldGen() {

                // OVERWORLD
                BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.AETHER_QUARTZ_ORE_PLACED);

                BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.RUNIC_IRON_ORE_PLACED);

                BiomeModifications.addFeature(BiomeSelectors.tag(HAS_STORMSILVER),
                                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.STORMSILVER_ORE_PLACED);

                // NETHER
                BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                                Biomes.NETHER_WASTES,
                                Biomes.BASALT_DELTAS),
                                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.CINDER_OPAL_ORE_PLACED);

                BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                                Biomes.SOUL_SAND_VALLEY),
                                GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.VOID_SALT_VEIN_PLACED);

                // END (Outer Islands)
                BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                                Biomes.END_HIGHLANDS,
                                Biomes.END_MIDLANDS,
                                Biomes.END_BARRENS),
                                GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.LUMEN_BUD_PATCH_PLACED);
        }
}
