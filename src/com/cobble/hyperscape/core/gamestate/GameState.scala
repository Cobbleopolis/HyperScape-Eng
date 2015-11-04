package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.entity.{EntityPlayer, Entity}
import com.cobble.hyperscape.gui.GuiScreen
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.world.World
import org.lwjgl.util.vector.Vector3f

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
	player.rotateEntity(0f, 0f, Math.toRadians(180).asInstanceOf[Float])

    var world: World

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
        world.worldModel.modelMatrix.setIdentity()
        world.worldModel.modelMatrix.rotate(player.rotation.getX, new Vector3f(1.0f, 0.0f, 0.0f))
        world.worldModel.modelMatrix.rotate(player.rotation.getY, new Vector3f(0.0f, 1.0f, 0.0f))
        world.worldModel.modelMatrix.rotate(player.rotation.getZ, new Vector3f(0.0f, 0.0f, 1.0f))
        world.worldModel.modelMatrix.translate(player.position)
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
