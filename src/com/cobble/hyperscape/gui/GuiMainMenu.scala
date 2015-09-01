package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class GuiMainMenu extends GuiScreen {

    var guiButton: GuiButton = null

    override def initGui(): Unit = {
        guiButton = new GuiButton("Thing")
        buttonList = buttonList :+ guiButton
    }

//    override def render(): Unit = {
//        guiButton.render()
//    }
}
