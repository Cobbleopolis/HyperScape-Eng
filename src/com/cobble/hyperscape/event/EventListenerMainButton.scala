package com.cobble.hyperscape.event

class EventListenerMainButton extends EventListenerButton {

    override def onButtonHold(button: Char, charVal: Int): Unit = {
        println(button + " | " + charVal)
    }

}
