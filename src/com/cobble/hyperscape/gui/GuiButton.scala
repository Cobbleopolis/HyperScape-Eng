package com.cobble.hyperscape.gui

import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.{RenderModel, GuiModel}
import org.lwjgl.opengl.{GL30, GL15, GL20}

/**
 * Used by GUI's to render buttons
 * @param text The text displayed on the button
 * @param x The x location of the button (default is 0)
 * @param y The y location of the button (default is 0)
 * @param height The height of the button (default is 0.2)
 * @param width The width of the button (default is 0.2)
 */
class GuiButton(text: String, x: Float = 0.0f, y: Float = 0, height: Float = 0.2f, width: Float = 0.2f) extends GuiElement {
    val verts: Array[Float] = Array(
        x,          y,         0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x,          y + width, 0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x - height, y,         0.0f,      0.4f, 0.0f, 0.3f, 1.0f,

        x,          y + width, 0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x - height, y + width, 0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x - height, y,         0.0f,      0.4f, 0.0f, 0.3f, 1.0f
    ) //new Array[Float](72)

    val guiModel = new GuiModel(verts)

    /**
     * Renders the button
     */
    def render(): Unit = {
        ShaderRegistry.bindShader("gui")
        guiModel.render()
    }

    /**
     * Destroys the button
     */
    def destroy(): Unit = {
        guiModel.destroy()
    }

}
