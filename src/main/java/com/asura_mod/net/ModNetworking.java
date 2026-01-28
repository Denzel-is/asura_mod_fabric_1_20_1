package com.asura_mod.net;

import com.asura_mod.Asura_mod;
import com.asura_mod.item.SpellbookItem;
import com.asura_mod.item.StaffItem;
import com.asura_mod.spell.SpellGraph;
import com.asura_mod.spell.SpellValidator;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Networking registration for Spellbook system.
 * Per SPELLBOOK_SPEC.md section 2.8
 */
public class ModNetworking {

    // C2S Packet IDs
    public static final ResourceLocation SAVE_GRAPH_C2S = new ResourceLocation(Asura_mod.MOD_ID, "save_graph");
    public static final ResourceLocation IMPRINT_TO_STAFF_C2S = new ResourceLocation(Asura_mod.MOD_ID, "imprint_staff");
    public static final ResourceLocation CAST_SPELL_C2S = new ResourceLocation(Asura_mod.MOD_ID, "cast_spell");
    public static final ResourceLocation REQUEST_STATUS_C2S = new ResourceLocation(Asura_mod.MOD_ID, "request_status");

    // S2C Packet IDs
    public static final ResourceLocation SPELL_STATUS_S2C = new ResourceLocation(Asura_mod.MOD_ID, "spell_status");
    public static final ResourceLocation SPELL_CAST_FX_S2C = new ResourceLocation(Asura_mod.MOD_ID, "spell_cast_fx");

    public static void registerC2SPackets() {
        // SaveGraphC2S - client sends spell graph to save to spellbook
        ServerPlayNetworking.registerGlobalReceiver(SAVE_GRAPH_C2S, (server, player, handler, buf, responseSender) -> {
            int bookSlot = buf.readVarInt();
            CompoundTag graphNbt = buf.readNbt();

            server.execute(() -> {
                // Validate slot
                ItemStack stack = player.getInventory().getItem(bookSlot);
                if (stack.isEmpty() || !(stack.getItem() instanceof SpellbookItem)) {
                    player.displayClientMessage(Component.literal("§cInvalid spellbook slot!"), true);
                    return;
                }

                // Save graph to spellbook NBT
                if (graphNbt != null) {
                    SpellGraph graph = SpellGraph.fromNbt(graphNbt);
                    SpellbookItem.setGraph(stack, graph);
                    player.displayClientMessage(
                            Component.translatable("message.asura_mod.spellbook.saved"),
                            true);
                    Asura_mod.LOGGER.info("Saved spell graph for {}", player.getName().getString());
                }
            });
        });

        // ImprintToStaffC2S - client requests imprint spell to staff
        ServerPlayNetworking.registerGlobalReceiver(IMPRINT_TO_STAFF_C2S,
                (server, player, handler, buf, responseSender) -> {
                    int bookSlot = buf.readVarInt();
                    int staffSlot = buf.readVarInt();

                    server.execute(() -> {
                        ItemStack bookStack = player.getInventory().getItem(bookSlot);
                        ItemStack staffStack = player.getInventory().getItem(staffSlot);

                        if (!(bookStack.getItem() instanceof SpellbookItem)) {
                            player.displayClientMessage(Component.literal("§cInvalid spellbook!"), true);
                            return;
                        }
                        if (!(staffStack.getItem() instanceof StaffItem)) {
                            player.displayClientMessage(Component.literal("§cInvalid staff!"), true);
                            return;
                        }

                        // Get graph from spellbook
                        SpellGraph graph = SpellbookItem.getGraph(bookStack);
                        if (graph.isEmpty()) {
                            player.displayClientMessage(Component.literal("§cSpellbook is empty!"), true);
                            return;
                        }

                        // Validate graph
                        var result = SpellValidator.validate(graph);
                        if (!result.isValid()) {
                            player.displayClientMessage(Component.literal("§c" + result.errors().get(0)), true);
                            return;
                        }

                        // Imprint to staff
                        StaffItem.setGraph(staffStack, graph);
                        StaffItem.setCharges(staffStack, 10); // Initial charges

                        player.displayClientMessage(
                                Component.translatable("message.asura_mod.staff.imprinted"),
                                true);
                        Asura_mod.LOGGER.info("Imprinted spell to staff for {}", player.getName().getString());
                    });
                });

        // CastSpellC2S - client requests spell cast
        ServerPlayNetworking.registerGlobalReceiver(CAST_SPELL_C2S, (server, player, handler, buf, responseSender) -> {
            int hand = buf.readVarInt(); // 0=main, 1=off

            server.execute(() -> {
                ItemStack stack = hand == 0 ? player.getMainHandItem() : player.getOffhandItem();
                if (!(stack.getItem() instanceof StaffItem)) {
                    return;
                }

                // Check charges
                int charges = StaffItem.getCharges(stack);
                if (charges <= 0) {
                    player.displayClientMessage(
                            Component.translatable("message.asura_mod.staff.no_charges"),
                            true);
                    return;
                }

                // Get graph
                SpellGraph graph = StaffItem.getGraph(stack);
                if (graph.isEmpty()) {
                    player.displayClientMessage(
                            Component.translatable("message.asura_mod.staff.empty"),
                            true);
                    return;
                }

                // Execute spell effects (to be implemented based on spell graph nodes)
                // For now, just consume charge and log the cast
                StaffItem.setCharges(stack, charges - 1);
                player.displayClientMessage(
                        Component.translatable("message.asura_mod.staff.cast"),
                        true);
                Asura_mod.LOGGER.info("{} cast spell, {} charges left", player.getName().getString(), charges - 1);

            });
        });

        // RequestStatusC2S - client requests spell validation status
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_STATUS_C2S,
                (server, player, handler, buf, responseSender) -> {
                    int bookSlot = buf.readVarInt();

                    server.execute(() -> {
                        ItemStack stack = player.getInventory().getItem(bookSlot);
                        if (!(stack.getItem() instanceof SpellbookItem)) {
                            return;
                        }

                        SpellGraph graph = SpellbookItem.getGraph(stack);
                        var result = SpellValidator.validate(graph);

                        // S2C packet for UI updates will be implemented when spell editor needs
                        // real-time validation
                        // For now, direct message is sufficient for testing
                        if (result.isValid()) {
                            player.displayClientMessage(
                                    Component.literal("§aValid! Cost: " + result.totalCost()),
                                    true);
                        } else {
                            player.displayClientMessage(
                                    Component.literal("§c" + String.join(", ", result.errors())),
                                    true);
                        }
                    });
                });

        Asura_mod.LOGGER.info("Registered C2S packets for Spellbook system");
    }

    public static void register() {
        registerC2SPackets();
        Asura_mod.LOGGER.info("Registered networking for " + Asura_mod.MOD_ID);
    }
}
