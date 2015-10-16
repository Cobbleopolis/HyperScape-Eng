package com.cobble.hyperscape.core

import com.cobble.hyperscape.core.gamestate.{GameState, GameStates}
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{EventRegistry, GameStateRegistry}
import com.cobble.hyperscape.render.Camera
import org.lwjgl.BufferUtils
import org.lwjgl.input.{Keyboard, Mouse}
import org.lwjgl.opengl.{Display, GL11}

class HyperScape {

    var prevMouseState: Int = -1

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
        while (Keyboard.next()) {
            val character = Keyboard.getEventCharacter
            val characterVal = Keyboard.getEventKey
            EventRegistry.getButtonEventListeners.foreach(eventListener => {
                eventListener.onButtonTypingHold(character, characterVal)
            })
        }

        if (Keyboard.getEventKeyState) {
            val character = Keyboard.getEventCharacter
            val characterVal = Keyboard.getEventKey
            EventRegistry.getButtonEventListeners.foreach(eventListener => {
                eventListener.onButtonHold(character, characterVal)
            })
        }

        while (Mouse.next()) {
            val x = Mouse.getX - (Display.getWidth / 2)
            val y = Mouse.getY - (Display.getHeight / 2)
            val dx = Mouse.getDX
            val dy = Mouse.getDY
            val mouseState = Mouse.getEventButton

            EventRegistry.getMouseListeners.foreach(eventListener => {
                eventListener.onMouseMove(x, y, dx, dy)
            })

            if (Mouse.getEventButtonState) {
                if (Mouse.getEventButton != -1) {
                    EventRegistry.getMouseListeners.foreach(eventListener => {
                        eventListener.onMouseDown(x, y, dx, dy, mouseState)
                    })
                    HyperScape.anyMouseButtonDown = true
                }
            }else {
                if (Mouse.getEventButton != -1) {
                    EventRegistry.getMouseListeners.foreach(eventListener => {
                        eventListener.onMouseUp(x, y, dx, dy, mouseState)
                    })
                    HyperScape.anyMouseButtonDown = false
                }
            }

        }


        if (HyperScape.anyMouseButtonDown) {
            val x = Mouse.getX - (Display.getWidth / 2)
            val y = Mouse.getY - (Display.getHeight / 2)
            val dx = Mouse.getDX
            val dy = Mouse.getDY
            val mouseState = Mouse.getEventButton
            EventRegistry.getMouseListeners.foreach(eventListener => {
                eventListener.mouseDown(x, y, dx, dy, mouseState)
            })
        }
    }

    def render(): Unit = {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
        HyperScape.mainCamera.mode = Reference.Camera.PERSPECTIVE_MODE
        HyperScape.mainCamera.updatePerspective()
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.currentGameState.perspectiveRender()

        //        GL11.glDisable(GL11.GL_DEPTH_TEST)
        HyperScape.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
        //        HyperScape.mainCamera.fov = 160
        HyperScape.mainCamera.updatePerspective()
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.currentGameState.orthographicRender()
        //        GL11.glEnable(GL11.GL_DEPTH_TEST)
    }

    def destroy(): Unit = {
        HyperScape.currentGameState.destroy()
    }
}

object HyperScape {
    /** The buffer used to upload to the GPU. Max is 1048576 floats */
    val uploadBuffer = BufferUtils.createFloatBuffer(1048576)
    /** The Camera that renders they game */
    val mainCamera = new Camera
    /** The current game state that the game is in */
    var currentGameState: GameState = null
    var lines: Boolean = false
    var debug = false
    var anyMouseButtonDown = false
}