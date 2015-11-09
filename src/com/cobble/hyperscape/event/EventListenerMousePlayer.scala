package com.cobble.hyperscape.event

import com.cobble.hyperscape.core.HyperScape

class EventListenerMousePlayer extends EventListenerMouse{

	override def onMouseMove(x: Int, y: Int, deltaX: Int, deltaY: Int): Unit = {
		HyperScape.currentGameState.world.player.rotateEntity(deltaY / 100, deltaX / 100, 0)
	}

}
