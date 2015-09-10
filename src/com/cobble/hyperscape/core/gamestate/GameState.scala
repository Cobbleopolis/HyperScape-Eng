package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.registry.ShaderRegistry

trait GameState {

    val defaultShader: String

    /**
     * Called when the game switches to the game state.
     */
    def changeTo(): Unit = {
        ShaderRegistry.bindShader(defaultShader)
    }

    /**
     * Called when the game ticks
     */
    def tick(): Unit = {

    }

    /**
     * Called when the game renders with a perspective matrix. The correct matrix has already been uploaded. This renders before the orthographic render.
     */
    def perspectiveRender(): Unit = {

    }

    /**
     * Called when the game renders with an orthographic matrix. The correct matrix has already been uploaded. This renders after the perspective render.
     */
    def orthographicRender(): Unit = {

    }

    /**
     * Called when the game switches to a different state or closes.
     */
    def destroy(): Unit = {

    }
}
