# SPELLBOOK_SPEC.md — Fabric 1.20.1 Spellbook + Scratch‑style Spell Editor (Tech Design & Dev Plan)

**Minecraft/Fabric:** 1.20.1 • Java 17 • Fabric API  
**Core idea:** игрок “программирует” заклинание в **Spellbook** через визуальный редактор (ноды), затем **прошивает** в **Staff** (один посох = одно заклинание) и кастует.  
**Сервер‑авторитет:** урон/разрушение/спавн/телепорт/изменение блоков выполняются **только на сервере**. Клиент — UI + визуальные эффекты.

---

## 1) Концепт

### 1.1 Предметы
- **Spellbook** (item): крафтится; **ПКМ** открывает редактор заклинаний.
- **Staff** (item): крафтится; хранит **ровно одно** прошитое заклинание.
- **Явная замена/сброс** (MVP):
  - В редакторе есть режим **Imprint**, который требует **подтверждение** перезаписи.
  - Кнопка **“Очистить посох”** (confirm dialog).
- **Beta:** отдельный блок **Imprinting Table** (книга + посох).

### 1.2 Редактор (Scratch-style)
Экран редактора = **“Астральная мастерская”**:
- **Слева:** палитра нод (категории, поиск, избранное).
- **Центр:** канвас (сеточная “звёздная схема”), drag & drop, соединения.
- **Справа:** параметры выбранной ноды.
- **Снизу:** статус (валидность), стоимость каста, предупреждения, кнопки Save/Imprint/Preview.

**MVP:** линейная цепочка (sequence).  
**Beta:** ориентированный граф (ветвления), циклы только через bounded‑repeat.

### 1.3 Ноды (категории)
- Trigger/Start
- Targeting (self / looking-at / nearest / area / stored point)
- Actions (lightning / explosion / particles / projectile / effect / heal / damage / teleport)
- Modifiers (element / damage / radius / spread / cooldown / repeat / duration)
- Control flow (delay / repeat N / chance % / sequence)

---

# 2) Требуемый результат (2.1–2.10)

## 2.1 Архитектура (пакеты/системы)

### Подсистемы
1) Items  
2) UI (Screen/ScreenHandler + widgets)  
3) Graph Model (nodes/edges/ports + registry)  
4) Serialization (NBT codec + миграции)  
5) Validation (rules + UI errors + server re-check)  
6) Runtime Executor (server tick, budgets)  
7) Networking (C2S/S2C)  
8) Anti‑grief / safety (лимиты, проверки мира)  
9) Localization (en_us + ru_ru)  
10) Debug tooling (команды, логи, dev‑оверлеи)

### Рекомендуемая структура пакетов (пример)
```
com.<modid>/
  Main.java
  registry/
    ModItems.java
    ModScreens.java
    ModNetworking.java
  item/
    SpellbookItem.java
    StaffItem.java
  screen/
    SpellEditorScreenHandler.java
    SpellEditorScreen.java
    widget/
      CanvasWidget.java
      PaletteWidget.java
      NodeWidget.java
      ParamPanelWidget.java
      StatusBarWidget.java
  spell/
    model/
      SpellGraph.java
      SpellNode.java
      Edge.java
      SpellParam.java
    node/
      SpellNodeType.java
      NodeRegistry.java
      builtins/
        StartNode.java
        SequenceNode.java
        DelayNode.java
        ChanceNode.java
        CooldownGateNode.java
        TargetSelfNode.java
        TargetLookNode.java
        TargetAreaNode.java
        DamageNode.java
        HealNode.java
        EffectNode.java
        ParticlesNode.java
        ProjectileNode.java
        ExplosionNode.java
    codec/
      SpellGraphCodec.java
      SpellParamCodec.java
    validate/
      SpellValidator.java
      ValidationError.java
      ValidationResult.java
      ValidationRules.java
    runtime/
      SpellExecutor.java
      ExecContext.java
      ExecBudget.java
      TargetContext.java
      SpellActions.java
    cooldown/
      CooldownManager.java
  net/
    c2s/
      SaveGraphC2SPacket.java
      ImprintToStaffC2SPacket.java
      CastSpellC2SPacket.java
      RequestStatusC2SPacket.java
    s2c/
      SpellStatusS2CPacket.java
      SpellCastFxS2CPacket.java
  util/
    Ids.java
    NbtUtilEx.java
```

### Ключевые роли
- `SpellNodeType`: метаданные ноды (ports + params schema + execute()).
- `NodeRegistry`: регистрация нод по `Identifier`.
- `SpellGraph`: узлы + связи + start.
- `SpellGraphCodec`: NBT encode/decode + миграции по `schemaVersion`.
- `SpellValidator`: проверяет правила, возвращает errors/warnings + cost.
- `SpellExecutor`: серверный интерпретатор с **budget** (шаги/частицы/энтити/блоки).
- `CooldownManager`: кд per player/per staff.

---

## 2.2 Модель данных (SpellGraph)

### Объекты
- `SpellGraph`
  - `int schemaVersion`
  - `int startNodeId`
  - `Map<Integer, SpellNode> nodes`
  - `List<Edge> edges`
- `SpellNode`
  - `int id`
  - `Identifier typeId`
  - `int x, y`
  - `Map<String, SpellParam> params`
- `Edge`
  - `int fromId, toId`
  - `String fromPort, toPort`

### MVP: chain/sequence
- Один exec‑выход на ноду (кроме `Chance` допускается позже).
- Ветки запрещены.
- Циклы запрещены.

### Beta: directed graph
- Разрешены ветки (`Chance`) и bounded циклы (`Repeat`).
- Циклы только через `RepeatNode`:
  - `maxIterations <= 12`
  - общий `MAX_STEPS_PER_CAST` остаётся жёстким.

### Типизация параметров
`SpellParam` = tagged union:
- int, float, bool, enum(string), id(Identifier)

---

## 2.3 Хранение (NBT) + версионирование

### Где хранится
- Spellbook:
  - `ItemStack` NBT `SpellGraph` (Compound)
- Staff:
  - `ImprintedGraph` (Compound)
  - `ImprintedName` (String) — имя/тема (опц.)
  - `ImprintHash` (Int) — быстрый контроль изменений (опц.)

### Схема NBT
`SpellGraph`:
- `schemaVersion` (Int)
- `start` (Int)
- `nodes` (List<Compound>): `id,type,x,y,params`
- `edges` (List<Compound>): `from,fromPort,to,toPort`
`params`:
- `{k:"int", i:3}` / `{k:"float", f:1.2}` / `{k:"bool", b:1}`
- `{k:"enum", s:"BREAK_BLOCKS"}` / `{k:"id", s:"minecraft:flame"}`

### Миграции
- `schemaVersion` обязателен.
- `SpellGraphCodec` обязан уметь:
  - загрузить старый формат
  - привести к текущему
  - сохранить обратно (при следующем Save)

---

## 2.4 Валидация (ошибки сборки)

### Лимиты MVP (константы)
- `MAX_NODES = 24`
- `MAX_EDGES = 32`
- `MAX_STEPS_PER_CAST = 60`
- `MAX_TOTAL_PARTICLES = 200`
- `MAX_PARTICLES_PER_NODE = 80`
- `MAX_ENTITIES_SPAWNED = 3`
- `MAX_EXPLOSION_RADIUS = 6`
- `MAX_BLOCKS_BROKEN_PER_CAST = 120`
- `MAX_CASTS_PER_10S = 6`

### Правила (минимум 14)
1) Ровно один Start и он = `startNodeId`.  
2) Все exec‑ноды достижимы из Start.  
3) MVP: циклы запрещены.  
4) `nodes <= MAX_NODES`.  
5) `edges <= MAX_EDGES`.  
6) `estimatedSteps <= MAX_STEPS_PER_CAST`.  
7) Если есть Explosion/Teleport/Lightning → требуется `CooldownGate` (или global staff cooldown).  
8) Explosion.radius <= MAX_EXPLOSION_RADIUS.  
9) `BREAK_BLOCKS`: budget blocksBreakRemaining >= 1 и power <= 4.0.  
10) Particles: count <= MAX_PARTICLES_PER_NODE и total <= MAX_TOTAL_PARTICLES.  
11) Spawn entities total <= MAX_ENTITIES_SPAWNED.  
12) Target required: Damage/Explosion/Effect требуют target context.  
13) TargetLook.range <= 32; TargetArea.radius <= 8.  
14) Любые параметры clamp’ятся в допустимый диапазон; если за пределами — warning + clamp (или error для критичных).

### UI поведение
- Ошибки: красная подсветка ноды + список снизу.
- Save/Imprint disabled при errors.
- Сервер повторно валидирует при Save/Imprint/Cast.

---

## 2.5 Список нод (таблица)

| NodeID | Category | Inputs | Outputs | Parameters | Cost impact | Server-only | MVP/Beta |
|---|---|---|---|---|---|---|---|
| `<modid>:start` | Trigger | — | exec | — | 0 | no | MVP |
| `<modid>:sequence` | Control | exec | exec | — | +0 | no | MVP |
| `<modid>:delay` | Control | exec | exec | ticks (1..60) | +0.01*ticks | server | MVP |
| `<modid>:chance` | Control | exec | exec_success/exec_fail | chance% (1..99) | +0.5 | server | MVP (simple) |
| `<modid>:cooldown_gate` | Modifier | exec | exec | cooldownTicks (20..600) | +0.002*cooldown | server | MVP |
| `<modid>:target_self` | Targeting | exec | exec (+target) | — | +0.2 | server | MVP |
| `<modid>:target_look` | Targeting | exec | exec (+target) | range (1..32), hitMode (ENTITY/BLOCK) | +0.05*range | server | MVP |
| `<modid>:target_area` | Targeting | exec | exec (+targets) | radius (1..8), filter (HOSTILE/ALL) | +0.4*radius | server | MVP |
| `<modid>:damage` | Action | exec + target(s) | exec | amount (0.5..20), type (MAGIC/PHYS) | +0.8*amount | server | MVP |
| `<modid>:heal` | Action | exec + target | exec | amount (0.5..20) | +0.7*amount | server | MVP |
| `<modid>:effect` | Action | exec + target | exec | effectId, durationTicks, amplifier | +0.02*duration | server | MVP |
| `<modid>:particles` | VFX | exec (+pos/target opt.) | exec | particleId, count (1..80), spread, speed | +0.03*count | server | MVP |
| `<modid>:projectile` | Action | exec (+dir/target opt.) | exec | speed, damage, gravity, lifetime | +6.0 | server | MVP |
| `<modid>:explosion` | Action | exec (+pos/target) | exec | mode (BREAK_BLOCKS/NO_BLOCK_DAMAGE), radius (1..6), power (0.5..4.0), fire (bool) | mode? +10 : +6  + radius*1.2 | server | MVP |
| `<modid>:lightning` | Action | exec (+pos) | exec | count (1..3), spread, damage | +8 + count*2 | server | Beta |
| `<modid>:teleport` | Action | exec + target | exec | maxRange, safeCheck | +12 | server | Beta |
| `<modid>:stored_point` | Targeting | exec | exec (+pos) | slot (A/B/C) | +1 | server | Beta |

---

## 2.6 UI/UX (магический стиль)

### Визуальная метафора
- **“Астральная мастерская”**: тёмный пергамент, тонкие рунические прожилки, слабое звёздное мерцание (статичный фон).
- Ноды — **рунические плитки** с вырезанными пазами, будто они “вставляются” в линию судьбы.
- Соединения — **светящиеся нити** (тонкие линии), активный путь подсвечивается при preview.

### Layout
- Palette (Left): категории + иконки, поиск, избранное.
- Canvas (Center): drag, snap‑to‑grid (8px), выделение рамкой.
- Params (Right): имя ноды, краткое “магическое” описание, затем параметры (slider/input/toggle/dropdown).
- Status (Bottom): валидность, стоимость, cooldown, кнопки Save/Imprint/Preview.

### UX must-have (MVP)
- drag&drop нод из палитры
- перемещение ноды по канвасу
- соединение “выход→вход”
- удаление (DEL) + подтверждение
- контекст меню (ПКМ): duplicate/delete
- (опционально) простейший undo (последнее действие)

---

## 2.7 Баланс: стоимость, кулдауны, риск

### Ресурс каста (MVP)
**Charges на Staff + немного durability** (без отдельной mana‑системы).
- Staff: `charges 0..100`
- cast расходует `chargesCost` + `durabilityCost`

### Стоимость (пример формул)
- Damage: `0.8*amount`
- Heal: `0.7*amount`
- Particles: `0.03*count`
- TargetLook: `0.05*range`
- Explosion:
  - `NO_BLOCK_DAMAGE`: `6 + 1.2*radius + 1.5*power`
  - `BREAK_BLOCKS`: `10 + 1.6*radius + 2.0*power + (fire?2:0)`

Округление:
- `chargesCost = ceil(totalCost)`
- `durabilityCost = 1 + floor(totalCost/8)`

### Взрыв: два режима
- `NO_BLOCK_DAMAGE`: урон/FX, не ломает блоки.
- `BREAK_BLOCKS`: ломает блоки, дороже, требует cooldown, ограничен `MAX_BLOCKS_BROKEN_PER_CAST`.

---

## 2.8 Сеть и безопасность

### Пакеты
C2S:
- `SaveGraphC2S(bookSlot, graphNbt, clientHash)`
- `ImprintToStaffC2S(bookSlot, staffSlot, confirmOverwrite)`
- `CastSpellC2S(hand, castMode, clientTick)`
- `RequestStatusC2S(bookSlot)` (опц., обновление cost/errors)

S2C:
- `SpellStatusS2C(errors[], warnings[], cost, cooldownTicks)`
- `SpellCastFxS2C(fxEvents[])` (только визуал)

### Защита
- hard cap размера NBT в пакете
- clamp параметров
- циклы запрещены в MVP
- budgets: шаги/частицы/энтити/блоки
- rate limit на SaveGraph и CastSpell

---

## 2.9 Локализация (en_us + ru_ru)

Файлы:
- `assets/<modid>/lang/en_us.json`
- `assets/<modid>/lang/ru_ru.json`

Ключи:
- `item.<modid>.spellbook`
- `item.<modid>.staff`
- `screen.<modid>.spell_editor.title`
- `node.<modid>.explosion`
- `param.<modid>.radius`
- `validation.<modid>.cycle_detected`

Пример “магических” строк (коротко):
- RU: “Астральный Скрипторий”, “Цена чар”, “Разломный Взрыв”, “Якорь Взгляда”
- EN: “Astral Scriptorium”, “Arcane Cost”, “Riftburst”, “Gaze Anchor”

---

## 2.10 План разработки (MVP → Beta → Release)

### MVP (10 шагов)
1) Items + recipes + models + lang  
2) Networking каркас (register packets)  
3) Graph model + Node registry  
4) NBT codec + миграции v1  
5) Validator (14 правил) + cost calc  
6) ScreenHandler + Screen (palette/canvas/params/status)  
7) SaveGraph (server validate → write to book)  
8) Imprint (server validate → write to staff)  
9) Cast + Executor (server budgets; explosion two modes)  
10) Debug commands + stress tests

### Beta
- ветвления/граф, bounded Repeat
- stored points
- preview path highlight
- полноценный undo/redo, zoom/pan

### Release
- баланс-пасс, конфиги лимитов, документация, расширенные ноды (Teleport/Lightning)

---

# 3) Псевдокод/каркас классов

## 3.1 Graph
```java
final class SpellGraph { int schemaVersion; int startNodeId; Map<Integer, SpellNode> nodes; List<Edge> edges; }
final class SpellNode { int id; Identifier typeId; int x,y; Map<String, SpellParam> params; }
final class Edge { int fromId,toId; String fromPort,toPort; }
```

## 3.2 Codec
```java
final class SpellGraphCodec {
  static NbtCompound encode(SpellGraph g) { ... }
  static SpellGraph decode(NbtCompound nbt) { ... }
  static NbtCompound migrate(NbtCompound old, int oldVer) { ... }
}
```

## 3.3 Validator
```java
final class SpellValidator {
  ValidationResult validate(SpellGraph g, ValidationContext ctx) { ... }
}
```

## 3.4 Executor (tick/budget)
```java
final class ExecBudget { int steps, particles, entities, blocks; }
final class SpellExecutor {
  ExecResult cast(ServerPlayerEntity p, ItemStack staff, SpellGraph g) { ... }
}
```

## 3.5 Packets
```java
record SaveGraphC2S(int bookSlot, NbtCompound graph) {}
record ImprintToStaffC2S(int bookSlot, int staffSlot, boolean confirmOverwrite) {}
record CastSpellC2S(Hand hand, int castMode) {}
```

---

# 4) Как НЕ путать с основным SPEC.md

Если у проекта уже есть основной `SPEC.md`, этот документ должен быть **отдельным источником правды**.

**Правило:** всё по spellbook делается только по `SPELLBOOK_SPEC.md`.

Готовая фраза для твоего ИИ/агента:
> “Работай строго по файлу `SPELLBOOK_SPEC.md` (Spellbook/Staff/Editor). Игнорируй `SPEC.md` и любые другие спеки, если они не про spellbook. Перед работой перечисли, какие файлы ты создашь/изменишь, и после — дай чек‑лист теста.”

