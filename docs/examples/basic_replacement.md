
!!! note
    If you find any issues with the examples, please [report them here](https://github.com/codetaylor/dropt/issues).

---

Goal: When `<minecraft:stone>` breaks, replace all drops with `<minecraft:string> * 1`.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      // whitelist match against these blocks
      .matchBlocks(["minecraft:stone"])
      // drop these items
      .addDrop(Dropt.drop()
          .items([<minecraft:string>])
      )
  );
```

### JSON

```js
{
  "rules": [
    {
      "match": {
        // whitelist match against these blocks
        "blocks": {
          "blocks": [
            "minecraft:stone:0"
          ]
        }
      },
      // drop these items
      "drops": [
        {
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
      // whitelist match against these blocks
      .matchBlocks(new String[]{
          "minecraft:stone"
      })
      // drop these items
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop().items(new String[]{
              DroptAPI.itemString(Items.STRING)
          })
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```