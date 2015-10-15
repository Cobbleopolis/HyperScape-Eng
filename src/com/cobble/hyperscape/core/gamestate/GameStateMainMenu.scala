package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.gui.{GuiMainMenu, GuiScreen}
import com.cobble.hyperscape.registry.{ShaderRegistry, TextureRegistry}
import com.cobble.hyperscape.render.RenderModel
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

class GameStateMainMenu extends GameState {

    val defaultShader: String = "terrain"
    val size: Float = 360f
    val modelArray: Array[Float] = Array(
        -size, -size, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
        size, -size, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
        size, size, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,

        -size, -size, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
        size, size, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
        -size, size, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
    )
    var gui: GuiScreen = null
    var count: Int = 60
    var model: RenderModel = null

    var offset: Float = 0.0f

    var vel: Float = 0.01f

    override def changeTo(): Unit = {
        super.changeTo()
        model = new RenderModel(modelArray)
        gui = new GuiMainMenu
        gui.initGui()
    }

    override def tick(): Unit = {
        count -= 1
        if (count == 0) {
            count = 60
            gui.buttonList.head.isHilighted = !gui.buttonList.head.isHilighted
        }
    }

    override def orthographicRender(): Unit = {
        //        HyperScape.mainCamera.view.translate(new Vector3f(0.0f, 0.0f, offset))
        ShaderRegistry.bindShader("terrain")
        TextureRegistry.bindTexture("terrain")
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.mainCamera.uploadView()
        val modelMatrix = new Matrix4f()
        modelMatrix.translate(new Vector3f(0, 0, -1))
        GLUtil.uploadModelMatrix(modelMatrix)
        //        model.render()
        gui.render()
        //        if (offset > 1.5 || offset < -1) vel = -vel
        //        offset -= vel
    }

    override def destroy(): Unit = {
        model.destroy()
        gui.destroy()
    }
    
}
