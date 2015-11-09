package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Blocks

import scala.util.Random

class WorldMainMenu extends World {

	val grav: Float = 0.3f

	val rand: Random = new Random

	for (z <- 0 until 255)
		for (x <- 0 until 255)
			setBlock(x, rand.nextInt(255), z, Blocks.blank)
}
