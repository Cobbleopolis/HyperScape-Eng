package com.cobble.hyperscape.core

import com.cobble.hyperscape.event.EventListenerMouseGui
import com.cobble.hyperscape.registry.{EventRegistry, ShaderRegistry, TextureRegistry}

object Init {

    /**
     * Loads all of the assets
     */
    def loadAssets(): Unit = {
        loadShaders()
        loadTextures()
        registerEventListeners()
        //        loadModels()
        println("Done Loading")
    }


    /**
     * Loads all of the shaders
     */
    def loadShaders(): Unit = {
        println("Loading Shaders...")
        ShaderRegistry.loadShader("res/shader/terrain.vert", "res/shader/terrain.frag", "terrain", Array((0, "in_Position"), (1, "in_TextureCoord"), (2, "in_Normal")))
        ShaderRegistry.loadShader("res/shader/mainMenu.vert", "res/shader/mainMenu.frag", "mainMenu", Array((0, "in_Position"), (1, "in_TextureCoord"), (2, "in_Normal")))
        ShaderRegistry.loadShader("res/shader/gui.vert", "res/shader/gui.frag", "gui", Array((0, "in_Position"), (1, "in_Color")))
        ShaderRegistry.loadShader("res/shader/font.vert", "res/shader/font.frag", "font", Array((0, "in_Position"), (1, "in_TextureCoord")))
        //        ShaderRegistry.loadShader("res/shader/terrain.vert", "res/shader/terrain.frag", "font", Array((0, "in_Position"), (1, "in_TextureCoord"), (2, "in_Normal")))
        ShaderRegistry.printAllShaders()
        println("Finished Loading Shaders")
    }

    /**
     * Loads all of the textures
     */
    def loadTextures(): Unit = {
        println("Loading Textures...")
        TextureRegistry.loadTexture("res/blocks.png", "terrain")
        TextureRegistry.loadTexture("res/player.png", "player")
        TextureRegistry.loadTexture("res/gui.png", "gui")
        TextureRegistry.loadTexture("res/font3.png", "font")
        println("Finished Loading Textures")
    }

    /**
     * Registers all of the event Listeners
     */
    def registerEventListeners(): Unit = {
        println("Registering Event Listeners...")
        EventRegistry.registerMouseEventListener(new EventListenerMouseGui)
        println("Finished Registering Event Listeners")
    }
}
