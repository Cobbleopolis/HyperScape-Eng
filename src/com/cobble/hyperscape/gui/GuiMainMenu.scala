package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.Vector3f

class GuiMainMenu extends GuiScreen {

    val woah = new GuiButton("WOAH A NEW BUTTON!", -500, 0, 340, 340, new Vector3f(0.141f, 0.317f, 0.709f))

    var name: String = "Main Menu"

    elementList = elementList :+ new GuiButton("Change that GUI!", -400, -300, 800, 100, new Vector3f(1f, 0f, 0f))
    //        buttonList = buttonList :+ new GuiButton("Hello World!", 0, 0, 340, 340, new Vector3f(0.4f, 0.0f, 0.3f), 7)
    elementList = elementList :+ new GuiButton("Hello World!", 0, 0, 340, 340, new Vector3f(0.4f, 0.0f, 0.3f))

    override def onClick(elementIndex: Int): Unit ={
        if (elementIndex == 0)
            HyperScape.currentGameState.changeGui(Guis.guiOptions)
        if (elementIndex == 1)
            if (elementList.size < 3)
                elementList = elementList :+ woah
            else
                elementList(2).changeText("You clicked a button")
        if (elementIndex == 2)
            elementList = elementList.dropRight(1)

        GLUtil.checkGLError()
    }

    //    override def render(): Unit = {
    //        guiButton.render()
    //    }
}
