package com.cobble.hyperscape.core

import com.cobble.hyperscape.Game
import com.cobble.hyperscape.core.gamestate.{GameStates, GameState}
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{GameStateRegistry, TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.render.{RenderModel, Camera}
import com.cobble.hyperscape.util.MathUtil
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL20, GL11}
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class HyperScape {


    def init(): Unit = {
        GameStates.registerGameStates()
        changeState(Reference.GameState.MAIN_MENU)
    }

    def changeState(newState: String): Unit = {
        if (HyperScape.currentGameState != null) HyperScape.currentGameState.destroy()
        HyperScape.currentGameState = GameStateRegistry.getGameState(newState)
        HyperScape.currentGameState.changeTo()
    }

    def tick(): Unit = {
        HyperScape.currentGameState.tick()
    }

    def render(): Unit = {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
        HyperScape.mainCamera.mode = Reference.Camera.PERSPECTIVE_MODE
        HyperScape.mainCamera.updatePerspective()
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.currentGameState.perspectiveRender()

        HyperScape.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
        HyperScape.mainCamera.fov = 160
        HyperScape.mainCamera.updatePerspective()
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.currentGameState.orthographicRender()
    }

    def destroy(): Unit = {
        HyperScape.currentGameState.destroy()
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