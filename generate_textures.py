from PIL import Image
import os

# Define color scheme for items
textures = {
    # Structure loot items
    'leyline_catalyst': (138, 43, 226),  # Blue-violet (rare catalyst)
    'map_fragment_shrine': (210, 180, 140),  # Tan (paper map)
    'runic_plate': (169, 169, 169),  # Dark gray (metal plate)
    'relic_scrap': (184, 134, 11),  # Dark goldenrod (ancient artifact)
    'heat_treated_core': (255, 140, 0),  # Dark orange (hot core)
    'wardened_ash': (105, 105, 105),  # Dim gray (ash)
    'null_charm': (72, 61, 139),  # Dark slate blue (void charm)
    'cinder_upgrade_template': (205, 92, 92),  # Indian red (smithing template)
    'astral_upgrade_template': (138, 43, 226),  # Blue-violet (astral template)
}

# Create output directory if needed
os.makedirs('src/main/resources/assets/asura_mod/textures/item', exist_ok=True)

# Generate solid color textures
for name, color in textures.items():
    img = Image.new('RGBA', (16, 16), color + (255,))
    img.save(f'src/main/resources/assets/asura_mod/textures/item/{name}.png')
    print(f'Created {name}.png')

print('All structure loot textures created!')
