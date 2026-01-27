package com.asura_mod.spell.screen;

import com.asura_mod.spell.SpellGraph;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

/**
 * Screen handler for the spell editor.
 * Per SPELLBOOK_SPEC.md section 2.6
 */
public class SpellEditorScreenHandler extends AbstractContainerMenu {

    private SpellGraph graph;
    private int spellbookSlot;

    // Client constructor
    public SpellEditorScreenHandler(int syncId, Inventory playerInventory) {
        super(ModScreenHandlers.SPELL_EDITOR_SCREEN_HANDLER, syncId);
        this.graph = new SpellGraph();
        this.spellbookSlot = -1;
    }

    // Server constructor
    public SpellEditorScreenHandler(int syncId, Inventory playerInventory, SpellGraph graph, int spellbookSlot) {
        super(ModScreenHandlers.SPELL_EDITOR_SCREEN_HANDLER, syncId);
        this.graph = graph != null ? graph : new SpellGraph();
        this.spellbookSlot = spellbookSlot;
    }

    public SpellGraph getGraph() {
        return graph;
    }

    public void setGraph(SpellGraph graph) {
        this.graph = graph;
    }

    public int getSpellbookSlot() {
        return spellbookSlot;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
