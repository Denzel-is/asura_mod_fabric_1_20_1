package com.asura_mod.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.*;

/**
 * Represents a complete spell graph with nodes and edges.
 * Per SPELLBOOK_SPEC.md section 2.2: SpellGraph record
 */
public class SpellGraph {
    private int version = 1;
    private String displayName = "";
    private final List<SpellNode> nodes = new ArrayList<>();
    private final List<SpellEdge> edges = new ArrayList<>();
    private int nextUid = 0;

    public SpellGraph() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName != null ? displayName : "";
    }

    public List<SpellNode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public List<SpellEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    /**
     * Add a node to the graph
     */
    public SpellNode addNode(SpellNode node) {
        nodes.add(node);
        if (node.getUid() >= nextUid) {
            nextUid = node.getUid() + 1;
        }
        return node;
    }

    /**
     * Generate next unique ID for a node
     */
    public int generateUid() {
        return nextUid++;
    }

    /**
     * Add an edge connecting two nodes
     */
    public SpellEdge addEdge(int sourceUid, int sourcePort, int targetUid, int targetPort) {
        SpellEdge edge = new SpellEdge(sourceUid, sourcePort, targetUid, targetPort);
        edges.add(edge);
        return edge;
    }

    /**
     * Remove a node and all connected edges
     */
    public void removeNode(int uid) {
        nodes.removeIf(n -> n.getUid() == uid);
        edges.removeIf(e -> e.sourceUid() == uid || e.targetUid() == uid);
    }

    /**
     * Get a node by UID
     */
    public SpellNode getNodeByUid(int uid) {
        return nodes.stream().filter(n -> n.getUid() == uid).findFirst().orElse(null);
    }

    /**
     * Compute hash for delta sync
     */
    public int computeHash() {
        int hash = version;
        hash = 31 * hash + displayName.hashCode();
        for (SpellNode node : nodes) {
            hash = 31 * hash + node.getUid();
            hash = 31 * hash + node.getTypeId().hashCode();
        }
        for (SpellEdge edge : edges) {
            hash = 31 * hash + edge.hashCode();
        }
        return hash;
    }

    /**
     * Check if graph is empty
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Serialize to NBT
     */
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("version", version);
        tag.putString("name", displayName);

        ListTag nodeList = new ListTag();
        for (SpellNode node : nodes) {
            nodeList.add(node.toNbt());
        }
        tag.put("nodes", nodeList);

        ListTag edgeList = new ListTag();
        for (SpellEdge edge : edges) {
            CompoundTag edgeTag = new CompoundTag();
            edgeTag.putInt("su", edge.sourceUid());
            edgeTag.putInt("sp", edge.sourcePort());
            edgeTag.putInt("tu", edge.targetUid());
            edgeTag.putInt("tp", edge.targetPort());
            edgeList.add(edgeTag);
        }
        tag.put("edges", edgeList);

        return tag;
    }

    /**
     * Deserialize from NBT
     */
    public static SpellGraph fromNbt(CompoundTag tag) {
        SpellGraph graph = new SpellGraph();
        graph.version = tag.getInt("version");
        graph.displayName = tag.getString("name");

        ListTag nodeList = tag.getList("nodes", Tag.TAG_COMPOUND);
        for (int i = 0; i < nodeList.size(); i++) {
            SpellNode node = SpellNode.fromNbt(nodeList.getCompound(i));
            if (node != null) {
                graph.addNode(node);
            }
        }

        ListTag edgeList = tag.getList("edges", Tag.TAG_COMPOUND);
        for (int i = 0; i < edgeList.size(); i++) {
            CompoundTag edgeTag = edgeList.getCompound(i);
            graph.addEdge(
                    edgeTag.getInt("su"),
                    edgeTag.getInt("sp"),
                    edgeTag.getInt("tu"),
                    edgeTag.getInt("tp"));
        }

        return graph;
    }

    /**
     * Edge connecting two node ports
     */
    public record SpellEdge(int sourceUid, int sourcePort, int targetUid, int targetPort) {
    }
}
