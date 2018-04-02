1.9.2
* Fixed: NPE crash when parsing trailing comma in RuleDrop list (#26)

1.9.1
* Added: limit the number of profiling rules to 150000
* Fixed: RuleDrop's listed after empty RuleDrops don't get parsed (#25)

1.9.0
* Added: allow empty hand matching (#24)

1.8.2
* Fixed: Blocks without rules, or that don't match a rule, don't drop their normal XP (#22)

1.8.1
* Fixed: RandomFortuneInt not behaving as expected (#20)
* Fixed: rule file priority reversed (#19)

1.8.0
* Added: new command `/dropt verbose` to log broken block resource location and meta to console

1.7.0
* Added: feature to allow replacing XP drops with the specified XP (#14)
* Fixed: harvest drop event handler was not set to lowest priority, causing drop modifications to produce undesired results (#14)

1.6.10
* Fixed: When adding XP drops to a block, XP ball appears but does not add to bar or make ding noise (#13)

1.6.9
* Fixed: NPE with fake player

1.6.8
* Fixed: harvester type is ignored

1.6.7
* Changed: moved recipe item parser code into the Athenaeum lib
* Requires: Athenaeum >= 1.10.5

1.6.6
* Fixed: improper meta matching for blocks (#10) 

1.6.5
* Added: new command `/dropt hand` to print and copy a held item's string that can be pasted into the drops section
* Added: support for generating NBT tags on dropped items

1.5.5
* Fixed: tool with zero durability is removed from player before rules are matched, resulting in improper matching (#5)

1.5.4
* Added: rule caching to significantly improve performance (#3)

1.4.4
* Fixed: NPE preventing drops (#4)

1.4.3
* Added: random, fortune modified xp to drops
* Changed: requires Athenaeum lib >= 1.4.3

1.3.4
* Added: config file to enable performance profiling options, see PERFORMANCE.md

1.3.3
* Changed: internal refactoring / code cleanup / performance enhancements
* Changed: introduced whitelist/blacklist for five lists: `match.blocks`, `match.items`, `match.harvester.heldItemMainHand`, `match.harvester.playerName`, `match.harvester.gamestages`
* Note: syntax breaking changes for: `match.blocks`, `match.items`, `match.harvester.heldItemMainHand`, `match.harvester.playerName`, `match.harvester.gamestages`

1.2.3
* Fixed: indexing bug with weighted picker

1.2.2
* Added: dropStrategy enum, "UNIQUE | REPEAT" defaults to repeat (current behavior)
* Fixed: critical bugs causing multiple file rule matching to fail and produce unexpected results
* Changed: minor debug output corrections

1.1.2
* Changed: the location of the log file has moved from the config folder to the instance's root folder: `[instance]/dropt.log`
* Changed: added to and polished debug output

1.1.1
* Added: debug feature, see SYNTAX.md and DEBUG.md

1.0.1
* Fixed: NPE (#1)

1.0.0