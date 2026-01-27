package com.asura_mod.registry;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterials implements Tier {
    RUNIC_IRON(2, 350, 6.0F, 2.0F, 14, () -> Ingredient.of(ModItems.RUNIC_IRON_INGOT)),
    STORMSILVER(2, 250, 8.0F, 2.0F, 18, () -> Ingredient.of(ModItems.STORMSILVER_INGOT)),
    CINDER(3, 1800, 9.0F, 3.0F, 12, () -> Ingredient.of(ModItems.CINDER_OPAL)),
    ASTRAL(4, 2200, 10.0F, 4.0F, 22, () -> Ingredient.of(ModItems.GREATER_LUMEN_PEARL));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(int level, int uses, float speed, float damage, int enchantmentValue,
            Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
