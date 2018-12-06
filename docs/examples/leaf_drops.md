
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: Add `<minecraft:stick>` to leaf drops.

---

Leaf blocks have a different block state depending on whether or not they should check for decay. This makes it hard to know what metadata needs to be supplied to the matcher.

Fortunately, Dropt has a command to assist: `/dropt verbose`.

The command can be toggled on and off, and while on, will log the info of all broken blocks. So run the command, break some blocks, and inspect the results to extract the info you need.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:leaves:*"])
      .addDrop(Dropt.drop()
          .items([<minecraft:stick>])
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
            "minecraft:leaves:*"
          ]
        }
      },
      "replaceStrategy": "ADD",
      "drops": [
        {
          "item": {
            "items": [
              "minecraft:stick"
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
          "minecraft:leaves:*"
      })
      .replaceStrategy(EnumReplaceStrategy.ADD)
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop().items(new String[]{
              DroptAPI.itemString(Items.STICK)
          })
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```