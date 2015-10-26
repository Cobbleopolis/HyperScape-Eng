package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.util.vector.Vector3f

class GuiMainMenu extends GuiScreen("Main Menu") {

//    val woah = new GuiButton("WOAH A NEW BUTTON!", -330f, 0f, 300f, 300f, new Vector3f(0.141f, 0.317f, 0.709f))

    elementList = elementList :+ new GuiButton("New Game", -330f, 0f, 660f, 75f, new Vector3f(1f, 0f, 0f))
    elementList = elementList :+ new GuiButton("Options", -330f, -300f, 660f, 75f, new Vector3f(1f, 0f, 0f))
//    elementList = elementList :+ new GuiButton("Hello World!", 0f, 0f, 300f, 300f, new Vector3f(0.4f, 0.0f, 0.3f))

    override def onClick(elementIndex: Int): Unit ={
        if (elementIndex == 1)
            HyperScape.currentGameState.changeGui(Guis.guiOptions)
//        if (elementIndex == 1)
//            if (elementList.size < 3)
//                elementList = elementList :+ woah
//            else
//                elementList = elementList.dropRight(1)
    }

    //    override def render(): Unit = {
    //        guiButton.render()
    //    }
}
