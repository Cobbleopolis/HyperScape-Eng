package com.cobble.hyperscape.render

import org.lwjgl.opengl.Display

/**
 *
 */
object VirtualResolution {

    var guiScale: Float = 1f

    def setScale(scale: Float): Unit = {
        guiScale = scale
    }

    def getScale: Float = {
        guiScale
    }
    
//    def calculateValues(): Unit = {
//        //TODO implement
//    }

//    def getVirtualCoord(x: Float, y: Float): (Float, Float) = {
//        //TODO implement
//    }

//    def getRealCoord(x: Float, y: Float): (Float, Float) = {
//        //TODO implement
//    }
}
