
### Class

```java
import mods.dropt.Rule
```

For more information, see:
{: .zen-description }

  * [/json/syntax/#irule](/json/syntax/#irule)
{: .zen-description }



#### Methods

```java
Rule debug();
```

Enable logging debug output for this rule.
{: .zen-description }


{: .zen-description }

Make sure to disable this when you're done using it. It can create a significant amount of output in the Dropt log.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irule](/json/syntax/#irule)
{: .zen-description }


```java
Rule matchBlocks(string[] blockStrings);
```

Describes which blocks this rule will match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchblocks](/json/syntax/#irulematchblocks)
{: .zen-description }


```java
Rule matchBlocks(string type, string[] blockStrings);
```

Describes which blocks this rule will match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchblocks](/json/syntax/#irulematchblocks)
{: .zen-description }


```java
Rule matchDrops(IIngredient[] items);
```

Describes which dropped items to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchdrops](/json/syntax/#irulematchdrops)
{: .zen-description }


```java
Rule matchDrops(string type, IIngredient[] items);
```

Describes which dropped items to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchdrops](/json/syntax/#irulematchdrops)
{: .zen-description }


```java
Rule matchHarvester(Harvester harvester);
```

Describes criteria about the harvester to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvester](/json/syntax/#irulematchharvester)
{: .zen-description }


```java
Rule matchBiomes(string[] ids);
```

Describes biome id's to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchbiome](/json/syntax/#irulematchbiome)
{: .zen-description }


```java
Rule matchBiomes(string type, string[] ids);
```

Describes biome id's to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchbiome](/json/syntax/#irulematchbiome)
{: .zen-description }


```java
Rule matchDimensions(int[] ids);
```

Describes dimension id's to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchdimension](/json/syntax/#irulematchdimension)
{: .zen-description }


```java
Rule matchDimensions(string type, int[] ids);
```

Describes dimension id's to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchdimension](/json/syntax/#irulematchdimension)
{: .zen-description }


```java
Rule matchVerticalRange(int min, int max);
```

Describes a vertical range to match.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematch](/json/syntax/#irulematch)
{: .zen-description }


```java
Rule replaceStrategy(string strategy);
```

Describes if and how the drops will be replaced.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irule](/json/syntax/#irule)
{: .zen-description }


```java
Rule dropStrategy(string strategy);
```

Describes how drops will be selected from the weighted picker.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irule](/json/syntax/#irule)
{: .zen-description }


```java
Rule dropCount(Range range);
```

Describes how many times the weighted picker will be queried for drops.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irule](/json/syntax/#irule)
{: .zen-description }


```java
Rule addDrop(Drop drop);
```

Add a drop to this rule.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }

