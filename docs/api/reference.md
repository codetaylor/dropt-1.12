```java
public static String modId()
```

 * **Returns:** the mod id of Dropt

```java
public static void registerRuleList(
    ResourceLocation id,
    int priority,
    List<IDroptRuleBuilder> builders
)
```

Call this during `com.codetaylor.mc.dropt.api.event.DroptLoadRulesEvent` to register rule lists.

 * **Parameters:**
   * `id` — the list id
   * `priority` — the list priority
   * `builders` — the list of rule builders to register

```java
public static IDroptRuleBuilder rule()
```

 * **Returns:** a new rule builder

```java
public static IDroptHarvesterRuleBuilder harvester()
```

 * **Returns:** a new harvester builder

```java
public static IDroptDropBuilder drop()
```

 * **Returns:** a new drop builder

```java
public static RandomFortuneInt range(
    int fixed
)
```

 * **Parameters:** `fixed` — the fixed range
 * **Returns:** a new fixed range

```java
public static RandomFortuneInt range(
    int min,
    int max
)
```

 * **Parameters:**
   * `min` — the range min
   * `max` — the range max
 * **Returns:** a new range

```java
public static RandomFortuneInt range(
    int min,
    int max,
    int fortuneModifier
)
```

 * **Parameters:**
   * `min` — the range min
   * `max` — the range max
   * `fortuneModifier` — the fortune modifier
 * **Returns:** a new fortune modified range

```java
public static RuleDropSelectorWeight weight(
    int weight
)
```

 * **Parameters:** `weight` — the weight
 * **Returns:** a new weight

```java
public static RuleDropSelectorWeight weight(
    int weight,
    int fortuneModifier
)
```

 * **Parameters:**
   * `weight` — the weight
   * `fortuneModifier` — the fortune modifier
 * **Returns:** a new fortune modified weight

```java
public static String itemString(
    String domain,
    String path
)
```

 * **Parameters:**
   * `domain` — the resource location domain
   * `path` — the resource location path
 * **Returns:** a ready-to-parse string
 * **See also:** #itemString(String, String, int, NBTTagCompound)

```java
public static String itemString(
    String domain,
    String path,
    int meta
)
```

 * **Parameters:**
   * `domain` — the resource location domain
   * `path` — the resource location path
   * `meta` — the item's meta
 * **Returns:** a ready-to-parse string
 * **See also:** #itemString(String, String, int, NBTTagCompound)

```java
public static String itemString(
    String domain,
    String path,
    int meta,
    @Nullable NBTTagCompound tag
)
```

Returns a ready-to-parse string in the format:

`domain:path:(*|meta)#nbt`

 * **Parameters:**
   * `domain` — the resource location domain
   * `path` — the resource location path
   * `meta` — the item's meta
   * `tag` — the item's tag
 * **Returns:** a ready-to-parse string

```java
public static String itemString(
    ItemStack itemStack
)
```

Returns a ready-to-parse string from the given {@link ItemStack}.

 * **Parameters:** `itemStack` — the item stack
 * **Returns:** a ready-to-parse string
 * **See also:** #itemString(String, String, int, NBTTagCompound)