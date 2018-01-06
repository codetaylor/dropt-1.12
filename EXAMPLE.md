```json
{
  "priority": 100,
  "rules": [
    {
      "match": {
        "blocks": [
          "minecraft:stone:0"
        ],
        "harvester": {
          "type": "PLAYER",
          "heldItemMainHand": [
            "minecraft:wooden_pickaxe:*",
            "minecraft:stone_pickaxe:*"
          ],
          "gamestages": {
            "type": "ALL",
            "stages": [
              "one",
              "two"
            ]
          }
        },
        "biomes": {
          "type": "BLACKLIST",
          "ids": [
            "minecraft:birch_forest_hills"
          ]
        },
        "dimensions": {
          "type": "WHITELIST",
          "ids": [
            0
          ]
        }
      },
      "mergeStrategy": "REPLACE_IF_SELECTED",
      "dropCount": {
        "min": 1,
        "max": 5,
        "fortuneModifier": 1
      },
      "drops": [
        {
          "selector": {
            "weight": {
              "value": 100,
              "fortuneModifier": 10
            },
            "silktouch": "ANY"
          },
          "item": {
            "item": [
              "minecraft:dye:4",
              "minecraft:dye:5"
            ],
            "quantity": {
              "min": 1,
              "max": 5,
              "fortuneModifier": 2
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 100,
              "fortuneModifier": 10
            },
            "silktouch": "REQUIRED",
            "fortuneLevelRequired": 0
          },
          "item": {
            "item": [
              "ore:logWood"
            ],
            "quantity": {
              "min": 1,
              "max": 5,
              "fortuneModifier": 1
            }
          }
        }
      ]
    }
  ]
}
```