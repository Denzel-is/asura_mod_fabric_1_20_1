package com.asura_mod.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Staff Item - stores one imprinted spell and casts it on use.
 * 
 * Per SPELLBOOK_SPEC.md section 1.1:
 * - Staff holds exactly one imprinted spell
 * - Cast on right-click if spell is imprinted
 * 
 * NBT Structure (section 2.3):
 * - ImprintedGraph (Compound) - the spell graph
 * - ImprintedName (String) - spell name/theme
 * - ImprintHash (Int) - quick change check
 */
public class StaffItem extends Item {

    public static final String TAG_IMPRINTED_GRAPH = "ImprintedGraph";
    public static final String TAG_IMPRINTED_NAME = "ImprintedName";
    public static final String TAG_IMPRINT_HASH = "ImprintHash";
    public static final String TAG_CHARGES = "Charges";

    public static final int MAX_CHARGES = 100;

    public StaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!hasImprintedSpell(stack)) {
            if (!level.isClientSide) {
                player.displayClientMessage(
                        Component.translatable("message.asura_mod.staff.no_spell"),
                        true);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            // TODO: Send CastSpellC2S packet (MVP Step 9)
            player.displayClientMessage(
                    Component.translatable("message.asura_mod.staff.cast_placeholder"),
                    true);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    /**
     * Check if this staff has an imprinted spell
     */
    public static boolean hasImprintedSpell(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TAG_IMPRINTED_GRAPH);
    }

    /**
     * Get the imprinted spell graph NBT
     */
    @Nullable
    public static CompoundTag getImprintedGraph(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_IMPRINTED_GRAPH)) {
            return tag.getCompound(TAG_IMPRINTED_GRAPH);
        }
        return null;
    }

    /**
     * Get the imprinted spell graph
     */
    public static com.asura_mod.spell.SpellGraph getGraph(ItemStack stack) {
        CompoundTag graphNbt = getImprintedGraph(stack);
        if (graphNbt != null) {
            return com.asura_mod.spell.SpellGraph.fromNbt(graphNbt);
        }
        return new com.asura_mod.spell.SpellGraph();
    }

    /**
     * Set the imprinted spell graph
     */
    public static void setGraph(ItemStack stack, com.asura_mod.spell.SpellGraph graph) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(TAG_IMPRINTED_GRAPH, graph.toNbt());
        tag.putString(TAG_IMPRINTED_NAME, graph.getDisplayName().isEmpty() ? "Unnamed Spell" : graph.getDisplayName());
        tag.putInt(TAG_IMPRINT_HASH, graph.computeHash());
    }

    /**
     * Get current charges
     */
    public static int getCharges(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_CHARGES)) {
            return tag.getInt(TAG_CHARGES);
        }
        return MAX_CHARGES;
    }

    /**
     * Set charges
     */
    public static void setCharges(ItemStack stack, int charges) {
        stack.getOrCreateTag().putInt(TAG_CHARGES, Math.max(0, Math.min(MAX_CHARGES, charges)));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (hasImprintedSpell(stack)) {
            CompoundTag tag = stack.getTag();
            String spellName = tag != null && tag.contains(TAG_IMPRINTED_NAME)
                    ? tag.getString(TAG_IMPRINTED_NAME)
                    : "Unknown Spell";
            tooltip.add(Component.translatable("tooltip.asura_mod.staff.spell", spellName));
            tooltip.add(Component.translatable("tooltip.asura_mod.staff.charges", getCharges(stack), MAX_CHARGES));
        } else {
            tooltip.add(Component.translatable("tooltip.asura_mod.staff.empty"));
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return hasImprintedSpell(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * getCharges(stack) / MAX_CHARGES);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float f = (float) getCharges(stack) / MAX_CHARGES;
        return java.awt.Color.HSBtoRGB(0.6f * f, 0.8f, 0.9f);
    }
}
