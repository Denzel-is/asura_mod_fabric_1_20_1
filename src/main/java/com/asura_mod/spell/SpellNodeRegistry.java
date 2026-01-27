package com.asura_mod.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry of all available spell nodes.
 * Per SPELLBOOK_SPEC.md section 2.3: SpellNodeRegistry singleton
 */
public class SpellNodeRegistry {
    private static final Map<ResourceLocation, SpellNodeType> nodeTypes = new HashMap<>();

    // Node categories (from SPELLBOOK_SPEC 2.2)
    public static final String CATEGORY_TRIGGER = "trigger";
    public static final String CATEGORY_MODIFIER = "modifier";
    public static final String CATEGORY_TARGET = "target";
    public static final String CATEGORY_FILTER = "filter";
    public static final String CATEGORY_EFFECT = "effect";
    public static final String CATEGORY_CONTROL = "control";

    public static void register(ResourceLocation id, SpellNodeType type) {
        nodeTypes.put(id, type);
    }

    public static SpellNodeType get(ResourceLocation id) {
        return nodeTypes.get(id);
    }

    public static boolean exists(ResourceLocation id) {
        return nodeTypes.containsKey(id);
    }

    public static Map<ResourceLocation, SpellNodeType> getAll() {
        return java.util.Collections.unmodifiableMap(nodeTypes);
    }

    /**
     * Create a node instance from NBT
     */
    public static SpellNode fromNbt(CompoundTag tag) {
        ResourceLocation typeId = new ResourceLocation(tag.getString("type"));
        SpellNodeType type = get(typeId);
        if (type == null) {
            return null;
        }
        return type.fromNbt(tag);
    }

    /**
     * Represents a registered node type with factory methods
     */
    public interface SpellNodeType {
        ResourceLocation getId();

        String getCategory();

        String getDisplayName();

        int getInputCount();

        int getOutputCount();

        float getBaseCost();

        SpellNode createDefault();

        SpellNode fromNbt(CompoundTag tag);
    }
}
