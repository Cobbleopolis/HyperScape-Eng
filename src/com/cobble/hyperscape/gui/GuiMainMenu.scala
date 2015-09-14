package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector4f, Vector3f, Matrix4f}

class GuiMainMenu extends GuiScreen {

    var guiButton: GuiButton = null

    var offset: Float = 0.0f

    var vel: Float = 0.01f

    override def initGui(): Unit = {
        guiButton = new GuiButton("Thing", -0.125f, -0.125f, 0.25f, 0.25f)
//        guiButton = new GuiButton("Thing", -.5f, -.5f, 1f, 1f)
        buttonList = buttonList :+ guiButton
        buttonList = buttonList :+ new GuiButton("Thing",  -.25f, -.25f, 0.5f, 0.5f, new Vector4f(0.4f, 0.0f, 0.3f, 1.0f))
    }

    override def render(): Unit = {
        ShaderRegistry.bindShader("gui")
//        HyperScape.mainCamera.view.translate(new Vector3f(offset, 0, 0))
        HyperScape.mainCamera.uploadView()
        val modelMatrix = new Matrix4f()
        modelMatrix.translate(new Vector3f(0, 0, 0))
        HyperScape.uploadBuffer.clear()
        modelMatrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
        buttonList.foreach(button => {
            button.render()
        })
        if (offset > 1.5 || offset < -1) vel = -vel
        offset -= vel
//        HyperScape.mainCamera.view.translate(new Vector3f(-offset, 0, 0))
    }
}
