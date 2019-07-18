
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: Replace any Redstone drops with the same number of Dirt.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchDrops([<minecraft:redstone>])
      .addDrop(Dropt.drop()
          .items([<minecraft:dirt>])
          .matchQuantity([<minecraft:redstone>])
      )
  );
```

### JSON

```js
{
  "rules": [
    {
      "match": {
        "drops": {
          "drops": [
            "minecraft:redstone"
          ]
        }
      },
      "drops": [
        {
          "item": {
            "items": [
              "minecraft:dirt"
            ],
            "matchQuantity": {
              "drops": [
                "minecraft:redstone"
              ]
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
      .matchDrops(new String[]{
          "minecraft:redstone"
      })
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
              .items(new String[] {
                  "minecraft:dirt"
              })
              .matchQuantity(new String[] {
                  "minecraft:redstone"
              })
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```