package com.asura_mod.spell.screen;

import com.asura_mod.Asura_mod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * Screen handler types registration.
 * Per SPELLBOOK_SPEC.md section 2.6
 */
public class ModScreenHandlers {

    public static final MenuType<SpellEditorScreenHandler> SPELL_EDITOR_SCREEN_HANDLER = Registry.register(
            BuiltInRegistries.MENU,
            new ResourceLocation(Asura_mod.MOD_ID, "spell_editor"),
            new MenuType<>(SpellEditorScreenHandler::new, FeatureFlags.VANILLA_SET));

    public static void register() {
        Asura_mod.LOGGER.info("Registered screen handlers for " + Asura_mod.MOD_ID);
    }
}
