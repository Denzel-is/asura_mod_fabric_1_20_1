from PIL import Image
import os

# Define block textures (simple ore overlays on base textures)
# Stone base: gray (128, 128, 128)
# Deepslate base: dark gray (64, 64, 64)
# Netherrack base: red-brown (112, 48, 32)

def create_ore_texture(base_color, ore_color, filename):
    """Create a simple ore texture with base color and ore veins"""
    img = Image.new('RGBA', (16, 16), base_color + (255,))
    pixels = img.load()
    
    # Add ore "veins" as scattered pixels
    ore_positions = [(4, 3), (5, 3), (3, 4), (4, 4), (5, 4), (4, 5),
                     (10, 8), (11, 8), (10, 9), (11, 9),
                     (7, 12), (8, 12), (7, 13)]
    
    for x, y in ore_positions:
        if 0 <= x < 16 and 0 <= y < 16:
            pixels[x, y] = ore_color + (255,)
    
    img.save(f'src/main/resources/assets/asura_mod/textures/block/{filename}')
    print(f'Created {filename}')

# Create output directory
os.makedirs('src/main/resources/assets/asura_mod/textures/block', exist_ok=True)

# Stone ores
create_ore_texture((128, 128, 128), (184, 224, 255), 'aether_quartz_ore.png')  # Light blue ore
create_ore_texture((128, 128, 128), (192, 192, 192), 'runic_iron_ore.png')  # Silver ore
create_ore_texture((128, 128, 128), (240, 240, 255), 'stormsilver_ore.png')  # Bright silver ore

# Deepslate ores
create_ore_texture((64, 64, 64), (184, 224, 255), 'deepslate_aether_quartz_ore.png')
create_ore_texture((64, 64, 64), (192, 192, 192), 'deepslate_runic_iron_ore.png')

# Nether ores
create_ore_texture((112, 48, 32), (255, 99, 71), 'cinder_opal_ore.png')  # Orange-red ore
create_ore_texture((112, 48, 32), (75, 0, 130), 'void_salt_vein.png')  # Purple ore

# Lumen bud (cross texture - simple glowing crystal)
lumen_img = Image.new('RGBA', (16, 16), (147, 112, 219) + (255,))  # Purple
lumen_img.save('src/main/resources/assets/asura_mod/textures/block/lumen_bud.png')
print('Created lumen_bud.png')

print('All block textures created!')
