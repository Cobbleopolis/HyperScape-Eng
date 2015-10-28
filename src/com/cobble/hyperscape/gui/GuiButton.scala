package com.cobble.hyperscape.gui

import com.cobble.hyperscape.render.{DropShadowModel, FontModel, GuiModel}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.{Vector4f, Vector3f}

/**
 * Used by GUI's to render buttons
 * @param text The original text displayed on the button
 * @param x The x location of the button (default is 0)
 * @param y The y location of the button (default is 0)
 * @param height The height of the button (default is 0.2)
 * @param width The width of the button (default is 0.2)
 */
class GuiButton(text: String, x: Float = 0.0f, y: Float = 0, width: Float = 0.2f, height: Float = 0.2f, color: Vector4f = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), textScale: Int = 3) extends GuiElement {
    val verts: Array[Float] = Array(
        x, y, 0f, color.getX, color.getY, color.getZ, color.getW,
        x + width, y + height, 0f, color.getX, color.getY, color.getZ, color.getW,
        x, y + height, 0f, color.getX, color.getY, color.getZ, color.getW,

        x, y, 0f, color.getX, color.getY, color.getZ, color.getW,
        x + width, y, 0f, color.getX, color.getY, color.getZ, color.getW,
        x + width, y + height, 0f, color.getX, color.getY, color.getZ, color.getW
    ) //new Array[Float](72)

//    var displayText: String = ""
//    var fontModel: FontModel = null
//    changeText(text)

    var displayText: String = text
    var fontModel = new FontModel(displayText, x + (width / 2) - (GLUtil.getFontWidth(displayText, textScale) / 2), y + (height / 2) - (GLUtil.getFontHeight(textScale) / 2), textScale)

    val dropShadow = new DropShadowModel(x - 10, y - 10, width + 20, height + 20, 3)
    val guiModel = new GuiModel(verts)

    var isHilighted: Boolean = false

    def containsPoint(mouseX: Int, mouseY: Int): Boolean =
        mouseX >= x && mouseX <= x + width &&
        mouseY >= y && mouseY <= y + height

    def render(): Unit = {
        dropShadow.render()
        fontModel.render()
        guiModel.render(isHilighted, isDown)
    }

    def destroy(): Unit = {
        dropShadow.destroy()
        guiModel.destroy()
        fontModel.destroy()
    }

    /**
     * Changes the button text
     * Note: This will not change the size og the button. If the button's size needs to change, create a new one and destroy this one.
     * @param text The new text to be displayed by the button
     */
    def changeText(text: String): Unit = {
        if (fontModel != null) fontModel.destroy()
        displayText = text
        fontModel = new FontModel(displayText, x + (width / 2) - (GLUtil.getFontWidth(displayText, textScale) / 2), y + (height / 2) - (GLUtil.getFontHeight(textScale) / 2), textScale)
    }

}
