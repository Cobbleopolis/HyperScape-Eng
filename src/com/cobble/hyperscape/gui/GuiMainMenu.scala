package com.cobble.hyperscape.gui

import org.lwjgl.util.vector.Vector3f

class GuiMainMenu extends GuiScreen {

    override def initGui(): Unit = {
        elementList = elementList :+ new GuiButton("Thing", 400, 35, 100, 100, new Vector3f(1f, 0f, 0f))
        //        buttonList = buttonList :+ new GuiButton("Hello World!", 0, 0, 340, 340, new Vector3f(0.4f, 0.0f, 0.3f), 7)
        elementList = elementList :+ new GuiButton("Hello World!", 0, 0, 340, 340, new Vector3f(0.4f, 0.0f, 0.3f))
    }

    //    override def render(): Unit = {
    //        guiButton.render()
    //    }
}
