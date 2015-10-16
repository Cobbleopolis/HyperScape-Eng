package com.cobble.hyperscape.event

import com.cobble.hyperscape.core.HyperScape

class EventListenerMainMouse extends EventListenerMouse {

    override def onMouseMove(x: Int, y: Int, deltaX: Int, deltaY: Int) = {
//        println("(" + x + ", " + y + ") (" + deltaX + ", " + deltaY + ")")
//        println(HyperScape.currentGameState == null)
//        println(HyperScape.currentGameState.currentGui == null)
//        println(HyperScape.currentGameState.currenGui.buttonList == null)
        HyperScape.currentGameState.currentGui.buttonList.foreach(element => {
            element.isHilighted = element.containsPoint(x, y)
        })
    }

}
