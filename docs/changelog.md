1.19.1

  * **Fixed:**
    * All rules for any given block will match regardless of criteria

---

1.19.0

  * **Added:**
    * Fallthrough feature to continue matching rules after a rule has been matched

---

1.18.0

  * **Added:**
    * Define a blockstate to replace the broken block with [(#69)](https://github.com/codetaylor/dropt/issues/69)

  * **Fixed:**
    * Improved bracket style in parser log messages for clarity
    * Removed unnecessary boxing from parsers for performance
    
---

1.17.0

  * **Added:**
    * Command `/dropt export` to export all rules currently loaded in memory as json files
    * `force` flag to force a drop to ignore the selector and always drop [(#65)](https://github.com/codetaylor/dropt/issues/65) [(Examples)](examples/forced_drops.md) [(DOC)](json/syntax/#iruledrop)
    * Support for explicit item quantity declaration in the drop's item list:
        * ie. `"items": ["minecraft:string * 100"]`
    * `drop` enum to allow dropping all items listed in a drop's item list [(#62)](https://github.com/codetaylor/dropt/issues/62) [(Examples)](examples/drop_all.md) [(DOC)](json/syntax/#iruledropitem)

  * **Fixed:**
    * Missing error error reporting when the block string parser can't find a block with the given resource location

  * **Changed:**
    * Bumped API to version 4
    
---

1.16.1

  * **Fixed:**
    * Egregious logic error in the held item matcher [(#61)](https://github.com/codetaylor/dropt/issues/61)

---

1.16.0

  * Added:
    * `matchQuantity` for matching a drop's quantity to an existing drop's quantity [(#55)](https://github.com/codetaylor/dropt/issues/55) [(Examples)](examples/match_quantity.md) [(DOC)](json/syntax/#iruledropmatchquantity)
    * `spawnDistance` for matching against distance from spawn [(#39)](https://github.com/codetaylor/dropt/issues/39) [(Examples)](examples/distance.md) [(DOC)](json/syntax/#irulematchspawndistance)
    * NBT matching for held items [(#57)](https://github.com/codetaylor/dropt/issues/57) [(Examples)](examples/nbt.md)

  * Fixed:
    * Edge case that caused undesired behavior if the quantity of item to drop exceeds the item's max stack size

---

1.15.1

  * Fixed:
    * Off-by-one error causing each parsed `ItemMatcher` to always match a meta of `0` (#56)

---

1.15.0

  * Added:
    * Harvester rule to match item held in off-hand (#53) Note: the main hand rule and off hand rule, if both defined, must both match for the rule to match. In other words, it's main hand match AND off hand match.

---

1.14.0

  * Added:
    * REAL_PLAYER and FAKE_PLAYER harvester type

  * Changed:
    * Bumped API to version 3

---

1.13.0

  * Added:
    * EXPLOSION harvester type that will match only when the block being harvested is destroyed by an explosion. Note that some dropped items are still destroyed by explosions as per the normal mechanics. (#49)

  * Changed:
    * Requires Athenaeum >= 1.16.0
    * Bumped API to version 2

---

1.12.0

  * Changed:
    * Dropt will now fail to load JSON rule lists with unknown fields and will log the failure to the console and log file. This should assist in debugging your JSON structure.

  * Added:
    * Config option to disable the strict JSON parsing, reverting to the previous behavior.

---

1.11.2

  * Fixed:
    * Dependency cycle (#45)

---

1.11.1

  * Fixed:
    * Crash due to missing the entire API -.- (#44)

  * Requires: Athenaeum >= 1.15.0

---

1.11.0

  * Added:
    * ZenScript support for the DroptAPI
    * A proper DroptAPI
    * New documentation

---

1.10.2

  * Prevent NPE (#41)
  * Fix: Blacklisted harvest level doesn't work as expected (#43)

---

1.10.1

  * Allow xp to drop when item array is empty (#42)

---

1.10.0

  * Add harvest level matching to held item matching

---

1.9.4

  * Changed: update to GameStages 2.0

---

1.9.3

  * Fixed: silk touch not working as expected with blocks that are coded to not be silk-touchable (#27)

---

1.9.2

  * Fixed: NPE crash when parsing trailing comma in RuleDrop list (#26)

---

1.9.1

  * Added: limit the number of profiling rules to 150000
  * Fixed: RuleDrop's listed after empty RuleDrops don't get parsed (#25)

---

1.9.0

  * Added: allow empty hand matching (#24)

---

1.8.2

  * Fixed: Blocks without rules, or that don't match a rule, don't drop their normal XP (#22)

---

1.8.1

  * Fixed: RandomFortuneInt not behaving as expected (#20)
  * Fixed: rule file priority reversed (#19)

---

1.8.0

  * Added: new command `/dropt verbose` to log broken block resource location and meta to console

---

1.7.0

  * Added: feature to allow replacing XP drops with the specified XP (#14)
  * Fixed: harvest drop event handler was not set to lowest priority, causing drop modifications to produce undesired results (#14)

---

1.6.10

  * Fixed: When adding XP drops to a block, XP ball appears but does not add to bar or make ding noise (#13)

---

1.6.9

  * Fixed: NPE with fake player

---

1.6.8

  * Fixed: harvester type is ignored

---

1.6.7

  * Changed: moved recipe item parser code into the Athenaeum lib
  * Requires: Athenaeum >= 1.10.5

---

1.6.6

  * Fixed: improper meta matching for blocks (#10)

---

1.6.5

  * Added: new command `/dropt hand` to print and copy a held item's string that can be pasted into the drops section
  * Added: support for generating NBT tags on dropped items

---

1.5.5

  * Fixed: tool with zero durability is removed from player before rules are matched, resulting in improper matching (#5)

---

1.5.4

  * Added: rule caching to significantly improve performance (#3)

---

1.4.4

  * Fixed: NPE preventing drops (#4)

---

1.4.3

  * Added: random, fortune modified xp to drops
  * Changed: requires Athenaeum lib >= 1.4.3

---

1.3.4

  * Added: config file to enable performance profiling options, see PERFORMANCE.md

---

1.3.3

  * Changed: internal refactoring / code cleanup / performance enhancements
  * Changed: introduced whitelist/blacklist for five lists: `match.blocks`, `match.items`, `match.harvester.heldItemMainHand`, `match.harvester.playerName`, `match.harvester.gamestages`
  * Note: syntax breaking changes for: `match.blocks`, `match.items`, `match.harvester.heldItemMainHand`, `match.harvester.playerName`, `match.harvester.gamestages`

---

1.2.3

  * Fixed: indexing bug with weighted picker

---

1.2.2

  * Added: dropStrategy enum, "UNIQUE | REPEAT" defaults to repeat (current behavior)
  * Fixed: critical bugs causing multiple file rule matching to fail and produce unexpected results
  * Changed: minor debug output corrections

---

1.1.2

  * Changed: the location of the log file has moved from the config folder to the instance's root folder: `[instance]/dropt.log`
  * Changed: added to and polished debug output

---

1.1.1

  * Added: debug feature, see SYNTAX.md and DEBUG.md

---

1.0.1

  * Fixed: NPE (#1)

---

1.0.0