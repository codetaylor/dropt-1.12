1.5.5:
* Fixed: tool with zero durability is removed from player before rules are matched, resulting in improper matching (#5)

1.5.4:
* Added: rule caching to significantly improve performance (#3)

1.4.4:
* Fixed: NPE preventing drops (#4)

1.4.3:
* Added: random, fortune modified xp to drops
* Changed: requires Athenaeum lib >= 1.4.3

1.3.4:
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