package com.asura_mod.spell.nodes;

import com.asura_mod.Asura_mod;
import com.asura_mod.spell.SpellNode;
import com.asura_mod.spell.SpellNodeRegistry;
import com.asura_mod.spell.SpellNodeRegistry.SpellNodeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/**
 * Registration of base spell nodes.
 * Per SPELLBOOK_SPEC.md section 2.2: Core node types
 */
public class BaseNodes {

    // ========== TRIGGER NODES ==========
    public static final ResourceLocation ON_USE = new ResourceLocation(Asura_mod.MOD_ID, "trigger/on_use");
    public static final ResourceLocation ON_TICK = new ResourceLocation(Asura_mod.MOD_ID, "trigger/on_tick");

    // ========== TARGET NODES ==========
    public static final ResourceLocation SELF = new ResourceLocation(Asura_mod.MOD_ID, "target/self");
    public static final ResourceLocation LOOK_AT = new ResourceLocation(Asura_mod.MOD_ID, "target/look_at");
    public static final ResourceLocation AREA = new ResourceLocation(Asura_mod.MOD_ID, "target/area");

    // ========== EFFECT NODES ==========
    public static final ResourceLocation DAMAGE = new ResourceLocation(Asura_mod.MOD_ID, "effect/damage");
    public static final ResourceLocation HEAL = new ResourceLocation(Asura_mod.MOD_ID, "effect/heal");
    public static final ResourceLocation POTION = new ResourceLocation(Asura_mod.MOD_ID, "effect/potion");
    public static final ResourceLocation LAUNCH = new ResourceLocation(Asura_mod.MOD_ID, "effect/launch");

    // ========== MODIFIER NODES ==========
    public static final ResourceLocation AMPLIFY = new ResourceLocation(Asura_mod.MOD_ID, "modifier/amplify");
    public static final ResourceLocation DELAY = new ResourceLocation(Asura_mod.MOD_ID, "modifier/delay");

    public static void register() {
        // Triggers
        SpellNodeRegistry.register(ON_USE,
                createSimpleType(ON_USE, SpellNodeRegistry.CATEGORY_TRIGGER, "On Use", 0, 1, 0.0f));
        SpellNodeRegistry.register(ON_TICK,
                createSimpleType(ON_TICK, SpellNodeRegistry.CATEGORY_TRIGGER, "On Tick", 0, 1, 0.5f));

        // Targets
        SpellNodeRegistry.register(SELF, createSimpleType(SELF, SpellNodeRegistry.CATEGORY_TARGET, "Self", 1, 1, 0.0f));
        SpellNodeRegistry.register(LOOK_AT,
                createSimpleType(LOOK_AT, SpellNodeRegistry.CATEGORY_TARGET, "Look At", 1, 1, 0.5f));
        SpellNodeRegistry.register(AREA, createSimpleType(AREA, SpellNodeRegistry.CATEGORY_TARGET, "Area", 1, 1, 1.0f));

        // Effects
        SpellNodeRegistry.register(DAMAGE,
                createSimpleType(DAMAGE, SpellNodeRegistry.CATEGORY_EFFECT, "Damage", 1, 0, 2.0f));
        SpellNodeRegistry.register(HEAL, createSimpleType(HEAL, SpellNodeRegistry.CATEGORY_EFFECT, "Heal", 1, 0, 1.5f));
        SpellNodeRegistry.register(POTION,
                createSimpleType(POTION, SpellNodeRegistry.CATEGORY_EFFECT, "Potion Effect", 1, 0, 1.0f));
        SpellNodeRegistry.register(LAUNCH,
                createSimpleType(LAUNCH, SpellNodeRegistry.CATEGORY_EFFECT, "Launch", 1, 0, 1.5f));

        // Modifiers
        SpellNodeRegistry.register(AMPLIFY,
                createSimpleType(AMPLIFY, SpellNodeRegistry.CATEGORY_MODIFIER, "Amplify", 1, 1, 0.5f));
        SpellNodeRegistry.register(DELAY,
                createSimpleType(DELAY, SpellNodeRegistry.CATEGORY_MODIFIER, "Delay", 1, 1, 0.25f));

        Asura_mod.LOGGER.info("Registered {} base spell nodes", SpellNodeRegistry.getAll().size());
    }

    /**
     * Helper to create a simple node type
     */
    private static SpellNodeType createSimpleType(ResourceLocation id, String category, String displayName,
            int inputCount, int outputCount, float baseCost) {
        return new SpellNodeType() {
            @Override
            public ResourceLocation getId() {
                return id;
            }

            @Override
            public String getCategory() {
                return category;
            }

            @Override
            public String getDisplayName() {
                return displayName;
            }

            @Override
            public int getInputCount() {
                return inputCount;
            }

            @Override
            public int getOutputCount() {
                return outputCount;
            }

            @Override
            public float getBaseCost() {
                return baseCost;
            }

            @Override
            public SpellNode createDefault() {
                return new SpellNode(0, id, 0, 0);
            }

            @Override
            public SpellNode fromNbt(CompoundTag tag) {
                return SpellNode.fromNbt(tag);
            }
        };
    }
}
