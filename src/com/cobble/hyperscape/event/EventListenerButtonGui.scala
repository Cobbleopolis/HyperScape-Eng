package com.cobble.hyperscape.event

class EventListenerButtonGui extends EventListenerButton {

	override def onButtonHold(button: Char, charVal: Int): Unit = {
		println(button + " | " + charVal)
	}

}
