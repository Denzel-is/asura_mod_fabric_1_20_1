package com.asura_mod;

import com.asura_mod.net.ModNetworkingClient;
import com.asura_mod.spell.SpellDebugOverlay;
import com.asura_mod.spell.screen.ModScreenHandlers;
import com.asura_mod.spell.screen.SpellEditorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class Asura_modClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register client-side networking (S2C packets)
		ModNetworkingClient.register();

		// Register screen factories (SPELLBOOK_SPEC 2.6)
		MenuScreens.register(ModScreenHandlers.SPELL_EDITOR_SCREEN_HANDLER, SpellEditorScreen::new);

		// Register debug HUD overlay (SPELLBOOK_SPEC MVP 10)
		SpellDebugOverlay.register();

		Asura_mod.LOGGER.info("Asura Mod client initialized!");
	}
}