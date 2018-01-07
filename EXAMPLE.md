## Example #1

Replace all dropped `ore:logWood` from any block with a single random dye 75% of the time.

```json
{
  "rules": [
    {
      "match": {
        "items": [
          "ore:logWood"
        ]
      },
      "replaceStrategy": "REPLACE_ITEMS_IF_SELECTED",
      "drops": [
        {
          "selector": {
            "weight": {
              "value": 25
            }
          },
          "item": {
            "items": [
              "minecraft:dye:*"
            ]
          }
        },
        {
          "selector": {
            "weight": {
              "value": 75
            }
          }
        }
      ]
    }
  ]
}
```