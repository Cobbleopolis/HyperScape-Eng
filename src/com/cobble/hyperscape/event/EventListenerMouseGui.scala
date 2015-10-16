package com.cobble.hyperscape.event

import com.cobble.hyperscape.core.HyperScape

class EventListenerMouseGui extends EventListenerMouse {

    override def onMouseMove(x: Int, y: Int, deltaX: Int, deltaY: Int): Unit = {
//        println("(" + x + ", " + y + ") (" + deltaX + ", " + deltaY + ")")
//        println(HyperScape.currentGameState == null)
//        println(HyperScape.currentGameState.currentGui == null)
//        println(HyperScape.currentGameState.currenGui.buttonList == null)
        HyperScape.currentGameState.currentGui.buttonList.foreach(element => {
            element.isHilighted = element.containsPoint(x, y)
        })
    }

    override def mouseDown(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {
        println("(" + x + ", " + y + ") (" + deltaX + ", " + deltaY + ")")
    }

    override def onMouseDown(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {
        println("Mouse Down")
    }

    override def onMouseUp(x: Int, y: Int, deltaX: Int, deltaY: Int, mouseButton: Int): Unit = {
        println("Mouse Up")
    }

}
