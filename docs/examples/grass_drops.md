
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: When tall grass breaks, 15% chance to add a drop.

---

Notice that here we've added a drop with no items and a weight of `85`. This itemless drop will still be selected, but will drop nothing. This is how you can achieve having a drop, but only some of the time.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:tallgrass:*"])
      .replaceStrategy("ADD")
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(85)) // drops nothing if selected
      )
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(15))
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
            "minecraft:tallgrass:*"
          ]
        }
      },
      "replaceStrategy": "ADD",
      "drops": [
        {
          "selector": {
            "weight": {
              "value": 85
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 15
            }
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
          "minecraft:tallgrass:*"
      })
      .replaceStrategy(EnumReplaceStrategy.ADD)
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
              .selector(DroptAPI.weight(85)),
          DroptAPI.drop()
              .selector(DroptAPI.weight(15))
              .items(new String[]{DroptAPI.itemString(Items.STRING)})
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```