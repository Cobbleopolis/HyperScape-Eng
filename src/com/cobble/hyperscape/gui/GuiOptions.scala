package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import org.lwjgl.util.vector.Vector3f

class GuiOptions extends GuiScreen ("Options") {

    elementList = elementList :+ new GuiButton("Go Back!", -400, -300, 800, 100, new Vector3f(1f, 0.5f, 0f))
    elementList = elementList :+ new GuiButton("This is a different GUI", -400, 0, 800, 300, new Vector3f(0.749f, 0.211f, 0.047f))

    override def onClick(elementIndex: Int): Unit ={
        if (elementIndex == 0)
            HyperScape.currentGameState.changeGui(Guis.guiMainMenu)
    }

}
