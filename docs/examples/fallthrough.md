
!!! note
    To report an issue, or to request a new example, please use the [issue tracker](https://github.com/codetaylor/dropt/issues).

---

Goal: Create two rules for the dirt block: one to replace the drops with a diamond, and a second to add an emerald.

---

When a rule is marked with the `fallthrough` directive, Dropt will attempt to match another rule for the block. It will continue to match rules for the block until it matches a rule without the `fallthrough` directive, or it runs out of rules for the given block.

---

### ZenScript

```js
import mods.dropt.Dropt;

Dropt.list("fallthrough_test")

  .add(Dropt.rule()
      .fallthrough()
      .matchBlocks(["minecraft:dirt"])
      .addDrop(Dropt.drop()
          .items([<minecraft:diamond>])
      )
  )
  .add(Dropt.rule()
      .matchBlocks(["minecraft:dirt"])
      .replaceStrategy("ADD")
      .addDrop(Dropt.drop()
          .items([<minecraft:emerald>])
      )
  );
```

### JSON

```json
{
  "rules": [
    {
      "fallthrough": true,
      "match": {
        "blocks": {"blocks": ["minecraft:dirt"]}
      },
      "drops": [
        {"item": {"items": ["minecraft:diamond"]}}
      ]
    },
    {
      "match": {
        "blocks": {"blocks": ["minecraft:dirt"]}
      },
      "replaceStrategy": "ADD",
      "drops": [
        {"item": {"items": ["minecraft:emerald"]}}
      ]
    }
  ]
}
```