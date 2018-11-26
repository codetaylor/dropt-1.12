```java
import mods.dropt.Dropt;

Dropt.list("test_list")

    // Set the list priority
    .priority(0)

    // When stone is broken, should replace with string with custom name.
    .add(Dropt.rule()
        .matchBlocks(["minecraft:stone"])
        .addDrop(Dropt.drop()
            .items([<minecraft:string>.withTag({display: {Name: "Theory"}})])
        )
    )

    // When cobblestone or <ore:sand> is dropped, should replace with leather.
    .add(Dropt.rule()
        .matchDrops([<minecraft:cobblestone>.or(<ore:sand>)])
        .addDrop(Dropt.drop()
            .items([<minecraft:leather>])
        )
    )

    // When dirt is dropped, should replace with leather 25% of the time and
    // string 75% of the time.
    .add(Dropt.rule()
        .matchDrops([<minecraft:dirt>])
        .addDrop(Dropt.drop()
            .selector(Dropt.weight(25))
            .items([<minecraft:leather>])
        )
        .addDrop(Dropt.drop()
            .selector(Dropt.weight(75))
            .items([<minecraft:string>])
        )
    );
```