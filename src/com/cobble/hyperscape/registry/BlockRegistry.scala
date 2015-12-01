package com.cobble.hyperscape.registry

import com.cobble.hyperscape.block.Block

object BlockRegistry {
    private var blocks: Map[Int, Block] = Map()

    /**
     * Registers a block with the game
     * @param block block to be registered
     */
    def registerBlock(block: Block): Unit = {
        println("Registering Block ID: " + block.blockID)
        blocks += (block.blockID -> block)
    }

    /**
     * Retrieves a block from the block registry
     * @param blockID The ID of the block to return
     * @return Returns a block with the corresponding block ID
     */
    def getBlock(blockID: Int): Block = {
        blocks.getOrElse(blockID, null)
    }
}
