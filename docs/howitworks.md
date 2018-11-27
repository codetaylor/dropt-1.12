## Rule Lists

Dropt maintains a collection of rule lists sorted by each list's priority. Rule lists can be defined using ZenScript, JSON, or the Java API.

### ZenScript

Rule lists can be defined using ZenScript and CraftTweaker.

See the [ZenScript reference](zs/reference.md) for details.

Familiarize yourself with [CraftTweaker and ZenScript here](https://crafttweaker.readthedocs.io/en/latest/).

### JSON

Dropt .json files are placed in the `<instance>/config/dropt` folder. Filenames are not important so long as they have the .json extension and you can have as many files as you like.

See the [JSON syntax](json/syntax.md) for more a description of the JSON syntax.

### Java API

Use the Java API if you are developing a mod and would like to leverage Dropt's features for your mod's block drop needs.

See the [Java API reference](api/reference.md) for more info.

## The Algorithm

When a block is broken, Dropt first intercepts the `BlockEvent.BreakEvent` event and caches a copy of the player's held item, caches any experience that the block is set to drop, and prevents xp from dropping by setting the event's xp to zero.

Next, Dropt intercepts the `BlockEvent.HarvestDropsEvent` to match criteria against the rule lists. Dropt uses a rule caching strategy here to reduce lookup time.

// TODO: finish

## Performance

Dropt parses all strings when the rules are loaded to reduce unnecessary string parsing and string concatenation during matching. Some string equivalency testing is used during matching. It uses a simple linear search to cache rules in lists and the profiling output below is the result of a worst-case scenario in which the rule to match is at the end of a 100k+ rule list.

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