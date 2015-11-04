package com.cobble.hyperscape.gui

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.reference.Reference

object Guis {

	HyperScape.mainCamera.mode = Reference.Camera.ORTHOGRAPHIC_MODE
	HyperScape.mainCamera.updatePerspective()

	val guiMainMenu = new GuiMainMenu

	val guiOptions = new GuiOptions
}
