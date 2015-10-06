package com.cobble.hyperscape.gui

import org.lwjgl.opengl.Display
import org.lwjgl.util.vector.Vector4f

class GuiMainMenu extends GuiScreen {

    override def initGui(): Unit = {
        val size: Float = Display.getHeight.asInstanceOf[Float] / 8f
        val halfSize: Float = size / 2f
        val doubleSize: Float = size * 2f
        buttonList = buttonList :+ new GuiButton("Thing", -halfSize, -halfSize, size, size)
        buttonList = buttonList :+ new GuiButton("Thing",  -size,  -size,   doubleSize,  doubleSize, new Vector4f(0.4f, 0.0f, 0.3f, 1.0f))
//        buttonList = buttonList :+ new GuiButton("Thing",  -.5f,   -.5f,    1.0f,  1.0f, new Vector4f(0.47f, 0.11f, 0.11f, 1.0f))
    }

//    override def render(): Unit = {
//        guiButton.render()
//    }
}
