package com.cobble.hyperscape.gui

import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.{RenderModel, GuiModel}
import org.lwjgl.opengl.{GL30, GL15, GL20}
import org.lwjgl.util.vector.{Vector4f, Vector3f}

/**
 * Used by GUI's to render buttons
 * @param text The text displayed on the button
 * @param x The x location of the button (default is 0)
 * @param y The y location of the button (default is 0)
 * @param height The height of the button (default is 0.2)
 * @param width The width of the button (default is 0.2)
 */
class GuiButton(text: String, x: Float = 0.0f, y: Float = 0, height: Float = 0.2f, width: Float = 0.2f, color: Vector4f = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)) extends GuiElement {
    val verts: Array[Float] = Array(
        x,         y,          0f,      color.getX, color.getY, color.getZ, color.getW,
        x + width, y + height, 0f,      color.getX, color.getY, color.getZ, color.getW,
        x,         y + height, 0f,      color.getX, color.getY, color.getZ, color.getW,

        x,         y,          0f,      color.getX, color.getY, color.getZ, color.getW,
        x + width, y,          0f,      color.getX, color.getY, color.getZ, color.getW,
        x + width, y + height, 0f,      color.getX, color.getY, color.getZ, color.getW
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
