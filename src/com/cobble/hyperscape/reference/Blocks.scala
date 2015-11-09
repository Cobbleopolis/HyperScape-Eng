package com.cobble.hyperscape.reference

import com.cobble.hyperscape.block._
import com.cobble.hyperscape.registry.BlockRegistry

object Blocks {
	val air = new BlockAir
	val blank = new BlockBlank
	val light = new BlockLight
	val model = new BlockModel
	val glass = new BlockGlass
	val pillar = new BlockPillar

	/**
	 * Registers the blocks with the game
	 */
	def registerBlocks(): Unit = {
		BlockRegistry.registerBlock(air)
		BlockRegistry.registerBlock(blank)
		BlockRegistry.registerBlock(light)
		BlockRegistry.registerBlock(model)
		BlockRegistry.registerBlock(glass)
		BlockRegistry.registerBlock(pillar)
	}
}
