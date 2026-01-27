package com.asura_mod.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/**
 * Base class for a spell node instance.
 * Per SPELLBOOK_SPEC.md section 2.2: SpellNode record/class
 */
public class SpellNode {
    private final int uid;
    private final ResourceLocation typeId;
    private float posX;
    private float posY;
    private CompoundTag params;

    public SpellNode(int uid, ResourceLocation typeId, float posX, float posY) {
        this.uid = uid;
        this.typeId = typeId;
        this.posX = posX;
        this.posY = posY;
        this.params = new CompoundTag();
    }

    public int getUid() {
        return uid;
    }

    public ResourceLocation getTypeId() {
        return typeId;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    public CompoundTag getParams() {
        return params;
    }

    public void setParams(CompoundTag params) {
        this.params = params != null ? params : new CompoundTag();
    }

    public SpellNodeRegistry.SpellNodeType getType() {
        return SpellNodeRegistry.get(typeId);
    }

    /**
     * Serialize to NBT
     */
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("uid", uid);
        tag.putString("type", typeId.toString());
        tag.putFloat("x", posX);
        tag.putFloat("y", posY);
        if (!params.isEmpty()) {
            tag.put("params", params);
        }
        return tag;
    }

    /**
     * Deserialize from NBT
     */
    public static SpellNode fromNbt(CompoundTag tag) {
        int uid = tag.getInt("uid");
        ResourceLocation typeId = new ResourceLocation(tag.getString("type"));
        float x = tag.getFloat("x");
        float y = tag.getFloat("y");

        SpellNode node = new SpellNode(uid, typeId, x, y);
        if (tag.contains("params")) {
            node.setParams(tag.getCompound("params"));
        }
        return node;
    }
}
