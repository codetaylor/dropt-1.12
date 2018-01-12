## Example #1

Replace all dropped `ore:logWood` from any block with a single random dye 25% of the time.

```json
{
  "rules": [
    {
      "match": {
        "drops": {
          "items": [
            "ore:logWood"
          ]
        }
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

## Example #2

Replace all drops from the block `minecraft:stone` and `minecraft:gravel` 
below Y level `20` for a player named `codetaylor`, who has the gamestages 
`one` and `two`, holding a `silk touch` `diamond pickaxe` with 
`2` to `6` `emeralds` or `diamonds` `50%` of the time.

```json
{
  "rules": [
    {
      "match": {
        "blocks": {
          "blocks": [
            "minecraft:stone:0",
            "minecraft:gravel"
          ] 
        },
        "verticalRange": {
          "min": 0,
          "max": 20
        },
        "harvester": {
          "playerName": {
            "names": [
              "codetaylor"
            ]
          },
          "gamestages": {
            "require": "ALL",
            "stages": [
              "one",
              "two"
            ]
          },
          "heldItemMainHand": {
            "items": [
              "minecraft:diamond_pickaxe:*"
            ]
          }
        }
      },
      "replaceStrategy": "REPLACE_ALL_IF_SELECTED",
      "drops": [
        {
          "selector": {
            "weight": {
              "value": 50
            },
            "silktouch": "REQUIRED"
          },
          "item": {
            "items": [
              "minecraft:emerald",
              "minecraft:diamond"
            ],
            "quantity": {
              "min": 2,
              "max": 6
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 50
            }
          }
        }
      ]
    }
  ]
}
```