package com.cobble.hyperscape.block

import com.cobble.hyperscape.registry.BlockRegistry

object Blocks {

    val blank: BlockBlank = new BlockBlank
    val thing: BlockThing = new BlockThing

    def registerBlocks(): Unit = {
        println("Registering Blocks...")
        BlockRegistry.registerBlock(blank)
        BlockRegistry.registerBlock(thing)
        println("Registered Blocks")
    }

}
