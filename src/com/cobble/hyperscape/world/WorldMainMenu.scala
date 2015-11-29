package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Blocks
import com.cobble.hyperscape.util.MathUtil

import scala.util.Random

class WorldMainMenu extends World {

    val grav: Float = 0.3f

    val rand: Random = new Random

    val period = MathUtil.TAU / 32
    val amp = 3

    var i = 0


    for (x <- 0 until 16)
        for (z <- 0 until 256) {
            val offset = z / 16
            for (y <- offset until (if (x % 16 > 8 && z % 16 > 8) offset + 5 else offset + 1))
                setBlock(x, y, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)
        }

    val block = chunks(0).getBlock(0, 1, 0)
    println(if (block != null) block.blockID else 0)

    override def tick(): Unit = {
        super.tick()
        i += 1
        if (i == 600)
            setBlock(0, 10, 0, Blocks.blank)
    }
}
