# Welcome to Dropt

Dropt is a block drop replacement mod that allows defining complex drop conditions and replacement rules with JSON, ZenScript, or the DroptAPI.

---

## Features

### Performance:

  * uses rule caching per block state to significantly improve performance

### Matches based on:

  * block (meta, meta wildcard, multiple meta values) (whitelist/blacklist)
  * items dropped (meta, meta wildcard, multiple meta values, oredict) (whitelist/blacklist)
  * harvester type (player, non-player, any)
  * harvester held item (meta, meta wildcard) (whitelist/blacklist)
  * harvester game stages (requires gamestages mod)
  * harvester player name
  * biome (whitelist/blacklist)
  * dimension (whitelist/blacklist)
  * vertical range

### Replacement strategies:

  * add to existing drops
  * replace all drops
  * replace all drops if drop selected
  * replace all matched dropped items (replace items defined in match)
  * replace all matched dropped items if drop selected (replace items defined in match)

### Drop strategies:

  * repeat (can select the same drop more than once)
  * unique (can select a drop only once)

### Drop selection count based on:

  * fixed value
  * random value in defined range
  * fortune modified

### Drop selection based on:

  * fortune modified weight value
  * minimum fortune level
  * silk touch requirement (required, excluded, any)

### Drop quantity based on:

  * fixed value
  * random value in defined range
  * fortune modified

### Drops:

  * can define drop list per drop
  * can define drops with meta wildcard
  * can define drops as oredict entry
  * can define xp to drop (fortune modified range)
  * can define drops with NBT data

---

## Supported Versions

  * Features and bugfixes:
    * Minecraft 1.12.2