
### Class

```java
import mods.dropt.Drop
```

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }



#### Methods

```java
Drop force();
```

Forces a drop to always drop, ignores selector.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }


```java
Drop selector(Weight weight);
```

Defines a selector for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropselector](/json/syntax/#iruledropselector)
{: .zen-description }


```java
Drop selector(Weight weight, string silkTouch);
```

Defines a selector for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropselector](/json/syntax/#iruledropselector)
{: .zen-description }


```java
Drop selector(Weight weight, string silkTouch, int fortuneLevelRequired);
```

Defines a selector for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropselector](/json/syntax/#iruledropselector)
{: .zen-description }


```java
Drop items(IItemStack[] items);
```

Defines the item list for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropitem](/json/syntax/#iruledropitem)
{: .zen-description }


```java
Drop items(string drop, IItemStack[] items);
```

Defines the item list for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropitem](/json/syntax/#iruledropitem)
{: .zen-description }


```java
Drop items(IItemStack[] items, Range range);
```

Defines the item list for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropitem](/json/syntax/#iruledropitem)
{: .zen-description }


```java
Drop items(string drop, IItemStack[] items, Range range);
```

Defines the item list for this drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledropitem](/json/syntax/#iruledropitem)
{: .zen-description }


```java
Drop matchQuantity(IItemStack[] drops);
```

Defines drops to match quantity.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }


```java
Drop xp(string replace, Range amount);
```

Defines an experience drop.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }


```java
Drop replaceBlock(string block);
```

Defines a blockstate to replace the block with.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }


```java
Drop replaceBlock(string block, Map properties);
```

Defines a blockstate to replace the block with.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#iruledrop](/json/syntax/#iruledrop)
{: .zen-description }

