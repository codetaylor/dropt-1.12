
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: When `<minecraft:stone>` is broken with silk touch, replace all drops with `<minecraft:string> * 1`.

We use the replacement strategy of `REPLACE_ALL_IF_SELECTED` here so we only replace the block's drops if an item is selected from our drop pool. Since the only item in the drop pool defines a selector that requires silk touch, the block's drops will only be replaced if the block is broken with silk touch.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone"])
      .replaceStrategy("REPLACE_ALL_IF_SELECTED")
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(1), "REQUIRED")
          .items([<minecraft:string>])
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
      "replaceStrategy": "REPLACE_ALL_IF_SELECTED",
      "drops": [
        {
          "selector": {
            "silktouch": "REQUIRED"
          },
          "item": {
            "items" : [
              "minecraft:string"
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
      .replaceStrategy(EnumReplaceStrategy.REPLACE_ALL_IF_SELECTED)
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
            .selector(DroptAPI.weight(1), EnumSilkTouch.REQUIRED)
            .items(new String[]{
                DroptAPI.itemString(Items.STRING)
            })
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```