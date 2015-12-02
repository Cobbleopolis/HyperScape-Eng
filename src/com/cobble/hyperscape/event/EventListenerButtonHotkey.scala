package com.cobble.hyperscape.event

import com.cobble.hyperscape.Game
import com.cobble.hyperscape.core.HyperScape
import org.lwjgl.input.{Keyboard, Mouse}

class EventListenerButtonHotkey extends EventListenerButton {

	var (fullscreenDelay: Int, fogDelay: Int) = (0, 0)

    override def onButtonHold(button: Char, charVal: Int): Unit = {
        //		println(button + " | " + charVal)
    }

    override def onButtonDown(button: Char, charVal: Int): Unit = {
//	    if (charVal == Keyboard.KEY_F11 && fullscreenDelay == 0) {
//		    Game.toggleFullScreen()
//		    Game.requestResize()
//		    fullscreenDelay = 120
//	    }

	    if (charVal == Keyboard.KEY_F5 && fogDelay == 0) {
		    HyperScape.drawFog = !HyperScape.drawFog
		    fogDelay = 120
	    }

    }

	override def onTick(): Unit = {
		if (fullscreenDelay > 0)
			fullscreenDelay -= 1

		if (fogDelay > 0)
			fogDelay -= 1
	}

}
