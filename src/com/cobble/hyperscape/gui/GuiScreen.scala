package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

trait GuiScreen {
    

    /** A list of the buttons in the Gui **/
    var buttonList: List[GuiButton] = List()

    def initGui(): Unit = {

    }

    /**
     * Renders the screen
     */
    def render(): Unit = {
        ShaderRegistry.bindShader("gui")
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

    }


    def destroy(): Unit = {
        buttonList.foreach(button => {
            button.destroy()
        })
    }
}
