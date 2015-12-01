package com.cobble.hyperscape.core.gamestate

import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.GameStateRegistry

object GameStates {

    val mainMenu = new GameStateMainMenu

    /**
     * Registers Game States
     */
    def registerGameStates(): Unit = {
        println("Registering Game States...")
        GameStateRegistry.registerGameState(mainMenu, Reference.GameState.MAIN_MENU)
        println("Finished Registering Game States...")
    }

}
