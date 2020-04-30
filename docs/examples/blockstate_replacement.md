
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: When `<minecraft:stone>` breaks, replace all drops with `<minecraft:cobblestone> * 1` and replace the in world blockstate with a random log, favoring the upright oak log.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone"])
      .addDrop(Dropt.drop()
          .selector(Dropt.weight(2))
          .items([<minecraft:cobblestone>])
          .replaceBlock("minecraft:log", {axis: "y", variant: "oak"})
      )
      .addDrop(Dropt.drop()
          .items([<minecraft:cobblestone>])
          .replaceBlock("minecraft:log", {axis: "x", variant: "spruce"})
      )
      .addDrop(Dropt.drop()
          .items([<minecraft:cobblestone>])
          .replaceBlock("minecraft:log", {axis: "z", variant: "birch"})
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
          "blocks": ["minecraft:stone"]
        }
      },
      "drops": [
        {
          "selector": { "weight": { "value": 2 } },
          "item": { "items": ["minecraft:cobblestone"] },
          "replaceBlock": {
            "block": "minecraft:log",
            "properties": { "axis": "y", "variant": "oak" }
          }
        },
        {
          "item": { "items": ["minecraft:cobblestone"] },
          "replaceBlock": {
            "block": "minecraft:log",
            "properties": { "axis": "x", "variant": "spruce" }
          }
        },
        {
          "item": { "items": ["minecraft:cobblestone"] },
          "replaceBlock": {
            "block": "minecraft:log",
            "properties": { "axis": "z", "variant": "birch" }
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
                .selector(DroptAPI.weight(2))
                .items(new String[]{
                    DroptAPI.itemString(new ItemStack(Blocks.COBBLESTONE))
                })
                .replaceBlock("minecraft:log", new HashMap<String, String>() {{
                  put("axis", "y");
                  put("variant", "oak");
                }}),
            DroptAPI.drop()
                .items(new String[]{
                    DroptAPI.itemString(new ItemStack(Blocks.COBBLESTONE))
                })
                .replaceBlock("minecraft:log", new HashMap<String, String>() {{
                  put("axis", "x");
                  put("variant", "spruce");
                }}),
            DroptAPI.drop()
                .items(new String[]{
                    DroptAPI.itemString(new ItemStack(Blocks.COBBLESTONE))
                })
                .replaceBlock("minecraft:log", new HashMap<String, String>() {{
                  put("axis", "z");
                  put("variant", "birch");
                }})
        })
    );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```