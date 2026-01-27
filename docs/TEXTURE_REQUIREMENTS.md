# Asura Mod - Texture Requirements

## AI-Generated Textures (6 готовы)

✅ **Уже созданы** (в `C:\Users\danil\.gemini\antigravity\brain\a75eb22f-8743-4cc1-b3fb-85054c27f921\`):
1. `rune_dust_texture_*.png` → скопировать в `src/main/resources/assets/asura_mod/textures/item/rune_dust.png`
2. `aether_quartz_shard_texture_*.png` → `textures/item/aether_quartz_shard.png`
3. `raw_runic_iron_texture_*.png` → `textures/item/raw_runic_iron.png`
4. `runic_iron_ingot_texture_*.png` → `textures/item/runic_iron_ingot.png`
5. `stormsilver_nugget_texture_*.png` → `textures/item/stormsilver_nugget.png`
6. `stormsilver_ingot_texture_*.png` → `textures/item/stormsilver_ingot.png`

---

## Простые цветные текстуры (автосгенерированы скриптом)

✅ **Созданы Python скриптом** (уже в нужных местах):

### Item Textures
- `cinder_opal.png` - оранжево-красный (255, 99, 71)
- `void_salt.png` - тёмно-фиолетовый (75, 0, 130)
- `lumen_pearl.png` - светло-жёлтый (255, 250, 205)
- `greater_lumen_pearl.png` - золотой (255, 215, 0)
- `starforged_frame.png` - фиолетовый (147, 112, 219)
- `leyline_catalyst.png` - сине-фиолетовый (138, 43, 226)
- `map_fragment_shrine.png` - коричневый (210, 180, 140)
- `runic_plate.png` - серый (169, 169, 169)
- `relic_scrap.png` - тёмно-золотой (184, 134, 11)
- `heat_treated_core.png` - оранжевый (255, 140, 0)
- `wardened_ash.png` - тёмно-серый (105, 105, 105)
- `null_charm.png` - синий (72, 61, 139)
- `cinder_upgrade_template.png` - красный (205, 92, 92)
- `astral_upgrade_template.png` - сине-фиолетовый (138, 43, 226)

### Block Textures
- `aether_quartz_ore.png` - серый камень + голубые вкрапления
- `deepslate_aether_quartz_ore.png` - тёмно-серый + голубые вкрапления
- `runic_iron_ore.png` - серый камень + серебряные вкрапления
- `deepslate_runic_iron_ore.png` - тёмно-серый + серебряные вкрапления
- `stormsilver_ore.png` - серый камень + яркие серебристые вкрапления
- `cinder_opal_ore.png` - красно-коричневый незерак + оранжевые вкрапления
- `void_salt_vein.png` - красно-коричневый незерак + фиолетовые вкрапления
- `lumen_bud.png` - фиолетовый кристалл (147, 112, 219)

---

## Что нужно сделать вручную (опционально)

Если хочешь **улучшить** текстуры:

### Priority 1: Ключевые материалы (видны чаще всего)
1. **Runic Iron Ingot** - может быть с более детальными рунами
2. **Stormsilver Ingot** - добавить эффект молнии/электричества
3. **Lumen Pearl** - сделать более объёмной с градиентом свечения
4. **Starforged Frame** - добавить звёздные частицы/космический эффект

### Priority 2: Редкие предметы (эпичность)
5. **Greater Lumen Pearl** - усиленное свечение
6. **Leyline Catalyst** - магический кристалл с энергией
7. **Cinder Upgrade Template** - шаблон кузнечный стиль (как в vanilla)
8. **Astral Upgrade Template** - космический шаблон

### Priority 3: Блоки руд
9. **Ore textures** - можно сделать как vanilla (overlay поверх камня/deepslate)
10. **Lumen Bud** - кристалл как amethyst clusters

---

## Структура файлов

```
src/main/resources/assets/asura_mod/
├── textures/
│   ├── item/
│   │   ├── rune_dust.png                    (16x16)
│   │   ├── aether_quartz_shard.png          (16x16)
│   │   ├── raw_runic_iron.png               (16x16)
│   │   ├── runic_iron_ingot.png             (16x16)
│   │   ├── stormsilver_nugget.png           (16x16)
│   │   ├── stormsilver_ingot.png            (16x16)
│   │   ├── cinder_opal.png                  (16x16)
│   │   ├── void_salt.png                    (16x16)
│   │   ├── lumen_pearl.png                  (16x16)
│   │   ├── greater_lumen_pearl.png          (16x16)
│   │   ├── starforged_frame.png             (16x16)
│   │   ├── leyline_catalyst.png             (16x16)
│   │   ├── map_fragment_shrine.png          (16x16)
│   │   ├── runic_plate.png                  (16x16)
│   │   ├── relic_scrap.png                  (16x16)
│   │   ├── heat_treated_core.png            (16x16)
│   │   ├── wardened_ash.png                 (16x16)
│   │   ├── null_charm.png                   (16x16)
│   │   ├── cinder_upgrade_template.png      (16x16)
│   │   └── astral_upgrade_template.png      (16x16)
│   │
│   └── block/
│       ├── aether_quartz_ore.png            (16x16)
│       ├── deepslate_aether_quartz_ore.png  (16x16)
│       ├── runic_iron_ore.png               (16x16)
│       ├── deepslate_runic_iron_ore.png     (16x16)
│       ├── stormsilver_ore.png              (16x16)
│       ├── cinder_opal_ore.png              (16x16)
│       ├── void_salt_vein.png               (16x16)
│       └── lumen_bud.png                    (16x16)
```

---

## Рекомендации по стилю

- **Pixel art 16x16** (как в Minecraft)
- **Соблюдать tier-цвета:**
  - T1 (Overworld): голубые/cyan тона
  - T2 (Overworld): серебро/серый
  - T3 (Nether): оранжево-красный + фиолетовый
  - T4 (End): жёлто-белый + космический фиолетовый
- **Руды:** overlay поверх базовой текстуры (stone/deepslate/netherrack)
- **Инготы:** стиль как iron_ingot/gold_ingot
- **Смиsing templates:** стиль как netherite_upgrade

---

## Текущий статус

✅ Все placeholder текстуры созданы (простые цветные квадраты)  
✅ 6 AI-сгенерированных текстур готовы  
⚠️ Можно использовать как есть для тестирования  
📝 Улучшение текстур — опционально, мод работает с placeholders
