package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

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
        GLUtil.uploadModelMatrix(modelMatrix)
        buttonList.foreach(button => {
            button.render()
        })

    }


    /**
     * Destroies the GUI and removes all the models from the Graphics card.
     */
    def destroy(): Unit = {
        buttonList.foreach(button => {
            button.destroy()
        })
    }
}
