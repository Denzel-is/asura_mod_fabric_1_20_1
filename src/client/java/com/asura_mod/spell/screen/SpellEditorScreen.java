package com.asura_mod.spell.screen;

import com.asura_mod.Asura_mod;
import com.asura_mod.spell.SpellGraph;
import com.asura_mod.spell.SpellNode;
import com.asura_mod.spell.SpellNodeRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Spell editor screen - "Astral Scriptorium"
 * Per SPELLBOOK_SPEC.md section 2.6
 */
public class SpellEditorScreen extends AbstractContainerScreen<SpellEditorScreenHandler> {

    // Graph canvas state
    private float panX = 0;
    private float panY = 0;
    private float zoom = 1.0f;
    private SpellNode selectedNode = null;
    private boolean isDragging = false;

    // UI regions
    private int paletteWidth = 120;
    private int canvasX, canvasY, canvasWidth, canvasHeight;

    public SpellEditorScreen(SpellEditorScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 320;
        this.imageHeight = 200;
    }

    @Override
    protected void init() {
        super.init();

        // Calculate regions
        canvasX = leftPos + paletteWidth + 4;
        canvasY = topPos + 4;
        canvasWidth = imageWidth - paletteWidth - 8;
        canvasHeight = imageHeight - 30;

        // Add palette buttons for each node category
        int buttonY = topPos + 20;
        int buttonHeight = 16;

        addRenderableWidget(
                Button.builder(Component.literal("Triggers"), b -> showCategory(SpellNodeRegistry.CATEGORY_TRIGGER))
                        .bounds(leftPos + 4, buttonY, paletteWidth - 8, buttonHeight).build());
        buttonY += buttonHeight + 2;

        addRenderableWidget(
                Button.builder(Component.literal("Targets"), b -> showCategory(SpellNodeRegistry.CATEGORY_TARGET))
                        .bounds(leftPos + 4, buttonY, paletteWidth - 8, buttonHeight).build());
        buttonY += buttonHeight + 2;

        addRenderableWidget(
                Button.builder(Component.literal("Effects"), b -> showCategory(SpellNodeRegistry.CATEGORY_EFFECT))
                        .bounds(leftPos + 4, buttonY, paletteWidth - 8, buttonHeight).build());
        buttonY += buttonHeight + 2;

        addRenderableWidget(
                Button.builder(Component.literal("Modifiers"), b -> showCategory(SpellNodeRegistry.CATEGORY_MODIFIER))
                        .bounds(leftPos + 4, buttonY, paletteWidth - 8, buttonHeight).build());

        // Bottom buttons
        addRenderableWidget(Button.builder(Component.literal("Save"), b -> saveGraph())
                .bounds(leftPos + 4, topPos + imageHeight - 22, 50, 18).build());

        addRenderableWidget(Button.builder(Component.literal("Validate"), b -> validateGraph())
                .bounds(leftPos + 58, topPos + imageHeight - 22, 60, 18).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Draw background
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF1a1a2e);

        // Draw palette area
        guiGraphics.fill(leftPos + 2, topPos + 2, leftPos + paletteWidth, topPos + imageHeight - 2, 0xFF16213e);

        // Draw canvas area
        guiGraphics.fill(canvasX, canvasY, canvasX + canvasWidth, canvasY + canvasHeight, 0xFF0f0f23);

        // Draw grid on canvas
        drawCanvasGrid(guiGraphics);

        // Draw nodes
        drawNodes(guiGraphics);

        // Draw edges
        drawEdges(guiGraphics);
    }

    private void drawCanvasGrid(GuiGraphics guiGraphics) {
        int gridSize = (int) (20 * zoom);
        if (gridSize < 5)
            gridSize = 5;

        int offsetX = (int) (panX % gridSize);
        int offsetY = (int) (panY % gridSize);

        for (int x = canvasX + offsetX; x < canvasX + canvasWidth; x += gridSize) {
            guiGraphics.fill(x, canvasY, x + 1, canvasY + canvasHeight, 0x20ffffff);
        }
        for (int y = canvasY + offsetY; y < canvasY + canvasHeight; y += gridSize) {
            guiGraphics.fill(canvasX, y, canvasX + canvasWidth, y + 1, 0x20ffffff);
        }
    }

    private void drawNodes(GuiGraphics guiGraphics) {
        SpellGraph graph = menu.getGraph();
        if (graph == null)
            return;

        for (SpellNode node : graph.getNodes()) {
            int nodeX = canvasX + (int) ((node.getPosX() + panX) * zoom);
            int nodeY = canvasY + (int) ((node.getPosY() + panY) * zoom);

            int nodeWidth = 60;
            int nodeHeight = 30;

            // Node background
            int bgColor = getNodeColor(node);
            guiGraphics.fill(nodeX, nodeY, nodeX + nodeWidth, nodeY + nodeHeight, bgColor);

            // Node border
            int borderColor = node == selectedNode ? 0xFFffff00 : 0xFFffffff;
            guiGraphics.renderOutline(nodeX, nodeY, nodeWidth, nodeHeight, borderColor);

            // Node label
            var type = node.getType();
            String label = type != null ? type.getDisplayName() : "???";
            guiGraphics.drawCenteredString(font, label, nodeX + nodeWidth / 2, nodeY + 10, 0xFFffffff);
        }
    }

    private int getNodeColor(SpellNode node) {
        var type = node.getType();
        if (type == null)
            return 0xFF444444;

        return switch (type.getCategory()) {
            case SpellNodeRegistry.CATEGORY_TRIGGER -> 0xFF2d6a4f;
            case SpellNodeRegistry.CATEGORY_TARGET -> 0xFF457b9d;
            case SpellNodeRegistry.CATEGORY_EFFECT -> 0xFF9d4e4e;
            case SpellNodeRegistry.CATEGORY_MODIFIER -> 0xFF7b5e9d;
            case SpellNodeRegistry.CATEGORY_FILTER -> 0xFF9d8b4e;
            case SpellNodeRegistry.CATEGORY_CONTROL -> 0xFF4e7b9d;
            default -> 0xFF444444;
        };
    }

    private void drawEdges(GuiGraphics guiGraphics) {
        SpellGraph graph = menu.getGraph();
        if (graph == null)
            return;

        for (var edge : graph.getEdges()) {
            SpellNode source = graph.getNodeByUid(edge.sourceUid());
            SpellNode target = graph.getNodeByUid(edge.targetUid());
            if (source == null || target == null)
                continue;

            int sx = canvasX + (int) ((source.getPosX() + 60 + panX) * zoom);
            int sy = canvasY + (int) ((source.getPosY() + 15 + panY) * zoom);
            int tx = canvasX + (int) ((target.getPosX() + panX) * zoom);
            int ty = canvasY + (int) ((target.getPosY() + 15 + panY) * zoom);

            // Simple line (no bezier for MVP)
            drawLine(guiGraphics, sx, sy, tx, ty, 0xFFaaaaaa);
        }
    }

    private void drawLine(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int color) {
        // Simple bresenham for MVP
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int steps = Math.max(dx, dy);
        if (steps == 0)
            return;

        for (int i = 0; i <= steps; i++) {
            int x = x1 + (x2 - x1) * i / steps;
            int y = y1 + (y2 - y1) * i / steps;
            guiGraphics.fill(x, y, x + 1, y + 1, color);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(font, title, 4, 6, 0xFFe0aaff);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Check if clicked on canvas
        if (mouseX >= canvasX && mouseX < canvasX + canvasWidth &&
                mouseY >= canvasY && mouseY < canvasY + canvasHeight) {

            // Find clicked node
            SpellGraph graph = menu.getGraph();
            for (SpellNode node : graph.getNodes()) {
                int nodeX = canvasX + (int) ((node.getPosX() + panX) * zoom);
                int nodeY = canvasY + (int) ((node.getPosY() + panY) * zoom);

                if (mouseX >= nodeX && mouseX < nodeX + 60 &&
                        mouseY >= nodeY && mouseY < nodeY + 30) {
                    selectedNode = node;
                    isDragging = true;
                    return true;
                }
            }

            // Deselect
            selectedNode = null;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isDragging && selectedNode != null) {
            selectedNode.setPosition(
                    selectedNode.getPosX() + (float) (deltaX / zoom),
                    selectedNode.getPosY() + (float) (deltaY / zoom));
            return true;
        }

        // Pan canvas with middle mouse
        if (button == 2) {
            panX += deltaX / zoom;
            panY += deltaY / zoom;
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // Zoom
        if (mouseX >= canvasX && mouseX < canvasX + canvasWidth &&
                mouseY >= canvasY && mouseY < canvasY + canvasHeight) {
            zoom = (float) Math.max(0.25, Math.min(2.0, zoom + delta * 0.1));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void showCategory(String category) {
        // Palette UI to show draggable node types - requires node spawning
        // implementation
        Asura_mod.LOGGER.info("Show category: " + category);
    }

    private void saveGraph() {
        // Sends SaveGraphC2S packet to save spell graph to spellbook
        // Note: Packet sending requires client-side network implementation
        Asura_mod.LOGGER.info("Save graph requested");
    }

    private void validateGraph() {
        // Sends RequestStatusC2S packet for server-side validation
        // Note: Packet sending requires client-side network implementation
        Asura_mod.LOGGER.info("Validate graph requested");
    }
}
