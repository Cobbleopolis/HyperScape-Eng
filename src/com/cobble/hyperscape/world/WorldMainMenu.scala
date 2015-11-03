package com.cobble.hyperscape.world

class WorldMainMenu extends World {

    val grav: Float = 0.3f

    for (z <- 0 until 20)
        for (x <- 0 until 20)
            setBlock(x.asInstanceOf[Byte], if (x % 2 == 0 && z % 2 == 0) 1 else 0, z.asInstanceOf[Byte], 0)
}
