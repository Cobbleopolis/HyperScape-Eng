package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import org.lwjgl.util.vector.Vector4f

class GuiOptions extends GuiScreen("Options") {

    elementList = elementList :+ new GuiButton("Back", -330f, -300f, 660f, 75f, new Vector4f(1f, 0.5f, 0f, 0.2f))
    elementList = elementList :+ new GuiButton("This is a different GUI", -330f, 0f, 660f, 300f, new Vector4f(0.749f, 0.211f, 0.047f, 0.2f))

    override def onClick(elementIndex: Int): Unit = {
        if (elementIndex == 0)
            HyperScape.currentGameState.changeGui(Guis.guiMainMenu)
    }

}
