
### Class

```java
import mods.dropt.Rule
```

<div class="zen-description zen-class-description">
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irule">/json/syntax/#irule</a></li></ul><br/>

</div>
#### Methods

```java
Rule debug();
```
<div class="zen-description zen-method-description">
Enable logging debug output for this rule.<br/>
<br/>
Make sure to disable this when you're done using it. It can create a significant amount of output in the Dropt log.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irule">/json/syntax/#irule</a></li></ul></div>

```java
Rule matchBlocks(string[] blockStrings);
```
<div class="zen-description zen-method-description">
Describes which blocks this rule will match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchblocks">/json/syntax/#irulematchblocks</a></li></ul></div>

```java
Rule matchBlocks(string type, string[] blockStrings);
```
<div class="zen-description zen-method-description">
Describes which blocks this rule will match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchblocks">/json/syntax/#irulematchblocks</a></li></ul></div>

```java
Rule matchDrops(IIngredient[] items);
```
<div class="zen-description zen-method-description">
Describes which dropped items to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchdrops">/json/syntax/#irulematchdrops</a></li></ul></div>

```java
Rule matchDrops(string type, IIngredient[] items);
```
<div class="zen-description zen-method-description">
Describes which dropped items to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchdrops">/json/syntax/#irulematchdrops</a></li></ul></div>

```java
Rule matchHarvester(Harvester harvester);
```
<div class="zen-description zen-method-description">
Describes criteria about the harvester to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchharvester">/json/syntax/#irulematchharvester</a></li></ul></div>

```java
Rule matchBiomes(string[] ids);
```
<div class="zen-description zen-method-description">
Describes biome id's to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchbiome">/json/syntax/#irulematchbiome</a></li></ul></div>

```java
Rule matchBiomes(string type, string[] ids);
```
<div class="zen-description zen-method-description">
Describes biome id's to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchbiome">/json/syntax/#irulematchbiome</a></li></ul></div>

```java
Rule matchDimensions(int[] ids);
```
<div class="zen-description zen-method-description">
Describes dimension id's to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchdimension">/json/syntax/#irulematchdimension</a></li></ul></div>

```java
Rule matchDimensions(string type, int[] ids);
```
<div class="zen-description zen-method-description">
Describes dimension id's to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematchdimension">/json/syntax/#irulematchdimension</a></li></ul></div>

```java
Rule matchVerticalRange(int min, int max);
```
<div class="zen-description zen-method-description">
Describes a vertical range to match.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irulematch">/json/syntax/#irulematch</a></li></ul></div>

```java
Rule replaceStrategy(string strategy);
```
<div class="zen-description zen-method-description">
Describes if and how the drops will be replaced.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irule">/json/syntax/#irule</a></li></ul></div>

```java
Rule dropStrategy(string strategy);
```
<div class="zen-description zen-method-description">
Describes how drops will be selected from the weighted picker.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irule">/json/syntax/#irule</a></li></ul></div>

```java
Rule dropCount(Range range);
```
<div class="zen-description zen-method-description">
Describes how many times the weighted picker will be queried for drops.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#irule">/json/syntax/#irule</a></li></ul></div>

```java
Rule addDrop(Drop drop);
```
<div class="zen-description zen-method-description">
Add a drop to this rule.<br/>
<br/>For more information, see:
<ul><li><a href="/json/syntax/#iruledrop">/json/syntax/#iruledrop</a></li></ul></div>
