package com.cobble.hyperscape.core

import com.cobble.hyperscape.registry.{ShaderRegistry, TextureRegistry}

object Init {

    /**
     * Loads all of the assets
     */
    def loadAssets(): Unit = {
        loadShaders()
        loadTextures()
//        loadModels()
        println("Done Loading")
    }


    /**
     * Loads all of the shaders
     */
    def loadShaders(): Unit = {
        println("Loading Shaders...")
        ShaderRegistry.loadShader("res/shader/mainMenu.vert", "res/shader/mainMenu.frag", "mainMenu")
        ShaderRegistry.loadShader("res/shader/terrain.vert", "res/shader/terrain.frag", "terrain")
        ShaderRegistry.loadShader("res/shader/gui.vert", "res/shader/gui.frag", "gui")
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
        println("Finished Loading Textures")
    }
}
