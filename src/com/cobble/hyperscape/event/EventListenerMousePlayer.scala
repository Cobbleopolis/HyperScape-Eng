package com.cobble.hyperscape.event

import com.cobble.hyperscape.core.HyperScape

class EventListenerMousePlayer extends EventListenerMouse {

    val mouseSensitivity: Float = 2f

    override def onMouseMove(x: Int, y: Int, deltaX: Int, deltaY: Int): Unit = {
        HyperScape.currentGameState.world.player.rotateEntity(Math.toRadians(-deltaY / mouseSensitivity).toFloat,
            Math.toRadians(deltaX / mouseSensitivity).toFloat, 0)
    }

}
