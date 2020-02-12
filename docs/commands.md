## Reload

```sh
/dropt reload
```

Reload configuration during runtime.

!!! warning
    This command only works with JSON and the DroptAPI.
    Reload will not work for rule lists loaded with ZenScript and will only work for rule lists loaded with the DroptAPI if you hotswap your changes before reloading.

## Hand

```sh
/dropt hand
```

Copy the held item's string for quick pasting into the .json file; also assists with lengthy NBT tags.

## Verbose

```sh
/dropt verbose
```

Log the resource location and meta value for all broken blocks to the console.

The block strings that Dropt uses are not the same as item stack strings. For example, leaves have different meta depending on their decay state. This command will help you sort out the block string you need.

## Export

```sh
/dropt export
```

Export all rules currently loaded in memory to the `config/dropt/export` folder as json files.