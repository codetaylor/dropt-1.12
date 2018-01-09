* [CHANGELOG.md](https://github.com/codetaylor/dropt/blob/master/CHANGELOG.md)
* [SYNTAX.md](https://github.com/codetaylor/dropt/blob/master/SYNTAX.md)
* [EXAMPLE.md](https://github.com/codetaylor/dropt/blob/master/EXAMPLE.md)
* [DEBUG.md](https://github.com/codetaylor/dropt/blob/master/DEBUG.md)
* [LICENSE](https://github.com/codetaylor/dropt/blob/master/LICENSE)

## Dropt

Dropt is a block drop replacement mod that allows defining complex drop conditions.

### Features

Matches based on:
* block
* items dropped
* harvester type
* harvester held item
* harvester game stages (requires gamestages mod)
* harvester player name
* biome (whitelist/blacklist)
* dimension (whitelist/blacklist)
* vertical range

Replacement strategies:
* add to existing drops
* replace all drops
* replace all drops if drop selected
* replace all matched dropped items
* replace all matched dropped items if drop selected

Drop selection count based on:
* fixed value
* random value in defined range
* fortune modified

Drop selection based on:
* fortune modified weight value
* minimum fortune level
* silk touch requirement (required, excluded, any)

Drop quantity based on:
* fixed value
* random value in defined range
* fortune modified

### Reloading

Dropt supports reloading its configuration during runtime.

```
/dropt reload
```

### Configuration

Dropt uses `.json` configuration files placed in the `[instance]/config/dropt` folder. Filenames are not important so long as they have the `.json` extension and you can have as many files as you like.

### Logging

Any errors in the `.json` syntax are reported via the Forge log. When reloading in-game, errors will be reported to the chat console.

All errors are also logged to the log file, `[instance]/dropt.log`, which is reset each time the game is loaded.

Additional debug output can be enabled per-rule and will be dumped to the log file. See [DEBUG.md](https://github.com/codetaylor/dropt/blob/master/DEBUG.md) and [SYNTAX.md](https://github.com/codetaylor/dropt/blob/master/SYNTAX.md) for more information.