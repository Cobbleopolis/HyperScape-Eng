package com.cobble.hyperscape.core

import com.cobble.hyperscape.core.gamestate.{GameState, GameStates}
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.GameStateRegistry
import com.cobble.hyperscape.render.Camera
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11

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

        GL11.glDisable(GL11.GL_DEPTH_TEST)
        HyperScape.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
//        HyperScape.mainCamera.fov = 160
        HyperScape.mainCamera.updatePerspective()
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.currentGameState.orthographicRender()
        GL11.glEnable(GL11.GL_DEPTH_TEST)
    }

    def destroy(): Unit = {
        HyperScape.currentGameState.destroy()
    }
}

object HyperScape {
    /** The buffer used to upload to the GPU. Max is 1048576 floats */
    val uploadBuffer = BufferUtils.createFloatBuffer(1048576)

    /** The current game state that the game is in */
    var currentGameState: GameState = null

    var lines: Boolean = false

    /** The Camera that renders they game */
    val mainCamera = new Camera

    var debug = false
}