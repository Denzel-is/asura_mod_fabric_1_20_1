ТЗ: “Magic Staff” на базе Trident (1.20.1)
0) Цель и ограничения
Цель

Создать предмет Magic Staff, который:

В инвентаре и в руке выглядит как посох (своя текстура).

В руке имеет ровно ту же позу/ориентацию, что у ванильного trident.

Не может быть брошен (игрок не должен иметь возможность “зарядить” и кинуть).

Работает в Minecraft Java 1.20.1:

Без Forge/Fabric

Только Resource Pack (визуал) + Datapack (логика запрета броска)

Поддержка нескольких посохов через разные CustomModelData (опционально).

Ограничения (важно понимать)

В ваниле невозможно сделать “новый предмет” без мода — поэтому посох будет trident с CustomModelData.

Ванильный трезубец имеет отдельную entity-модель для полёта, но нам это не нужно, т.к. бросок запрещаем.

1) Входные данные

staff.png — текстура посоха

Рекомендуемый размер: 16×16 или 32×32 (ванильно и предсказуемо в GUI).

Формат: PNG с прозрачностью.

Путь в паке: assets/<ns>/textures/item/staff.png

Параметры:

namespace (далее <ns>), например my_magic

CustomModelData (далее CMD), например 777

Название предмета (опционально): "Magic Staff"

2) Выходные артефакты
2.1 Resource Pack (RP)

Полная структура папок

JSON-модели для:

GUI/инвентарь (staff.json)

В руке как trident (staff_in_hand.json)

Overrides в ванильных моделях:

minecraft:models/item/trident.json

minecraft:models/item/trident_in_hand.json

2.2 Datapack (DP)

Функции, которые перехватывают попытку броска и отменяют её.

(Опционально) запрет на “charging” через удаление/замену предмета, или блокировка использования.

3) Как и где взять ванильные файлы
3.1 Где лежит 1.20.1.jar

Путь зависит от лаунчера, но в целом:

.minecraft/versions/1.20.1/1.20.1.jar

3.2 Что извлечь из jar

Нужны файлы:

assets/minecraft/models/item/trident.json

assets/minecraft/models/item/trident_in_hand.json
(в некоторых версиях также встречается trident_throwing.json, но мы его не используем, т.к. броска не будет)

Извлечь можно любым архиватором (jar = zip).

4) Resource Pack: структура и файлы
4.1 pack.mcmeta

RP для 1.20.1 использует pack_format: 15.

resourcepack/pack.mcmeta

{
  "pack": {
    "pack_format": 15,
    "description": "Magic Staff RP (1.20.1)"
  }
}

4.2 Текстура

resourcepack/assets/<ns>/textures/item/staff.png

4.3 Модель для инвентаря (2D)

resourcepack/assets/<ns>/models/item/staff.json

{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "<ns>:item/staff"
  }
}

4.4 Модель для руки (поза trident)

Идея: берём ванильный trident_in_hand как “родителя”, меняем текстуру.

resourcepack/assets/<ns>/models/item/staff_in_hand.json

{
  "parent": "minecraft:item/trident_in_hand",
  "textures": {
    "particle": "<ns>:item/staff"
  }
}


Примечание: particle используется как базовая текстура для некоторых рендер-случаев. Для trident-семейства этого достаточно, чтобы заменить вид.

4.5 Override в trident.json (GUI/инвентарь)

Берём ванильный файл из jar и добавляем overrides.

resourcepack/assets/minecraft/models/item/trident.json

{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "minecraft:item/trident"
  },
  "overrides": [
    {
      "predicate": { "custom_model_data": 777 },
      "model": "<ns>:item/staff"
    }
  ]
}

4.6 Override в trident_in_hand.json (вид в руке)

Берём ванильный trident_in_hand.json и добавляем override.

resourcepack/assets/minecraft/models/item/trident_in_hand.json

{
  "parent": "minecraft:item/trident",
  "textures": {
    "particle": "minecraft:item/trident"
  },
  "overrides": [
    {
      "predicate": { "custom_model_data": 777 },
      "model": "<ns>:item/staff_in_hand"
    }
  ]
}

Важно по приоритетам overrides

Если планируется несколько посохов, overrides должны быть списком и порядок — от более специфичных к менее специфичным.

Мы НЕ добавляем throwing predicate, потому что бросок запрещаем и не хотим поддерживать это состояние.

5) Datapack: запрет броска
5.1 Идея запрета

В ваниле бросок трезубца — это “использование предмета” (charging → release).
Надёжный ванильный способ: отслеживать minecraft.used:minecraft.trident и сразу “откатывать” (возвращать предмет / отменять эффект).

Поскольку “отменить событие” напрямую нельзя, делаем так:

Если игрок использовал трезубец с CMD=777, мы:

удаляем появившийся брошенный трезубец (если успел появиться)

возвращаем посох в инвентарь

(опционально) выдаём сообщение / звук
Это даёт эффект “не кидается”.

5.2 Структура Datapack
datapack/
  pack.mcmeta
  data/
    <ns>/
      functions/
        load.mcfunction
        tick.mcfunction
        staff_prevent_throw.mcfunction
      advancements/
        detect_use_staff.json

5.3 pack.mcmeta (DP)

datapack/pack.mcmeta

{
  "pack": {
    "pack_format": 15,
    "description": "Magic Staff DP (1.20.1)"
  }
}

5.4 Advancement: детект “использовал trident”

datapack/data/<ns>/advancements/detect_use_staff.json

{
  "criteria": {
    "used_trident": {
      "trigger": "minecraft:used_item",
      "conditions": {
        "item": {
          "items": ["minecraft:trident"],
          "nbt": "{CustomModelData:777}"
        }
      }
    }
  },
  "rewards": {
    "function": "<ns>:staff_prevent_throw"
  }
}

5.5 Function: staff_prevent_throw

datapack/data/<ns>/functions/staff_prevent_throw.mcfunction
Минимально рабочая логика:

# 1) Удаляем любые брошенные трезубцы рядом с игроком (на случай если успел появиться entity)
kill @e[type=minecraft:trident,distance=..5,limit=10]

# 2) Возвращаем предмет игроку (если он потратился/исчез из руки)
give @s minecraft:trident{CustomModelData:777,display:{Name:'{"text":"Magic Staff"}'}} 1

# 3) Сбрасываем advancement, чтобы он сработал снова в следующий раз
advancement revoke @s only <ns>:detect_use_staff


Это “жёсткий” вариант: даже если игрок попытается заспамить, предмет всегда возвращается, а летящий трезубец удаляется.

5.6 Подключение в load/tick
load (один раз при /reload)

datapack/data/<ns>/functions/load.mcfunction

tellraw @a {"text":"Magic Staff datapack loaded","color":"green"}

tick (каждый тик) — можно НЕ делать

В данном решении tick не обязателен, т.к. мы работаем через advancement-триггер.

Но чтобы load запускался, нужно прописать теги функций:

datapack/data/minecraft/tags/functions/load.json

{ "values": ["<ns>:load"] }

6) Команда выдачи предмета (для теста)
/give @p minecraft:trident{CustomModelData:777,display:{Name:'{"text":"Magic Staff"}'}} 1

7) Acceptance Criteria (критерии приёмки)

При выдаче трезубца с CustomModelData:777:

В инвентаре отображается текстура посоха

При взятии в руку:

В руке предмет держится точно как ванильный трезубец

При попытке бросить (ПКМ удержание/отпуск):

Трезубец не улетает

В инвентаре/руке остаётся посох

В мире не остаётся entity трезубца

Обычный трезубец без CMD:

Работает полностью ванильно и не ломается

Совместимость:

Работает на чистом клиенте/сервере 1.20.1 с установленными RP+DP.

8) Расширения (опционально, но предусмотреть)
8.1 Несколько посохов

Добавить список overrides:

CMD=777 → staff_a

CMD=778 → staff_b
и соответствующие модели/текстуры.

8.2 Интеракции “посох кастует”

Datapack может по ПКМ (использованию) вместо броска:

спавнить частицы

давать эффект

запускать функцию “spell cast”
(сейчас мы уже ловим used_item — туда легко добавить каст).

9) Что ИИ должна сгенерировать автоматически (вывод “под ключ”)

Готовую папку Resource Pack:

pack.mcmeta

assets/<ns>/textures/item/staff.png (вставить текстуру)

assets/<ns>/models/item/staff.json

assets/<ns>/models/item/staff_in_hand.json

assets/minecraft/models/item/trident.json (с override)

assets/minecraft/models/item/trident_in_hand.json (с override)

Готовую папку Datapack:

pack.mcmeta

advancement detect_use_staff.json

функции: load, staff_prevent_throw

тег load.json