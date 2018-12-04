
!!! note
    If you find any issues with the examples, please [report them here](https://github.com/codetaylor/dropt/issues).

---

Goal: Change drops based on fortune, with a hard-requirement for each fortune tier.

*A hard-requirement means that the next tier up won't drop unless the player has at least that fortune level.*

---

Varying the fortune is a little tricky. Let me explain what the script is doing.

First, it adds four drops to the drop pool, of which only one will be selected to drop.

The first drop at the top of the list adds a drop that varies between 1 - 2 drops and has a weight of `1`. This drop will always be added to the drop pool.

The next drop, the one right below it, adds a drop that varies between 4 - 8 drops, but requires a minimum fortune level of `1` to be added to the drop pool. It has a weight of `10` meaning that if added to the pool, it is about 10 times more likely to get chosen than the first drop added, the one with no selection rules and a weight of `1`. 

The next drop varies between 16 - 32 drops and has a minimum fortune level of `2` and a weight of `100`. This drop is about 10 times more likely to be chosen than the `Fortune I` drop and about 100 times more likely to be chosen than the `No Fortune` drop. This drop will only be added to the pool if the player has `Fortune II` or greater.

Finally, the last drop added varies between 32 - 64 and has a minimum fortune level of `3` and a weight of `1000`. This drop is about 10 times more likely to be chosen than the `Fortune II` drop.  This drop will only be added to the pool if the player has `Fortune III`.

What this does is create a drop pool that scales up as the player increases fortune, but allows you to configure each tier with a random range of drop quantity.

Now keep in mind, that while it is unlikely, it is possible that a player with `Fortune III` could mine a block and get very unlucky by having the first drop rule selected. However, with the examples below, that would be a 1 in 1111 chance of happening.

Using the fortune level requirement ensures that the items will never even be selected for a chance to drop unless the player has the correct fortune level.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:quartz_ore"])
      .addDrop(Dropt.drop()
          .items([<minecraft:quartz>], Dropt.range(1, 2))
      )
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(10), "ANY", 1)
          .items([<minecraft:quartz>], Dropt.range(4, 8))
      )
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(100), "ANY", 2)
          .items([<minecraft:quartz>], Dropt.range(16, 32))
      )
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(1000), "ANY", 3)
          .items([<minecraft:quartz>], Dropt.range(32, 64))
      )
  );
```

### JSON

```js
{
  "rules": [
    {
      "match": {
        "blocks": {
          "blocks": [
            "minecraft:quartz_ore"
          ]
        }
      },
      "drops": [
        {
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 1,
              "max": 2
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 10
            },
            "fortuneLevelRequired": 1
          },
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 4,
              "max": 8
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 100
            },
            "fortuneLevelRequired": 2
          },
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 16,
              "max": 32
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 1000
            },
            "fortuneLevelRequired": 3
          },
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 32,
              "max": 64
            }
          }
        }
      ]
    }
  ]
}
```

### DroptAPI

```java
@SubscribeEvent
public void on(DroptLoadRulesEvent event) {

  List<IDroptRuleBuilder> list = new ArrayList<>();

  list.add(DroptAPI.rule()
      .matchBlocks(new String[]{"minecraft:quartz_ore"})
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
              .items(new String[]{"minecraft:quartz"}, DroptAPI.range(1, 2)),
          DroptAPI.drop()
              .selector(DroptAPI.weight(10), EnumSilktouch.ANY, 1)
              .items(new String[]{"minecraft:quartz"}, DroptAPI.range(4, 8)),
          DroptAPI.drop()
              .selector(DroptAPI.weight(100), EnumSilktouch.ANY, 2)
              .items(new String[]{"minecraft:quartz"}, DroptAPI.range(16, 32)),
          DroptAPI.drop()
              .selector(DroptAPI.weight(1000), EnumSilktouch.ANY, 3)
              .items(new String[]{"minecraft:quartz"}, DroptAPI.range(32, 64))
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```