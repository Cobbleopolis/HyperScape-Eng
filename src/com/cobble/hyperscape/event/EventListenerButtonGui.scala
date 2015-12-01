package com.cobble.hyperscape.event

import org.lwjgl.input.Mouse

class EventListenerButtonGui extends EventListenerButton {

    override def onButtonHold(button: Char, charVal: Int): Unit = {
        //		println(button + " | " + charVal)
    }

    override def onButtonDown(button: Char, charVal: Int): Unit = {
        //		if (charVal == Keyboard.KEY_ESCAPE)
        println("Hello")
        Mouse.setGrabbed(false)
        Mouse.setClipMouseCoordinatesToWindow(false)
    }

}
