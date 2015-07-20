package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.render.RenderModel
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class GameStateMainMenu extends GameState {

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
    }

    override def tick(): Unit = {
        ShaderRegistry.bindShader("mainMenu")
        TextureRegistry.bindTexture("terrain")
    }

    override def orthographicRender(): Unit = {
        HyperScape.mainCamera.uploadView()
        var modelMatrix = new Matrix4f()
        modelMatrix.translate(new Vector3f(0, 0, -1))
        HyperScape.uploadBuffer.clear()
        modelMatrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
        model.render()
    }

    override def destroy(): Unit = {
        model.destroy()
    }
    
}
