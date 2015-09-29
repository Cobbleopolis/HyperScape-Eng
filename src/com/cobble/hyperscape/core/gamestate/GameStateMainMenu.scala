package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.gui.{GuiMainMenu, GuiScreen, GuiButton}
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.render.RenderModel
import org.lwjgl.opengl.{GL11, GL20}
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class GameStateMainMenu extends GameState {

    val defaultShader: String = "terrain"

    var gui: GuiScreen= null

    val modelArray: Array[Float] = Array(
        -1.0f, -1.0f, 0.0f,      0.0f, 1.0f,      0.0f, 0.0f, 1.0f,
        1.0f, -1.0f, 0.0f,       1.0f, 1.0f,      0.0f, 0.0f, 1.0f,
        1.0f,  1.0f, 0.0f,       1.0f, 0.0f,      0.0f, 0.0f, 1.0f,

        -1.0f, -1.0f, 0.0f,      0.0f, 1.0f,      0.0f, 0.0f, 1.0f,
        1.0f,  1.0f,  0.0f,      1.0f, 0.0f,      0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f,  0.0f,      0.0f, 0.0f,      0.0f, 0.0f, 1.0f
    )

    var model: RenderModel = null

    var offset: Float = 0.0f

    var vel: Float = 0.01f

    override def changeTo(): Unit = {
        super.changeTo()
        model = new RenderModel(modelArray)
        gui = new GuiMainMenu
        gui.initGui()
    }

    override def tick(): Unit = {}

    override def orthographicRender(): Unit = {
//        HyperScape.mainCamera.view.translate(new Vector3f(0.0f, 0.0f, offset))
        ShaderRegistry.bindShader("terrain")
        TextureRegistry.bindTexture("terrain")
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.mainCamera.uploadView()
        val modelMatrix = new Matrix4f()
        modelMatrix.translate(new Vector3f(0, 0, -1))
        HyperScape.uploadBuffer.clear()
        modelMatrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
        model.render()
//        gui.render()
//        if (offset > 1.5 || offset < -1) vel = -vel
//        offset -= vel
    }

    override def destroy(): Unit = {
        model.destroy()
        gui.destroy()
    }
    
}
