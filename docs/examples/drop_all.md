
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: When `<minecraft:stone>` breaks, replace all drops with `<minecraft:string> * 100` AND `<minecraft:diamond> * 10`.

There are actually two ways that this can be accomplished. Both items could be defined as separate drops with the `dropCount` set to `2` and the `dropStrategy` set to `UNIQUE`. The other way, the way we will look at here, is to define both items in the same drop list and use the `ALL` drop list strategy. Drops defined using the second method will still only count as one drop with respect to the `dropCount` even if they drop more than one item.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone"])
      .addDrop(Dropt.drop()
          .items("ALL", [<minecraft:string> * 100, <minecraft:diamond> * 10])
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
          "item": {
            "drop": "ALL",
            "items" : [
              "minecraft:string * 100",
              "minecraft:diamond * 10"
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
          DroptAPI.drop().items(EnumDropListStrategy.ALL, new String[]{
              "minecraft:string * 100",
              "minecraft:diamond * 10"
          })
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```