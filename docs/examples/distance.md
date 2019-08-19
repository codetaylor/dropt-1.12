
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: Replace cobblestone drops with dirt blocks if broken within 32 block radius of world spawn.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:cobblestone"])
      .matchSpawnDistance("WHITELIST", 0, 32)
      .addDrop(Dropt.drop()
          .items([<minecraft:dirt>])
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
            "minecraft:cobblestone"
          ]
        },
        "spawnDistance": {
          "type": "WHITELIST",
          "min": 0,
          "max": 32
        }
      },
      "drops": [
        {
          "item": {
            "items": [
              "minecraft:dirt"
            ]
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
      .matchBlocks(new String[]{
          "minecraft:cobblestone"
      })
      .matchSpawnDistance(EnumListType.WHITELIST, 0, 32)
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop().items(new String[]{
              DroptAPI.itemString(Blocks.DIRT)
          })
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```