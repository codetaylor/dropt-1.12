### Dropt

```java
import mods.dropt.Dropt
```

<div class="zen-description zen-class-description">
This class acts as an entry point to the Dropt API.

Each method returns either a ready-to-configure object or a
pre-configured object using the given parameters.

</div>
#### Dropt - Methods

```java
static RuleList list(string name);
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure list object.
Subsequent calls with the same name will return the same list object.
</div>

```java
static Rule rule();
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure Rule object.
</div>

```java
static Harvester harvester();
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure Harvester object.
</div>

```java
static Drop drop();
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure Drop object.
</div>

```java
static Range range(int fixed);
```
<div class="zen-description zen-method-description">
Returns a range object with a fixed value.
</div>

```java
static Range range(int min, int max);
```
<div class="zen-description zen-method-description">
Returns a range object with a minimum and maximum value.

The minimum and maximum values are inclusive.
</div>

```java
static Range range(int min, int max, int fortuneModifier);
```
<div class="zen-description zen-method-description">
Returns a range object with the given minimum, maximum, and fortune
modifier value.

The minimum and maximum values are inclusive.
</div>

```java
static Weight weight(int weight);
```
<div class="zen-description zen-method-description">
Returns a weight object with the given weight.
</div>

```java
static Weight weight(int weight, int fortuneModifier);
```
<div class="zen-description zen-method-description">
Returns a weight object with the given weight and fortune modifier.
</div>

### RuleList

```java
import mods.dropt.RuleList
```

<div class="zen-description zen-class-description">
</div>
#### RuleList - Methods

```java
RuleList priority(int priority);
```
<div class="zen-description zen-method-description">
Set the priority of this rule list.
Rule lists with a larger priority will be matched first.
</div>

```java
RuleList add(Rule rule);
```
<div class="zen-description zen-method-description">
Add a configured rule to this rule list.
</div>

### Rule

```java
import mods.dropt.Rule
```

<div class="zen-description zen-class-description">
</div>
#### Rule - Methods

```java
Rule debug();
```
<div class="zen-description zen-method-description">
Enable logging debug output for this rule.
Make sure to disable this when you're done using it. It can create
a significant amount of output in the Dropt log.
</div>

```java
Rule matchBlocks(string[] blockStrings);
```
<div class="zen-description zen-method-description">
Describes which blocks this rule will match.
@see /json/syntax/#irulematchblocks
</div>

```java
Rule matchBlocks(string type, string[] blockStrings);
```
<div class="zen-description zen-method-description">
Describes which blocks this rule will match.
@see /json/syntax/#irulematchblocks
</div>

```java
Rule matchDrops(IIngredient[] items);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchDrops(string type, IIngredient[] items);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchHarvester(Harvester harvester);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchBiomes(string[] ids);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchBiomes(string type, string[] ids);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchDimensions(int[] ids);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchDimensions(string type, int[] ids);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule matchVerticalRange(int min, int max);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule replaceStrategy(string strategy);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule dropStrategy(string strategy);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule dropCount(Range range);
```
<div class="zen-description zen-method-description">
</div>

```java
Rule addDrop(Drop drop);
```
<div class="zen-description zen-method-description">
</div>

### Harvester

```java
import mods.dropt.Harvester
```

<div class="zen-description zen-class-description">
</div>
#### Harvester - Methods

```java
Harvester type(string type);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester mainHand(string harvestLevel);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester mainHand(IItemStack[] items);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester mainHand(string type, IItemStack[] items);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester mainHand(string type, IItemStack[] items, string harvestLevel);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester gameStages(string[] stages);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester gameStages(string require, string[] stages);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester gameStages(string type, string require, string[] stages);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester playerName(string[] names);
```
<div class="zen-description zen-method-description">
</div>

```java
Harvester playerName(string type, string[] names);
```
<div class="zen-description zen-method-description">
</div>

### Drop

```java
import mods.dropt.Drop
```

<div class="zen-description zen-class-description">
</div>
#### Drop - Methods

```java
Drop selector(Weight weight);
```
<div class="zen-description zen-method-description">
</div>

```java
Drop selector(Weight weight, string silkTouch);
```
<div class="zen-description zen-method-description">
</div>

```java
Drop selector(Weight weight, string silkTouch, int fortuneLevelRequired);
```
<div class="zen-description zen-method-description">
</div>

```java
Drop items(IItemStack[] items);
```
<div class="zen-description zen-method-description">
</div>

```java
Drop items(IItemStack[] items, Range range);
```
<div class="zen-description zen-method-description">
</div>

```java
Drop xp(string replace, Range amount);
```
<div class="zen-description zen-method-description">
</div>
