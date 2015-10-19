package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.Vector3f

class GuiMainMenu extends GuiScreen {

    elementList = elementList :+ new GuiButton("Change that GUI!", -400, -300, 800, 100, new Vector3f(1f, 0f, 0f))
    //        buttonList = buttonList :+ new GuiButton("Hello World!", 0, 0, 340, 340, new Vector3f(0.4f, 0.0f, 0.3f), 7)
    elementList = elementList :+ new GuiButton("Hello World!", 0, 0, 340, 340, new Vector3f(0.4f, 0.0f, 0.3f))

    override def onClick(elementIndex: Int): Unit ={
        if (elementIndex == 0)
            HyperScape.currentGameState.changeGui(Guis.guiOptions)
        GLUtil.checkGLError()
    }

    //    override def render(): Unit = {
    //        guiButton.render()
    //    }
}
