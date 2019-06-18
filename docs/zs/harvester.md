
### Class

```java
import mods.dropt.Harvester
```

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvester](/json/syntax/#irulematchharvester)
{: .zen-description }



#### Methods

```java
Harvester type(string type);
```

Matches based on player / non-player / explosion.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvester](/json/syntax/#irulematchharvester)
{: .zen-description }


```java
Harvester mainHand(string harvestLevel);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester mainHand(IItemStack[] items);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester mainHand(string type, IItemStack[] items);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester mainHand(string type, IItemStack[] items, string harvestLevel);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester offHand(string harvestLevel);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester offHand(IItemStack[] items);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester offHand(string type, IItemStack[] items);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester offHand(string type, IItemStack[] items, string harvestLevel);
```

Matches based on what the player is or isn't holding.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterhelditem](/json/syntax/#irulematchharvesterhelditem)
{: .zen-description }


```java
Harvester gameStages(string[] stages);
```

Matches based on which game stages the player has or has not unlocked.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvestergamestage](/json/syntax/#irulematchharvestergamestage)
{: .zen-description }


```java
Harvester gameStages(string require, string[] stages);
```

Matches based on which game stages the player has or has not unlocked.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvestergamestage](/json/syntax/#irulematchharvestergamestage)
{: .zen-description }


```java
Harvester gameStages(string type, string require, string[] stages);
```

Matches based on which game stages the player has or has not unlocked.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvestergamestage](/json/syntax/#irulematchharvestergamestage)
{: .zen-description }


```java
Harvester playerName(string[] names);
```

Matches based on player name.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterplayername](/json/syntax/#irulematchharvesterplayername)
{: .zen-description }


```java
Harvester playerName(string type, string[] names);
```

Matches based on player name.
{: .zen-description }

For more information, see:
{: .zen-description }

  * [/json/syntax/#irulematchharvesterplayername](/json/syntax/#irulematchharvesterplayername)
{: .zen-description }

