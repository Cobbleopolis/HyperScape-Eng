package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.world.{World, WorldMainMenu}
import org.lwjgl.util.vector.Vector4f

class GuiMainMenu extends GuiScreen("Main Menu") {

    //    val woah = new GuiButton("WOAH A NEW BUTTON!", -330f, 0f, 300f, 300f, new Vector3f(0.141f, 0.317f, 0.709f))

    val left: Float = HyperScape.mainCamera.getLeftEdge
    val defaultY: Float = HyperScape.mainCamera.getTopEdge * 0.9f

    elementList = elementList :+ new GuiButton("New Game", left, defaultY, 660f, 75f, new Vector4f(0f, 0f, 0f, 0.2f))
    elementList = elementList :+ new GuiButton("Options", left, defaultY - 105f, 660f, 75f, new Vector4f(0f, 0f, 0f, 0.2f))
    elementList = elementList :+ new GuiButton("Exit Game", left, defaultY - 210f, 660f, 75f, new Vector4f(0f, 0f, 0f, 0.2f))

    //    elementList = elementList :+ new GuiButton("Hello World!", 0f, 0f, 300f, 300f, new Vector4f(0.4f, 0.0f, 0.3f, 0.2f))

    override def onClick(elementIndex: Int): Unit = {
        if (elementIndex == 1)
            HyperScape.currentGameState.changeGui(Guis.guiOptions)
        if (elementIndex == 2)
            HyperScape.requestClose()
        //            if (elementList.size < 3)
        //                elementList = elementList :+ woah
        //            else
        //                elementList = elementList.dropRight(1)
    }

    //    override def render(): Unit = {
    //        guiButton.render()
    //    }
}
