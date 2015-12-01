package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.world.{World, WorldMainMenu}

class GameStateMainMenu extends GameState {

    val defaultShader: String = "terrain"
    val size: Float = 360f
    var count: Int = 60

    var offset: Float = 0.0f

    var vel: Float = 0.01f

    var world: World = new WorldMainMenu

}
