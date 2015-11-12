package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Blocks
import com.cobble.hyperscape.util.MathUtil

import scala.util.Random

class WorldMainMenu extends World {

	val grav: Float = 0.3f

	val rand: Random = new Random

	for (z <- 0 until 255)
		for (x <- 0 until 255) {
			val period = MathUtil.TAU / 32
			val amp = 3
			val y = ((amp * Math.sin(x * period) + amp) + (amp * Math.cos(z * period) + amp)).toInt
			setBlock(x, y, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)
		}
}
