package com.cobble.hyperscape.core

import com.cobble.hyperscape.core.GameState.GameState
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.render.{RenderModel, Camera}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL20, GL11}
import org.lwjgl.util.vector.Matrix4f

class HyperScape {
    val modelArray: Array[Float] = Array(
        -1.0f, -1.0f, -1.0f,      0.0f, 1.0f,      0.0f, 0.0f, 1.0f,
         1.0f, -1.0f, -1.0f,      1.0f, 1.0f,      0.0f, 0.0f, 1.0f,
         1.0f,  1.0f, -1.0f,      1.0f, 0.0f,      0.0f, 0.0f, 1.0f
    )

    var model: RenderModel = null

    def init(): Unit = {
        changeState(GameState.MAIN_MENU)
        HyperScape.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
        HyperScape.mainCamera.fov = 160
        HyperScape.mainCamera.updatePerspective()
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.mainCamera.uploadView()
        model = new RenderModel(modelArray)
    }

    def changeState(newState: GameState): Unit = {
        HyperScape.currentGameState = newState
    }

    def tick(): Unit = {
        ShaderRegistry.bindShader("mainMenu")
        TextureRegistry.bindTexture("terrain")
    }

    def render(): Unit = {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
        var modelMatrix = new Matrix4f()
        HyperScape.uploadBuffer.clear()
        modelMatrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
        model.render()
    }

    def destroy(): Unit = {
        model.destroy()
    }
}

object HyperScape {
    /** The buffer used to upload to the GPU. Max is 64000000 floats */
    val uploadBuffer = BufferUtils.createFloatBuffer(64000000)

    var shaderSelector = 0

    var currentGameState: GameState = null

    var lines: Boolean = false

    /** The Camera that renders they game */
    val mainCamera = new Camera

    var debug = false
}