package com.cobble.hyperscape.event

class EventListenerGuiButton extends EventListenerButton {

    override def onButtonHold(button: Char, charVal: Int): Unit = {
        println(button + " | " + charVal)
    }

}
