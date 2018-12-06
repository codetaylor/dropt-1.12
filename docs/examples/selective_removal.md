
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: Prevent the item `<minecraft:cobblestone>` from dropping when `<minecraft:stone>` or `<minecraft:cobblestone>` is broken.

---

Here we're asking Dropt to match when either `minecraft:stone` or `minecraft:cobblestone` is broken and `minecraft:cobblestone` is dropped. The replace strategy tells Dropt that we want to replace any items that we've listed in the drop match section with drops selected from our pool. Since we've only added a single empty drop, the empty drop will always be selected.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone", "minecraft:cobblestone"])
      .matchDrops([<minecraft:cobblestone>])
      .replaceStrategy("REPLACE_ITEMS")
      .addDrop(Dropt.drop())
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
            "minecraft:stone",
            "minecraft:cobblestone"
          ]
        },
        "drops": {
          "drops": [
            "minecraft:cobblestone"
          ]
        }
      },
      "replaceStrategy": "REPLACE_ITEMS",
      "drops": [{}]
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
          "minecraft:stone",
          "minecraft:cobblestone"
      })
      .matchDrops(new String[]{
          "minecraft:cobblestone"
      })
      .replaceStrategy(EnumReplaceStrategy.REPLACE_ITEMS)
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```