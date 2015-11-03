package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.gui.Guis
import com.cobble.hyperscape.render.RenderModel
import com.cobble.hyperscape.world.{WorldMainMenu, World}

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
    //    override var currentGui: GuiScreen = null
    var count: Int = 60
    var model: RenderModel = null

    var offset: Float = 0.0f

    var vel: Float = 0.01f

    val world: World = new WorldMainMenu

    override def changeTo(): Unit = {
        super.changeTo()
        model = new RenderModel(modelArray)
        changeGui(Guis.guiMainMenu)
        //        ShaderRegistry.getShader("font").inputs.foreach(input => println("(" + input._1 + ", " + input._2 + ")"))
        //        println("Main Menu " + (currentGui == null))
    }

    override def tick(): Unit = {
        //        count -= 1
        //        if (count == 0) {
        //            count = 60
        //            currentGui.buttonList.head.isHilighted = !currentGui.buttonList.head.isHilighted
        //        }
    }

    override def perspectiveRender(): Unit = {
        world.render()
    }
    override def orthographicRender(): Unit = {
        //        HyperScape.mainCamera.view.translate(new Vector3f(0.0f, 0.0f, offset))
        //        ShaderRegistry.bindShader("terrain")
        //        TextureRegistry.bindTexture("terrain")
        //        HyperScape.mainCamera.uploadPerspective()
        //        HyperScape.mainCamera.uploadView()
        //        val modelMatrix = new Matrix4f()
        //        modelMatrix.translate(new Vector3f(0, 0, -1))
        //        GLUtil.uploadModelMatrix(modelMatrix)
        //                model.render()
        currentGui.render()
        //        if (offset > 1.5 || offset < -1) vel = -vel
        //        offset -= vel
    }

    override def destroy(): Unit = {
        super.destroy()
        model.destroy()
    }

}
