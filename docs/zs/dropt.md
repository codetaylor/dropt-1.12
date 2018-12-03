### Class

```java
import mods.dropt.Dropt
```

This class acts as an entry point to the Dropt API.
{: .zen-description }


Each method returns either a ready-to-configure object or a pre-configured object using the given parameters.
{: .zen-description }



#### Methods

```java
static RuleList list(string name);
```

Returns a new, ready-to-configure list object.
{: .zen-description }

Subsequent calls with the same name will return the same list object.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulelist](/json/syntax/#irulelist)
{: .zen-description }


```java
static Rule rule();
```

Returns a new, ready-to-configure Rule object.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irule](/json/syntax/#irule)
{: .zen-description }


```java
static Harvester harvester();
```

Returns a new, ready-to-configure Harvester object.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvester](/json/syntax/#irulematchharvester)
{: .zen-description }


```java
static Drop drop();
```

Returns a new, ready-to-configure Drop object.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }


```java
static Range range(int fixed);
```

Returns a range object with a fixed value.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irangeint](/json/syntax/#irangeint)
{: .zen-description }


```java
static Range range(int min, int max);
```

Returns a range object with a minimum and maximum value.
{: .zen-description }

The minimum and maximum values are inclusive.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irangeint](/json/syntax/#irangeint)
{: .zen-description }


```java
static Range range(int min, int max, int fortuneModifier);
```

Returns a range object with the given minimum, maximum, and fortune modifier value.
{: .zen-description }

The minimum and maximum values are inclusive.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irandomfortuneint](/json/syntax/#irandomfortuneint)
{: .zen-description }


```java
static Weight weight(int weight);
```

Returns a weight object with the given weight.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropselectorweight](/json/syntax/#iruledropselectorweight)
{: .zen-description }


```java
static Weight weight(int weight, int fortuneModifier);
```

Returns a weight object with the given weight and fortune modifier.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropselectorweight](/json/syntax/#iruledropselectorweight)
{: .zen-description }

