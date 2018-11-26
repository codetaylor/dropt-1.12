### Dropt

This class acts as an entry point to the Dropt API.

Each method returns either a ready-to-configure object or a
pre-configured object using the given parameters.

```java
import mods.dropt.Dropt
```

```java
/**
 * Returns a new, ready-to-configure list object.
 * Subsequent calls with the same name will return the same list object.
 */
static RuleList list(string name);

/**
 * Returns a new, ready-to-configure Rule object.
 */
static Rule rule();

/**
 * Returns a new, ready-to-configure Harvester object.
 */
static Harvester harvester();

/**
 * Returns a new, ready-to-configure Drop object.
 */
static Drop drop();

/**
 * Returns a range object with a fixed value.
 */
static Range range(int fixed);

/**
 * Returns a range object with a minimum and maximum value.
 * 
 * The minimum and maximum values are inclusive.
 */
static Range range(int min, int max);

/**
 * Returns a range object with the given minimum, maximum, and fortune
 * modifier value.
 * 
 * The minimum and maximum values are inclusive.
 */
static Range range(int min, int max, int fortuneModifier);

/**
 * Returns a weight object with the given weight.
 */
static Weight weight(int weight);

/**
 * Returns a weight object with the given weight and fortune modifier.
 */
static Weight weight(int weight, int fortuneModifier);
```

### RuleList

```java
import mods.dropt.RuleList
```

```java
RuleList priority(int priority);

RuleList add(Rule rule);
```

### Rule

```java
import mods.dropt.Rule
```

```java
Rule debug();

Rule matchBlocks(string[] blockStrings);

Rule matchBlocks(string type, string[] blockStrings);

Rule matchDrops(IIngredient[] items);

Rule matchDrops(string type, IIngredient[] items);

Rule matchHarvester(Harvester harvester);

Rule matchBiomes(string[] ids);

Rule matchBiomes(string type, string[] ids);

Rule matchDimensions(int[] ids);

Rule matchDimensions(string type, int[] ids);

Rule matchVerticalRange(int min, int max);

Rule replaceStrategy(string strategy);

Rule dropStrategy(string strategy);

Rule dropCount(Range range);

Rule addDrop(Drop drop);
```

### Harvester

```java
import mods.dropt.Harvester
```

```java
Harvester type(string type);

Harvester mainHand(string harvestLevel);

Harvester mainHand(IItemStack[] items);

Harvester mainHand(string type, IItemStack[] items);

Harvester mainHand(string type, IItemStack[] items, string harvestLevel);

Harvester gameStages(string[] stages);

Harvester gameStages(string require, string[] stages);

Harvester gameStages(string type, string require, string[] stages);

Harvester playerName(string[] names);

Harvester playerName(string type, string[] names);
```

### Drop

```java
import mods.dropt.Drop
```

```java
Drop selector(Weight weight);

Drop selector(Weight weight, string silkTouch);

Drop selector(Weight weight, string silkTouch, int fortuneLevelRequired);

Drop items(IItemStack[] items);

Drop items(IItemStack[] items, Range range);

Drop xp(string replace, Range amount);
```
