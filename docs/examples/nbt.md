
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: Match against held item NBT and replace block drops with items that have NBT.

---

Dropt supports NBT matching on held items and also supports dropping items with NBT.

!!! warning
    If the player renames an item, enchants an item, or if the item's NBT is altered in any way, its NBT will no longer match.

In the example below, we've set up the rule to match a held item, specifically a Diamond Pickaxe with some NBT.

```
"minecraft:diamond_pickaxe:*#{RepairCost:0,display:{Name:\"Pick Astley\"}}"
```

This string is the result of using an anvil to name a fresh Diamond Pickaxe and running the `/dropt hand` command while holding it. Note the `RepairCost: 0` tag.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("list_name")

  .add(Dropt.rule()
      .matchBlocks(["minecraft:stone"])
      .matchHarvester(Dropt.harvester()
          .type("PLAYER")
          .mainHand([
              <minecraft:diamond_pickaxe:*>.withTag({RepairCost: 0, display: {Name: "Pick Astley"}})
          ])
      )
      .addDrop(Dropt.drop()
          .items([
              <minecraft:cobblestone>.withTag({display: {Name: "Never"}}),
              <minecraft:cobblestone>.withTag({display: {Name: "Gonna"}}),
              <minecraft:cobblestone>.withTag({display: {Name: "Give"}}),
              <minecraft:cobblestone>.withTag({display: {Name: "You"}}),
              <minecraft:cobblestone>.withTag({display: {Name: "Stone"}})
          ])
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
            "minecraft:stone"
          ]
        },
        "harvester": {
          "type": "PLAYER",
          "heldItemMainHand": {
            "items": [
              "minecraft:diamond_pickaxe:*#{RepairCost:0,display:{Name:\"Pick Astley\"}}"
            ]
          }
        }
      },
      "drops": [{
        "item": {
          "items": [
            "minecraft:cobblestone:0#{display:{Name:\"Never\"}}",
            "minecraft:cobblestone:0#{display:{Name:\"Gonna\"}}",
            "minecraft:cobblestone:0#{display:{Name:\"Give\"}}",
            "minecraft:cobblestone:0#{display:{Name:\"You\"}}",
            "minecraft:cobblestone:0#{display:{Name:\"Stone\"}}"
          ]
        }
      }]
    }
  ]
}
```