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

For more information, browse the following package:
https://github.com/codetaylor/dropt/tree/master/src/api/java/com/codetaylor/mc/dropt/api