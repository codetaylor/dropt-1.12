## Block Strings

Block strings are the strings used to describe which in-world block states a rule matcher should look for.

```
// ZenScript
.matchBlocks(["minecraft:quartz_ore"])
```

```
// json
"match": {
  "blocks": {
    "blocks": [
      "minecraft:quartz_ore"
    ]
  }
}
```

```
// Java API
.matchBlocks(new String[]{"minecraft:quartz_ore"})
```

The strings provided in the match descriptors above are not item strings. Block strings describe an in-world block state and can deviate from what you would expect.

For example, the block strings for logs have a different meta value depending on which direction the log is facing, leaves have a different meta value depending on their decay state, furnaces have a different meta value depending on whether or not they're lit *and* which direction it's facing.

If you are trying to match a block with a complex set of in-world block states, there are some things you can do to expose the strings you need to use for the given block: `F3`, `/dropt verbose`, and `Rule Debugging`.

#### Using `F3`

Press `F3` to activate the debug menu and check on the right-hand side, near the bottom of the text. You should be able to see the in-world block state of the block you're looking at.

#### Using `/dropt verbose`

Alternatively, Dropt has a command, `/dropt verbose`, that will log the block string of any broken block to the chat.

!!! Warning
    `/dropt verbose` only works if the block is broken in `Survival` mode.
    
#### Using Rule Debugging

Debugging enables you to look at a rule and determine why it is or isn't matching.

See [Debugging](debugging.md) for details.