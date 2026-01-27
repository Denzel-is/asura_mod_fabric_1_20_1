# Asura Mod — Task Tracker

**modid:** `asura_mod`  
**MC Version:** 1.20.1 (Fabric)  
**Source of Truth:** `SPEC.md`

---

## AG-01: Registry (Blocks & Items)
**Status:** 🟡 In Progress  
**SPEC Sections:** 3.1, 3.2, 3.3  
**Files:**
- `src/main/java/com/asura_mod/registry/ModBlocks.java`
- `src/main/java/com/asura_mod/registry/ModItems.java`
- Updated `Asura_mod.java`

**Done Criteria:**
- All blocks/items registered
- `/give @s asura_mod:<item>` works for each item
- No crashes on mod load

---

## AG-02: Models & Localization
**Status:** ⚪ Pending  
**SPEC Sections:** N/A (implied)  
**Files:**
- `src/main/resources/assets/asura_mod/models/...`
- `src/main/resources/assets/asura_mod/lang/en_us.json`

**Done Criteria:**
- Items render in inventory
- Blocks have proper models/blockstates
- All names localized

---

## AG-03: Worldgen JSON
**Status:** ⚪ Pending  
**SPEC Sections:** 4.1–4.6  
**Files:**
- `src/main/resources/data/asura_mod/worldgen/configured_feature/...`
- `src/main/resources/data/asura_mod/worldgen/placed_feature/...`
- Biome tags

**Done Criteria:**
- Each ore spawns at correct Y/biomes
- Lumen buds spawn in End outer islands only
- Spectator mode verification passed

---

## AG-04: Structures
**Status:** ⚪ Pending  
**SPEC Sections:** 5.1–5.5  
**Files:**
- `src/main/resources/data/asura_mod/structures/*.nbt`
- `src/main/resources/data/asura_mod/worldgen/structure/...`
- `src/main/resources/data/asura_mod/worldgen/structure_set/...`

**Done Criteria:**
- `/locate structure asura_mod:<id>` works for all 5 structures
- Structures spawn in correct biomes/dimensions
- Chests have correct loot tables

---

## AG-05: Loot Tables
**Status:** ⚪ Pending  
**SPEC Sections:** 5.1–5.5, 8  
**Files:**
- `src/main/resources/data/asura_mod/loot_tables/chests/...`

**Done Criteria:**
- Rare items max 1 per chest
- Loot chances match SPEC section 8
- 50+ chest test passed

---

## AG-06: Recipes
**Status:** ⚪ Pending  
**SPEC Sections:** 6.1–6.4  
**Files:**
- `src/main/resources/data/asura_mod/recipes/...`

**Done Criteria:**
- All tools/weapons/armor craftable
- Smithing upgrades work (Cinder Pickaxe, Astral Cloak)
- Smelting recipes functional

---

## AG-07: Balance & Polish
**Status:** ⚪ Pending  
**SPEC Sections:** 9  
**Files:**
- Various stat adjustments

**Done Criteria:**
- Full test suite (SPEC 9) passed
- `walkthrough.md` created
- No major bugs/crashes

---

## Legend
- ⚪ Pending
- 🟡 In Progress
- 🟢 Done
- 🔴 Blocked
