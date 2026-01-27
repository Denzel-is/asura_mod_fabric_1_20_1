# ТЗ (Fabric 1.20.1) — Asura Mod: ресурсы, руды, структуры, лут, экипировка (без спеллов)

**Проект-основа:** архив `asura_mod-template-1.20.1.zip`  
**MC/Fabric:** Minecraft **1.20.1**, Fabric Loader, Fabric API, Java **17**  
**modid / namespace:** `asura_mod` (см. `fabric.mod.json`, `Asura_mod.MOD_ID`)  
**Entry points:** `com.asura_mod.Asura_mod`, `com.asura_mod.Asura_modClient`, `com.asura_mod.Asura_modDataGenerator`  
**Важно:** никаких spellbooks/заклинаний/книг. Только worldgen + предметы/экипировка/пассивки.

---

## 0) Цель и ограничения

### 0.1 Цель
Добавить прогрессию “маг-ресурсов” без магии-спеллов:
- 4 тира ресурсов (T1–T4) с привязкой к измерениям
- новые руды/ресурсы (Overworld/Nether/End)
- 5 структур мира (Overworld/Nether/End)
- лут-таблицы структур с лимитами (анти-фарм)
- инструменты/оружие/броня/плащи/аксессуары (MVP)
- экономика с расходниками (“sinks”), чтобы ценность ресурсов не обесценивалась

### 0.2 Не входит в MVP
- заклинания, книги, spell system
- настоящий полёт (как Elytra). Допустимо: slow-fall/air-control
- jigsaw-данжи (в MVP только template structures; jigsaw — отдельный этап)

### 0.3 Definition of Done (MVP)
- worldgen всех руд/ресурсов соответствует параметрам (раздел 2)
- структуры спавнятся и лут корректный (раздел 3–4)
- базовые крафты и статы предметов реализованы (раздел 5)
- лимиты редкого лута соблюдены (анти-фарм)
- тестирование по чек-листу (раздел 9) проходит

---

## 1) Структура проекта (как в архиве) и куда добавлять файлы

### 1.1 Java
- `src/main/java/com/asura_mod/` — общий код (регистрация блоков/айтемов/worldgen)
- `src/client/java/com/asura_mod/` — клиент + datagen (у тебя уже есть `Asura_modDataGenerator`)
- Рекомендуемые пакеты:
  - `com.asura_mod.registry` — `ModBlocks`, `ModItems`
  - `com.asura_mod.worldgen` — `ModWorldGen`, `ModConfiguredFeatures`, `ModPlacedFeatures`, (опц.) `ModBiomeTags`
  - `com.asura_mod.structure` — (опц.) `ModStructures` (если потребуется кодом)
  - `com.asura_mod.util` — утилиты/константы

### 1.2 Resources (runtime)
- Assets: `src/main/resources/assets/asura_mod/`
  - `lang/en_us.json` (и/или `ru_ru.json`)
  - `models/item`, `models/block`, `blockstates`, `textures/...`
- Data pack: `src/main/resources/data/asura_mod/`
  - worldgen:
    - `worldgen/configured_feature/`
    - `worldgen/placed_feature/`
    - `worldgen/structure/`
    - `worldgen/structure_set/`
  - tags:
    - `tags/worldgen/biome/` (теги биомов под Stormsilver и т.п.)
  - loot:
    - `loot_tables/chests/`
  - recipes:
    - `recipes/`
  - structures templates (NBT):
    - `structures/`  ← сюда кладём `*.nbt` для template-структур

---

## 2) Прогрессия ресурсов (T1–T4)

### 2.1 Тиры
- **T1 (Overworld):**
  - `rune_dust` (item, sink)
  - `aether_quartz_shard` (item)
- **T2 (Overworld):**
  - `raw_runic_iron` → smelt → `runic_iron_ingot`
  - `stormsilver_nugget` → craft → `stormsilver_ingot`
- **T3 (Nether):**
  - `cinder_opal` (gem)
  - `void_salt` (crystal)
- **T4 (End):**
  - `lumen_pearl`
  - `greater_lumen_pearl`
  - `starforged_frame` (ключевой компонент эндгейма, только из End структуры)

### 2.2 Экономические правила (обязательно)
- `rune_dust` участвует в:
  - апгрейдах/ремонте/перероллах (минимум 1 постоянная точка расхода)
- `leyline_catalyst` — редкий расходник из структур; нужен на:
  - сильные апгрейды (smithing) и/или перероллы сокетов
- редкий лут из структур:
  - **template items / ключевые компоненты / чармы** → всегда `max 1` на сундук

---

## 3) Блоки и айтемы (Registry IDs)

> Формат ID: `asura_mod:<id>`.  
> Все ниже — **обязательный минимальный набор** для MVP.

### 3.1 Ores / blocks
- `aether_quartz_ore`
- `deepslate_aether_quartz_ore`
- `runic_iron_ore`
- `deepslate_runic_iron_ore`
- `stormsilver_ore` (без deepslate версии)
- `cinder_opal_ore` (nether)
- `void_salt_vein` (nether ore-like)
- `lumen_bud` (End “bud” block для кластеров)

### 3.2 Items / materials
- `aether_quartz_shard`
- `rune_dust`
- `raw_runic_iron`
- `runic_iron_ingot`
- `stormsilver_nugget`
- `stormsilver_ingot`
- `cinder_opal`
- `void_salt`
- `lumen_pearl`
- `greater_lumen_pearl`
- `starforged_frame`

### 3.3 Structure loot уникальные items
- `leyline_catalyst` (sink)
- `map_fragment_shrine`
- `runic_plate`
- `relic_scrap`
- `heat_treated_core`
- `cinder_upgrade_template` (smithing template item)
- `wardened_ash`
- `null_charm`
- `astral_upgrade_template` (smithing template item)

---

## 4) Worldgen (точные параметры)

### 4.1 Overworld: Aether Quartz (как “обычное золото” по ощущению)
- `veinsPerChunk`: **8**
- `veinSize`: **9**
- height: **triangle Y=-16..32**
- biomes: все overworld

### 4.2 Overworld: Runic Iron
- `veinsPerChunk`: **6**
- `veinSize`: **8**
- height: **triangle Y=-48..16**
- biomes: все overworld (опционально: снижать вес в desert/badlands)

### 4.3 Overworld: Stormsilver
- `veinsPerChunk`: **2**
- `veinSize`: **5**
- height: **uniform Y=64..160**
- biomes: только горные/пиковые/ветреные (через biome tag)

### 4.4 Nether: Cinder Opal
- `veinsPerChunk`: **4**
- `veinSize`: **6**
- height: **uniform Y=10..110**
- biomes: `nether_wastes`, `basalt_deltas` (в basalt_deltas выше вес)

### 4.5 Nether: Void Salt
- `veinsPerChunk`: **1**
- `veinSize`: **4**
- height: **triangle Y=20..80**
- biomes: `soul_sand_valley` only

### 4.6 End: Lumen Bud clusters
- `patchesPerChunk`: **1**
- `rarity`: **1/6 чанков**
- `count per patch`: **3–6 buds**
- height: **Y=40..90**
- location: **outer end islands only**
- drops: `lumen_pearl (0–2)` + шанс **1/8** на `greater_lumen_pearl`

---

## 5) Структуры мира (MVP: template structures + structure_set)

### 5.1 Leyline Shrine (Overworld)
- biomes: plains/forest/meadow + near rivers (в MVP можно просто по biome tags без “near river”)
- frequency: примерно как ruined portal (реже деревни)
- contents: 1–2 сундука + декоративный центр

**Лут:**
- common: `aether_quartz_shard`, `rune_dust`
- uncommon: `map_fragment_shrine`
- rare: `leyline_catalyst` **10%**, **max 1/сундук**

### 5.2 Arcane Ruins (Overworld underground)
- Y=-20..-50
- frequency: как small dungeon, но реже
- mechanics: ловушки, 2–4 сундука

**Лут:**
- common: T1 мелкими стаками
- uncommon: `raw_runic_iron` (мелко), `stormsilver_nugget`
- rare: `runic_plate` **15% max 1**, `relic_scrap` **12% max 1**
> Важно: руины НЕ должны заменить майнинг Runic Iron.

### 5.3 Ember Forge (Nether)
- biomes: `nether_wastes`, `basalt_deltas`
- frequency: реже bastion
- placement: на лавовых платформах

**Лут:**
- common: `cinder_opal`
- rare: `heat_treated_core` **10% max 1**
- very rare: `cinder_upgrade_template` **3–5% max 1**

### 5.4 Ossuary Rift (Nether)
- biome: `soul_sand_valley`
- лут:
  - common: `void_salt`
  - rare: `wardened_ash` (max 1–2)
  - very rare: `null_charm` (max 1)

### 5.5 Astral Observatory (End, outer islands)
- frequency: как end city, но чуть чаще и меньше
- placement: мосты над пустотой

**Лут:**
- common: `lumen_pearl`
- uncommon: `greater_lumen_pearl`
- rare: `starforged_frame` (max 1)
- rare: `astral_upgrade_template` (max 1)

---

## 6) Предметы и крафты (MVP)

### 6.1 Инструменты
**Runic Tool Set** (pickaxe/axe/shovel/hoe)
- material: `runic_iron_ingot`
- базовая логика: чуть выше iron durability
- пассивка: шанс +1 `rune_dust` при добыче модовых руд
- recipe: vanilla shaped

**Stormsilver Tools**
- material: `stormsilver_ingot`
- логика: выше mining speed, ниже durability
- recipe: vanilla shaped

**Cinder Pickaxe (smithing upgrade)**
- base: Runic Pickaxe
- smithing: `cinder_upgrade_template` + `cinder_opal`
- эффект: бонус на nether blocks (netherrack/basalt/blackstone и т.п.), без универсального “best pick”

### 6.2 Оружие
**Runic Blade**
- recipe: sword pattern + один компонент заменить на `aether_quartz_shard`

**Stormsilver Rapier**
- recipe: custom shaped
- роль: высокая скорость атаки, ниже базовый урон

**Void Salt Dagger**
- recipe: short shaped
- роль: utility (шанс снять позитивный эффект)

### 6.3 Броня/плащи
**Runic Armor (T2)**
- material: Runic Iron + Rune Dust
- пассивки: лёгкая “стабилизация/regen” (через атрибуты/эффекты)
- крафт: smithing upgrade или дополнительные ингредиенты

**Stormsilver Cloak (T2)**
- slot: chest (низкая броня)
- material: Stormsilver + Aether Quartz + Leather/Phantom Membrane
- пассивки: скорость/лёгкий прыжок

**Cinder Mantle (T3)**
- material: Cinder Opal + Runic Plate + (временно) Phantom Membrane
- пассивки: частичный fire resistance (не полный)

**Astral Cloak (T4)**
- material: Lumen Pearls + Starforged Frame + Phantom Membrane
- пассивки: slow-fall + air-control (не полёт)

### 6.4 Аксессуары (экономически важные)
- `rune_pouch` (QoL + sink на крафт)
- `leyline_compass` (дорогой, чтобы не ломал exploration)
- `relic_socket` + `relic_gem` (переролл = rune_dust + leyline_catalyst)

> Примечание: аксессуары можно вынести во 2-й этап, если MVP перегружен.

---

## 7) Реализация: что делаем в Java, что делаем в JSON

### 7.1 Обязательное в Java
1) `ModBlocks` — регистрация блоков руд/будов
2) `ModItems` — регистрация всех items
3) Вызовы регистрации из `Asura_mod#onInitialize()`
4) (Если нужно) подключение worldgen через `BiomeModifications`  
   - либо полностью datapack-ом, если подключение сделано через placed_feature + biome tags

### 7.2 Обязательное в data/ (JSON)
- worldgen configured/placed features для каждой руды/ресурса
- biome tags под Stormsilver (mountains)
- structure/structure_set json
- loot tables для сундуков структур
- recipes (shaped/shapeless/smithing)

---

## 8) Лут-лимиты (анти-фарм) — строго

- `leyline_catalyst`: max 1/сундук, шанс 10%
- `cinder_upgrade_template`: max 1/сундук, шанс 3–5%
- `astral_upgrade_template`: max 1/сундук
- `starforged_frame`: max 1/сундук
- `null_charm`: max 1/сундук
- `runic_plate`, `relic_scrap`, `heat_treated_core`: max 1/сундук

---

## 9) Тестирование (команды)

### 9.1 Worldgen
- проверить высоты/биомы в креативе + spectator:
  - Overworld: Aether Quartz, Runic Iron, Stormsilver (mountain only)
  - Nether: Cinder Opal, Void Salt (soul sand valley only)
  - End: Lumen buds outer islands only

### 9.2 Структуры
- `/locate structure asura_mod:<id>`
- `/place structure asura_mod:<id>`
- Проверить сундуки и лут-лимиты.

---

# 10) Как отдавать это ТЗ “antigravity” и работать с ним без потери контекста

## 10.1 Один файл-источник правды
Создай в корне проекта файл:
- `SPEC.md` (или `docs/SPEC.md`) — **копия этого ТЗ**.

Плюс (очень желательно):
- `TASKS.md` — список задач с ID и статусом
- `CHANGELOG.md` — изменения требований (что поменялось и почему)

## 10.2 Правило общения с antigravity
1) **Один раз** даёшь antigravity файл `SPEC.md` целиком.
2) Далее общаешься через задачи с ID, ссылаясь на разделы SPEC:
   - “Сделай задачу AG-03: реализовать worldgen JSON по разделу 4.1–4.5 и добавить в `src/main/resources/data/asura_mod/worldgen/...`”
3) Любые изменения требований — только через `CHANGELOG.md` + правку SPEC (и ссылку на пункт).

## 10.3 Шаблон промпта для antigravity (копируй-вставь)
**Роль:** ты техлид Fabric 1.20.1 (Java). Работай строго по `SPEC.md` как по источнику правды.  
**Ограничения:** без spellbooks/заклинаний. Не менять modid (`asura_mod`).  
**Процесс:**
- Перед началом задачи: назови, какие файлы будешь создавать/править и где они лежат.
- Делай работу маленькими PR-кусочками: по 1–2 подсистемы за раз.
- После выполнения: дай чек-лист теста (команды / ожидаемый результат).

**Задача:** <вставь AG-xx>  
**Ссылки на SPEC:** <например “Раздел 3.1–3.3, 4.1–4.3”>  
**Ожидаемый результат:** список файлов + их содержимое/патчи.

## 10.4 Как “просто говорить что делать”, чтобы antigravity не забывал
Используй всегда один формат:
- **AG-XX (цель)**  
- **SPEC ссылки (разделы)**  
- **Файлы (куда/что)**  
- **Критерий готовности (как проверить)**

Пример:
- AG-04: добавить лут-таблицы для Leyline Shrine и Arcane Ruins  
  SPEC: 5.1, 5.2, 8  
  Files: `data/asura_mod/loot_tables/chests/leyline_shrine.json`, `.../arcane_ruins.json`  
  Done: открыть 50 сундуков через /place и убедиться в max 1 для редких

---

## 11) Список задач (backlog) — минимальный порядок

- **AG-01:** Registry блоков/айтемов (ModBlocks/ModItems), подключить в `Asura_mod#onInitialize`
- **AG-02:** Блок- и item-модели/локализация (минимум en_us)
- **AG-03:** Worldgen JSON (configured + placed + biome tags)
- **AG-04:** Структуры: NBT templates + structure/structure_set JSON
- **AG-05:** Loot tables сундуков + лимиты
- **AG-06:** Recipes (shaped/shapeless/smithing)
- **AG-07:** Баланс-полировка (durability/speed/шансы лута) по результатам тестов

