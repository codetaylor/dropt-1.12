### Debugging

To debug a specific rule, set the property `debug`, in the root of the rule, to `true`. 
This is useful to see if a rule is matching, why it is or isn't matching, or if it is even 
being tested. Additionally, you'll be able to see how the rule is affecting the drop list.

Debug output will be saved to the log file in the instance's root folder: `[instance]/dropt.log`.

Note: Be careful how many rules you enable debug for; enable too many and your log file will explode.

Enabling the debug output for the rule from Example #1, the debug output looks like this:

```text
[INFO]  Located rule file: test.json
[INFO]  Rule file loaded: test.json
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] Parsing rule 0 in file test.json
[DEBUG] [PARSE] No block matches defined, skipped parsing block match
[DEBUG] [PARSE] Parsed item match: ore:logWood
[DEBUG] [PARSE] Expanding oreDict entry: ore:logWood
[DEBUG] [PARSE] Added item matcher: ItemMatcher{domain='minecraft', path='log', meta=32767, metas=[]}
[DEBUG] [PARSE] Added item matcher: ItemMatcher{domain='minecraft', path='log2', meta=32767, metas=[]}
[DEBUG] [PARSE] Parsing drop items for IRuleDrop at index 0
[DEBUG] [PARSE] Parsed item drop: minecraft:dye:32767
[DEBUG] [PARSE] Found registered item: net.minecraft.item.ItemDye@3ef396c9
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@0
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@1
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@2
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@3
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@4
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@5
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@6
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@7
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@8
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@9
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@10
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@11
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@12
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@13
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@14
[DEBUG] [PARSE] Added itemStack to drop: 1xitem.dyePowder@15
[DEBUG] [PARSE] Drop item.items object not defined or empty in IRuleDrop at index 1, skipped parsing drop item
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@41b5b94d
[DEBUG] [EVENT] BlockState: minecraft:leaves[check_decay=true,decayable=true,variant=oak]
[DEBUG] [EVENT] Harvester: null
[DEBUG] [EVENT] Drops: []
[DEBUG] [EVENT] Position: BlockPos{x=152, y=79, z=253}
[DEBUG] [EVENT] Fortune Level: 0
[DEBUG] [EVENT] Silktouch: false
[DEBUG] [EVENT] Dimension: 0
[DEBUG] [EVENT] Biome: minecraft:forest
[DEBUG] 
[DEBUG] [MATCH] [OK] Vertical position within bounds: 0 <= 79 <= 255
[DEBUG] [MATCH] [OK] No block matches defined in rule
[DEBUG] [MATCH] [!!] No item match found
[DEBUG] [!!] Rule not matched
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@5fc395ec
[DEBUG] [EVENT] BlockState: minecraft:leaves[check_decay=true,decayable=true,variant=oak]
[DEBUG] [EVENT] Harvester: null
[DEBUG] [EVENT] Drops: []
[DEBUG] [EVENT] Position: BlockPos{x=153, y=82, z=253}
[DEBUG] [EVENT] Fortune Level: 0
[DEBUG] [EVENT] Silktouch: false
[DEBUG] [EVENT] Dimension: 0
[DEBUG] [EVENT] Biome: minecraft:forest
[DEBUG] 
[DEBUG] [MATCH] [OK] Vertical position within bounds: 0 <= 82 <= 255
[DEBUG] [MATCH] [OK] No block matches defined in rule
[DEBUG] [MATCH] [!!] No item match found
[DEBUG] [!!] Rule not matched
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@10aa8aff
[DEBUG] [EVENT] BlockState: minecraft:log[axis=y,variant=birch]
[DEBUG] [EVENT] Harvester: EntityPlayerMP['Player368'/361, l='New World', x=151.99, y=76.00, z=255.40]
[DEBUG] [EVENT] Drops: [1xtile.log@2]
[DEBUG] [EVENT] Position: BlockPos{x=148, y=77, z=255}
[DEBUG] [EVENT] Fortune Level: 0
[DEBUG] [EVENT] Silktouch: false
[DEBUG] [EVENT] Dimension: 0
[DEBUG] [EVENT] Biome: minecraft:forest
[DEBUG] 
[DEBUG] [MATCH] [OK] Vertical position within bounds: 0 <= 77 <= 255
[DEBUG] [MATCH] [OK] No block matches defined in rule
[DEBUG] [MATCH] [--] Attempting to match candidate [1xtile.log@2] with: [ItemMatcher{domain='minecraft', path='log', meta=32767, metas=[]}]
[DEBUG] [MATCH] [OK] Domain match: (match) minecraft == minecraft (candidate)
[DEBUG] [MATCH] [OK] Path match: (match) log == log (candidate)
[DEBUG] [MATCH] [OK] Meta match: (match) 32767 == 2 (candidate)
[DEBUG] [MATCH] [OK] Item match found
[DEBUG] [MATCH] [--] Harvester type: ANY
[DEBUG] [MATCH] [--] Harvester detected, checking harvester: EntityPlayerMP['Player368'/361, l='New World', x=151.99, y=76.00, z=255.40]
[DEBUG] [MATCH] [OK] No entries in heldItemMainHand to match
[DEBUG] [MATCH] [OK] No player names defined
[DEBUG] [MATCH] [OK] No game stages defined
[DEBUG] [MATCH] [OK] Harvester matching passed
[DEBUG] [MATCH] [OK] No biome matches defined
[DEBUG] [MATCH] [OK] No dimension matches defined
[DEBUG] [OK] Rule matched
[DEBUG] 
[DEBUG] [DROP] Selecting drop candidates...
[DEBUG] [DROP] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: RuleDropItem{_items=[1xitem.dyePowder@0, 1xitem.dyePowder@1, 1xitem.dyePowder@2, 1xitem.dyePowder@3, 1xitem.dyePowder@4, 1xitem.dyePowder@5, 1xitem.dyePowder@6, 1xitem.dyePowder@7, 1xitem.dyePowder@8, 1xitem.dyePowder@9, 1xitem.dyePowder@10, 1xitem.dyePowder@11, 1xitem.dyePowder@12, 1xitem.dyePowder@13, 1xitem.dyePowder@14, 1xitem.dyePowder@15], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Added drop using weight value: 25
[DEBUG] [DROP] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: RuleDropItem{_items=[], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Added drop using weight value: 75
[DEBUG] [DROP] Valid drop candidates found: 2
[DEBUG] [DROP] Total weight: 100
[DEBUG] [DROP] Drop count: 1
[DEBUG] [DROP] Selected drop: RuleDropItem{_items=[], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Selected drop quantity: 1
[DEBUG] [DROP] Skipping selected drop due to empty drop item list
[DEBUG] [DROP] Returning drop list: [1xtile.log@2]
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@1f9850b6
[DEBUG] [EVENT] BlockState: minecraft:leaves[check_decay=true,decayable=true,variant=oak]
[DEBUG] [EVENT] Harvester: null
[DEBUG] [EVENT] Drops: []
[DEBUG] [EVENT] Position: BlockPos{x=154, y=80, z=252}
[DEBUG] [EVENT] Fortune Level: 0
[DEBUG] [EVENT] Silktouch: false
[DEBUG] [EVENT] Dimension: 0
[DEBUG] [EVENT] Biome: minecraft:forest
[DEBUG] 
[DEBUG] [MATCH] [OK] Vertical position within bounds: 0 <= 80 <= 255
[DEBUG] [MATCH] [OK] No block matches defined in rule
[DEBUG] [MATCH] [!!] No item match found
[DEBUG] [!!] Rule not matched
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@744da53
[DEBUG] [EVENT] BlockState: minecraft:log[axis=y,variant=birch]
[DEBUG] [EVENT] Harvester: EntityPlayerMP['Player368'/361, l='New World', x=151.97, y=76.00, z=255.42]
[DEBUG] [EVENT] Drops: [1xtile.log@2]
[DEBUG] [EVENT] Position: BlockPos{x=148, y=76, z=255}
[DEBUG] [EVENT] Fortune Level: 0
[DEBUG] [EVENT] Silktouch: false
[DEBUG] [EVENT] Dimension: 0
[DEBUG] [EVENT] Biome: minecraft:forest
[DEBUG] 
[DEBUG] [MATCH] [OK] Vertical position within bounds: 0 <= 76 <= 255
[DEBUG] [MATCH] [OK] No block matches defined in rule
[DEBUG] [MATCH] [--] Attempting to match candidate [1xtile.log@2] with: [ItemMatcher{domain='minecraft', path='log', meta=32767, metas=[]}]
[DEBUG] [MATCH] [OK] Domain match: (match) minecraft == minecraft (candidate)
[DEBUG] [MATCH] [OK] Path match: (match) log == log (candidate)
[DEBUG] [MATCH] [OK] Meta match: (match) 32767 == 2 (candidate)
[DEBUG] [MATCH] [OK] Item match found
[DEBUG] [MATCH] [--] Harvester type: ANY
[DEBUG] [MATCH] [--] Harvester detected, checking harvester: EntityPlayerMP['Player368'/361, l='New World', x=151.97, y=76.00, z=255.42]
[DEBUG] [MATCH] [OK] No entries in heldItemMainHand to match
[DEBUG] [MATCH] [OK] No player names defined
[DEBUG] [MATCH] [OK] No game stages defined
[DEBUG] [MATCH] [OK] Harvester matching passed
[DEBUG] [MATCH] [OK] No biome matches defined
[DEBUG] [MATCH] [OK] No dimension matches defined
[DEBUG] [OK] Rule matched
[DEBUG] 
[DEBUG] [DROP] Selecting drop candidates...
[DEBUG] [DROP] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: RuleDropItem{_items=[1xitem.dyePowder@0, 1xitem.dyePowder@1, 1xitem.dyePowder@2, 1xitem.dyePowder@3, 1xitem.dyePowder@4, 1xitem.dyePowder@5, 1xitem.dyePowder@6, 1xitem.dyePowder@7, 1xitem.dyePowder@8, 1xitem.dyePowder@9, 1xitem.dyePowder@10, 1xitem.dyePowder@11, 1xitem.dyePowder@12, 1xitem.dyePowder@13, 1xitem.dyePowder@14, 1xitem.dyePowder@15], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Added drop using weight value: 25
[DEBUG] [DROP] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: RuleDropItem{_items=[], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Added drop using weight value: 75
[DEBUG] [DROP] Valid drop candidates found: 2
[DEBUG] [DROP] Total weight: 100
[DEBUG] [DROP] Drop count: 1
[DEBUG] [DROP] Selected drop: RuleDropItem{_items=[], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Selected drop quantity: 1
[DEBUG] [DROP] Skipping selected drop due to empty drop item list
[DEBUG] [DROP] Returning drop list: [1xtile.log@2]
[DEBUG] --------------------------------------------------------------------------------------
[DEBUG] [EVENT] net.minecraftforge.event.world.BlockEvent$HarvestDropsEvent@740f3797
[DEBUG] [EVENT] BlockState: minecraft:log[axis=y,variant=birch]
[DEBUG] [EVENT] Harvester: EntityPlayerMP['Player368'/361, l='New World', x=151.97, y=76.00, z=255.42]
[DEBUG] [EVENT] Drops: [1xtile.log@2]
[DEBUG] [EVENT] Position: BlockPos{x=148, y=78, z=255}
[DEBUG] [EVENT] Fortune Level: 0
[DEBUG] [EVENT] Silktouch: false
[DEBUG] [EVENT] Dimension: 0
[DEBUG] [EVENT] Biome: minecraft:forest
[DEBUG] 
[DEBUG] [MATCH] [OK] Vertical position within bounds: 0 <= 78 <= 255
[DEBUG] [MATCH] [OK] No block matches defined in rule
[DEBUG] [MATCH] [--] Attempting to match candidate [1xtile.log@2] with: [ItemMatcher{domain='minecraft', path='log', meta=32767, metas=[]}]
[DEBUG] [MATCH] [OK] Domain match: (match) minecraft == minecraft (candidate)
[DEBUG] [MATCH] [OK] Path match: (match) log == log (candidate)
[DEBUG] [MATCH] [OK] Meta match: (match) 32767 == 2 (candidate)
[DEBUG] [MATCH] [OK] Item match found
[DEBUG] [MATCH] [--] Harvester type: ANY
[DEBUG] [MATCH] [--] Harvester detected, checking harvester: EntityPlayerMP['Player368'/361, l='New World', x=151.97, y=76.00, z=255.42]
[DEBUG] [MATCH] [OK] No entries in heldItemMainHand to match
[DEBUG] [MATCH] [OK] No player names defined
[DEBUG] [MATCH] [OK] No game stages defined
[DEBUG] [MATCH] [OK] Harvester matching passed
[DEBUG] [MATCH] [OK] No biome matches defined
[DEBUG] [MATCH] [OK] No dimension matches defined
[DEBUG] [OK] Rule matched
[DEBUG] 
[DEBUG] [DROP] Selecting drop candidates...
[DEBUG] [DROP] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: RuleDropItem{_items=[1xitem.dyePowder@0, 1xitem.dyePowder@1, 1xitem.dyePowder@2, 1xitem.dyePowder@3, 1xitem.dyePowder@4, 1xitem.dyePowder@5, 1xitem.dyePowder@6, 1xitem.dyePowder@7, 1xitem.dyePowder@8, 1xitem.dyePowder@9, 1xitem.dyePowder@10, 1xitem.dyePowder@11, 1xitem.dyePowder@12, 1xitem.dyePowder@13, 1xitem.dyePowder@14, 1xitem.dyePowder@15], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Added drop using weight value: 25
[DEBUG] [DROP] [SELECTOR] [OK] Required fortune level met: (required) 0 <= 0 (candidate)
[DEBUG] [DROP] Added drop to weighted picker: RuleDropItem{_items=[], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Added drop using weight value: 75
[DEBUG] [DROP] Valid drop candidates found: 2
[DEBUG] [DROP] Total weight: 100
[DEBUG] [DROP] Drop count: 1
[DEBUG] [DROP] Selected drop: RuleDropItem{_items=[1xitem.dyePowder@0, 1xitem.dyePowder@1, 1xitem.dyePowder@2, 1xitem.dyePowder@3, 1xitem.dyePowder@4, 1xitem.dyePowder@5, 1xitem.dyePowder@6, 1xitem.dyePowder@7, 1xitem.dyePowder@8, 1xitem.dyePowder@9, 1xitem.dyePowder@10, 1xitem.dyePowder@11, 1xitem.dyePowder@12, 1xitem.dyePowder@13, 1xitem.dyePowder@14, 1xitem.dyePowder@15], quantity=RandomFortuneInt{fixed=0, min=1, max=1, fortuneModifier=0}}
[DEBUG] [DROP] Selected drop quantity: 1
[DEBUG] [DROP] Added ItemStack to drop list: 1xitem.dyePowder@7
[DEBUG] [DROP] Removing all items specified in the item matcher, replace strategy: REPLACE_ITEMS_IF_SELECTED
[DEBUG] [DROP] [--] Attempting to match candidate [1xtile.log@2] with: [ItemMatcher{domain='minecraft', path='log', meta=32767, metas=[]}]
[DEBUG] [DROP] [OK] Domain match: (match) minecraft == minecraft (candidate)
[DEBUG] [DROP] [OK] Path match: (match) log == log (candidate)
[DEBUG] [DROP] [OK] Meta match: (match) 32767 == 2 (candidate)
[DEBUG] [DROP] Removed: 1xtile.log@2
[DEBUG] Returning drop list: [1xitem.dyePowder@7]
```