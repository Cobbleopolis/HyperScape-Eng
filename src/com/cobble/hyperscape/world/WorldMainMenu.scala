package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Blocks
import com.cobble.hyperscape.util.MathUtil
import org.lwjgl.util.vector.Vector3f

import scala.util.Random

class WorldMainMenu extends World {

    val grav: Float = 0.3f

    val rand: Random = new Random

    val period = MathUtil.TAU / 256
    val amp = 32

    var i = 0

    for (x <- 0 until 256)
        for (z <- 0 until 256) {
            //            val y = ((amp * Math.sin(x * period) + amp) + (amp * Math.cos(z * period) + amp)).toInt
            val xy = (amp * -Math.cos(x * period) + amp).toInt
            val zy = (amp * -Math.cos(z * period) + amp).toInt
            val y: Int = xy + zy
            setBlock(x, y, z, if (rand.nextBoolean()) Blocks.blank else Blocks.thing)
        }

	getSurroundingBlocks(16, 2, 0).foreach(block =>
		println(if (block != null) block.blockID else 0)
	)


    //    override def tick(): Unit = {
    //        super.tick()
    //        if (i == 600)
    //            setBlock(0, 10, 0, Blocks.blank)
    //        i += 1
    //    }
}
