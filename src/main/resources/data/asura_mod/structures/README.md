# Structure NBT Files

This directory contains NBT template files for mod structures.

## How to Create Structure NBT Files

1. **In-Game Method (Recommended)**:
   - Enter a creative world with this mod loaded
   - Build the structure you want
   - Use Structure Block in "Save" mode
   - Set the structure name (e.g., `asura_mod:leyline_shrine/shrine_center`)
   - Save the structure
   - Find the .nbt file in your world's `generated` folder
   - Copy it to this directory

2. **Required Structure Files**:
   - `leyline_shrine/shrine_center.nbt` - Small shrine with 1-2 chests
   - `arcane_ruins/main_room.nbt` - Underground dungeon with corridors
   - `ember_forge/forge_main.nbt` - Nether forge platform
   - `ossuary_rift/rift_main.nbt` - Soul-themed structure
   - `astral_observatory/observatory_main.nbt` - End tower/platform

3. **Structure Guidelines**:
   - Include chest blocks at positions where loot should spawn
   - Set chest loot table to `asura_mod:chests/<structure_name>`
   - Mark jigsaw connections if extending structures later

## Loot Tables Reference
Each structure should reference its loot table:
- `asura_mod:chests/leyline_shrine`
- `asura_mod:chests/arcane_ruins`
- `asura_mod:chests/ember_forge`
- `asura_mod:chests/ossuary_rift`
- `asura_mod:chests/astral_observatory`
