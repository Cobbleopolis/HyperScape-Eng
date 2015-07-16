package com.cobble.hyperscape

import java.io._

import com.cobble.hyperscape.core.{HyperScape, Init}
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import org.lwjgl.{LWJGLException, Sys}
import org.lwjgl.opengl._

object Game {
    val WINDOW_TITLE = "HyperScape - Indev"
    val WIDTH = 1080
    val HEIGHT = 720

    var hyperScape: HyperScape = null

    var lastFrame: Long = 0

    var isFullscreen = false

    /**
     * Main method of the game
     * @param args Arguments passed to the game
     */
    def main(args: Array[String]): Unit = {
        setNatives()
        initGL()
        Init.loadAssets()
        ShaderRegistry.bindShader("mainMenu")
        //        TextureRegistry.bindTexture("terrain")

        lastFrame = getTime
        HyperScape.debug = args.contains("--debug")

        hyperScape = new HyperScape
        hyperScape.init()

        while (!Display.isCloseRequested) {
            if (Display.wasResized()) {
                HyperScape.mainCamera.updatePerspective()
                HyperScape.mainCamera.uploadPerspective()
                GL11.glViewport(0, 0, Display.getWidth, Display.getHeight)
            }
            hyperScape.tick()
            // Map the internal OpenGL coordinate system to the entire screen
            hyperScape.render()
            val err = GL11.glGetError()
            if (err != 0)
                println(err)
            Display.sync(60)
            Display.update()
        }
        hyperScape.destroy()
        ShaderRegistry.destroyAllShaders()
        TextureRegistry.destroyAllTextures()
        Display.destroy()

    }

    /**
     * Sets the natives based on the operating system
     */
    def setNatives(): Unit = {
        val os = System.getProperty("os.name").toLowerCase
        var suffix = ""
        if (os.contains("win")) {
            suffix = "windows"
        } else if (os.contains("mac")) {
            suffix = "macosx"
        } else {
            suffix = "linux"
        }
        //        val nativePath = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "lwjgl" + File.separator + "native" + File.separator + suffix
        val nativePath = System.getProperty("user.dir") + File.separator + "native" + File.separator + suffix
        System.setProperty("org.lwjgl.librarypath", nativePath)
    }

    /**
     * Initializes OpenGL
     */
    def initGL(): Unit = {
        // Setup an OpenGL context with API version 3.3
        try {
            val pixelFormat = new PixelFormat()
            val contextAtrributes = new ContextAttribs(3, 3)
                .withForwardCompatible(true)
                .withProfileCore(true)

            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT))
            Display.setTitle(WINDOW_TITLE)
            Display.setResizable(true)
            Display.create(pixelFormat, contextAtrributes)
        } catch {
            case e: LWJGLException => e.printStackTrace(); System.exit(-1)
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT)
        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glClearColor(0.67058823529411764705882352941176f, 0.8078431372549019607843137254902f, 1f, 1f)
        GL11.glEnable(GL11.GL_DEPTH_TEST)

        //        GL11.glEnable(GL11.GL_BLEND)
        //        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    }

    /**
     * Get the time in milliseconds
     * @return The system time in milliseconds
     */
    def getTime: Long = {
        (Sys.getTime * 1000) / Sys.getTimerResolution
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     * @return milliseconds passed since last frame
     */
    def getDelta: Int = {
        val time = getTime
        val delta = (time - lastFrame).toInt
        lastFrame = time
        delta
    }
}
