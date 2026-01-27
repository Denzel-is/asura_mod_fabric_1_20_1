package com.asura_mod.spell;

import com.asura_mod.Asura_mod;
import com.asura_mod.item.StaffItem;
import com.asura_mod.registry.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Debug HUD overlay for spell system.
 * Shows current staff info, charges, and cast status.
 * Per SPELLBOOK_SPEC.md MVP Step 10
 */
public class SpellDebugOverlay {

    private static boolean enabled = true;
    private static String lastCastMessage = "";
    private static long lastCastTime = 0;

    public static void register() {
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            if (!enabled)
                return;

            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.options.hideGui)
                return;

            renderOverlay(guiGraphics, mc);
        });

        Asura_mod.LOGGER.info("Registered SpellDebugOverlay");
    }

    private static void renderOverlay(GuiGraphics graphics, Minecraft mc) {
        Player player = mc.player;
        if (player == null)
            return;

        // Check if holding staff
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        ItemStack staff = null;
        if (mainHand.getItem() instanceof StaffItem) {
            staff = mainHand;
        } else if (offHand.getItem() instanceof StaffItem) {
            staff = offHand;
        }

        if (staff == null)
            return;

        // Render debug info in top-left corner
        int x = 4;
        int y = 4;
        int lineHeight = 10;

        // Background
        graphics.fill(x - 2, y - 2, x + 140, y + lineHeight * 5 + 2, 0x80000000);

        // Staff info
        graphics.drawString(mc.font, "§b[Spell Debug]", x, y, 0xFFFFFF);
        y += lineHeight;

        if (StaffItem.hasImprintedSpell(staff)) {
            SpellGraph graph = StaffItem.getGraph(staff);
            int charges = StaffItem.getCharges(staff);

            String spellName = graph.getDisplayName().isEmpty() ? "Unnamed" : graph.getDisplayName();
            graphics.drawString(mc.font, "Spell: §a" + spellName, x, y, 0xFFFFFF);
            y += lineHeight;

            int nodeCount = graph.getNodes().size();
            int edgeCount = graph.getEdges().size();
            graphics.drawString(mc.font, "Nodes: §e" + nodeCount + " §7| Edges: §e" + edgeCount, x, y, 0xFFFFFF);
            y += lineHeight;

            // Charges bar
            float chargePercent = (float) charges / StaffItem.MAX_CHARGES;
            String chargeColor = chargePercent > 0.5 ? "§a" : (chargePercent > 0.2 ? "§e" : "§c");
            graphics.drawString(mc.font, "Charges: " + chargeColor + charges + "/" + StaffItem.MAX_CHARGES, x, y,
                    0xFFFFFF);
            y += lineHeight;

            // Validation status
            var result = SpellValidator.validate(graph);
            String status = result.isValid() ? "§aValid" : "§cInvalid";
            graphics.drawString(mc.font,
                    "Status: " + status + " §7Cost: §d" + String.format("%.1f", result.totalCost()), x, y, 0xFFFFFF);
        } else {
            graphics.drawString(mc.font, "§7No spell imprinted", x, y, 0xFFFFFF);
        }

        // Show last cast message
        if (System.currentTimeMillis() - lastCastTime < 2000 && !lastCastMessage.isEmpty()) {
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int msgWidth = mc.font.width(lastCastMessage);
            graphics.drawString(mc.font, lastCastMessage, (screenWidth - msgWidth) / 2, 50, 0xFFFFFF);
        }
    }

    /**
     * Show a cast message on screen
     */
    public static void showCastMessage(String message) {
        lastCastMessage = message;
        lastCastTime = System.currentTimeMillis();
    }

    /**
     * Toggle overlay visibility
     */
    public static void toggle() {
        enabled = !enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
