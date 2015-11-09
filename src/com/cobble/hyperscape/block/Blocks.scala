package com.cobble.hyperscape.block

import com.cobble.hyperscape.registry.BlockRegistry

object Blocks {

	val blank: BlockBlank = new BlockBlank

	def registerBlocks(): Unit = {
		println("Registering Blocks...")
		BlockRegistry.registerBlock(blank)
		println("Registered Blocks")
	}

}
