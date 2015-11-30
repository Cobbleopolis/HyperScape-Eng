package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Blocks
import com.cobble.hyperscape.util.MathUtil
import jdk.nashorn.internal.ir.Block

import scala.util.Random

class WorldMainMenu extends World {

    val grav: Float = 0.3f

    val rand: Random = new Random

    val period = MathUtil.TAU / 32
    val amp = 3

    var i = 0


    for (x <- 0 until 256)
        for (z <- 0 until 256) {
            val y = (x / 16) + (z / 16)
                setBlock(x, y, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)
        }


//    override def tick(): Unit = {
//        super.tick()
//        if (i == 600)
//            setBlock(0, 10, 0, Blocks.blank)
//        i += 1
//    }
}
