* [SYNTAX.md](https://github.com/codetaylor/dropt/blob/master/SYNTAX.md)
* [EXAMPLE.md](https://github.com/codetaylor/dropt/blob/master/EXAMPLE.md)

## Dropt

Dropt is a block drop replacement mod that allows defining complex drop conditions.

### Reloading

Dropt supports reloading its configuration during runtime.

```
/dropt reload
```

### Configuration

Dropt uses `.json` configuration files placed in the `[instance]/config/dropt` folder. Filenames are not important so long as they have the `.json` extension and you can have as many files as you like.

### Errors

Any errors in the `.json` syntax are reported via the Forge log. When reloading in-game, errors will be reported to the chat console.

All errors are also logged to the log file: `[instance]/config/dropt/dropt.log`.