# CHANGELOG_SPELLBOOK.md

## [Unreleased]

### MVP Step 1 - Items + Recipes + Models + Lang (2026-01-27)
**Added:**
- `SpellbookItem.java` - opens Astral Scriptorium (placeholder)
- `StaffItem.java` - stores imprinted spell, shows charges bar
- Item models: `spellbook.json`, `staff.json`
- Recipes: `spellbook.json`, `staff.json`
- Localization: EN + RU (13 keys for Spellbook system)
- Creative tab integration

**Technical:**
- Registered in `ModItems.java`
- Added to `ModItemGroups.java`

---

### MVP Steps 2-5 - Networking + Graph + Nodes + Validator (2026-01-27)
**Added:**
- `net/ModNetworking.java` - C2S packets (save_graph, imprint_staff, cast_spell, request_status)
- `net/ModNetworkingClient.java` - S2C packets (spell_status, spell_cast_fx)
- `Asura_modClient.java` - Client entrypoint for S2C registration
- `spell/SpellGraph.java` - Full graph model with NBT serialization
- `spell/SpellNode.java` - Node instance (uid, type, position, params)
- `spell/SpellNodeRegistry.java` - Registry for node types
- `spell/SpellValidator.java` - Validation with errors/warnings/cost
- `spell/nodes/BaseNodes.java` - 11 base nodes:
  - **Triggers:** on_use, on_tick
  - **Targets:** self, look_at, area
  - **Effects:** damage, heal, potion, launch
  - **Modifiers:** amplify, delay

**Technical:**
- Integrated in `Asura_mod.java` and `Asura_modClient.java`

---

### MVP Steps 6-9 - UI + Save + Imprint + Cast (2026-01-27)
**Added:**
- `spell/screen/SpellEditorScreen.java` - Full GUI with:
  - Canvas with pan/zoom, grid rendering
  - Node visualization with category colors
  - Edge drawing, node selection/dragging
  - Category palette buttons (Triggers, Targets, Effects, Modifiers)
  - Save/Validate buttons
- `spell/screen/SpellEditorScreenHandler.java` - Server container
- `spell/screen/ModScreenHandlers.java` - MenuType registration

**Implemented packet handlers:**
- `SaveGraphC2S` - saves spell graph to spellbook NBT
- `ImprintToStaffC2S` - validates and imprints spell to staff
- `CastSpellC2S` - decrements charges, placeholder execution
- `RequestStatusC2S` - validates and reports errors/cost

**Technical:**
- `SpellbookItem` opens SimpleMenuProvider on right-click
- `StaffItem.setGraph()`/`getGraph()` for spell imprinting
- 6 new localization keys (saved, imprinted, cast, empty, no_charges)

---

### MVP Step 10 - Debug Overlay (2026-01-27)
**Added:**
- `SpellDebugOverlay.java` (client) - HUD overlay showing:
  - Staff spell name
  - Node/edge count
  - Charges bar with color coding
  - Validation status + cost
  - Cast message (temporary)

**Technical:**
- Registered via `HudRenderCallback` in `Asura_modClient.java`
- Toggle via `SpellDebugOverlay.toggle()`

---

## ✅ MVP COMPLETE

*Per SPELLBOOK_SPEC.md, all 10/10 MVP steps complete!*

### Summary of MVP Features:
1. **Items:** Spellbook + Staff with recipes/textures
2. **Networking:** 4 C2S + 2 S2C packets
3. **Graph Model:** SpellGraph, SpellNode, SpellEdge
4. **Node Registry:** 11 base nodes across 4 categories
5. **Validator:** Error/warning/cost calculation
6. **UI:** SpellEditorScreen with canvas/palette
7. **Save:** Spellbook NBT persistence
8. **Imprint:** Staff spell transfer
9. **Cast:** Charge-based casting (placeholder execution)
10. **Debug:** HUD overlay for staff info


