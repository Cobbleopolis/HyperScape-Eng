package com.cobble.hyperscape.world

class WorldMainMenu extends World {

	val grav: Float = 0.3f

	for (z <- 0 until 255)
		for (x <- 0 until 255)
			setBlock(x.asInstanceOf[Byte], 0, z.asInstanceOf[Byte], 0)
}
