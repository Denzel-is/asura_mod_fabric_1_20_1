package com.asura_mod.item;

import com.asura_mod.spell.SpellGraph;
import com.asura_mod.spell.screen.SpellEditorScreenHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Spellbook Item - opens the Spell Editor screen when right-clicked.
 * Stores the current spell graph in NBT.
 * 
 * Per SPELLBOOK_SPEC.md section 1.1
 */
public class SpellbookItem extends Item {

    public static final String TAG_SPELL_GRAPH = "SpellGraph";

    public SpellbookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // Load graph from NBT
            SpellGraph graph = getGraph(stack);

            // Open screen
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (syncId, inv, p) -> new SpellEditorScreenHandler(syncId, inv, graph, slot),
                    Component.translatable("screen.asura_mod.spell_editor")));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    /**
     * Get the spell graph from the item stack
     */
    public static SpellGraph getGraph(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_SPELL_GRAPH)) {
            return SpellGraph.fromNbt(tag.getCompound(TAG_SPELL_GRAPH));
        }
        return new SpellGraph();
    }

    /**
     * Save the spell graph to the item stack
     */
    public static void setGraph(ItemStack stack, SpellGraph graph) {
        stack.getOrCreateTag().put(TAG_SPELL_GRAPH, graph.toNbt());
    }
}
