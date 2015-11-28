package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Blocks
import com.cobble.hyperscape.util.MathUtil

import scala.util.Random

class WorldMainMenu extends World {

    val grav: Float = 0.3f

    val rand: Random = new Random

    val period = MathUtil.TAU / 32
    val amp = 3

    for (z <- 0 until 16)
        for (x <- 0 until 255) {
            val offset = z / 16
            for (y <- offset until (if (x % 16 > 8 && z % 16 > 8) offset + 5 else offset))
                setBlock(x, y, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)
        }
}
