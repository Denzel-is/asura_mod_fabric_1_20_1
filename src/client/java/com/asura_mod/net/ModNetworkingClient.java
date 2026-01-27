package com.asura_mod.net;

import com.asura_mod.Asura_mod;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

/**
 * Client-side networking registration for Spellbook system.
 * Per SPELLBOOK_SPEC.md section 2.8
 */
public class ModNetworkingClient {

    public static void registerS2CPackets() {
        // SpellStatusS2C - server sends validation errors/warnings/cost
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.SPELL_STATUS_S2C,
                (client, handler, buf, responseSender) -> {
                    int errorCount = buf.readVarInt();
                    String[] errors = new String[errorCount];
                    for (int i = 0; i < errorCount; i++) {
                        errors[i] = buf.readUtf();
                    }

                    int warningCount = buf.readVarInt();
                    String[] warnings = new String[warningCount];
                    for (int i = 0; i < warningCount; i++) {
                        warnings[i] = buf.readUtf();
                    }

                    float cost = buf.readFloat();
                    int cooldownTicks = buf.readVarInt();

                    client.execute(() -> {
                        // TODO: Update SpellEditorScreen with status (MVP Step 6)
                        Asura_mod.LOGGER.info("SpellStatus received: {} errors, {} warnings, cost={}",
                                errorCount, warningCount, cost);
                    });
                });

        // SpellCastFxS2C - server sends visual effects to render
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.SPELL_CAST_FX_S2C,
                (client, handler, buf, responseSender) -> {
                    int fxEventCount = buf.readVarInt();

                    client.execute(() -> {
                        // TODO: Render spell effects (MVP Step 9)
                        Asura_mod.LOGGER.info("SpellCastFx received: {} events", fxEventCount);
                    });
                });

        Asura_mod.LOGGER.info("Registered S2C packets for Spellbook system");
    }

    public static void register() {
        registerS2CPackets();
    }
}
