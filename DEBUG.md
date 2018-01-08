### Debug

To debug a specific rule, set the property `debug`, in the root of the rule, to `true`. This is useful to see if a rule is matching, why it is or isn't matching, or if it is even being tested. Additionally, you'll be able to see how the rule is affecting the drop list.

Debug output will be saved to the log file and will look something like this:

```
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@38587bd0
[DEBUG] [EVENT] BlockState: minecraft:log[axis=y,variant=birch]
[DEBUG] [EVENT] Harvester: EntityPlayerMP['Player320'/1128, l='New World', x=157.36, y=76.00, z=252.54]
[DEBUG] [EVENT] Drops: [1xtile.log@2]
[DEBUG] [OK] Vertical position within bounds: 0 <= 76 <= 255
[DEBUG] [OK] No block matches defined in rule
[DEBUG] [--] Attempting to match candidate [1xtile.log@2] with: [ItemMatcher{domain='minecraft', path='log', meta=32767, metas=[]}]
[DEBUG] [OK] Domain match: (match) minecraft == minecraft (candidate)
[DEBUG] [OK] Path match: (match) log == log (candidate)
[DEBUG] [OK] Meta match: (match) 32767 == 2 (candidate)
[DEBUG] [OK] Item match found
[DEBUG] [--] Harvester type: ANY
[DEBUG] [--] Harvester detected, checking harvester: EntityPlayerMP['Player320'/1128, l='New World', x=157.36, y=76.00, z=252.54]
[DEBUG] [OK] No entries in heldItemMainHand to match
[DEBUG] [OK] No player names defined
[DEBUG] [OK] No game stages defined
[DEBUG] [OK] Harvester matching passed
[DEBUG] [OK] No biome matches defined
[DEBUG] [OK] No dimension matches defined
[DEBUG] [OK] Rule matched
[DEBUG] [DROP] Replace strategy: REPLACE_ITEMS_IF_SELECTED
[DEBUG] [DROP] Validating drop candidates...
[DEBUG] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop@5a0a271c
[DEBUG] [DROP] Added drop using weight value: 25
[DEBUG] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop@6fba7084
[DEBUG] [DROP] Added drop using weight value: 75
[DEBUG] [DROP] Valid drop candidates found: 100
[DEBUG] [DROP] Drop count: 1
[DEBUG] [DROP] Selected drop: RuleDropItem{_items=[], quantity=com.codetaylor.mc.dropt.modules.dropt.rule.data.RandomFortuneInt@2ee6cb15}
[DEBUG] [DROP] Selected drop quantity: 1
[DEBUG] [DROP] Skipping selected drop due to empty drop item list
[DEBUG] [DROP] Returning drop list: [1xtile.log@2]
```