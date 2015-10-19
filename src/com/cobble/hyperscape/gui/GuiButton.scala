package com.cobble.hyperscape.gui

import com.cobble.hyperscape.render.{DropShadowModel, FontModel, GuiModel}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.Vector3f

/**
 * Used by GUI's to render buttons
 * @param text The text displayed on the button
 * @param x The x location of the button (default is 0)
 * @param y The y location of the button (default is 0)
 * @param height The height of the button (default is 0.2)
 * @param width The width of the button (default is 0.2)
 */
class GuiButton(text: String, x: Float = 0.0f, y: Float = 0, width: Float = 0.2f, height: Float = 0.2f, color: Vector3f = new Vector3f(1.0f, 1.0f, 1.0f), textScale: Int = 3) extends GuiElement {
    val verts: Array[Float] = Array(
        x, y, 0f, color.getX, color.getY, color.getZ, 1.0f,
        x + width, y + height, 0f, color.getX, color.getY, color.getZ, 1.0f,
        x, y + height, 0f, color.getX, color.getY, color.getZ, 1.0f,

        x, y, 0f, color.getX, color.getY, color.getZ, 1.0f,
        x + width, y, 0f, color.getX, color.getY, color.getZ, 1.0f,
        x + width, y + height, 0f, color.getX, color.getY, color.getZ, 1.0f
    ) //new Array[Float](72)
    val fontModel = new FontModel(text, x + (width / 2) - (GLUtil.getFontWidth(text, textScale) / 2), y + (height / 2) - (GLUtil.getFontHeight(textScale) / 2), textScale)
    val dropShadow = new DropShadowModel(x - 30, y - 30, width + 30, height + 30, 3)
    val guiModel = new GuiModel(verts)

    var isHilighted: Boolean = false

    def containsPoint(x: Int, y: Int): Boolean = {
        x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height
    }

    def render(): Unit = {
        GLUtil.checkGLError()
        dropShadow.render()
        fontModel.render()
//        guiModel.render(isHilighted, isDown)
    }

    def destroy(): Unit = {
//        dropShadow.destroy()
        guiModel.destroy()
        fontModel.destroy()
    }

}
