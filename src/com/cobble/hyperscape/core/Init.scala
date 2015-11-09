package com.cobble.hyperscape.core

import com.cobble.hyperscape.registry.{ModelRegistry, TextureRegistry, ShaderRegistry}

object Init {

	/**
	 * Loads all of the assets
	 */
	def loadAssets(): Unit = {
		loadShaders()
		loadTextures()
		loadModels()
		println("Done Loading")
	}

	/**
	 * Loads all of the shaders
	 */
	def loadShaders(): Unit = {
		println("Loading Shaders...")
		ShaderRegistry.loadShader("res/shader/terrain.vert", "res/shader/terrain.frag", "terrain")
		println("Finished Loading Shaders")
	}

	/**
	 * Loads all of the textures
	 */
	def loadTextures(): Unit = {
		println("Loading Textures...")
		TextureRegistry.loadTexture("res/blocks.png", "terrain")
		TextureRegistry.loadTexture("res/player.png", "player")
		println("Finished Loading Textures")
	}

	/**
	 * Loads all of the models
	 */
	def loadModels(): Unit = {
		println("Loading Models...")
		ModelRegistry.loadModel("res/model/cube.obj", "cube")
		ModelRegistry.loadModel("res/model/sphere.obj", "model")
		ModelRegistry.loadModel("res/model/pillar.obj", "pillar")
		ModelRegistry.loadModel("res/model/player.obj", "player")
		println("Finished Loading Models")
	}

}
