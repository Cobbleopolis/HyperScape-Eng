package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.gui.{GuiMainMenu, GuiScreen, GuiButton}
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.render.RenderModel
import org.lwjgl.opengl.{GL11, GL20}
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class GameStateMainMenu extends GameState {

    var gui: GuiScreen= null

    val modelArray: Array[Float] = Array(
        -1.0f, -1.0f, 0.0f,      0.0f, 1.0f,      0.0f, 0.0f, 1.0f,
        1.0f, -1.0f, 0.0f,      1.0f, 1.0f,      0.0f, 0.0f, 1.0f,
        1.0f,  1.0f, 0.0f,      1.0f, 0.0f,      0.0f, 0.0f, 1.0f,

        -1.0f, -1.0f, 0.0f,      0.0f, 1.0f,      0.0f, 0.0f, 1.0f,
        1.0f,  1.0f, 0.0f,      1.0f, 0.0f,      0.0f, 0.0f, 1.0f,
        -1.0f,  1.0f, 0.0f,      0.0f, 0.0f,      0.0f, 0.0f, 1.0f
    )

    var model: RenderModel = null

    override def changeTo(): Unit = {
        model = new RenderModel(modelArray)
        gui = new GuiMainMenu
        gui.initGui()
    }

    override def tick(): Unit = {
        ShaderRegistry.bindShader("gui")
        TextureRegistry.bindTexture("terrain")
    }

    override def orthographicRender(): Unit = {
//        HyperScape.mainCamera.view.translate(new Vector3f(.1F, 0F, 0F))
//        HyperScape.mainCamera.uploadView()
//        val modelMatrix = new Matrix4f()
//        modelMatrix.translate(new Vector3f(0, 0, -1))
//        HyperScape.uploadBuffer.clear()
//        modelMatrix.store(HyperScape.uploadBuffer)
//        HyperScape.uploadBuffer.flip()
//        var modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
//        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
        gui.render()
    }

    override def destroy(): Unit = {
        model.destroy()
        gui.destroy()
    }
    
}
