
!!! note
    If you find any issues with the examples, please [report them here](https://github.com/codetaylor/dropt/issues).

---

Goal: When a player harvests any dirt variant without a shovel, drop nothing.

---

```js
.mainHand("BLACKLIST", [], "shovel;0;-1")
```

This bit is doing the heavy lifting for this example. What we've done is told Dropt to match this rule if the main hand item is *not* (blacklist) a `shovel` of at least harvest level `0`.

The `shovel;0;-1` harvest level string tells Dropt to match a `shovel` with a minimum harvest level of `0` and a maximum harvest level of `-1`, which is just ignored because it is `-1`. We then simply inverse that with the `BLACKLIST` type.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:dirt:*"])
      .matchHarvester(Dropt.harvester()
          .type("PLAYER") // restrict to player
          .mainHand("BLACKLIST", [], "shovel;0;-1")
      )
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
        "blocks": {
          "blocks": [
            "minecraft:dirt:*"
          ]
        },
        "harvester": {
          "type": "PLAYER",
          "heldItemMainHand": {
            "type": "BLACKLIST",
            "harvestLevel": "shovel;0;-1"
          }
        }
      },
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
      .matchBlocks(new String[]{
          "minecraft:dirt:*"
      })
      .matchHarvester(DroptAPI.harvester()
          .type(EnumHarvesterType.PLAYER)
          .mainHand(EnumListType.BLACKLIST, "shovel;0;-1")
      )
      .addDrops(new IDroptDropBuilder[]{
          DroptAPI.drop()
              .items(new String[]{DroptAPI.itemString(Items.STRING)})
      })
  );

  ResourceLocation resourceLocation = new ResourceLocation("my_mod_id", "rule_list_name");
  int priority = 0;
  DroptAPI.registerRuleList(resourceLocation, priority, list);
}
```