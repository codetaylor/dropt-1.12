All parameters are required unless explicitly marked *Optional*.

## IRuleList

<big><pre>{
  "priority": int,
  "rules": [IRule](#IRule)[]
}</pre></big>

* `priority`: `int`
  * *Optional* - if omitted, defaults to `0`.
  * Files with a higher priority will have their rules matched first.

## IRule
<big><pre>{
  "match": [IRuleMatch](#IRuleMatch),
  "mergeStrategy": "REPLACE" | "ADD" | "REPLACE_IF_SELECTED",
  "dropCount": [IRangeInt](#IRangeInt),
  "drops": [IRuleDrop](#IRuleDrop)[]
}</pre></big>

Each `IRule` is matched in the same order it is read from the file.

* `mergeStrategy`: `enum`
  * *Optional* - if omitted, defaults to `ADD`.
  * `REPLACE`: All block drops will be replaced by drops from this rule.
  * `ADD`: Any selected drops from this rule will be added to the block's existing drops. If this rule is matched and no drops are selected, the block will drop nothing.
  * `REPLACE_IF_SELECTED`: All block drops will be replaced by drops from this rule if and only if drops are selected from this rule.
  
## IRuleMatch
<big><pre>{
  "blocks": String[],
  "harvester": [IRuleMatchHarvester](#IRuleMatchHarvester),
  "biomes": [IRuleMatchBiome](#IRuleMatchBiome),
  "dimensions": [IRuleMatchDimension](#IRuleMatchDimension)
}</pre></big>

The `IRuleMatch` is responsible for matching the rule to the block. When a block is broken, the first `IRule` to successfully satisfy the requirements of the `IRuleMatch` is selected.

* `blocks`: `String[]`
  * Syntax: `domain:path:meta`
  * Example: `minecraft:dye:4`

## IRuleMatchHarvester
<big><pre>{
  "type": "PLAYER" | "NON_PLAYER" | "ANY",
  "heldItemMainHand": String[],
  "gamestages": [IRuleMatchHarvesterGameStage](#IRuleMatchHarvesterGameStage)
}</pre></big>

* `type`: `enum`
  * *Optional* - if omitted, defaults to `ANY`.
  * `PLAYER`: The block must be broken by a player to match.
  * `NON_PLAYER`: The block must not be broken by a player to match.
  * `ANY`: The matcher does not care what broke the block. If a player broke the block and either `heldItemMainHand` or `gamestages` is provided, they will be checked.
  
* `heldItemMainHand`: `String[]`
  * *Optional* - if omitted, defaults to `[]`.
  * Syntax: `domain:path:meta`, `meta` can be a wildcard `*`. It is advised to use the meta wildcard `*` when matching tools.

## IRuleMatchHarvesterGameStage
<big><pre>{
  "type": "ALL" | "ANY",
  "stages": String[]
}</pre></big>

* `type`: `enum`
  * *Optional* - if omitted, defaults to `ANY`.
  * `ANY`: The player must have any of the listed gamestages to match.
  * `ALL`: The player must have all of the listed gamestages to match.
  
* `stages`: `String[]`

## IRuleMatchBiome
<big><pre>{
  "type": "WHITELIST" | "BLACKLIST",
  "ids": String[]
}</pre></big>

* `type`: `enum`
  * *Optional* - if omitted, defaults to `WHITELIST`.
  * `WHITELIST`: If the block is broken in a biome in the list, the match passes.
  * `BLACKLIST`: If the block is broken in a biome not in the list, the match passes.
  
* `ids`: `String[]`
  * Syntax: `domain:path`
  * Example: `minecraft:birch_forest_hills`
  
## IRuleMatchDimension
<big><pre>{
  "type": "WHITELIST" | "BLACKLIST",
  "ids": int[]
}</pre></big>

* `type`: `enum`
  * *Optional* - if omitted, defaults to `WHITELIST`.
  * `WHITELIST`: If the block is broken in a dimension in the list, the match passes.
  * `BLACKLIST`: If the block is broken in a dimension not in the list, the match passes.
  
* `ids`: `int[]`
  * An array containing the integer id's of dimensions.
  
## IRangeInt
<big><pre>{
  "min": int,
  "max": int,
  "fortuneModifier": int
}</pre></big>

Calculation: `random.nextInt(max - min) + min + fortuneModifier * fortuneLevel`

* `min`: `int`
  * *Optional* - if omitted, defaults to `0`.
  * The minimum value that will be randomly selected (inclusive).
  
* `max`: `int`
  * *Optional* - if omitted, defaults to `0`.
  * The maximum value that will be randomly selected (inclusive).
  
* `fortuneModifier`
  * This value will be added to the result of the random value for each level of fortune passed into the `BlockEvent.HarvestDropsEvent`.

## IRuleDrop

<big><pre>{
  "selector": [IRuleDropSelector](#IRuleDropSelector),
  "item": [IRuleDropItem](#IRuleDropItem)
}</pre></big>

This defines what the rule can potentially drop. The `IRuleDropSelector` acts as a predicate to identify drop candidates and the `IRuleDropItem` defines the actual drop.

If a rule is matched, the `IRuleDropSelector` for each `IRuleDrop` is queried to its candidacy for the weighted picker. If the drop is a valid candidate it will be placed into the weighted picker using the fortune modified weight described in the `IRuleDropSelector`.

## IRuleDropSelector

<big><pre>{
  "weight": [IRuleDropSelectorWeight](#IRuleDropSelectorWeight),
  "silktouch": "REQUIRED" | "EXCLUDED" | "ANY",
  "fortuneLevelRequired": int
}</pre></big>

* `silktouch`: `enum`
  * *Optional* - if omitted, defaults to `ANY`.
  * `REQUIRED`: Silk touch is required on the tool the player is using to break the block to have a chance of getting this drop.
  * `EXCLUDED`: Silk touch must not be on the tool the player is using to break the block to have a chance of getting this drop.
  * `ANY`: Silk touch is overlooked.
  
* `fortuneLevelRequired`: `int`
  * *Optional* - if omitted, defaults to `0`.
  * The minimum fortune level required for this drop to be a candidate.

## IRuleDropSelectorWeight

<big><pre>{
  "value": int,
  "fortuneModifier": int
}</pre></big>

Calculation: `value + fortuneModifier * fortuneLevel`

* `value`: `int`
  * *Optional* - if omitted, defaults to `1`.
  * This is the weight used when adding this drop to the weighted picker.
  
* `fortuneModifier`: `int`
  * *Optional* - if omitted, defaults to `0`.
  * This value will be added to weight value for each level of fortune passed into the `BlockEvent.HarvestDropsEvent`.
  
## IRuleDropItem

<big><pre>{
  "item": string
  "quantity": [IRangeInt](#IRuleDropSelectorWeight)
}</pre></big>

This defines the item for the `IRuleDrop`.

* `item`: `string`
  * Syntax: `domain:path:meta`, *meta must not be a wildcard*.