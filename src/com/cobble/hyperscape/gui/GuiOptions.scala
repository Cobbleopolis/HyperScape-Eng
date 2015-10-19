package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import org.lwjgl.util.vector.Vector3f

class GuiOptions extends GuiScreen {

    elementList = elementList :+ new GuiButton("Go Back!", -400, -300, 800, 100, new Vector3f(1f, 0.5f, 0f))

    override def onClick(elementIndex: Int): Unit ={
        if (elementIndex == 0)
            HyperScape.currentGameState.changeGui(Guis.guiMainMenu)
    }

}
