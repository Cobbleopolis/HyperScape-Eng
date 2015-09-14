package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector4f, Vector3f, Matrix4f}

class GuiMainMenu extends GuiScreen {

    override def initGui(): Unit = {
        buttonList = buttonList :+ new GuiButton("Thing", -0.125f, -0.125f, 0.25f, 0.25f)
        buttonList = buttonList :+ new GuiButton("Thing",  -.25f,  -.25f,   0.5f,  0.5f, new Vector4f(0.4f, 0.0f, 0.3f, 1.0f))
        buttonList = buttonList :+ new GuiButton("Thing",  -.5f,   -.5f,    1.0f,  1.0f, new Vector4f(0.47f, 0.11f, 0.11f, 1.0f))
    }

//    override def render(): Unit = {
//        guiButton.render()
//    }
}
