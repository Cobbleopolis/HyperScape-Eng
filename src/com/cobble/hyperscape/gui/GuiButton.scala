package com.cobble.hyperscape.gui

import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.render.{RenderModel, GuiModel}
import org.lwjgl.opengl.{GL30, GL15, GL20}

class GuiButton(id: Int, text: String, x: Float = 0.2f, y: Float = 0, height: Float = 0.2f, width: Float = 0.2f) {
    val verts: Array[Float] = Array(
        x,          y,         0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x,          y + width, 0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x - height, y,         0.0f,      0.4f, 0.0f, 0.3f, 1.0f,

        x,          y + width, 0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x - height, y + width, 0.0f,      0.4f, 0.0f, 0.3f, 1.0f,
        x - height, y,         0.0f,      0.4f, 0.0f, 0.3f, 1.0f
    ) //new Array[Float](72)

    val guiModel = new GuiModel(verts)

    def render(): Unit = {
        ShaderRegistry.bindShader("gui")
        guiModel.render()
    }

    def destroy(): Unit = {
        guiModel.destroy()
    }

}
