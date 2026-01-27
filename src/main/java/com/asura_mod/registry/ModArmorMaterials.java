package com.asura_mod.registry;

import com.asura_mod.Asura_mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    RUNIC_IRON("runic_iron", 20, new int[] { 2, 5, 6, 2 }, 12, SoundEvents.ARMOR_EQUIP_IRON,
            1.0F, 0.0F, () -> Ingredient.of(ModItems.RUNIC_IRON_INGOT)),

    STORMSILVER("stormsilver", 15, new int[] { 1, 4, 5, 1 }, 15, SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0F, 0.0F, () -> Ingredient.of(ModItems.STORMSILVER_INGOT)),

    CINDER("cinder", 28, new int[] { 3, 6, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_NETHERITE,
            2.0F, 0.1F, () -> Ingredient.of(ModItems.CINDER_OPAL)),

    ASTRAL("astral", 35, new int[] { 3, 6, 8, 3 }, 20, SoundEvents.ARMOR_EQUIP_DIAMOND,
            3.0F, 0.1F, () -> Ingredient.of(ModItems.GREATER_LUMEN_PEARL));

    private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final net.minecraft.sounds.SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability,
            net.minecraft.sounds.SoundEvent equipSound, float toughness, float knockbackResistance,
            Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public net.minecraft.sounds.SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return new ResourceLocation(Asura_mod.MOD_ID, this.name).toString();
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
