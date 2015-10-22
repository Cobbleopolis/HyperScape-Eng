package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.{FontModel, GuiModel}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.{Vector4f, Matrix4f, Vector3f}

/**
 * All elements should be added in the constructor.
 */
trait GuiScreen {

    val color: Vector4f = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)

    val z: Float = -2f
    
    val size: Float = 460f

    val bevel: Float = 20

    val verts: Array[Float] = Array(
        -size, -size, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Left
         size,  size, z, color.getX, color.getY, color.getZ, color.getW, //Top Right
        -size,  size, z, color.getX, color.getY, color.getZ, color.getW, //Top Left

        -size, -size, z, color.getX, color.getY, color.getZ, color.getW, // Bottom Left
         size, -size, z, color.getX, color.getY, color.getZ, color.getW, //Bottom Left
         size,  size, z, color.getX, color.getY, color.getZ, color.getW //Top Right
    )

    val guiModel: GuiModel = new GuiModel(verts)

    /** A list of the element in the Gui **/
    var elementList: List[GuiButton] = List()

    /** The name of the GUI displayed at the top of the GUI **/
    var name: String

    val fontModel: FontModel = new FontModel("This is a Gui", -size - bevel, 20f)

    /**
     * Renders the screen
     */
    def render(): Unit = {
        ShaderRegistry.bindShader("gui")
        HyperScape.mainCamera.uploadView()
        val modelMatrix = new Matrix4f()
//        modelMatrix.translate(new Vector3f(0, 0, 0))
        GLUtil.uploadModelMatrix(modelMatrix)
        guiModel.render()
        fontModel.render()
        elementList.foreach(elem => {
            elem.render()
        })

    }

    /**
     * Called when one of the elements in the Gui Screen is clicked
     * @param elementIndex The index of the element that was clicked
     */
    def onClick(elementIndex: Int): Unit = {}


    /**
     * Destroies the GUI and removes all the models from the Graphics card.
     */
    def destroy(): Unit = {
        guiModel.destroy()
        elementList.foreach(button => {
            button.destroy()
        })
    }
}
