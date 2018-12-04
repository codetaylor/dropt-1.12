# Getting Started

Dropt maintains a collection of rule lists that can be defined using ZenScript, JSON, or the Java API.

These rule lists are matched when a block is broken.

## ZenScript

Rule lists can be defined using ZenScript and CraftTweaker.

Familiarize yourself with [CraftTweaker and ZenScript here](https://crafttweaker.readthedocs.io/en/latest/).

See the [ZenScript Dropt reference](zs/dropt.md) for details.

## JSON

Dropt .json files are placed in the `<instance>/config/dropt` folder. Filenames are not important so long as they have the .json extension and you can have as many files as you like.

See the [JSON syntax](json/syntax.md) for more a description of the JSON syntax.

## Java API

Use the Java API if you are developing a mod and would like to leverage Dropt's features for your mod's block drop needs.

See the [Java API reference](api/reference.md) for more info.

# Under the Hood

To understand how to design your rules, it is important to understand how dropt works.

## BreakEvent

Dropt intercepts the `BlockEvent.BreakEvent` in order to cache the player's held item. This is because the player's held item is damaged between this event and the `BlockEvent.HarvestDropsEvent`. If the held item isn't cached, and the item has only one durability remaining, it won't be accurately matched by Dropt during the harvest event because it will be empty.

## HarvestDropsEvent

Dropt intercepts the `BlockEvent.HarvestDropsEvent` at the lowest priority to match criteria against the rule lists. Dropt uses a rule caching strategy here to reduce lookup time.

### Caching and Performance

When a block is broken, Dropt first checks the rule cache for the given block. If the cache doesn't have an entry for the given block, Dropt iterates all rules and places any rule that matches *just* the given block into the cache. The next time that the same block is broken, the cache will return the cached rules and match against only those, saving a significant amount of time.

Dropt parses all strings when the rules are loaded to reduce unnecessary string parsing and string concatenation during matching. Some string equivalency testing is used during matching. The profiling output below is the result of a worst-case scenario in which the rule to match is at the end of a 100k+ rule list.

```text
[INFO] Injected 103573 rules in 118 ms
[INFO] Parsed 103575 rules in 532 ms
[INFO] Cached 1 rules from 103575 rules in 56 ms, blockState: minecraft:stone[variant=stone]
[INFO] Searched 1 rules in 1 ms
[INFO] Modified drops in 0 ms
[INFO] Searched 1 rules in 1 ms
[INFO] Modified drops in 0 ms
```

To reproduce the profiling on your machine, see the config options.

### List Priority

Dropt sorts and iterates its rule list in order of each lists priority.

Rule lists with a larger priority will be matched first.

Rule lists with the same priority will be matched in the order that they were supplied to Dropt, which is undefined.

## Selecting Drops

If a rule matches, a weighted picker is built by iterating over the defined drops. If a drop's selector allows it to be selected (ie. silktouch, min. fortune level, etc..) the drop will be placed into the weighted picker with the weight defined by its selector.

Weights are summed, therefore `chance = weight / sum`.

For example, the chances for the following items with the given weights can be derived like so:

  * ItemA, 20; `chance = 20 / 60 = 0.3333`
  * ItemB, 40; `chance = 40 / 60 = 0.6666`


After the weighted picker is built, Dropt begins selecting drops from the picker.

If the drop selected contains more than one item, a random item will be chosen from the list and dropped with the quantity defined by the drop.

The rule's `dropStrategy` controls whether or not the same item can be picked more than once.

The rule's `replaceStrategy` controls how the picked drops either replace or are added to any existing drops the block may have.
