package com.cobble.hyperscape.event

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.reference.Reference

class EventListenerMouseGui extends EventListenerMouse {

    override def onMouseMove(x: Int, y: Int, deltaX: Int, deltaY: Int): Unit = {
        //        println("(" + x + ", " + y + ") (" + deltaX + ", " + deltaY + ")")
        //        println(HyperScape.currentGameState == null)
        //        println(HyperScape.currentGameState.currentGui == null)
        //        println(HyperScape.currentGameState.currenGui.buttonList == null)
        HyperScape.currentGameState.currentGui.elementList.foreach(element => {
            element.isHilighted = element.containsPoint(x, y)
            //            element.isDown = element.containsPoint(x, y)
        })
    }

    override def mouseDown(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {
        //        println("(" + x + ", " + y + ") (" + deltaX + ", " + deltaY + ") | " + mouseButton)
    }

    override def onMouseDown(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {
        if (HyperScape.currentGameState != null)
            if (HyperScape.currentGameState.currentGui != null)
                HyperScape.currentGameState.currentGui.elementList.foreach(elem =>
                    if (elem.containsPoint(x, y) && mouseButton == Reference.Mouse.MOUSE_BUTTON_LEFT)
                        elem.isDown = true
                )
    }

    override def onMouseUp(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {
        //        println("Mouse Up")
        if (HyperScape.currentGameState != null)
            if (HyperScape.currentGameState.currentGui != null) {
                var i = 0
                HyperScape.currentGameState.currentGui.elementList.foreach(elem => {
                    if (elem.containsPoint(x, y) && mouseButton == Reference.Mouse.MOUSE_BUTTON_LEFT)
                        HyperScape.currentGameState.currentGui.onClick(i)
                    elem.isDown = false
                    i += 1
                })
            }
    }

}
