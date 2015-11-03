package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.entity.{EntityPlayer, Entity}
import com.cobble.hyperscape.gui.GuiScreen
import com.cobble.hyperscape.registry.ShaderRegistry

trait GameState {

    /**
     * The default shader used by the shader when it is switched to.
     */
    val defaultShader: String

    /**
     * The current gui used by the game state. null by default and when there is no gui
     */
    var currentGui: GuiScreen = null

    var player: EntityPlayer = new EntityPlayer

    /**
     * Called when the game switches to the game state.
     */
    def changeTo(): Unit = {
        ShaderRegistry.bindShader(defaultShader)
    }

    /**
     * Changes the current gui
     * @param gui The gui to change to (can be null).
     */
    def changeGui(gui: GuiScreen): Unit = {
//        if (currentGui != null) currentGui.destroy()
        currentGui = gui
//        if (currentGui != null) currentGui.initGui()
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
        if (currentGui != null) currentGui.destroy()
    }
}
