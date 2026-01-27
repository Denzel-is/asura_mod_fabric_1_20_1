package com.asura_mod.spell;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates spell graphs for correctness.
 * Per SPELLBOOK_SPEC.md section 2.5: SpellValidator
 */
public class SpellValidator {

    /**
     * Validation result containing errors, warnings, and computed cost
     */
    public record ValidationResult(
            List<String> errors,
            List<String> warnings,
            float totalCost,
            int cooldownTicks,
            boolean isValid) {
        public static ValidationResult valid(float cost, int cooldown) {
            return new ValidationResult(List.of(), List.of(), cost, cooldown, true);
        }

        public static ValidationResult invalid(List<String> errors) {
            return new ValidationResult(errors, List.of(), 0, 0, false);
        }
    }

    /**
     * Validate a spell graph
     */
    public static ValidationResult validate(SpellGraph graph) {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        float totalCost = 0;

        // Check for empty graph
        if (graph.isEmpty()) {
            errors.add("Spell graph is empty");
            return ValidationResult.invalid(errors);
        }

        // Check all nodes have registered types
        SpellNode triggerNode = null;
        for (SpellNode node : graph.getNodes()) {
            var type = node.getType();
            if (type == null) {
                errors.add("Unknown node type: " + node.getTypeId());
                continue;
            }

            totalCost += type.getBaseCost();

            // Find trigger node
            if (SpellNodeRegistry.CATEGORY_TRIGGER.equals(type.getCategory())) {
                if (triggerNode != null) {
                    warnings.add("Multiple trigger nodes found, only first will be used");
                } else {
                    triggerNode = node;
                }
            }
        }

        // Must have at least one trigger node
        if (triggerNode == null) {
            errors.add("No trigger node found. Add a trigger to start the spell.");
        }

        // Validate edges reference existing nodes
        for (var edge : graph.getEdges()) {
            if (graph.getNodeByUid(edge.sourceUid()) == null) {
                errors.add("Edge references non-existent source node: " + edge.sourceUid());
            }
            if (graph.getNodeByUid(edge.targetUid()) == null) {
                errors.add("Edge references non-existent target node: " + edge.targetUid());
            }
        }

        // TODO: Add more validation rules (cycles, type compatibility, etc.)

        if (!errors.isEmpty()) {
            return new ValidationResult(errors, warnings, 0, 0, false);
        }

        // Calculate cooldown based on cost (simple formula for MVP)
        int cooldownTicks = (int) (totalCost * 10); // 0.5 sec per 1 cost unit

        return new ValidationResult(errors, warnings, totalCost, cooldownTicks, true);
    }
}
