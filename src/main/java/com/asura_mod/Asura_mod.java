package com.asura_mod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura_mod.registry.ModBlocks;
import com.asura_mod.registry.ModItems;
import com.asura_mod.worldgen.ModWorldGen;

public class Asura_mod implements ModInitializer {
	public static final String MOD_ID = "asura_mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Register blocks and items (SPEC AG-01)
		ModBlocks.register();
		ModItems.register();

		// Register WorldGen (SPEC AG-03)
		ModWorldGen.generateModWorldGen();

		LOGGER.info("Asura Mod initialized successfully!");
	}
}