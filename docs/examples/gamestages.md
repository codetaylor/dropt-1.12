
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: When `<minecraft:stone>` is broken by a player with both game stages `stage_A` and `stage_B`, replace all drops with `<minecraft:string> * 1`.

---

### ZenScript

```js
import mods.dropt.Dropt;
import mods.dropt.Harvester;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone"])
      .matchHarvester(Dropt.harvester()
        .gameStages("WHITELIST", "ALL", ["stage_A", "stage_B"])
      )
      .addDrop(Dropt.drop()
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
        },
        "harvester": {
          "gamestages": {
            "type": "WHITELIST",
            "require": "ALL",
            "stages": [
              "stage_A", 
              "stage_B"
            ]         
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
          "minecraft:stone"
      })
      .matchHarvester(DroptAPI.harvester()
        .gameStages(
          EnumListType.WHITELIST, 
          EnumHarvesterGameStageType.ALL, 
          new String[]{"stage_A", "stage_B"}
        )
      ) 
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