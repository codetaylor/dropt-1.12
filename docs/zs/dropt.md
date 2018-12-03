### Class

```java
import mods.dropt.Dropt
```

<div class="zen-description zen-class-description">
This class acts as an entry point to the Dropt API.<br/>
<br/>
<br/>
<br/>
Each method returns either a ready-to-configure object or a pre-configured object using the given parameters.<br/>
<br/>

</div>
#### Methods

```java
static RuleList list(string name);
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure list object.<br/>
Subsequent calls with the same name will return the same list object.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#irulelist">json/syntax/#irulelist</a></li></ul></div>

```java
static Rule rule();
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure Rule object.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#irule">json/syntax/#irule</a></li></ul></div>

```java
static Harvester harvester();
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure Harvester object.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#irulematchharvester">json/syntax/#irulematchharvester</a></li></ul></div>

```java
static Drop drop();
```
<div class="zen-description zen-method-description">
Returns a new, ready-to-configure Drop object.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#iruledrop">json/syntax/#iruledrop</a></li></ul></div>

```java
static Range range(int fixed);
```
<div class="zen-description zen-method-description">
Returns a range object with a fixed value.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#irangeint">json/syntax/#irangeint</a></li></ul></div>

```java
static Range range(int min, int max);
```
<div class="zen-description zen-method-description">
Returns a range object with a minimum and maximum value.<br/>
<br/>
The minimum and maximum values are inclusive.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#irangeint">json/syntax/#irangeint</a></li></ul></div>

```java
static Range range(int min, int max, int fortuneModifier);
```
<div class="zen-description zen-method-description">
Returns a range object with the given minimum, maximum, and fortune modifier value.<br/>
<br/>
The minimum and maximum values are inclusive.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#irandomfortuneint">json/syntax/#irandomfortuneint</a></li></ul></div>

```java
static Weight weight(int weight);
```
<div class="zen-description zen-method-description">
Returns a weight object with the given weight.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#iruledropselectorweight">json/syntax/#iruledropselectorweight</a></li></ul></div>

```java
static Weight weight(int weight, int fortuneModifier);
```
<div class="zen-description zen-method-description">
Returns a weight object with the given weight and fortune modifier.<br/>
<br/>For more information, see:
<ul><li><a href="json/syntax/#iruledropselectorweight">json/syntax/#iruledropselectorweight</a></li></ul></div>
