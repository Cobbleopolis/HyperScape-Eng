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


//    for (x <- 0 until 16)
//        for (z <- 0 until 256) {
//            val offset = z / 16
//            for (y <- offset until (if (x % 16 > 8 && z % 16 > 8) offset + 5 else offset + 1))
//                setBlock(x, y, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)
//        }
    for (x <- 0 until 16)
        for (z <- 0 until 16)
            setBlock(x, 0, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)

//    val block = chunks(0).blocks(0)
//    val block2 = chunks(0).blocks(1)
//    println(block)
//    println(block2)
    println(chunks(0).getBlock(5, 0, 0).blockID)
    println(getBlock(5, 0, 0).blockID)
    println(chunks(0).getBlock(0, 0, 0).blockID)
    println(getBlock(0, 0, 0).blockID)
    println(chunks.size)

//    override def tick(): Unit = {
//        super.tick()
//        if (i == 600)
//            setBlock(0, 10, 0, Blocks.blank)
//        i += 1
//    }
}
