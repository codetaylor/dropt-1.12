
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: When `<minecraft:stone>` breaks, always drop `<minecraft:string> * 1` with a 25% chance to also drop a diamond.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone"])
      .addDrop(Dropt.drop()
          .force()
          .items([<minecraft:string>])
      )
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(75)) // drop nothing 75% of the time
      )
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(25))
          .items([<minecraft:diamond>])
      )
  );
```

### JSON

```json
{
  "rules": [
    {
      "match": {
        "blocks": {
          "blocks": [
            "minecraft:stone:0"
          ]
        }
      },
      "drops": [
        {
          "force": true,
          "item": {
            "items" : [
              "minecraft:string"
            ]
          }
        },
        {
          "selector": {
            "weight": {
              "value":75
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value":25
            }
          },
          "item": {
            "items" : [
              "minecraft:diamond"
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
          "minecraft:stone"
      })
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
              .force()
              .items(new String[]{DroptAPI.itemString(Items.STRING)}),
          DroptAPI.drop()
              .selector(DroptAPI.weight(75)), // drop nothing 75% of the time
          DroptAPI.drop()
              .selector(DroptAPI.weight(25))
              .items(new String[]{DroptAPI.itemString(Items.DIAMOND)})
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```