# Magic Staff - Installation & Usage Guide

## Описание / Description

Этот проект добавляет магический посох в Minecraft 1.20.1 используя Resource Pack и Datapack.
The project adds a magic staff to Minecraft 1.20.1 using Resource Pack and Datapack.

## Установка / Installation

### Resource Pack

1. Скопируйте папку `resourcepack` в директорию `.minecraft/resourcepacks/`
   Copy the `resourcepack` folder to `.minecraft/resourcepacks/`

2. Переименуйте папку `resourcepack` в `MagicStaff_RP` (или любое имя на ваш выбор)
   Rename `resourcepack` folder to `MagicStaff_RP` (or any name you prefer)

3. В игре: Настройки → Resource Packs → активируйте пак
   In game: Options → Resource Packs → activate the pack

### Datapack

1. Откройте папку вашего мира: `.minecraft/saves/<ваш_мир>/datapacks/`
   Open your world folder: `.minecraft/saves/<your_world>/datapacks/`

2. Скопируйте папку `datapack` в эту директорию
   Copy the `datapack` folder to this directory

3. Переименуйте папку `datapack` в `MagicStaff_DP` (или любое имя)
   Rename `datapack` folder to `MagicStaff_DP` (or any name)

4. В игре выполните команду: `/reload`
   In game execute command: `/reload`

5. Вы должны увидеть сообщение: "Magic Staff datapack loaded" (зеленым цветом)
   You should see message: "Magic Staff datapack loaded" (in green)

## Получение посоха / Getting the Staff

Выполните команду в игре / Execute command in game:

```
/give @p minecraft:trident{CustomModelData:777,display:{Name:'{"text":"Magic Staff"}'}} 1
```

## Как это работает / How It Works

- **Resource Pack** заменяет модель и текстуру трезубца с CustomModelData:777 на посох
  **Resource Pack** replaces trident model and texture with CustomModelData:777 to staff

- **Datapack** предотвращает бросок посоха, используя advancement триггер
  **Datapack** prevents staff throwing using advancement trigger

- Обычный трезубец (без CustomModelData) работает нормально
  Regular trident (without CustomModelData) works normally

## Тестирование / Testing

1. ✓ В инвентаре посох отображается с кастомной текстурой
   ✓ Staff displays custom texture in inventory

2. ✓ В руке посох держится как трезубец (та же поза)
   ✓ Staff is held like trident (same pose) in hand

3. ✓ При попытке бросить (ПКМ) посох НЕ улетает
   ✓ When trying to throw (RMB) staff does NOT fly away

4. ✓ Посох остается в инвентаре после попытки броска
   ✓ Staff remains in inventory after throw attempt

5. ✓ Обычный трезубец работает нормально
   ✓ Regular trident works normally

## Параметры конфигурации / Configuration Parameters

- **Namespace**: `my_magic`
- **CustomModelData**: `777`
- **Item Name**: "Magic Staff"

## Структура файлов / File Structure

```
resourcepack/
├── pack.mcmeta
└── assets/
    ├── my_magic/
    │   ├── textures/item/staff.png
    │   └── models/item/
    │       ├── staff.json
    │       └── staff_in_hand.json
    └── minecraft/models/item/
        ├── trident.json
        └── trident_in_hand.json

datapack/
├── pack.mcmeta
└── data/
    ├── my_magic/
    │   ├── advancements/detect_use_staff.json
    │   └── functions/
    │       ├── load.mcfunction
    │       └── staff_prevent_throw.mcfunction
    └── minecraft/tags/functions/load.json
```

## Расширение / Extension

Чтобы добавить больше посохов, добавьте новые overrides с разными CustomModelData значениями (например, 778, 779...).
To add more staffs, add new overrides with different CustomModelData values (e.g., 778, 779...).

## Замечание об текстуре / Note about Texture

Текстура `staff.png` была скопирована из существующего мода для демонстрации.
Вы можете заменить её на свою собственную текстуру (16x16 или 32x32 PNG).

The `staff.png` texture was copied from existing mod for demonstration.
You can replace it with your own texture (16x16 or 32x32 PNG).
