## Example #1

Replace all dropped `ore:logWood` from any block with a single random dye 25% of the time.

```json
{
  "rules": [
    {
      "match": {
        "drops": {
          "drops": [
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

## Example #3

Take control of your fortune.

```json
{
  "rules": [
    {
      "match": {
        "blocks": {
          "blocks": [
            "minecraft:quartz_ore"
          ]
        }
      },
      "drops": [
        {
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 1,
              "max": 2
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 10
            },
            "fortuneLevelRequired": 1
          },
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 4,
              "max": 8
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 100
            },
            "fortuneLevelRequired": 2
          },
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 16,
              "max": 32
            }
          }
        },
        {
          "selector": {
            "weight": {
              "value": 1000
            },
            "fortuneLevelRequired": 3
          },
          "item": {
            "items": [
              "minecraft:quartz"
            ],
            "quantity": {
              "min": 32,
              "max": 64
            }
          }
        }
      ]
    }
  ]
}
```

Varying the fortune is a little tricky. Let me explain what the script above is doing.

First, it adds four drops to the drop pool, of which only one will be selected to drop.

The first drop at the top of the list adds a drop that varies between 1 - 2 drops and has a weight of `1`. This drop will always be added to the drop pool.

The next drop, the one right below it, adds a drop that varies between 4 - 8 drops, but requires a minimum fortune level of `1` to be added to the drop pool. It has a weight of `10` meaning that if added to the pool, it is about 10 times more likely to get chosen than the first drop added, the one with no selection rules and a weight of `1`. 

The next drop varies between 16 - 32 drops and has a minimum fortune level of `2` and a weight of `100`. This drop is about 10 times more likely to be chosen than the `Fortune I` drop and about 100 times more likely to be chosen than the `No Fortune` drop. This drop will only be added to the pool if the player has `Fortune II` or greater.

Finally, the last drop added varies between 32 - 64 and has a minimum fortune level of `3` and a weight of `1000`. This drop is about 10 times more likely to be chosen than the `Fortune II` drop.  This drop will only be added to the pool if the player has `Fortune III`.

What this does is create a drop pool that scales up as the player increases fortune, but allows you to configure each tier with a random range of drop quantity.

Now keep in mind, that while it is unlikely, it is possible that a player with `Fortune III` could mine a block and get very unlucky by having the first drop rule selected. However, with the example above, that would be a 1 in 1111 chance of happening.

## Example #4

Match geolosys ore blocks and prevent nuclearcraft gems and dust from dropping.

Script provided by [Fireztonez](https://github.com/Fireztonez).

```json
{
  "rules": [
    {
      "match": {
        "blocks": {
          "blocks": [
            "geolosys:ore_vanilla:0",
            "geolosys:ore_vanilla:1",
            "geolosys:ore_vanilla:3",
            "geolosys:ore_vanilla:4",
            "geolosys:ore_vanilla:5",
            "geolosys:ore_vanilla:6"
          ]
        },
        "drops": {
          "drops": [
            "nuclearcraft:gem:*",
            "nuclearcraft:dust:*"
          ]
        }
      },
      "replaceStrategy": "REPLACE_ITEMS",
      "drops": [{}]
    }
  ]
}
```